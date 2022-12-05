package com.example.doan_tmdt.Presenter;

import com.example.doan_tmdt.Models.Product;
import com.example.doan_tmdt.Models.User;
import com.example.doan_tmdt.my_interface.IProduct;
import com.example.doan_tmdt.my_interface.ProductView;
import com.example.doan_tmdt.my_interface.UserView;

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
    @Override
    public void OnSucess() {

    }

    @Override
    public void OnFail() {

    }

    @Override
    public void getDataProduct(String id, String ten, Long gia, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        callback.getDataProduct(id, ten, gia, hinhanh, loaisp, mota, soluong, hansudung, type, trongluong);
    }
}
