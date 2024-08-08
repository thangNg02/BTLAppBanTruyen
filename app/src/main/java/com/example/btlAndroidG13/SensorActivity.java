package com.example.btlAndroidG13;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    EditText name, time,giatrias;
    TextView giatri;
    Calendar calendar ;
    SimpleDateFormat simpleDateFormat;
    LinearLayout layoutsensor;
    String date;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SensorManager senseManage;
    private Sensor lightSensor ;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        layoutsensor = findViewById(R.id.layoutsensor);
        giatrias = findViewById(R.id.anhsang);
        time = findViewById(R.id.time);
        name = findViewById(R.id.name);
        giatri = findViewById(R.id.giatri);
        senseManage = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        lightSensor = senseManage.getDefaultSensor(Sensor.TYPE_LIGHT);
        db.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Profile").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot q : queryDocumentSnapshots){
                            name.setText(q.getString("hoten"));
                        }
                    }
                });
    }

    public void onSensorChanged(SensorEvent event) {
        double lightValue =0;
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            lightValue = Math.round(event.values[0] * Math.pow(10, 2)) / Math.pow(10, 2);

            giatri.setText("Giá trị ánh sáng: " + lightValue);
        }
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        if(lightValue > 200) {
            layoutsensor.setVisibility(View.VISIBLE);
            giatrias.setText(lightValue+"");
            date = simpleDateFormat.format(calendar.getTime());
            time.setText(date+"");
        }
        else layoutsensor.setVisibility(View.GONE);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lightSensor != null) {
            senseManage.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        senseManage.unregisterListener(this);
    }
}