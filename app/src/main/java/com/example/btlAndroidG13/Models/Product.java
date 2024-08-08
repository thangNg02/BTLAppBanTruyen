package com.example.btlAndroidG13.Models;

import androidx.annotation.NonNull;

import com.example.btlAndroidG13.my_interface.IProduct;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;

public class Product implements Serializable{

    private  String id;
    private  String idtruyen;
    private  String tentruyen;
    private  long giatien;
    private  String hinhanh;
    private  String theloai;
    private  String mota;
    private  long soluong;
    private  String ngayxuatban;
    private  long type;
    private  String trongluong;

    private IProduct callback;
    private FirebaseFirestore db;

    public Product() {
    }

    public Product(IProduct callback) {
        this.callback=callback;
        db=FirebaseFirestore.getInstance();
    }

    public Product(String tentruyen) {
        this.tentruyen = tentruyen;
    }

    public Product(String id, String idtruyen, String tentruyen, long giatien, String hinhanh, String theloai, String mota, long soluong, String ngayxuatban, long type, String trongluong) {
        this.id = id;
        this.idtruyen = idtruyen;
        this.tentruyen = tentruyen;
        this.giatien = giatien;
        this.hinhanh = hinhanh;
        this.theloai = theloai;
        this.mota = mota;
        this.soluong = soluong;
        this.ngayxuatban = ngayxuatban;
        this.type = type;
        this.trongluong = trongluong;
    }

    public Product(String id, String tentruyen, long giatien, String hinhanh, String theloai, String mota, long soluong, String ngayxuatban, long type, String trongluong) {
        this.id = id;
        this.tentruyen = tentruyen;
        this.giatien = giatien;
        this.hinhanh = hinhanh;
        this.theloai = theloai;
        this.mota = mota;
        this.soluong = soluong;
        this.ngayxuatban = ngayxuatban;
        this.type = type;
        this.trongluong = trongluong;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdtruyen() {
        return idtruyen;
    }

    public void setIdtruyen(String idtruyen) {
        this.idtruyen = idtruyen;
    }

    public String getTentruyen() {
        return tentruyen;
    }

    public void setTentruyen(String tentruyen) {
        this.tentruyen = tentruyen;
    }

    public long getGiatien() {
        return giatien;
    }

    public void setGiatien(long giatien) {
        this.giatien = giatien;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTheloai() {
        return theloai;
    }

    public void setTheloai(String theloai) {
        this.theloai = theloai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public long getSoluong() {
        return soluong;
    }

    public void setSoluong(long soluong) {
        this.soluong = soluong;
    }

    public String getNgayxuatban() {
        return ngayxuatban;
    }

    public void setNgayxuatban(String ngayxuatban) {
        this.ngayxuatban = ngayxuatban;
    }

    public long getType() {
        return type;
    }

    public void setType(long type) {
        this.type = type;
    }

    public String getTrongluong() {
        return trongluong;
    }

    public void setTrongluong(String trongluong) {
        this.trongluong = trongluong;
    }

    public void HandleGetDataProduct(){
        db.collection("SanPham").
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                        callback.getDataProduct(d.getId(),d.getString("tentruyen"),
                                d.getLong("giatien"),d.getString("hinhanh"),
                                d.getString("theloai"),d.getString("mota"),
                                d.getLong("soluong"),d.getString("ngayxuatban"),
                                d.getLong("type"),d.getString("trongluong"));

                    }
                }

            }
        });
    }

    public void HandleGetWithIDProduct(String idproduct){
        db.collection("SanPham").document(idproduct).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot d) {
                callback.getDataProduct(idproduct,d.getString("tentruyen"),
                        d.getLong("giatien"),d.getString("hinhanh"),
                        d.getString("theloai"),d.getString("mota"),
                        d.getLong("soluong"),d.getString("ngayxuatban"),
                        d.getLong("type"),d.getString("trongluong"));

            }
        });
    }
}
