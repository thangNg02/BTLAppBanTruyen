package com.example.doan_tmdt.View.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.doan_tmdt.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AdminChartBillActivity extends AppCompatActivity {
    private BarChart barChart;
    private PieChart pieChart;
    private  float dangxuly = 0,danggiaohang=0,giaohangthanhcong=0,huyhang=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_chart_bill);

//        // Define format
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss O yyyy");
//
//        // Date/time strings
//        String strEndDate = "Mon Jun 08 19:45:08 GMT+07:00 2020";
//        String strStartDate = "Sun Jan 22 19:45:08 GMT+07:00 2017";
//
//        // Define ZoneOffset
//        ZoneOffset zoneOffset = ZoneOffset.ofHours(6);
//
//        // Parse date/time strings into OffsetDateTime
//        OffsetDateTime startDate = OffsetDateTime.parse(strStartDate, formatter).withOffsetSameLocal(zoneOffset);
//        OffsetDateTime endDate = OffsetDateTime.parse(strEndDate, formatter).withOffsetSameLocal(zoneOffset);
//
//        // Calculate period between `startDate` and `endDate`
//        Period period = Period.between(startDate.toLocalDate(), endDate.toLocalDate());
//
//        // Display result
//        System.out.println(
//                period.getYears() + " years and " + period.getMonths() + " months since " + startDate.getYear());

        Toolbar toolbar = findViewById(R.id.toolbar);
        pieChart = findViewById(R.id.piechart);
        barChart = findViewById(R.id.barchart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Back");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1,10));
        barEntries.add(new BarEntry(2,20));
        barEntries.add(new BarEntry(3,30));
        barEntries.add(new BarEntry(4,40));
        barEntries.add(new BarEntry(5,50));
        barEntries.add(new BarEntry(6,60));
        barEntries.add(new BarEntry(7,70));
        barEntries.add(new BarEntry(8,80));
        barEntries.add(new BarEntry(9,90));
        barEntries.add(new BarEntry(10,100));
        barEntries.add(new BarEntry(11,110));
        barEntries.add(new BarEntry(12,120));

        // Initialize bar data set
        BarDataSet barDataSet = new BarDataSet(barEntries, "Doanh thu");
        // Set colors
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        // Hide draw value
        barDataSet.setDrawValues(false);
        // Set bar data
        barChart.setData(new BarData(barDataSet));
        // Set animation
        barChart.animateY(2000);
        barChart.getDescription().setText("Biểu đồ doanh thu");
        barChart.getDescription().setTextColor(Color.BLUE);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("HoaDon").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot q : queryDocumentSnapshots){
                    if(q.getLong("trangthai")==1){
                        dangxuly++;
                    }else if(q.getLong("trangthai")==2){
                        danggiaohang++;
                    }else if(q.getLong("trangthai")==3){
                        giaohangthanhcong++;
                    }else{
                        huyhang++;
                    }
                }
                pieEntries.add(new PieEntry(dangxuly,"Đang Xử Lý"));
                pieEntries.add(new PieEntry(danggiaohang,"Đang giao"));
                pieEntries.add(new PieEntry(giaohangthanhcong,"Đã giao"));
                pieEntries.add(new PieEntry(huyhang,"Đã hủy"));
                ArrayList<Integer> arrayList = new ArrayList<>();

                for(int color : ColorTemplate.MATERIAL_COLORS){
                    arrayList.add(color);
                }
                for(int color : ColorTemplate.VORDIPLOM_COLORS){
                    arrayList.add(color);
                }
                Legend l = pieChart.getLegend();

                l.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
                l.setOrientation(Legend.LegendOrientation.VERTICAL);
                PieDataSet pieDataSet = new PieDataSet(pieEntries,"Biểu Đồ Trạng Thái Đơn Hàng");
                pieDataSet.setColors(arrayList);
                PieData pieData = new PieData(pieDataSet);
                pieData.setDrawValues(true);
                pieData.setValueFormatter(new PercentFormatter());
                pieData.setValueTextSize(14);
                pieChart.setData(pieData);
                pieChart.invalidate();
                pieChart.setDrawEntryLabels(true);
                pieChart.setUsePercentValues(true);
                pieChart.setEntryLabelTextSize(12);
                pieChart.setEntryLabelColor(Color.WHITE);
                pieChart.animateXY(2000, 2000);
                pieChart.setCenterText("Trạng Thái Đơn Hàng");

                pieChart.getDescription().setEnabled(false);

            }
        });
    }
}