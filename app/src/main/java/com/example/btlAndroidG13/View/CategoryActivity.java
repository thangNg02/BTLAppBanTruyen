package com.example.btlAndroidG13.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlAndroidG13.Adapter.CategoryAdapter;
import com.example.btlAndroidG13.Models.Product;
import com.example.btlAndroidG13.R;
import com.example.btlAndroidG13.my_interface.IClickOpenBottomSheet;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView tvCategory;
    private EditText edtSearch;
    private RecyclerView rcvCategory;
    private CategoryAdapter categoryAdapter;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Product product;
    private ArrayList<Product> arr_khac, arr_micay, arr_chaosup, arr_pizza, arr_sandwich, arr_douong, arr_lau, arr_doannhanh;
    private String[] categoryNames = {"Hài hước", "Hành động", "Nấu ăn", "Kiếm hiệp", "Xuyên không"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        InitWidget();
        Init();
        Event();
    }

    private void Init() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("loaiproduct", 1);
        // Sử dụng position để lấy tên thể loại từ mảng categoryNames
        String categoryName = categoryNames[position];
        tvCategory.setText(categoryName);
        Log.d("zxc", position + "");
        switch (position){
            case 0:
                firestore.collection("SanPham").
                        whereEqualTo("theloai","Hài hước").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_khac.add(new Product(d.getId(),d.getString("tentruyen"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("theloai"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("ngayxuatban"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_khac, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {

                                    product = arr_khac.get(position);
                                    SendData();
                                }
                            });
                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                            // Thêm đường phân cách giữa các dòng
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                            rcvCategory.addItemDecoration(itemDecoration);
                            rcvCategory.setAdapter(categoryAdapter);
                        }

                    }
                });
                break;
            case 1:
                firestore.collection("SanPham").
                        whereEqualTo("theloai","Hành động").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_micay.add(new Product(d.getId(),d.getString("tentruyen"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("theloai"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("ngayxuatban"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_micay, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
//                                    setBottomSheetDialog();
                                    product = arr_micay.get(position);
                                    SendData();
                                }
                            });
                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                            // Thêm đường phân cách giữa các dòng
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                            rcvCategory.addItemDecoration(itemDecoration);
                            rcvCategory.setAdapter(categoryAdapter);
                        }

                    }
                });
                break;
            case 2:
                firestore.collection("SanPham").
                        whereEqualTo("theloai","Nấu ăn").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_chaosup.add(new Product(d.getId(),d.getString("tentruyen"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("theloai"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("ngayxuatban"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_chaosup, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    product = arr_chaosup.get(position);
                                    SendData();
                                }
                            });
                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                            // Thêm đường phân cách giữa các dòng
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                            rcvCategory.addItemDecoration(itemDecoration);
                            rcvCategory.setAdapter(categoryAdapter);
                        }

                    }
                });
                break;
            case 3:
                firestore.collection("SanPham").
                        whereEqualTo("theloai","Kiếm hiệp").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_pizza.add(new Product(d.getId(),d.getString("tentruyen"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("theloai"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("ngayxuatban"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_pizza, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    product = arr_pizza.get(position);
                                    SendData();
                                }
                            });
                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                            // Thêm đường phân cách giữa các dòng
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                            rcvCategory.addItemDecoration(itemDecoration);
                            rcvCategory.setAdapter(categoryAdapter);
                        }

                    }
                });
                break;
            case 4:
                firestore.collection("SanPham").
                        whereEqualTo("theloai","Xuyên không").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_sandwich.add(new Product(d.getId(),d.getString("tentruyen"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("theloai"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("ngayxuatban"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_sandwich, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    product = arr_sandwich.get(position);
                                    SendData();
                                }
                            });
                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                            // Thêm đường phân cách giữa các dòng
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                            rcvCategory.addItemDecoration(itemDecoration);
                            rcvCategory.setAdapter(categoryAdapter);
                        }

                    }
                });
                break;
        }
    }

    private void SendData(){
        Intent intent = new Intent(CategoryActivity.this, DetailSPActivity.class);
        intent.putExtra("search", product);
        startActivity(intent);
    }

    private void Event() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void InitWidget() {
        imgBack = findViewById(R.id.img_back);
        tvCategory = findViewById(R.id.tv_category);
        edtSearch = findViewById(R.id.edt_search);
        rcvCategory = findViewById(R.id.rcv_category);

        arr_khac = new ArrayList<>();
        arr_micay = new ArrayList<>();
        arr_chaosup = new ArrayList<>();
        arr_pizza = new ArrayList<>();
        arr_sandwich = new ArrayList<>();
        arr_douong = new ArrayList<>();
        arr_lau = new ArrayList<>();
        arr_doannhanh = new ArrayList<>();

    }

}