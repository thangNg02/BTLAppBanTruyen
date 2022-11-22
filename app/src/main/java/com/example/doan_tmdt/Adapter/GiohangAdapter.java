package com.example.doan_tmdt.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_tmdt.Models.Giohang;
import com.example.doan_tmdt.Models.LoaiProduct;
import com.example.doan_tmdt.Models.Product;
import com.example.doan_tmdt.R;
import com.example.doan_tmdt.fragment.CartFragment;
import com.example.doan_tmdt.my_interface.IClickLoaiProduct;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class GiohangAdapter extends RecyclerView.Adapter<GiohangAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Product> mListGiohang;
    private int number;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public GiohangAdapter(Context context, ArrayList<Product> mListGiohang) {
        this.context = context;
        this.mListGiohang = mListGiohang;
    }
    public void updateListGioHang(ArrayList<Product> newArr){
        this.mListGiohang.clear();
        for (int i = 0; i < newArr.size(); i++)
            this.mListGiohang.add(newArr.get(i));
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.dong_giohang, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, int position) {

        Product product = mListGiohang.get(position);
        holder.tvTenGiohang.setText(product.getTensp());
        holder.tvGiatienGiohang.setText(String.valueOf(product.getGiatien()));
        holder.tvNumberGiohang.setText(String.valueOf(product.getSoluong()));


        holder.btnMinusGiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String soluong = holder.tvNumberGiohang.getText().toString();
                number = Integer.parseInt(soluong) - 1;
                product.setSoluong(Long.parseLong(String.valueOf(number)));
                db.collection("GioHang").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("ALL").whereEqualTo("id_product",product.getIdsp()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()!=0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                long soluong_sp = d.getLong("soluong");
                                soluong_sp = product.getSoluong();
                                db.collection("GioHang").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .collection("ALL").document(d.getId()).update("soluong",soluong_sp);
                            }
                        }
                    }
                });
                holder.tvNumberGiohang.setText(String.valueOf(number));
                int numberCurrent = Integer.parseInt(holder.tvNumberGiohang.getText().toString());
                int costCurrent = Integer.parseInt(holder.tvGiatienGiohang.getText().toString());
                holder.tvTotalGiohang.setText(NumberFormat.getInstance().format(numberCurrent * costCurrent));
                CartFragment.TongTienGioHang();
            }
        });
        holder.btnPlusGiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soluong = holder.tvNumberGiohang.getText().toString();
                number = Integer.parseInt(soluong) + 1;
                product.setSoluong(Long.parseLong(String.valueOf(number)));
                db.collection("GioHang").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("ALL").whereEqualTo("id_product",product.getIdsp()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        Log.d("SL",queryDocumentSnapshots.size()+"");
                        if(queryDocumentSnapshots.size()!=0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                long soluong_sp = d.getLong("soluong");
                                Log.d("soluong", String.valueOf(soluong_sp));
                                soluong_sp = product.getSoluong();
                                db.collection("GioHang").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .collection("ALL").document(d.getId()).update("soluong",soluong_sp);

                            }
                        }
                    }
                });
                holder.tvNumberGiohang.setText(String.valueOf(number));
                int numberCurrent = Integer.parseInt(holder.tvNumberGiohang.getText().toString());
                int costCurrent = Integer.parseInt(holder.tvGiatienGiohang.getText().toString());
                holder.tvTotalGiohang.setText(NumberFormat.getInstance().format(numberCurrent * costCurrent));
                CartFragment.TongTienGioHang();
            }
        });
        holder.tvTrongluongGiohang.setText(product.getTrongluong());
        int numberCurrent = Integer.parseInt(holder.tvNumberGiohang.getText().toString());
        int costCurrent = Integer.parseInt(holder.tvGiatienGiohang.getText().toString());
        holder.tvTotalGiohang.setText(NumberFormat.getInstance().format(numberCurrent * costCurrent));
        Picasso.get().load(product.getHinhanh()).into(holder.imgGiohang);

    }

    @Override
    public int getItemCount() {
        return mListGiohang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTenGiohang, tvTrongluongGiohang, tvNumberGiohang, tvGiatienGiohang, tvTotalGiohang;
        private ImageView btnMinusGiohang, btnPlusGiohang, imgGiohang;

        public ViewHolder(@NotNull View itemView) {
            super(itemView);

            tvTenGiohang = itemView.findViewById(R.id.tv_ten_giohang);
            tvTrongluongGiohang = itemView.findViewById(R.id.tv_trongluong_giohang);
            tvNumberGiohang = itemView.findViewById(R.id.tv_number_giohang);
            tvGiatienGiohang = itemView.findViewById(R.id.tv_giatien_giohang);
            tvTotalGiohang = itemView.findViewById(R.id.tv_total_giohang);
            imgGiohang = itemView.findViewById(R.id.img_giohang);
            btnMinusGiohang = itemView.findViewById(R.id.btn_minus_giohang);
            btnPlusGiohang = itemView.findViewById(R.id.btn_plus_giohang);
        }
    }
}
