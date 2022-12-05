package com.example.doan_tmdt.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doan_tmdt.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class NotifyFragment extends Fragment implements OnMapReadyCallback {
    private Toolbar toolbar;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();;
    private TextView txtdiachi,txtsdt,txtnoidung;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_notify, container, false);

        toolbar = v.findViewById(R.id.toolbar);
        txtdiachi = v.findViewById(R.id.txtdiachi);
        txtsdt = v.findViewById(R.id.txtsdt);
        txtnoidung = v.findViewById(R.id.txtnoidung);

        db.collection("ThongTinCuaHang").document("lzzQYfeQvhP1dZ0uTkud")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(@NonNull DocumentSnapshot documentSnapshot) {

                txtdiachi.setText("Địa chỉ : "+documentSnapshot.getString("diachi"));
                txtsdt.setText("Liên hệ : "+documentSnapshot.getString("sdt"));
                txtnoidung.setText("Nội Dung : "+documentSnapshot.getString("noidung"));


            }
        });

        // GG maps
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        db.collection("ThongTinCuaHang").document("lzzQYfeQvhP1dZ0uTkud").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String kinhdo = documentSnapshot.getString("kinhdo");
                String vido = documentSnapshot.getString("vido");
                String title = documentSnapshot.getString("title");
                String snippet = documentSnapshot.getString("snippet");
                //đọc vị trí gg map
                assert kinhdo != null;
                assert vido != null;
                Log.e("location", kinhdo +" - " + vido);
                LatLng latLng = new LatLng(Float.parseFloat(vido), Float.parseFloat(kinhdo)); // Longitude: Kinh độ, Latitude: vĩ độ trên ggmaps
                MarkerOptions options = new MarkerOptions();
                options.position(latLng);
                options.title(title);
                options.snippet(snippet); // option hiển thị thông tin vị trí lấy từ gg map
                googleMap.addMarker(options);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18)); // đọc camera
            }
        });
    }
}