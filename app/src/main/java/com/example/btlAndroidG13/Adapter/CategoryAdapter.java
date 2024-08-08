package com.example.btlAndroidG13.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlAndroidG13.Models.Product;
import com.example.btlAndroidG13.R;
import com.example.btlAndroidG13.my_interface.IClickOpenBottomSheet;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Product> mListCategory;
    private IClickOpenBottomSheet iClickOpenBottomSheet;

    public CategoryAdapter(Context context, ArrayList<Product> mListCategory, IClickOpenBottomSheet iClickOpenBottomSheet) {
        this.context = context;
        this.mListCategory = mListCategory;
        this.iClickOpenBottomSheet = iClickOpenBottomSheet;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.dong_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Product product = mListCategory.get(position);
        holder.tvTenCategory.setText(product.getTentruyen());
        holder.tvGiaCategory.setText(NumberFormat.getInstance().format(product.getGiatien())+"");
        holder.tvTrongLuongCategory.setText(product.getTrongluong());
        Picasso.get().load(product.getHinhanh()).into(holder.imgCategory);

        holder.layoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickOpenBottomSheet.onClickOpenBottomSheet(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListCategory != null){
            return mListCategory.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgCategory;
        private TextView tvTenCategory;
        private TextView tvGiaCategory;
        private TextView tvTrongLuongCategory;
        private LinearLayout layoutCategory;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.img_category);
            tvTenCategory = itemView.findViewById(R.id.tv_ten_category);
            tvGiaCategory = itemView.findViewById(R.id.tv_gia_category);
            tvTrongLuongCategory = itemView.findViewById(R.id.tv_trongluong_category);
            layoutCategory = itemView.findViewById(R.id.layout_category);
        }
    }
}
