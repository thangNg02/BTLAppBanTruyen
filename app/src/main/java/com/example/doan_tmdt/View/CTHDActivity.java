package com.example.doan_tmdt.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_tmdt.Adapter.ChitietHoadonAdapter;
import com.example.doan_tmdt.Models.HoaDon;
import com.example.doan_tmdt.Models.Product;
import com.example.doan_tmdt.Presenter.GioHangPresenter;
import com.example.doan_tmdt.Presenter.HoaDonPreSenter;
import com.example.doan_tmdt.R;
import com.example.doan_tmdt.my_interface.GioHangView;
import com.example.doan_tmdt.my_interface.HoaDonView;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class CTHDActivity extends AppCompatActivity implements GioHangView, HoaDonView {

    private ImageView imgBackCTHD;
    private TextView tvMaCTHD, tvNgaydatCTHD, tvHotenCTHD, tvDiachiCTHD, tvSdtCTHD, tvTongtienCTHD, tvTrangthaiCTHD;
    private RecyclerView rcvCTHD;
    private Button btnCapnhat;
    private HoaDon hoaDon;
    private ArrayList<Product> mlist;
    private ChitietHoadonAdapter chitietHoadonAdapter;
    private GioHangPresenter gioHangPresenter;
    private HoaDonPreSenter hoaDonPreSenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cthdactivity);

        InitWidget();
        Init();

        imgBackCTHD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hoaDonPreSenter.CapNhatTrangThai(4, hoaDon.getId());
                mlist.clear();
                chitietHoadonAdapter.notifyDataSetChanged();
                Toast.makeText(CTHDActivity.this, "Đã hủy đơn hàng", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void Init() {
        mlist = new ArrayList<>();
        Intent intent = getIntent();
        hoaDon = (HoaDon) intent.getSerializableExtra("HD");
        tvMaCTHD.setText(hoaDon.getId());
        tvNgaydatCTHD.setText(hoaDon.getNgaydat());
        tvHotenCTHD.setText(hoaDon.getHoten());
        tvDiachiCTHD.setText(hoaDon.getDiachi());
        tvSdtCTHD.setText(hoaDon.getSdt());
        tvTongtienCTHD.setText(hoaDon.getTongtien());
        switch ((int) hoaDon.getType()){
            case  1:
                tvTrangthaiCTHD.setText("Đang xử lý");
                break;
            case  2:
                tvTrangthaiCTHD.setText("Đang giao hàng");
                btnCapnhat.setVisibility(View.GONE);
                break;
            case  3:
                tvTrangthaiCTHD.setText("Giao Hàng Thành Công");
                btnCapnhat.setVisibility(View.GONE);
                break;
            case  4:
                tvTrangthaiCTHD.setText("Hủy Đơn Hàng");
                btnCapnhat.setVisibility(View.GONE);
                break;
        }
        hoaDonPreSenter = new HoaDonPreSenter(this);
        gioHangPresenter = new GioHangPresenter(this);
        gioHangPresenter.HandlegetDataCTHD(hoaDon.getId(),hoaDon.getUid());
    }

    private void InitWidget() {
        imgBackCTHD = findViewById(R.id.img_back_cthd);
        tvMaCTHD = findViewById(R.id.tv_ma_cthd);
        tvNgaydatCTHD = findViewById(R.id.tv_ngaydat_cthd);
        tvHotenCTHD = findViewById(R.id.tv_hoten_cthd);
        tvDiachiCTHD = findViewById(R.id.tv_diachi_cthd);
        tvSdtCTHD = findViewById(R.id.tv_sdt_cthd);
        tvTongtienCTHD = findViewById(R.id.tv_tongtien_cthd);
        tvTrangthaiCTHD = findViewById(R.id.tv_trangthai_cthd);
        rcvCTHD = findViewById(R.id.rcv_cthd);
        btnCapnhat = findViewById(R.id.btn_capnhat_cthd);
    }

    @Override
    public void getDataSanPham(String id, String idsp, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {
        mlist.add(new Product(id,idsp,tensp,giatien,hinhanh,loaisp,mota,soluong,hansudung,type,trongluong));
        chitietHoadonAdapter = new ChitietHoadonAdapter();
        chitietHoadonAdapter.setDataChitietHoadon(mlist);
        rcvCTHD.setLayoutManager(new LinearLayoutManager(this));
        rcvCTHD.setAdapter(chitietHoadonAdapter);
    }

    @Override
    public void getDataHD(String id, String uid, String ghichu, String diachi, String hoten, String ngaydat, String phuongthuc, String sdt, String tongtien, Long type) {

    }

    @Override
    public void OnSucess() {

    }

    @Override
    public void OnFail() {

    }
}