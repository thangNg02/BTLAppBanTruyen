package com.example.doan_tmdt.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_tmdt.Adapter.BannerAdapter;
import com.example.doan_tmdt.Adapter.LoaiProductAdapter;
import com.example.doan_tmdt.Adapter.ProductAdapter;
import com.example.doan_tmdt.MainActivity;
import com.example.doan_tmdt.Models.Giohang;
import com.example.doan_tmdt.Models.LoaiProduct;
import com.example.doan_tmdt.Models.Product;
import com.example.doan_tmdt.Presenter.GioHangPresenter;
import com.example.doan_tmdt.R;
import com.example.doan_tmdt.View.CategoryActivity;
import com.example.doan_tmdt.View.SearchActivity;
import com.example.doan_tmdt.my_interface.GioHangView;
import com.example.doan_tmdt.my_interface.IClickLoaiProduct;
import com.example.doan_tmdt.my_interface.IClickOpenBottomSheet;
import com.example.doan_tmdt.ultil.MyReceiver;
import com.example.doan_tmdt.ultil.NetworkUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment implements GioHangView {

    private Product product;
    private GioHangPresenter gioHangPresenter;

    // Bottom Sheet Dialog
    private TextView tvTenBottom, tvGiaBottom, tvSoluongBottom, tvMotaBottom;
    private ImageView imgBottom, btnMinusBottom, btnPlusBottom;
    private Button btnBottom;
    private BottomSheetDialog bottomSheetDialog;
    private int slBottom = 1;

    // Data Product
    private  ArrayList<Product> arr_ds_sp,arr_sp_nb,arr_sp_du,arr_sp_hq,arr_sp_mc,arr_sp_yt,arr_sp_lau,arr_sp_gy;
    private ProductAdapter productDSAdapter,productNBAdapter,productDUAdapter,productHQAdapter,productMCAdapter,productYTAdapter,productLauAdapter,productGYAdapter;
    private RecyclerView rcvDSSP,rcvSPNoiBat,rcvSPDoUong,rcvSPHQ,rcvSPMC,rcvSPYT,rcvSPLau,rcvSPGY;

    // Banner
    private ArrayList<String> arrayList;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private BannerAdapter bannerAdapter;

    // Infor User
    private Toolbar toolbarHome;
    private View view;
    private CircleImageView cirAvatarHome;
    private TextView tvNameHome, tvEmailHome;
    public static final int MY_REQUEST_CODE = 10;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    // Loai Product
    private RecyclerView rcvLoaiProduct;
    private LoaiProductAdapter loaiProductAdapter;
    private List<LoaiProduct> mlistproduct;

    //Search Data
    private EditText edtSearchHome;
    private ArrayList<Product> arr_all_product;

    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarHome);
        setHasOptionsMenu(true);
        InitWidget();
        Event();
        if (NetworkUtil.isNetworkConnected(getContext())){
            LoadInfor();
            Banner();
            InitProduct();
            GetDataDSSanPham();
            GetDataSPNoiBat();
            GetDataSPDoUong();
            GetDataSPHanQuoc();
            GetDataSPMiCay();
            GetDataSPYeuThich();
            GetDataSPLau();
            GetDataSPGoiY();
        }

        return view;
    }
    private void replace(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NotNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_two);
        View view = MenuItemCompat.getActionView(menuItem);

        CircleImageView cirToolbarProfile = view.findViewById(R.id.cir_toolbar_profile);
        firestore.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Profile")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                    if(documentSnapshot!=null){
                        try{
                            if(documentSnapshot.getString("avatar").length()>0){
                                Picasso.get().load(documentSnapshot.getString("avatar").trim()).into(cirToolbarProfile);
                            }
                        }catch (Exception e){
                            Log.d("ERROR",e.getMessage());
                        }
                    }
                }
            }
        });
        cirToolbarProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Menu two clicked", Toast.LENGTH_SHORT).show();
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_one:
                Toast.makeText(getContext(), "Menu one", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_two:
                replace(new ProfileFragment());
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void Event() {

        edtSearchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

    }

    private void InitProduct() {
        arr_ds_sp = new ArrayList<>();
        arr_sp_nb = new ArrayList<>();
        arr_sp_du = new ArrayList<>();
        arr_sp_hq = new ArrayList<>();
        arr_sp_mc = new ArrayList<>();
        arr_sp_yt = new ArrayList<>();
        arr_sp_lau = new ArrayList<>();
        arr_sp_gy = new ArrayList<>();
        arr_all_product = new ArrayList<>();
    }
    // Danh sách Product
    public  void  GetDataDSSanPham(){
        firestore.collection("SanPham").
                whereEqualTo("type",1).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                        arr_ds_sp.add(new Product(d.getId(),d.getString("tensp"),
                                d.getLong("giatien"),d.getString("hinhanh"),
                                d.getString("loaisp"),d.getString("mota"),
                                d.getLong("soluong"),d.getString("hansudung"),
                                d.getLong("type"),d.getString("trongluong")));
                    }
                    productDSAdapter = new ProductAdapter(getContext(), arr_ds_sp, 1, new IClickOpenBottomSheet() {
                        @Override
                        public void onClickOpenBottomSheet(int position) {
                            showBottomSheetDialog();

                            // Do something
                            product = arr_ds_sp.get(position);
                            tvTenBottom.setText(product.getTensp());
                            tvGiaBottom.setText(NumberFormat.getInstance().format(product.getGiatien())+"");
//                            tvSoluongBottom.setText(product.getSoluong()+"");
                            tvMotaBottom.setText(product.getMota());
                            Picasso.get().load(product.getHinhanh()).into(imgBottom);
                            eventBottomSheet();

                            bottomSheetDialog.show();
                        }
                    });
                    rcvDSSP.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                    rcvDSSP.setAdapter(productDSAdapter);
                }

            }
        });
    }


    // Sản phẩm nổi bật
    public  void  GetDataSPNoiBat(){
        firestore.collection("SanPham").
                whereEqualTo("type",2).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                        arr_sp_nb.add(new Product(d.getId(),d.getString("tensp"),
                                d.getLong("giatien"),d.getString("hinhanh"),
                                d.getString("loaisp"),d.getString("mota"),
                                d.getLong("soluong"),d.getString("hansudung"),
                                d.getLong("type"),d.getString("trongluong")));
                    }
                    productNBAdapter = new ProductAdapter(getContext(), arr_sp_nb, 2, new IClickOpenBottomSheet() {
                        @Override
                        public void onClickOpenBottomSheet(int position) {
                            showBottomSheetDialog();

                            // Do something
                            product = arr_sp_nb.get(position);
                            tvTenBottom.setText(product.getTensp());
                            tvGiaBottom.setText(NumberFormat.getInstance().format(product.getGiatien())+"");
//                            tvSoluongBottom.setText(product.getSoluong()+"");
                            tvMotaBottom.setText(product.getMota());
                            Picasso.get().load(product.getHinhanh()).into(imgBottom);
                            eventBottomSheet();

                            bottomSheetDialog.show();
                        }
                    });
                    rcvSPNoiBat.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                    rcvSPNoiBat.setAdapter(productNBAdapter);
                }

            }
        });
    }
    // Sản phẩm đồ uống
    public  void  GetDataSPDoUong(){
        firestore.collection("SanPham").
                whereEqualTo("type",3).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                        // lấy id trên firebase
                        arr_sp_du.add(new Product(d.getId(),d.getString("tensp"),
                                d.getLong("giatien"),d.getString("hinhanh"),
                                d.getString("loaisp"),d.getString("mota"),
                                d.getLong("soluong"),d.getString("hansudung"),
                                d.getLong("type"),d.getString("trongluong")));
                    }
                    productDUAdapter = new ProductAdapter(getContext(), arr_sp_du, 3, new IClickOpenBottomSheet() {
                        @Override
                        public void onClickOpenBottomSheet(int position) {
                            showBottomSheetDialog();

                            // Do something
                            product = arr_sp_du.get(position);
                            tvTenBottom.setText(product.getTensp());
                            tvGiaBottom.setText(NumberFormat.getInstance().format(product.getGiatien())+"");
//                            tvSoluongBottom.setText(product.getSoluong()+"");
                            tvMotaBottom.setText(product.getMota());
                            Picasso.get().load(product.getHinhanh()).into(imgBottom);
                            eventBottomSheet();

                            bottomSheetDialog.show();
                        }
                    });
                    rcvSPDoUong.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                    rcvSPDoUong.setAdapter(productDUAdapter);
                }

            }
        });
    }

    // Sản phẩm Hàn Quốc
    public  void  GetDataSPHanQuoc(){
        firestore.collection("SanPham").
                whereEqualTo("type",4).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                        // lấy id trên firebase
                        arr_sp_hq.add(new Product(d.getId(),d.getString("tensp"),
                                d.getLong("giatien"),d.getString("hinhanh"),
                                d.getString("loaisp"),d.getString("mota"),
                                d.getLong("soluong"),d.getString("hansudung"),
                                d.getLong("type"),d.getString("trongluong")));
                    }
                    productHQAdapter = new ProductAdapter(getContext(), arr_sp_hq, 4, new IClickOpenBottomSheet() {
                        @Override
                        public void onClickOpenBottomSheet(int position) {
                            showBottomSheetDialog();

                            // Do something
                            product = arr_sp_hq.get(position);
                            tvTenBottom.setText(product.getTensp());
                            tvGiaBottom.setText(NumberFormat.getInstance().format(product.getGiatien())+"");
//                            tvSoluongBottom.setText(product.getSoluong()+"");
                            tvMotaBottom.setText(product.getMota());
                            Picasso.get().load(product.getHinhanh()).into(imgBottom);
                            eventBottomSheet();

                            bottomSheetDialog.show();
                        }
                    });
                    rcvSPHQ.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                    rcvSPHQ.setAdapter(productHQAdapter);
                }

            }
        });
    }
    // Sản phẩm Mì cay
    public  void  GetDataSPMiCay(){
        firestore.collection("SanPham").
                whereEqualTo("type",5).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                        arr_sp_mc.add(new Product(d.getId(),d.getString("tensp"),
                                d.getLong("giatien"),d.getString("hinhanh"),
                                d.getString("loaisp"),d.getString("mota"),
                                d.getLong("soluong"),d.getString("hansudung"),
                                d.getLong("type"),d.getString("trongluong")));
                    }
                    productMCAdapter = new ProductAdapter(getContext(), arr_sp_mc, 5, new IClickOpenBottomSheet() {
                        @Override
                        public void onClickOpenBottomSheet(int position) {
                            showBottomSheetDialog();

                            // Do something
                            product = arr_sp_mc.get(position);
                            tvTenBottom.setText(product.getTensp());
                            tvGiaBottom.setText(NumberFormat.getInstance().format(product.getGiatien())+"");
//                            tvSoluongBottom.setText(product.getSoluong()+"");
                            tvMotaBottom.setText(product.getMota());
                            Picasso.get().load(product.getHinhanh()).into(imgBottom);
                            eventBottomSheet();

                            bottomSheetDialog.show();
                        }
                    });
                    rcvSPMC.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                    rcvSPMC.setAdapter(productMCAdapter);
                }

            }
        });
    }
    // Sản phẩm Yêu thích
    public  void  GetDataSPYeuThich(){
        firestore.collection("SanPham").
                whereEqualTo("type",6).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                        // lấy id trên firebase
                        arr_sp_yt.add(new Product(d.getId(),d.getString("tensp"),
                                d.getLong("giatien"),d.getString("hinhanh"),
                                d.getString("loaisp"),d.getString("mota"),
                                d.getLong("soluong"),d.getString("hansudung"),
                                d.getLong("type"),d.getString("trongluong")));
                    }
                    productYTAdapter = new ProductAdapter(getContext(), arr_sp_yt, 6, new IClickOpenBottomSheet() {
                        @Override
                        public void onClickOpenBottomSheet(int position) {
                            showBottomSheetDialog();

                            // Do something
                            product = arr_sp_yt.get(position);
                            tvTenBottom.setText(product.getTensp());
                            tvGiaBottom.setText(NumberFormat.getInstance().format(product.getGiatien())+"");
//                            tvSoluongBottom.setText(product.getSoluong()+"");
                            tvMotaBottom.setText(product.getMota());
                            Picasso.get().load(product.getHinhanh()).into(imgBottom);
                            eventBottomSheet();

                            bottomSheetDialog.show();
                        }
                    });
                    rcvSPYT.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                    rcvSPYT.setAdapter(productYTAdapter);
                }

            }
        });
    }
    // Sản phẩm Lẩu
    public  void  GetDataSPLau(){
        firestore.collection("SanPham").
                whereEqualTo("type",7).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                        // lấy id trên firebase
                        arr_sp_lau.add(new Product(d.getId(),d.getString("tensp"),
                                d.getLong("giatien"),d.getString("hinhanh"),
                                d.getString("loaisp"),d.getString("mota"),
                                d.getLong("soluong"),d.getString("hansudung"),
                                d.getLong("type"),d.getString("trongluong")));
                    }
                    productLauAdapter = new ProductAdapter(getContext(), arr_sp_lau, 7, new IClickOpenBottomSheet() {
                        @Override
                        public void onClickOpenBottomSheet(int position) {
                            showBottomSheetDialog();

                            // Do something
                            product = arr_sp_lau.get(position);
                            tvTenBottom.setText(product.getTensp());
                            tvGiaBottom.setText(NumberFormat.getInstance().format(product.getGiatien())+"");
//                            tvSoluongBottom.setText(product.getSoluong()+"");
                            tvMotaBottom.setText(product.getMota());
                            Picasso.get().load(product.getHinhanh()).into(imgBottom);
                            eventBottomSheet();

                            bottomSheetDialog.show();
                        }
                    });
                    rcvSPLau.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                    rcvSPLau.setAdapter(productLauAdapter);
                }

            }
        });
    }
    // Sản phẩm Gợi ý
    public  void  GetDataSPGoiY(){
        firestore.collection("SanPham").
                whereEqualTo("type",8).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                        // lấy id trên firebase
                        arr_sp_gy.add(new Product(d.getId(),d.getString("tensp"),
                                d.getLong("giatien"),d.getString("hinhanh"),
                                d.getString("loaisp"),d.getString("mota"),
                                d.getLong("soluong"),d.getString("hansudung"),
                                d.getLong("type"),d.getString("trongluong")));
                    }
                    productGYAdapter = new ProductAdapter(getContext(), arr_sp_gy, 8, new IClickOpenBottomSheet() {
                        @Override
                        public void onClickOpenBottomSheet(int position) {
                            showBottomSheetDialog();

                            // Do something
                            product = arr_sp_gy.get(position);
                            tvTenBottom.setText(product.getTensp());
                            tvGiaBottom.setText(NumberFormat.getInstance().format(product.getGiatien())+"");
//                            tvSoluongBottom.setText(product.getSoluong()+"");
                            tvMotaBottom.setText(product.getMota());
                            Picasso.get().load(product.getHinhanh()).into(imgBottom);
                            eventBottomSheet();

                            bottomSheetDialog.show();
                        }
                    });
                    rcvSPGY.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
                    rcvSPGY.setAdapter(productGYAdapter);
                }

            }
        });
    }

    public void showBottomSheetDialog() {
        // Bottom sheet Dialog
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.layout_persistent_bottom_sheet);
        tvTenBottom = bottomSheetDialog.findViewById(R.id.tv_ten_bottom);
        imgBottom = bottomSheetDialog.findViewById(R.id.img_bottom);
        tvGiaBottom = bottomSheetDialog.findViewById(R.id.tv_gia_bottom);
        btnMinusBottom = bottomSheetDialog.findViewById(R.id.btn_minus_bottom);
        btnPlusBottom = bottomSheetDialog.findViewById(R.id.btn_plus_bottom);
        tvSoluongBottom = bottomSheetDialog.findViewById(R.id.tv_soluong_bottom);
        tvMotaBottom = bottomSheetDialog.findViewById(R.id.tv_mota_bottom);
        btnBottom = bottomSheetDialog.findViewById(R.id.btn_bottom);

    }
    public void eventBottomSheet(){
        btnMinusBottom.setVisibility(View.GONE);
        btnMinusBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slBottom = Integer.parseInt(tvSoluongBottom.getText().toString()) - 1;
                tvSoluongBottom.setText(String.valueOf(slBottom));

                if (slBottom < 2){
                    btnMinusBottom.setVisibility(View.GONE);
                } else btnMinusBottom.setVisibility(View.VISIBLE);
            }
        });
        btnPlusBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slBottom = Integer.parseInt(tvSoluongBottom.getText().toString()) + 1;
                tvSoluongBottom.setText(String.valueOf(slBottom));

                if (slBottom < 2){
                    btnMinusBottom.setVisibility(View.GONE);
                } else btnMinusBottom.setVisibility(View.VISIBLE);
            }
        });

        btnBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gioHangPresenter.AddCart(product.getId(), Long.valueOf(slBottom));
            }
        });
    }

    private void Banner() {
        arrayList = new ArrayList<>();
        firestore= FirebaseFirestore.getInstance();
        firestore.collection("Banner").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                    arrayList.add(d.getString("hinhanh"));
                }
                bannerAdapter = new BannerAdapter(getContext(),arrayList);
                viewPager.setAdapter(bannerAdapter);
                circleIndicator.setViewPager(viewPager);
                bannerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    //3s sang 1 banner khác
                    public void run() {
                        int k=viewPager.getCurrentItem();
                        if(k>=arrayList.size()-1){
                            k  = 0;
                        }else{
                            k++;
                        }
                        handler.postDelayed(this,3000);
                        viewPager.setCurrentItem(k,true);

                    }
                },3000);

            }
        });
    }

    private void LoadInfor() {
        tvEmailHome.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        firestore.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Profile")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                    if(documentSnapshot!=null){
                        try{
                            tvNameHome.setText(documentSnapshot.getString("hoten").length()>0 ?
                                    documentSnapshot.getString("hoten") : "");

                            if(documentSnapshot.getString("avatar").length()>0){
                                Picasso.get().load(documentSnapshot.getString("avatar").trim()).into(cirAvatarHome);
                            }
                        }catch (Exception e){
                            Log.d("ERROR",e.getMessage());
                        }
                    }
                }
            }
        });
    }

    private void InitWidget() {
//        searchView = view.findViewById(R.id.search_view_home);
//        searchView.clearFocus();
        edtSearchHome = view.findViewById(R.id.edt_search_home);

        toolbarHome = view.findViewById(R.id.toolbar_home);
        cirAvatarHome = view.findViewById(R.id.cir_avatar_home);
        tvNameHome = view.findViewById(R.id.tv_name_home);
        tvEmailHome = view.findViewById(R.id.tv_email_home);
        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.circle_indicator);

        rcvLoaiProduct = view.findViewById(R.id.rcv_loai_product);
        loaiProductAdapter = new LoaiProductAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvLoaiProduct.setLayoutManager(linearLayoutManager);
        loaiProductAdapter.setData(getListLoaiProduct(), new IClickLoaiProduct() {
            @Override
            public void onClickItemLoaiProduct(int position) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("loaiproduct", position);
                startActivity(intent);
            }
        });

        rcvDSSP = view.findViewById(R.id.rcv_ds_sanpham);
        rcvSPNoiBat = view.findViewById(R.id.rcv_sp_noibat);
        rcvSPDoUong = view.findViewById(R.id.rcv_sp_douong);
        rcvSPHQ = view.findViewById(R.id.rcv_sp_hanquoc);
        rcvSPMC = view.findViewById(R.id.rcv_sp_micay);
        rcvSPYT = view.findViewById(R.id.rcv_sp_yeuthich);
        rcvSPLau = view.findViewById(R.id.rcv_sp_lau);
        rcvSPGY = view.findViewById(R.id.rcv_sp_goiy);

        gioHangPresenter = new GioHangPresenter(HomeFragment.this);

    }

    private List<LoaiProduct> getListLoaiProduct(){
        mlistproduct = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("LoaiProduct").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                    mlistproduct.add(new LoaiProduct(d.getString("tenloai"), d.getString("hinhanhloai")));
                    rcvLoaiProduct.setAdapter(loaiProductAdapter);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("error", "Lỗi: " + e);
                Toast.makeText(getContext(), "Lỗi" + e, Toast.LENGTH_SHORT).show();
            }
        });

        return mlistproduct;
    }

    public TextView getTvNameHome(){
        return tvNameHome;
    }
    public TextView getTvEmailHome(){
        return tvEmailHome;
    }
    public CircleImageView getCirAvatarHome(){
        return cirAvatarHome;
    }


    @Override
    public void OnSucess() {
        Toast.makeText(getActivity(), "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnFail() {
        Toast.makeText(getActivity(), "Thêm vào giỏ hàng lỗi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDataSanPham(String id, String idsp, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {

    }
}