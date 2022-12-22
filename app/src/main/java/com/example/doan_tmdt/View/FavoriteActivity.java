package com.example.doan_tmdt.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_tmdt.Adapter.BinhLuanAdapter;
import com.example.doan_tmdt.Adapter.FavoriteAdapter;
import com.example.doan_tmdt.Models.Favorite;
import com.example.doan_tmdt.Models.Product;
import com.example.doan_tmdt.Presenter.FavoritePresenter;
import com.example.doan_tmdt.Presenter.ProductPresenter;
import com.example.doan_tmdt.R;
import com.example.doan_tmdt.my_interface.FavoriteView;
import com.example.doan_tmdt.my_interface.IClickCTHD;
import com.example.doan_tmdt.my_interface.ProductView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity implements ProductView, FavoriteView{

    private ProgressBar progressBar;
    private TextView tvNullFavorite;
    private ImageView imgFavoriteBack;
    private RecyclerView rcvFavorite;

    private ProductPresenter productPresenter;
    private FavoritePresenter favoritePresenter;
    private ArrayList<Favorite> mlistFavorite;
    private ArrayList<Product> mlistProduct;
    private FavoriteAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        InitWidget();
        DeleteFavorite();
    }

    private void DeleteFavorite() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }
            //chức năng xóa sp trong giỏ hàng
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                AlertDialog.Builder buidler = new AlertDialog.Builder(FavoriteActivity.this);
                buidler.setMessage("Bạn có muốn xóa  sản phẩm " + mlistProduct.get(pos).getTensp() + " không?");
                buidler.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db.collection("Favorite").document(mlistFavorite.get(pos+i).getIdlove()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                i++;
                                mlistProduct.remove(pos);
                                adapter.notifyDataSetChanged();

                                if (mlistProduct.size() == 0){
                                    tvNullFavorite.setVisibility(View.VISIBLE);
                                }
                                Toast.makeText(FavoriteActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                buidler.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.notifyDataSetChanged();
                    }
                });
                buidler.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rcvFavorite);
    }

    private void InitWidget() {
        progressBar = findViewById(R.id.progressBar_favorite);
        tvNullFavorite = findViewById(R.id.tv_null_favorite);
        imgFavoriteBack = findViewById(R.id.img_favorite_back);
        rcvFavorite = findViewById(R.id.rcv_favorite);
        mlistProduct = new ArrayList<>();
        mlistFavorite = new ArrayList<>();

//        progressBar.setVisibility(View.VISIBLE);
        productPresenter = new ProductPresenter(this);
        favoritePresenter = new FavoritePresenter(this);
        favoritePresenter.HandleGetFavorite(FirebaseAuth.getInstance().getCurrentUser().getUid());


        imgFavoriteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }


    @Override
    public void getDataFavorite(String idlove, String idproduct, String iduser) {
        mlistFavorite.add(new Favorite(idlove, idproduct, FirebaseAuth.getInstance().getCurrentUser().getUid()));
        productPresenter.HandleGetWithIDProduct(idproduct);
//        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    public void OnSucess() {

    }

    @Override
    public void OnFail() {

    }

    @Override
    public void getDataProduct(String id, String ten, Long gia, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        mlistProduct.add(new Product(id, ten, gia, hinhanh, loaisp, mota, soluong, hansudung, type, trongluong));
        if (mlistFavorite.size() != 0){
            tvNullFavorite.setVisibility(View.GONE);
        } else {
            tvNullFavorite.setVisibility(View.VISIBLE);
        }
//        progressBar.setVisibility(View.GONE);
        adapter = new FavoriteAdapter(FavoriteActivity.this, mlistProduct, new IClickCTHD() {
            @Override
            public void onClickCTHD(int pos) {
                Product product = mlistProduct.get(pos);
                Intent intent = new Intent(FavoriteActivity.this, DetailSPActivity.class);
                intent.putExtra("search", product);
                startActivity(intent);
            }
        });

        rcvFavorite.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this, RecyclerView.VERTICAL, false));
        rcvFavorite.setAdapter(adapter);
    }

}