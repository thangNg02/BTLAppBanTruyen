package com.example.doan_tmdt.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.doan_tmdt.Adapter.LichSuSearchAdapter;
import com.example.doan_tmdt.Adapter.SearchAdapter;
import com.example.doan_tmdt.MainActivity;
import com.example.doan_tmdt.Models.Product;
import com.example.doan_tmdt.Presenter.ProductPresenter;
import com.example.doan_tmdt.Presenter.StoryPresenter;
import com.example.doan_tmdt.R;
import com.example.doan_tmdt.my_interface.IClickCTHD;
import com.example.doan_tmdt.my_interface.ProductView;
import com.example.doan_tmdt.my_interface.StoryView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchActivity extends AppCompatActivity implements ProductView, StoryView {
    private ImageView imgBackSearch;
    private SearchView searchView;
    private RecyclerView rcvSearch, rcvLichSuSearch;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private ArrayList<Product> mlistsearch;
    private SearchAdapter adapter;

    private ProductPresenter productPresenter;
    private StoryPresenter storyPresenter;

    private LichSuSearchAdapter lichSuSearchAdapter;
    private ArrayList<String> mlistStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        InitWidget();
        Event();
    }

    private void Event() {
        imgBackSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void StorySearch(String text){
        HashMap<String,String> hashMap =  new HashMap<>();
        hashMap.put("noidungtimkiem", text);
        firestore.collection("LichSuTimKiem").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Story").add(hashMap);
    }

    private void InitWidget() {

        imgBackSearch = findViewById(R.id.img_back_search);
        searchView = findViewById(R.id.search_view);
        rcvSearch = findViewById(R.id.rcv_search_monan);

        rcvLichSuSearch = findViewById(R.id.rcv_lichsu);

        productPresenter = new ProductPresenter(this);
        storyPresenter = new StoryPresenter(this);
        mlistsearch = new ArrayList<>();
        mlistStory = new ArrayList<>();

        productPresenter.HandleGetDataProduct();
        storyPresenter.HandleGetStory(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    public void OnSucess() {

    }

    @Override
    public void OnFail() {

    }

    @Override
    public void getDataProduct(String id, String ten, Long gia, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        mlistsearch.add(new Product(id, ten, gia, hinhanh, loaisp, mota, soluong, hansudung, type, trongluong));
        adapter = new SearchAdapter(SearchActivity.this, mlistsearch, new IClickCTHD() {
            @Override
            public void onClickCTHD(int pos) {
                Product product = mlistsearch.get(pos);
                Intent intent = new Intent(SearchActivity.this, DetailSPActivity.class);
                intent.putExtra("search", product);
                startActivity(intent);
                Toast.makeText(SearchActivity.this, product.getTensp(), Toast.LENGTH_SHORT).show();
            }
        });
        rcvSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this,RecyclerView.VERTICAL,false));
        rcvSearch.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                StorySearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
    }

    @Override
    public void getDataStory(String noidung) {
        mlistStory.add(noidung);
        lichSuSearchAdapter = new LichSuSearchAdapter();
        lichSuSearchAdapter.setdata(SearchActivity.this, mlistStory, new IClickCTHD() {
            @Override
            public void onClickCTHD(int pos) {
                String s = mlistStory.get(pos);
                searchView.setQuery(s, false);
            }
        });
        GridLayoutManager manager = new GridLayoutManager(SearchActivity.this, 6);
        rcvLichSuSearch.setLayoutManager(manager);
        rcvLichSuSearch.setAdapter(lichSuSearchAdapter);
    }
}