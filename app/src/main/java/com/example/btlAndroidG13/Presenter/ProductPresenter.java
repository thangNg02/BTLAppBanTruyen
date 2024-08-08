package com.example.btlAndroidG13.Presenter;

import com.example.btlAndroidG13.Models.Product;
import com.example.btlAndroidG13.my_interface.IProduct;
import com.example.btlAndroidG13.my_interface.ProductView;

public class ProductPresenter implements IProduct {

    private Product product;
    private ProductView callback;

    public ProductPresenter(ProductView callback){
        product = new Product(this);
        this.callback = callback;
    }

    public void HandleGetDataProduct(){
        product.HandleGetDataProduct();
    }

    public void HandleGetWithIDProduct(String idproduct){
        product.HandleGetWithIDProduct(idproduct);
    }
    @Override
    public void OnSucess() {

    }

    @Override
    public void OnFail() {

    }

    @Override
    public void getDataProduct(String id, String ten, Long gia, String hinhanh, String theloai, String mota, Long soluong, String ngayxuatban, Long type, String trongluong) {
        callback.getDataProduct(id, ten, gia, hinhanh, theloai, mota, soluong, ngayxuatban, type, trongluong);
    }
}
