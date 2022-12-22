package com.example.doan_tmdt.Presenter;

import com.example.doan_tmdt.Models.Binhluan;
import com.example.doan_tmdt.Models.Favorite;
import com.example.doan_tmdt.my_interface.BinhLuanView;
import com.example.doan_tmdt.my_interface.FavoriteView;
import com.example.doan_tmdt.my_interface.IBinhLuan;
import com.example.doan_tmdt.my_interface.IFavorite;

public class FavoritePresenter implements IFavorite {
    private Favorite favorite;
    private FavoriteView callback;

    public FavoritePresenter(FavoriteView callback){
        this.callback = callback;
        favorite = new Favorite(this);
    }

    public void HandleGetFavorite(String iduser){
        favorite.HandleGetFavorite(iduser);
    }


    @Override
    public void getDataFavorite(String idlove, String idproduct, String iduser) {
        callback.getDataFavorite(idlove, idproduct, iduser);
    }
}
