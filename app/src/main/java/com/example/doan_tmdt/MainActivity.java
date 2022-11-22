package com.example.doan_tmdt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.doan_tmdt.fragment.BillFragment;
import com.example.doan_tmdt.fragment.CartFragment;
import com.example.doan_tmdt.fragment.HomeFragment;
import com.example.doan_tmdt.fragment.NotifyFragment;
import com.example.doan_tmdt.fragment.ProfileFragment;
import com.example.doan_tmdt.ultil.MyReceiver;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    public  static CountDownTimer countDownTimer;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    private MeowBottomNavigation bottomNavigation_Main;
    private HomeFragment homeFragment = new HomeFragment();
    private FirebaseFirestore db;
    // Check Internet
    private BroadcastReceiver MyReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        InitWidget();
        Init();
        MyReceiver = new MyReceiver();      // Check Internet
        broadcastIntent();                  // Check Internet

        Event();
        setProFile();

    }

    public void setProFile() {
        db = FirebaseFirestore.getInstance();
        if (homeFragment.getTvNameHome()==null){
            return;
        }
        if (homeFragment.getTvEmailHome()==null){
            return;
        }
        if (homeFragment.getCirAvatarHome() == null){
            return;
        }
        homeFragment.getTvEmailHome().setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        db.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Profile")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                    if(documentSnapshot!=null){
                        try{
                            homeFragment.getTvNameHome().setText(documentSnapshot.getString("hoten").length()>0 ?
                                    documentSnapshot.getString("hoten") : "");

                            if(documentSnapshot.getString("avatar").length()>0){
                                Picasso.get().load(documentSnapshot.getString("avatar").trim()).into(homeFragment.getCirAvatarHome());
                            }
                        }catch (Exception e){
                            Log.d("ERROR",e.getMessage());
                        }
                    }
                }
            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void InitWidget() {
        bottomNavigation_Main = findViewById(R.id.bnv_Main);
    }

    private void Init() {
        bottomNavigation_Main.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home));
        bottomNavigation_Main.add(new MeowBottomNavigation.Model(2, R.drawable.ic_profile));
        bottomNavigation_Main.add(new MeowBottomNavigation.Model(3, R.drawable.ic_cart));
        bottomNavigation_Main.add(new MeowBottomNavigation.Model(4, R.drawable.ic_notification));
        bottomNavigation_Main.add(new MeowBottomNavigation.Model(5, R.drawable.ic_contact));
        bottomNavigation_Main.setCount(4, "10");        // Hiển thông báo
        bottomNavigation_Main.show(1,true);
        replace(new HomeFragment());

    }

    private void replace(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

    private void Event() {
        bottomNavigation_Main.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        replace(new HomeFragment());
                        break;

                    case 2:
                        replace(new ProfileFragment());
                        break;

                    case 3:
                        replace(new CartFragment());
                        break;

                    case 4:
                        replace(new NotifyFragment());
                        break;

                    case 5:
                        replace(new BillFragment());
                        break;
                }
                return null;
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
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(MyReceiver, filter);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
//        editsearch.setText("");
        if(countDownTimer!=null){
            countDownTimer.start();
        }
    }

    // Check Internet
    public void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    // Check Internet
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }

}