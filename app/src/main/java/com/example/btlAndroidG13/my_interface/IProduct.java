package com.example.btlAndroidG13.my_interface;

public interface IProduct {
    void OnSucess();

    void OnFail();

    void getDataProduct(String id, String ten, Long gia, String hinhanh, String theloai, String mota, Long soluong, String ngayxuatban, Long type, String trongluong);
}
