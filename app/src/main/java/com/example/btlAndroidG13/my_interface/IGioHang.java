package com.example.btlAndroidG13.my_interface;

public interface IGioHang {
    void OnSucess();

    void OnFail();

    void getDataSanPham(String id, String id_tentruyen,String tentruyen, Long giatien, String hinhanh, String theloai, String mota, Long soluong, String ngayxuatban, Long type, String trongluong);
}