package com.example.doan_tmdt.View.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_tmdt.Adapter.ChitietHoadonAdapter;
import com.example.doan_tmdt.Models.HoaDon;
import com.example.doan_tmdt.Models.Product;
import com.example.doan_tmdt.Presenter.GioHangPresenter;
import com.example.doan_tmdt.Presenter.HoaDonPreSenter;
import com.example.doan_tmdt.R;
import com.example.doan_tmdt.View.CTHDActivity;
import com.example.doan_tmdt.my_interface.GioHangView;
import com.example.doan_tmdt.my_interface.HoaDonView;

import java.util.ArrayList;

public class AdminCTHDActivity extends AppCompatActivity implements GioHangView, HoaDonView {

    private ImageView imgAdminFinishHD;
    private TextView tvAdminMaHD, tvAdminDateHD, tvAdminNameHD, tvAdminAddressHD, tvAdminSdtHD, tvAdminTotalHD, tvAdminStatusHD;
    private RecyclerView rcvAminHD;
    private Button btnAdminUpdate;
    private HoaDon hoaDon;
    private ArrayList<Product> mlist;
    private AdminCTHDAdapter adapter;
    private GioHangPresenter gioHangPresenter;
    private HoaDonPreSenter hoaDonPreSenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cthdactivity);

        InitWidget();
        Init();
        Event();
    }

    private void Event() {
        imgAdminFinishHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdminUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DiaLogUpDate();
                Toast.makeText(AdminCTHDActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

//    private void DiaLogUpDate() {
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.dialog_update_trangthai);
//        dialog.show();
//        adapter.notifyDataSetChanged();
//
//        Spinner spiner = dialog.findViewById(R.id.spinerCapNhat);
//        String[] s = {"Chọn Mục","Đang xử lý","Đang giao hàng","Giao hàng thành công","Hủy hàng"} ;
//        ArrayAdapter arrayAdapter  = new ArrayAdapter(this, android.R.layout.simple_list_item_1,s);
//        spiner.setAdapter( arrayAdapter);
//        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position>0){
//                    hoaDonPreSenter.CapNhatTrangThai(position,hoaDon.getId());
//                    dialog.cancel();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//    }

    private void Init() {
        mlist = new ArrayList<>();
        Intent intent = getIntent();
        hoaDon = (HoaDon) intent.getSerializableExtra("CTHD");
        tvAdminMaHD.setText(hoaDon.getId());
        tvAdminDateHD.setText(hoaDon.getNgaydat());
        tvAdminNameHD.setText(hoaDon.getHoten());
        tvAdminAddressHD.setText(hoaDon.getDiachi());
        tvAdminSdtHD.setText(hoaDon.getSdt());
        tvAdminTotalHD.setText(hoaDon.getTongtien());
        switch ((int) hoaDon.getType()){
            case  1:
                tvAdminStatusHD.setText("Đang xử lý");
                break;
            case  2:
                tvAdminStatusHD.setText("Đang giao hàng");
                break;
            case  3:
                tvAdminStatusHD.setText("Giao Hàng Thành Công");
                break;
            case  4:
                tvAdminStatusHD.setText("Hủy Đơn Hàng");
                break;
        }
        hoaDonPreSenter = new HoaDonPreSenter(this);
        gioHangPresenter = new GioHangPresenter(this);
        gioHangPresenter.HandlegetDataCTHD(hoaDon.getId(),hoaDon.getUid());
    }

    private void InitWidget() {
        imgAdminFinishHD = findViewById(R.id.img_admin_finish);
        tvAdminMaHD = findViewById(R.id.tv_admin_mahd);
        tvAdminDateHD = findViewById(R.id.tv_admin_datehd);
        tvAdminNameHD = findViewById(R.id.tv_admin_namehd);
        tvAdminAddressHD = findViewById(R.id.tv_admin_addresshd);
        tvAdminSdtHD = findViewById(R.id.tv_admin_sdthd);
        tvAdminTotalHD = findViewById(R.id.tv_admin_totalhd);
        tvAdminStatusHD = findViewById(R.id.tv_admin_statushd);
        rcvAminHD = findViewById(R.id.rcv_admin_hd);
        btnAdminUpdate = findViewById(R.id.btn_admin_updatehd);
    }

    @Override
    public void OnSucess() {

    }

    @Override
    public void getDataHD(String id, String uid, String ghichu, String diachi, String hoten, String ngaydat, String phuongthuc, String sdt, String tongtien, Long type) {

    }

    @Override
    public void OnFail() {

    }

    @Override
    public void getDataSanPham(String id, String idsp, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        mlist.add(new Product(id,idsp,tensp,giatien,hinhanh,loaisp,mota,soluong,hansudung,type,trongluong));
        adapter = new AdminCTHDAdapter();
        adapter.setData(mlist);
        rcvAminHD.setLayoutManager(new LinearLayoutManager(this));
        rcvAminHD.setAdapter(adapter);
    }
}