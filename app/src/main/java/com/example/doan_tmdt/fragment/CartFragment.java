package com.example.doan_tmdt.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.doan_tmdt.Adapter.GiohangAdapter;
import com.example.doan_tmdt.Models.Chat;
import com.example.doan_tmdt.Models.Giohang;
import com.example.doan_tmdt.Models.Product;
import com.example.doan_tmdt.Presenter.GioHangPresenter;
import com.example.doan_tmdt.R;
import com.example.doan_tmdt.my_interface.GioHangView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import vn.momo.momo_partner.AppMoMoLib;
import vn.momo.momo_partner.MoMoParameterNamePayment;
import vn.momo.momo_partner.utils.MoMoUtils;

public class CartFragment<ActivityResult> extends Fragment implements GioHangView {

    private ScrollView scrollViewCart;
    private TextView tvNullCart;
    private View view;

    private RecyclerView rcvGioHang;
    private GiohangAdapter giohangAdapter;
    private GioHangPresenter gioHangPresenter;
    public  ArrayList<Product> listGiohang;
    private  TextView tvDongia, tvPhiVanChuyen, tvTongTien;
    private TextView btnThanhToan;

    private Intent intent;
    private Product product;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Hiển thị dialog thanh toán
    private Spinner spinnerPhuongthuc;
    private  String s[]={"Thanh toán khi nhận hàng","Thanh toán MOMO"};
    private String tienthanhtoan = "";
    private String hoten, diachi, sdt, ghichu;
    private String ngaydat, phuongthuc;

