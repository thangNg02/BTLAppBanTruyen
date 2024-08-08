package com.example.btlAndroidG13.my_interface;

public interface GioHangView {
    void OnSucess();

    void OnFail();

    void getDataSanPham(String id, String idtruyen, String tentruyen, Long giatien, String hinhanh, String theloai, String mota, Long soluong, String ngayxuatban, Long type, String trongluong);
}
