package com.example.btlAndroidG13.Presenter;

import com.example.btlAndroidG13.Models.Giohang;
import com.example.btlAndroidG13.Models.Product;
import com.example.btlAndroidG13.my_interface.GioHangView;
import com.example.btlAndroidG13.my_interface.IGioHang;

import java.util.ArrayList;

public class GioHangPresenter implements IGioHang {
    private Giohang giohang;
    private GioHangView callback;

    public GioHangPresenter(GioHangView callback) {
        this.callback = callback;
        giohang = new Giohang(this);
    }

    public  void AddCart(String idsp, Long soluong){
        giohang.AddCart(idsp, soluong);
    }

    public  void  HandlegetDataGioHang(){
        giohang.HandlegetDataGioHang();
    }
    public  void  HandleDeleteDataGioHang(String id){
        giohang.HandleDeleteDataGioHang(id);
    }

    public void HandleAddHoaDon(String ghichu, String ngaydat, String diachi, String hoten, String sdt, String phuongthuc, String tongtien, ArrayList<Product> arrayList) {
        giohang.HandleThanhToan(ghichu,ngaydat,diachi,hoten,sdt,phuongthuc,tongtien,arrayList);
    }
    @Override
    public void OnSucess() {
        callback.OnSucess();

    }

    @Override
    public void OnFail() {
        callback.OnFail();
    }

    @Override
    public void getDataSanPham(String id, String idtruyen,String tentruyen, Long giatien, String hinhanh, String theloai, String mota, Long soluong, String ngayxuatban, Long type, String trongluong) {
        callback.getDataSanPham(id,idtruyen,tentruyen,giatien,hinhanh,theloai,mota,soluong,ngayxuatban,type,trongluong);
    }

    public void HandlegetDataCTHD(String id) {
        giohang.HandleGetDataCTHD(id);

    }
    public void HandlegetDataCTHD(String id,String uid) {
        giohang.HandleGetDataCTHD(id,uid);

    }
}
