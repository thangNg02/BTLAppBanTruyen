package com.example.btlAndroidG13.View;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.btlAndroidG13.MainActivity;
import com.example.btlAndroidG13.Models.Admin;
import com.example.btlAndroidG13.R;
import com.example.btlAndroidG13.View.Admin.AdminHomeActivity;
import com.example.btlAndroidG13.ultil.MyReceiver;
import com.example.btlAndroidG13.ultil.NetworkUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @noinspection ALL
 */
public class SignInActivity extends AppCompatActivity {

    private Button btnDangNhap;
    private EditText edtEmailUser, edtMatKhauUser;
    private TextView tvForgotPassword, btnDangKy;
    private CircleImageView cirDangNhapFacebook, cirDangNhapGoogle;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    private String sosanh;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    // Check Internet
    private BroadcastReceiver MyReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        InitWidget();
        MyReceiver = new MyReceiver();      // Check Internet
        broadcastIntent();                  // Check Internet
        if (NetworkUtil.isNetworkConnected(this)) {
            Event();

        }

    }

    private void Event() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                progressDialog.setContentView(R.layout.layout_loading);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                String strEmail = edtEmailUser.getText().toString().trim();
                String strMatKhau = edtMatKhauUser.getText().toString().trim();
                ArrayList<Admin> arrayList = new ArrayList<>();
                if(strEmail.length()>0 ){
                    if(strMatKhau.length()>0){
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            db.collection("Admin").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot q : queryDocumentSnapshots) {
                                        arrayList.add(new Admin(q.getString("username"), q.getString("pass")));
                                    }
                                    String tkadmin;
                                    String mkadmin;
                                    int tk = 0;
                                    for (int i = 0; i < arrayList.size(); i++) {
                                        Admin admin = arrayList.get(i);
                                        tkadmin = admin.getUsername();
                                        mkadmin = admin.getPassword();
                                        sosanh = tkadmin;
                                        if (strEmail.equals(tkadmin) && strMatKhau.equals(mkadmin)) {
                                            tk = 1;
                                            break;
                                        } else if (strEmail.equals("admin@gmail.com") && strMatKhau.equals("123456")){
                                            Intent intentAdmin = new Intent(SignInActivity.this, AdminHomeActivity.class);
                                            startActivity(intentAdmin);
                                            finish();
                                        } else {
                                            tk = 2;
                                        }
                                    }
                                    switch (tk){
                                        case 1:
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(SignInActivity.this, AdminHomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        case 2:
                                            FirebaseAuth auth = FirebaseAuth.getInstance();
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            auth.signInWithEmailAndPassword(strEmail, strMatKhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()){
                                                        FirebaseUser firebaseUser = auth.getCurrentUser();
                                                        if (firebaseUser != null) {
                                                            DatabaseReference userRef = database.getReference("Users").child(firebaseUser.getUid());

                                                        // Cập nhật trạng thái status thành "online"
                                                        userRef.child("status").setValue("online")
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {

                                                                        } else {

                                                                        }
                                                                    }
                                                                });
                                                    } else {
                                                        // Đăng nhập không thành công
                                                    }
                                                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                                                    startActivity(intent);
                                                    finishAffinity();
                                                    progressDialog.dismiss();
                                                } else if (!isEmailValid(strEmail) && !sosanh.equals(strEmail)) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(SignInActivity.this, "Email định dạng không đúng", Toast.LENGTH_SHORT).show();

                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(SignInActivity.this, "Tài khoản hoặc mật khẩu không đúng.\nVui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                        break;
                                }
                            }
                        });

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this, "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(SignInActivity.this, "Bạn chưa nhập Email", Toast.LENGTH_SHORT).show();
                }
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

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onBackPressed() {
        progressDialog.dismiss();
        // Sự kiện nhấn back 2 lần để out app
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Nhấn một lần nữa để thoát!",
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

        progressDialog = new ProgressDialog(this);
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

    private void checkUser() {
        //firebase khai báo
        firebaseAuth = FirebaseAuth.getInstance();
        //kiểm tra xem user đang là admin hay user
        //lấy user hiện tại
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        //Kiểm tra trong db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseUser.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String currentUserId = firebaseAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserRef = ref.child(currentUserId);

                        currentUserRef.child("status").setValue("online");
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });
    }
}