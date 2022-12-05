package com.example.doan_tmdt.View.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.doan_tmdt.R;
import com.example.doan_tmdt.View.SignInActivity;

public class AdminHomeActivity extends AppCompatActivity {

    private CardView cHoaDon, cThongKe, cAddProduct, cSignOut, cAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Init();
        Event();

    }

    private void Event() {
        cAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminUsersActivity.class);
                startActivity(intent);
            }
        });
        cHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminBillMainActivity.class);
                startActivity(intent);
            }
        });

        cThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminChartBillActivity.class);
                startActivity(intent);
            }
        });
        cAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminProductActivity.class);
                startActivity(intent);
            }
        });

        cSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Init() {
        cAddUser = findViewById(R.id.cAddUser);
        cHoaDon = findViewById(R.id.cHoaDon);
        cThongKe = findViewById(R.id.cThongKe);
        cAddProduct = findViewById(R.id.cAddProduct);
        cSignOut = findViewById(R.id.cSignOut);
    }
}