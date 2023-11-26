package com.example.btlAndroidG13.Presenter;

import com.example.btlAndroidG13.Models.Binhluan;
import com.example.btlAndroidG13.my_interface.BinhLuanView;
import com.example.btlAndroidG13.my_interface.IBinhLuan;

public class BinhLuanPresenter implements IBinhLuan {
    private Binhluan binhluan;
    private BinhLuanView callback;

    public BinhLuanPresenter(BinhLuanView callback){
        this.callback = callback;
        binhluan = new Binhluan(this);
    }

    public void HandleGetBinhLuanLimit(String idproduct){
        binhluan.HandleGetBinhLuanLimit(idproduct);
    }

    public void HandleGetAllBinhLuan(String idproduct){
        binhluan.HandleGetAllBinhLuan(idproduct);
    }
    @Override
    public void getDataBinhLuan(String idbinhluan, String idproduct, String iduser, String rate, String noidung, String timenow) {
        callback.getDataBinhLuan(idbinhluan, idproduct, iduser, rate, noidung, timenow);
    }
}
