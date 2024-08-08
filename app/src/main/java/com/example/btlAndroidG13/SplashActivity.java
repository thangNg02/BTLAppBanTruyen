package com.example.btlAndroidG13;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlAndroidG13.View.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    ImageView logo;
    TextView name, own1, own2;
    Animation topAnim, bottomAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        logo = findViewById(R.id.logoimg);
        name = findViewById(R.id.logonameimg);
        own1 = findViewById(R.id.ownone);
        own2 = findViewById(R.id.owntwo);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        logo.setAnimation(topAnim);
        name.setAnimation(bottomAnim);
        own1.setAnimation(bottomAnim);
        own2.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            // check ktra đã đăng nhập vào thẳng vào Home
            public void run() {
                nextActivity();
            }

        }, 2000);
    }

    private void nextActivity() {
        // Check user đã login hay chưa, Firebase sẽ cung cấp 1 hàm lấy dc thông tin của user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Thực hiện check đối tượng user để biết người dùng đã login hay chưa
        if (user == null) {
            // Chưa login
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        } else {
            // Đã login
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}