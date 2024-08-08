package com.example.btlAndroidG13.View.Admin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlAndroidG13.Models.User;
import com.example.btlAndroidG13.R;
import com.example.btlAndroidG13.my_interface.IClickCTHD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdminAdapter extends RecyclerView.Adapter<MessageAdminAdapter.ViewHolder> {
    private ArrayList<User> mlistUser; // Danh sách người dùng
    private Context context;
    private IClickCTHD iClickCTHD;

    public MessageAdminAdapter(Context context, ArrayList<User> mlistUser, IClickCTHD iClickCTHD) {
        this.context = context;
        this.mlistUser = mlistUser;
        this.iClickCTHD = iClickCTHD;
    }
    // Constructor và các phương thức khác của adapter

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate layout người dùng vào một item view của RecyclerView
        View userView = inflater.inflate(R.layout.users_message_items, parent, false);

        // Return ViewHolder với item view đã inflate
        return new ViewHolder(userView);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Lấy dữ liệu người dùng tại vị trí position
        User user = mlistUser.get(position);

        if (user.getName().isEmpty()){
            holder.tvUsername.setText("Username");
        } else {
            holder.tvUsername.setText(user.getName());
        }

        if (user.getAvatar().isEmpty()) {
            holder.chatImgViewUser.setImageResource(R.drawable.ic_user);
        } else {
            Picasso.get().load(user.getAvatar()).into(holder.chatImgViewUser);
        }

        holder.tvEmail.setText(user.getEmail());
        holder.relativeMessageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickCTHD.onClickCTHD(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlistUser.size(); // Trả về số lượng phần tử trong danh sách
    }

    // ViewHolder để giữ item view của RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUsername;
        public TextView tvEmail;
        public CircleImageView chatImgViewUser;
        public LinearLayout relativeMessageUser;
        public ViewHolder(View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvUsernameMessage);
            tvEmail = itemView.findViewById(R.id.tvEmailMessage);
            chatImgViewUser = itemView.findViewById(R.id.cir_users_message);
            relativeMessageUser = itemView.findViewById(R.id.relative_message_user);

        }
    }
}
