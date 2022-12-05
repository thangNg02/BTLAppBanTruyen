package com.example.doan_tmdt.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doan_tmdt.R;
import com.example.doan_tmdt.ultil.MyReceiver;
import com.example.doan_tmdt.ultil.NetworkUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText edtEmailForgot, edtPassForgot;
    private Button btnForgot;

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
        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stremail = edtEmailForgot.getText().toString().trim();
                String strpass = edtPassForgot.getText().toString().trim();
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
        });
    }

    private void InitWidget() {
        edtEmailForgot = findViewById(R.id.edt_email_forgot);
        edtPassForgot = findViewById(R.id.edt_pass_forgot);
        btnForgot = findViewById(R.id.btn_forgot);
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