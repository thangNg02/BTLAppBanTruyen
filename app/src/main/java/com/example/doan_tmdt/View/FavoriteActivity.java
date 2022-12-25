package com.example.doan_tmdt.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_tmdt.Adapter.BinhLuanAdapter;
import com.example.doan_tmdt.Adapter.FavoriteAdapter;
import com.example.doan_tmdt.Models.Favorite;
import com.example.doan_tmdt.Models.Product;
import com.example.doan_tmdt.Presenter.FavoritePresenter;
import com.example.doan_tmdt.Presenter.ProductPresenter;
import com.example.doan_tmdt.R;
import com.example.doan_tmdt.my_interface.FavoriteView;
import com.example.doan_tmdt.my_interface.IClickCTHD;
import com.example.doan_tmdt.my_interface.ProductView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
    }

}