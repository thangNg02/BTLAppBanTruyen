package com.example.doan_tmdt.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_tmdt.Adapter.CategoryAdapter;
import com.example.doan_tmdt.Adapter.ProductAdapter;
import com.example.doan_tmdt.Models.Product;
import com.example.doan_tmdt.Presenter.GioHangPresenter;
import com.example.doan_tmdt.R;
import com.example.doan_tmdt.fragment.HomeFragment;
import com.example.doan_tmdt.my_interface.GioHangView;
import com.example.doan_tmdt.my_interface.IClickOpenBottomSheet;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView tvCategory;
    private EditText edtSearch;
    private RecyclerView rcvCategory;
    private CategoryAdapter categoryAdapter;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Product product;
    private ArrayList<Product> arr_khac, arr_micay, arr_chaosup, arr_pizza, arr_sandwich, arr_douong, arr_lau, arr_doannhanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        InitWidget();
        Init();
        Event();
    }

    private void Init() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("loaiproduct", 1);
        Log.d("zxc", position + "");
        switch (position){
            case 0:
                tvCategory.setText("KHÁC");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Khác").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_khac.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_khac, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {

                                    product = arr_khac.get(position);
                                    SendData();
                                }
                            });
                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                            // Thêm đường phân cách giữa các dòng
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                            rcvCategory.addItemDecoration(itemDecoration);
                            rcvCategory.setAdapter(categoryAdapter);
                        }

                    }
                });
                break;
            case 1:
                tvCategory.setText("MÌ CAY");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Mì cay").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_micay.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_micay, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
//                                    setBottomSheetDialog();
                                    product = arr_micay.get(position);
                                    SendData();
                                }
                            });
                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                            // Thêm đường phân cách giữa các dòng
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                            rcvCategory.addItemDecoration(itemDecoration);
                            rcvCategory.setAdapter(categoryAdapter);
                        }

                    }
                });
                break;
            case 2:
                tvCategory.setText("CHÁO & SÚP");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Cháo và Súp").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_chaosup.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_chaosup, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    product = arr_chaosup.get(position);
                                    SendData();
                                }
                            });
                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                            // Thêm đường phân cách giữa các dòng
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                            rcvCategory.addItemDecoration(itemDecoration);
                            rcvCategory.setAdapter(categoryAdapter);
                        }

                    }
                });
                break;
            case 3:
                tvCategory.setText("PIZZA");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Pizza").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_pizza.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_pizza, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    product = arr_pizza.get(position);
                                    SendData();
                                }
                            });
                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                            // Thêm đường phân cách giữa các dòng
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                            rcvCategory.addItemDecoration(itemDecoration);
                            rcvCategory.setAdapter(categoryAdapter);
                        }

                    }
                });
                break;
            case 4:
                tvCategory.setText("SANDWICH");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Sandwich").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_sandwich.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_sandwich, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    product = arr_sandwich.get(position);
                                    SendData();
                                }
                            });
                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                            // Thêm đường phân cách giữa các dòng
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                            rcvCategory.addItemDecoration(itemDecoration);
                            rcvCategory.setAdapter(categoryAdapter);
                        }

                    }
                });
                break;
            case 5:
                tvCategory.setText("ĐỒ UỐNG");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Đồ uống").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_douong.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_douong, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    product = arr_douong.get(position);
                                    SendData();
                                }
                            });
                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                            // Thêm đường phân cách giữa các dòng
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                            rcvCategory.addItemDecoration(itemDecoration);
                            rcvCategory.setAdapter(categoryAdapter);
                        }

                    }
                });
                break;
            case 6:
                tvCategory.setText("LẨU");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Lẩu").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_lau.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_lau, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    product = arr_lau.get(position);
                                    SendData();
                                }
                            });
                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                            // Thêm đường phân cách giữa các dòng
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                            rcvCategory.addItemDecoration(itemDecoration);
                            rcvCategory.setAdapter(categoryAdapter);
                        }

                    }
                });
                break;
            case 7:
                tvCategory.setText("ĐỒ ĂN NHANH");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Đồ ăn nhanh").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_doannhanh.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_doannhanh, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    product = arr_doannhanh.get(position);
                                    SendData();
                                }
                            });
                            rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                            // Thêm đường phân cách giữa các dòng
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                            rcvCategory.addItemDecoration(itemDecoration);
                            rcvCategory.setAdapter(categoryAdapter);
                        }

                    }
                });
                break;
        }
    }

    private void SendData(){
        Intent intent = new Intent(CategoryActivity.this, DetailSPActivity.class);
        intent.putExtra("search", product);
        startActivity(intent);
    }

    private void Event() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void InitWidget() {
        imgBack = findViewById(R.id.img_back);
        tvCategory = findViewById(R.id.tv_category);
        edtSearch = findViewById(R.id.edt_search);
        rcvCategory = findViewById(R.id.rcv_category);

        arr_khac = new ArrayList<>();
        arr_micay = new ArrayList<>();
        arr_chaosup = new ArrayList<>();
        arr_pizza = new ArrayList<>();
        arr_sandwich = new ArrayList<>();
        arr_douong = new ArrayList<>();
        arr_lau = new ArrayList<>();
        arr_doannhanh = new ArrayList<>();

    }

}