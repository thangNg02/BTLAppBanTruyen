package com.example.doan_tmdt.Models;

public class LoaiProduct {
    private String tenloai;
    private String hinhanh;

    public LoaiProduct(String tenloai, String hinhanh) {
        this.tenloai = tenloai;
        this.hinhanh = hinhanh;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
