package com.example.btlAndroidG13.View;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlAndroidG13.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText edtEmailForgot, edtPassForgot;
    private Button btnForgot;
    private ImageView ImageViewBackIc;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    // Check Internet
    private BroadcastReceiver MyReceiver = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        InitWidget();

//        MyReceiver = new MyReceiver();      // Check Internet
//        broadcastIntent();                  // Check Internet
//        if (NetworkUtil.isNetworkConnected(this)){
//
//        }
        Event();
    }

    private void Event() {

        ImageViewBackIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stremail = edtEmailForgot.getText().toString().trim();

                if (edtEmailForgot != null) {
                    Toast.makeText(ForgotPasswordActivity.this, "Vui lòng nhập Email", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.sendPasswordResetEmail(stremail)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ForgotPasswordActivity.this, "Đã gửi Email", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {

                                }
                            });
                }
            }
        });
    }

    private void InitWidget() {
        edtEmailForgot = findViewById(R.id.edt_email_forgot);
        btnForgot = findViewById(R.id.btn_forgot);
        ImageViewBackIc = findViewById(R.id.imV_back);
    }

    // Check Internet
//    public void broadcastIntent() {
//        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//
//    }
//
//    // Check Internet
//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(MyReceiver);
//    }
}