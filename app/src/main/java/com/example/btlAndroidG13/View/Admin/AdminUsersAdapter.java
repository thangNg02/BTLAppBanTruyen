package com.example.btlAndroidG13.View.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlAndroidG13.Models.User;
import com.example.btlAndroidG13.R;
import com.example.btlAndroidG13.my_interface.IClickCTHD;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminUsersAdapter extends RecyclerView.Adapter<AdminUsersAdapter.ViewHolder>{

    private Context context;
    private ArrayList<User> mlistUser;
    private IClickCTHD iClickCTHD;

    public AdminUsersAdapter(Context context, ArrayList<User> mlistUser, IClickCTHD iClickCTHD) {
        this.context = context;
        this.mlistUser = mlistUser;
        this.iClickCTHD = iClickCTHD;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_admin_users, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = mlistUser.get(position);
        String status = user.getStatus();

        if (user.getName().equals("")){
            holder.tvNameUsers.setText("User");
        } else {
            holder.tvNameUsers.setText(user.getName());
        }

        holder.tvEmailUsers.setText(user.getEmail());

        if (user.getAvatar().equals("")){
            holder.cirUsers.setImageResource(R.drawable.ic_user);
        } else {
            Picasso.get().load(user.getAvatar()).into(holder.cirUsers);
        }

        if (status != null && status.equals("online")) {
            holder.imgStatus.setImageResource(R.drawable.dot_online);
        } else {
            holder.imgStatus.setImageResource(R.drawable.dot_offline);
        }

        holder.relativeItemUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickCTHD.onClickCTHD(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mlistUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CircleImageView cirUsers;
        private TextView tvNameUsers, tvEmailUsers;
        private ImageView imgDeleteUsers, imgStatus;
        private RelativeLayout relativeItemUsers;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            cirUsers = itemView.findViewById(R.id.cir_users);
            tvNameUsers = itemView.findViewById(R.id.tv_name_users);
            tvEmailUsers = itemView.findViewById(R.id.tv_email_users);
            imgStatus = itemView.findViewById(R.id.imgV_users);
            imgDeleteUsers = itemView.findViewById(R.id.imgV_delete_user);
            relativeItemUsers = itemView.findViewById(R.id.relative_item_users);
        }
    }
}
