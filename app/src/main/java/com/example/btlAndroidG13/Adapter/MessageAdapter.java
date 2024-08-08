package com.example.btlAndroidG13.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlAndroidG13.Models.Chat;
import com.example.btlAndroidG13.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context context;
    private List<Chat> mListChat;
    private String imageURL;

    private String iduser = "";

    FirebaseUser firebaseUser;

    public MessageAdapter(Context context, List<Chat> mListChat, String imageURL) {
        this.context = context;
        this.mListChat = mListChat;
        this.imageURL = imageURL;
    }

    public void Who(String a){
        iduser = a;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

        Chat chat = mListChat.get(position);

        // Khi đặt tên giống nhau ta sẽ settext cho cả bên left và right
        holder.tvShowMsg.setText(chat.getMessage());

        // Convert dp to pixels
        float scale = context.getResources().getDisplayMetrics().density;
        int marginInDp = 10; // Giá trị margin bạn muốn
        int marginInPixels = (int) (marginInDp * scale + 0.5f);

        // Set bottom margin for tvShowMsg
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.tvShowMsg.getLayoutParams();
        layoutParams.bottomMargin = marginInPixels;
        holder.tvShowMsg.setLayoutParams(layoutParams);

//        if (imageURL != null) {
//            if (imageURL.equals("default")) {
//                holder.imgChatRight.setImageResource(R.drawable.user);
//            } else {
//                Glide.with(context).load(imageURL).into(holder.imgChatRight);
//            }
//        } else {
//            // Xử lý khi imageURL là null, có thể set một ảnh mặc định khác hoặc ẩn imageView
//        }

        if (position == mListChat.size() - 1){
            if (chat.isIsseen()){
                holder.tvSeen.setText("Đã xem");
            } else {
                holder.tvSeen.setText("Đã gửi");


            }
        } else {
            holder.tvSeen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mListChat.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvShowMsg;
        private final ImageView imgChatRight, imgChatLeft;

        ImageView imgItemUser;
        public TextView tvSeen;
        public ViewHolder(View itemView) {
            super(itemView);

            // Chú ý tvShowMsg và imgChat của cả bên left và right phải đặt tên giống nhau
            tvShowMsg = itemView.findViewById(R.id.tv_show_msg);
            imgChatLeft = itemView.findViewById(R.id.img_chat_left);
            imgChatRight = itemView.findViewById(R.id.img_chat_right);

            imgItemUser = itemView.findViewById(R.id.img_item_user);
            tvSeen = itemView.findViewById(R.id.tv_seen);

        }
    }

    @Override
    public int getItemViewType(int position) {

        Log.d("iduser", iduser);
        if (iduser.equals(mListChat.get(position).getReceiver())) {
            Log.d("vitri", "right user");
            return MSG_TYPE_RIGHT;
        } else {
            Log.d("vitri", "left user");
            return MSG_TYPE_LEFT;
        }

    }

}
