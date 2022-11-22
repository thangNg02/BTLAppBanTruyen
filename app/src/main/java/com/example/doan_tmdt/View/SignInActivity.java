package com.example.doan_tmdt.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_tmdt.MainActivity;
import com.example.doan_tmdt.Models.User;
import com.example.doan_tmdt.R;
import com.example.doan_tmdt.ultil.MyReceiver;
import com.example.doan_tmdt.ultil.NetworkUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignInActivity extends AppCompatActivity {

    private Button btnDangNhap, btnDangKy;
    private EditText edtEmailUser, edtMatKhauUser;
    private TextView tvForgotPassword;
    private CircleImageView cirDangNhapFacebook, cirDangNhapGoogle;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;

    // Check Internet
    private BroadcastReceiver MyReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        InitWidget();
        MyReceiver = new MyReceiver();      // Check Internet
        broadcastIntent();                  // Check Internet
        if (NetworkUtil.isNetworkConnected(this)){
            Event();
        }

    }

    private void Event() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = edtEmailUser.getText().toString().trim();
                String strMatKhau = edtMatKhauUser.getText().toString().trim();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(strEmail, strMatKhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                            User user = new User();
                            user.setIduser(auth.getUid());
                            user.setEmail(strEmail);
                            user.setPassword(strMatKhau);
                            finishAffinity();
                        } else {
                            Toast.makeText(SignInActivity.this, "Tài khoản hoặc mật khẩu không đúng.\nVui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUp = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intentSignUp);
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentForgot = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
                startActivity(intentForgot);
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Sự kiện nhấn back 2 lần để out app
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    private void InitWidget() {
        btnDangNhap = findViewById(R.id.btn_dangnhap);
        btnDangKy = findViewById(R.id.btn_dangky);
        edtEmailUser = findViewById(R.id.edt_email_user);
        edtMatKhauUser = findViewById(R.id.edt_matkhau_user);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        cirDangNhapFacebook = findViewById(R.id.cir_dangnhap_facebook);
        cirDangNhapGoogle = findViewById(R.id.cir_dangnhap_google);
    }

    // Check Internet
    public void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    // Check Internet
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(MyReceiver);
    }
}