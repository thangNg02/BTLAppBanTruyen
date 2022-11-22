package com.example.doan_tmdt.Models;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.doan_tmdt.my_interface.IGioHang;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class Giohang {
    private String id;
    private String id_product;
    private long soluong;

    private IGioHang callback;
    private FirebaseFirestore db;

    public Giohang() {
    }

    public Giohang(String id, String id_product, long soluong) {
        this.id = id;
        this.id_product = id_product;
        this.soluong = soluong;
    }

    public Giohang(String id_product, long soluong) {
        this.id_product = id_product;
        this.soluong = soluong;
    }

    public Giohang(IGioHang callback){
        this.callback = callback;
        db = FirebaseFirestore.getInstance();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public long getSoluong() {
        return soluong;
    }

    public void setSoluong(long soluong) {
        this.soluong = soluong;
    }

    //check giỏ hàng đúng id user
    public  void AddCart(String idsp, Long soluong){
        db.collection("GioHang").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("ALL").whereEqualTo("id_product",idsp).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()!=0){
                    if(queryDocumentSnapshots.size()>0){
                        for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                            long   soluong_sp = d.getLong("soluong");
                            //check so luong sp tăng lên 1
                            if(soluong_sp>0){
                                soluong_sp = soluong_sp + soluong;
                                db.collection("GioHang").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .collection("ALL").document(d.getId()).update("soluong",soluong_sp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            callback.OnSucess();
                                        }else{
                                            callback.OnFail();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }else{
                    Giohang hangModels = new Giohang(idsp,soluong);
                    db.collection("GioHang").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .collection("ALL").add(hangModels).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                callback.OnSucess();
                            }else {
                                callback.OnFail();
                            }
                        }
                    });
                }
            }
        });
    }

    public  void HandlegetDataGioHang(){
        db.collection("GioHang").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("ALL").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    for(QueryDocumentSnapshot s : queryDocumentSnapshots){
                        db.collection("SanPham").document(s.getString("id_product"))
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(@NonNull DocumentSnapshot d) {

                                callback.getDataSanPham(s.getId(),s.getString("id_product"),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),
                                        d.getString("mota"),
                                        s.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong"));


                            }
                        });
                    }
                }

            }
        });
    }
    public  void HandleDeleteDataGioHang(String id){
        db.collection("GioHang").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("ALL").document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    callback.OnSucess();
                }else {
                    callback.OnFail();
                }
            }
        });
    }

    public void HandleGetDataCTHD(String id) {

        db.collection("ChitietHoaDon").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("ALL").whereEqualTo("id_hoadon",id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    for(QueryDocumentSnapshot s : queryDocumentSnapshots){
                        Log.d("CHECKED",s.getString("id_product"));
                        db.collection("SanPham").document(s.getString("id_product"))
                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(@NonNull DocumentSnapshot d) {
                                callback.getDataSanPham(s.getId(),s.getString("id_product"),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),
                                        d.getString("mota"),
                                        s.getLong("soluong"),d.getString("hansudung"),
                                        1l,d.getString("trongluong"));
                            }
                        });
                    }
                }

            }
        });
    }
    public void HandleGetDataCTHD(String id,String uid) {

        if(uid!=null){
            db.collection("ChitietHoaDon").document(uid)
                    .collection("ALL").whereEqualTo("id_hoadon",id).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.size()>0){
                        for(QueryDocumentSnapshot s : queryDocumentSnapshots){
                            Log.d("CHECKED",s.getString("id_product"));
                            db.collection("SanPham").document(s.getString("id_product"))
                                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(@NonNull DocumentSnapshot d) {
                                    callback.getDataSanPham(s.getId(),s.getString("id_product"),d.getString("tensp"),
                                            d.getLong("giatien"),d.getString("hinhanh"),
                                            d.getString("loaisp"),
                                            d.getString("mota"),
                                            s.getLong("soluong"),d.getString("hansudung"),
                                            1l,d.getString("trongluong"));
                                }
                            });
                        }
                    }

                }
            });

        }else{

        }


    }


}