    // Momo
    int total;
    Chat chat;
    Number number;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);

        InitWidget();
        DeleteDataGioHang();
        TongTienGioHang();

        Event();
        return view;
    }

    private void Event() {
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listGiohang.size()>0){
                    DiaLogThanhToan();
                }else{
                    Toast.makeText(getActivity(), "Giỏ hàng của bạn đang trống !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void DiaLogThanhToan() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_thanhtoan);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        CustomInit(dialog);
    }

    private void CustomInit(Dialog dialog) {
        spinnerPhuongthuc = dialog.findViewById(R.id.spinner_phuongthuc);
        EditText edthoten = dialog.findViewById(R.id.edt_hoten);
        EditText edtdiachi = dialog.findViewById(R.id.edt_diachi);
        EditText edtsdt = dialog.findViewById(R.id.edt_sdt);
        EditText edtghichu = dialog.findViewById(R.id.edt_ghichu);
        TextView tvtongtien = dialog.findViewById(R.id.tv_tongtien);
        Button btnxacnhan = dialog.findViewById(R.id.btn_xacnhan);
        ImageView btnCancel = dialog.findViewById(R.id.btn_cancel);
        dialog.setCanceledOnTouchOutside(false);


        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),
                android.R.layout.simple_list_item_1, s);
        spinnerPhuongthuc.setAdapter(arrayAdapter);

        // Set info dialog
        tienthanhtoan = tvTongTien.getText().toString().trim();
        db.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Profile").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0) {
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                    if(documentSnapshot!=null){
                        edthoten.setText(documentSnapshot.getString("hoten"));
                        edtdiachi.setText(documentSnapshot.getString("diachi"));
                        edtsdt.setText(documentSnapshot.getString("sdt"));
                    }
                }
            }
        });
        tvtongtien.setText(tienthanhtoan);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hoten = edthoten.getText().toString().trim();
                diachi = edtdiachi.getText().toString().trim();
                sdt = edtsdt.getText().toString().trim();
                ghichu = edtghichu.getText().toString().trim();
                if(hoten.length()>0){
                    if(diachi.length()>0){
                        if(sdt.length()>0){
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Calendar calendar = Calendar.getInstance();
                            ngaydat = simpleDateFormat.format(calendar.getTime());
                            phuongthuc = spinnerPhuongthuc.getSelectedItem().toString();
                            switch (spinnerPhuongthuc.getSelectedItemPosition()){
                                case 0:
                                    DatHangGuiTinNhan();
                                    gioHangPresenter.HandleAddHoaDon(ghichu,simpleDateFormat.format(calendar.getTime()),diachi,hoten,sdt,spinnerPhuongthuc.getSelectedItem().toString(),tienthanhtoan,listGiohang);
                                    Toast.makeText(getActivity(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                    break;
                                case 1:
                                    DatHangGuiTinNhan();
                                    AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);
                                    requestPayment();
                                    gioHangPresenter.HandleAddHoaDon(ghichu,simpleDateFormat.format(calendar.getTime()),diachi,hoten,sdt,spinnerPhuongthuc.getSelectedItem().toString(),tienthanhtoan,listGiohang);
                                    dialog.cancel();
                                    break;
                            }

                        }else{
                            Toast.makeText(getActivity(), "Số điện thoại không để trống", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "Địa chỉ không để trống", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Họ tên không để trống", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void DatHangGuiTinNhan(){


        // Realtime Database
        // Khi đặt hàng xong thì người dùng sẽ được gửi 1 tin nhắn tự động từ tk admin
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> map = new HashMap<>();
        map.put("sender", "WvPK8OV0erKJP8w2KZNp");  // Người gửi đang fix cứng là id của admin
        map.put("receiver", FirebaseAuth.getInstance().getCurrentUser().getUid());
        String donhang = "";
        for (Product product: listGiohang){
            donhang += product.getTensp() + " x " + product.getSoluong() + "\n";
        }
        String mess = "Đơn hàng của bạn: " + "\n" + donhang + "\n" + "Ngày đặt: " + ngaydat + "\n" + "Địa chỉ: " + diachi + "\n" + "SĐT: " + sdt
                + "\n" + "Người nhận: " + hoten + "\n" + "Phương thức thanh toán: " + phuongthuc
                + "\n" + "Tổng tiền: " + tienthanhtoan;
        map.put("message", mess);
        map.put("isseen", false);

        reference.child("Chats").push().setValue(map);

        // Add user to chat fragment
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chatlist")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("WvPK8OV0erKJP8w2KZNp");

        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    chatRef.child("id").setValue("WvPK8OV0erKJP8w2KZNp");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public  void TongTienGioHang() {
        int tongtien = 0;
        tvPhiVanChuyen.setText(String.valueOf(10000));
        int phi = Integer.parseInt(tvPhiVanChuyen.getText().toString());
        for (Product product: listGiohang){
            tongtien += product.getGiatien() * product.getSoluong();
        }
        tvDongia.setText(String.valueOf(tongtien));
        int dongia = Integer.parseInt(tvDongia.getText().toString());
        tvTongTien.setText(NumberFormat.getInstance().format(phi + dongia));
    }

    private void InitWidget() {

        scrollViewCart = view.findViewById(R.id.scrollView_cart);
        tvNullCart = view.findViewById(R.id.tv_null_cart);
        rcvGioHang = view.findViewById(R.id.rcv_giohang);
        tvDongia = view.findViewById(R.id.tv_dongia);
        tvPhiVanChuyen = view.findViewById(R.id.tv_phivanchuyen);
        tvTongTien = view.findViewById(R.id.tv_tongtien);
        btnThanhToan = view.findViewById(R.id.btn_thanhtoan);

        listGiohang = new ArrayList<>();
        gioHangPresenter = new GioHangPresenter(CartFragment.this);
        gioHangPresenter.HandlegetDataGioHang();



    }

    @Override
    public void OnSucess() {

        listGiohang.clear();
        giohangAdapter.notifyDataSetChanged();
        TongTienGioHang();
        scrollViewCart.setVisibility(View.INVISIBLE);
        tvNullCart.setVisibility(View.VISIBLE);
        Log.d("c", "onSuccess");
    }

    @Override
    public void OnFail() {
        Toast.makeText(getContext(), "thất bại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDataSanPham(String id, String idsp,String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        try{
            listGiohang.add(new Product(id,idsp,tensp,giatien,hinhanh,loaisp,mota,soluong,hansudung,type,trongluong));
            if (listGiohang.size() != 0){
                scrollViewCart.setVisibility(View.VISIBLE);
                tvNullCart.setVisibility(View.GONE);
            } else {
                scrollViewCart.setVisibility(View.GONE);
                tvNullCart.setVisibility(View.VISIBLE);
            }
            giohangAdapter = new GiohangAdapter(getContext(),listGiohang, CartFragment.this);
            rcvGioHang.setLayoutManager(new LinearLayoutManager(getContext()));
            rcvGioHang.setAdapter(giohangAdapter);
            TongTienGioHang();
        }catch (Exception e){

        }

    }

    private void DeleteDataGioHang(){
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }
            //chức năng xóa sp trong giỏ hàng
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                AlertDialog.Builder buidler = new AlertDialog.Builder(getContext());
                buidler.setMessage("Bạn có muốn xóa  sản phẩm " + listGiohang.get(pos).getTensp() + " không?");
                buidler.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangPresenter.HandleDeleteDataGioHang(listGiohang.get(pos).getId());
                        listGiohang.remove(pos);
                        TongTienGioHang();
                        giohangAdapter.notifyDataSetChanged();
                        if (listGiohang.size() == 0){
                            scrollViewCart.setVisibility(View.INVISIBLE);
                            tvNullCart.setVisibility(View.VISIBLE);
                        }
                        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();

                    }
                });
                buidler.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangAdapter.notifyDataSetChanged();
                    }
                });
                buidler.show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rcvGioHang);
    }

    //Thanh toán momo
    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        long mahd =   System.currentTimeMillis();
        try {
            number = NumberFormat.getInstance().parse(tienthanhtoan);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        eventValue.put("merchantname", "Afforda Company - Nguyen Van Chinh"); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", "MOMO1NRV20220112"); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", Integer.parseInt(String.valueOf(number))); //Kiểu integer
        eventValue.put("orderId", "order"+mahd); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn

        eventValue.put("fee", Integer.parseInt(String.valueOf(number))); //Kiểu integer
        eventValue.put("description", "Mô tả"); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  "MOMO1NRV20220112"+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", "MOMO1NRV20220112");
        Log.d("end", "end1");
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "Thanh Toán Đồ ăn");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Đặc Biệt");
            String name ="";
            for(Product sanPham : listGiohang){
                name+=sanPham.getTensp()+",";

            }
            objExtraData.put("movie_name", name);
            objExtraData.put("movie_format", "Đồ ăn");
            Log.d("end", "end2");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("end", "Lỗi: " + e);
        }
        eventValue.put("extraData", objExtraData.toString());
        eventValue.put("extra", "");
        Log.d("end", "end3");
        AppMoMoLib.getInstance().requestMoMoCallBack(getActivity(), eventValue);
        Log.d("end", "end4");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("CHECKED","checked1");
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            Log.d("CHECKED","checked2");
            if(data != null) {
                Log.d("CHECKED","checked3");
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    Log.d("Messagesss","message: " + "Get token " + data.getStringExtra("message"));
                    String checked = data.getStringExtra("message");
                    Log.d("CHECKED",checked);
                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                    gioHangPresenter.HandleAddHoaDon(ghichu,simpleDateFormat.format(calendar.getTime()),diachi,hoten,sdt,spinnerPhuongthuc.getSelectedItem().toString(),tienthanhtoan,listGiohang);

                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        Log.d("Message Error : ","message: " + "Get token " + data.getStringExtra("message"));

                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.d("Message Fail : ","message: " + "Get token " + data.getStringExtra("message"));
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    Log.d("Message Fail 1 : ","message: " + "Get token " + data.getStringExtra("message"));
                } else {
                    //TOKEN FAIL
                    Log.d("Message Fail 2 : ","message: " + "Get token " + data.getStringExtra("message"));
                }
            } else {
                Log.d("Message Fail 3 : ","message: " + "Get token " + data.getStringExtra("message"));
            }
        } else {
            Log.d("Message Fail 4 : ","message: " + "Get token " + data.getStringExtra("message"));
        }
    }


}