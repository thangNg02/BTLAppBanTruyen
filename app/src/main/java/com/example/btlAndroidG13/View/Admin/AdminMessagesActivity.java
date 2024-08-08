package com.example.btlAndroidG13.View.Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlAndroidG13.Models.User;
import com.example.btlAndroidG13.Presenter.UserPresenter;
import com.example.btlAndroidG13.R;
import com.example.btlAndroidG13.my_interface.IClickCTHD;
import com.example.btlAndroidG13.my_interface.UserView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminMessagesActivity extends AppCompatActivity implements UserView {

    private ImageView imgBackChat;
    private RecyclerView rcvChats;
    private MessageAdminAdapter adapter;
    private ArrayList<User> mlistUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private UserPresenter userPresenter;

    DatabaseReference reference1, reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        InitWidget();
        Init();
        Event();

    }

    private void Event() {
        imgBackChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void Init() {
        mlistUser = new ArrayList<>();
    }

    @SuppressLint("WrongViewCast")
    private void InitWidget() {
        imgBackChat = findViewById(R.id.img_back_chat);
        rcvChats = findViewById(R.id.recycler_view_chat);

        userPresenter = new UserPresenter(AdminMessagesActivity.this);
        db.collection("IDUser").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot q : queryDocumentSnapshots){
                    userPresenter.HandleGetUsers(q.getString("iduser"), q.getString("email"));
                }
            }
        });

    }

    @Override
    public void OnSucess() {

    }

    @Override
    public void OnFail() {

    }

    @Override
    public void getDataUser(String id, String email, String name, String address, String phone, String date, String gender, String avatar) {

        mlistUser.add(new User(id, email, name, address, phone, date, gender, avatar));
        adapter = new MessageAdminAdapter(AdminMessagesActivity.this, mlistUser, new IClickCTHD() {
            @Override
            public void onClickCTHD(int pos) {

            }
        });
        rcvChats.setLayoutManager(new GridLayoutManager(AdminMessagesActivity.this, 1));
        rcvChats.setAdapter(adapter);
    }



}