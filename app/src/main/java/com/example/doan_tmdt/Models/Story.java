package com.example.doan_tmdt.Models;

import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.doan_tmdt.Adapter.LichSuSearchAdapter;
import com.example.doan_tmdt.View.SearchActivity;
import com.example.doan_tmdt.my_interface.IClickCTHD;
import com.example.doan_tmdt.my_interface.IProduct;
import com.example.doan_tmdt.my_interface.IStory;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Story {

    private String noidungtimkiem;
    private IStory callback;
    private FirebaseFirestore db;

    public Story(IStory callback) {
        this.callback=callback;
        db=FirebaseFirestore.getInstance();
    }

    public Story(String noidungtimkiem) {
        this.noidungtimkiem = noidungtimkiem;
    }

    public String getNoidungtimkiem() {
        return noidungtimkiem;
    }

    public void setNoidungtimkiem(String noidungtimkiem) {
        this.noidungtimkiem = noidungtimkiem;
    }

    public void HandleGetStory(String iduser){
        db.collection("LichSuTimKiem").document(iduser).collection("Story")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot q: queryDocumentSnapshots){
                    callback.getDataStory(q.getString("noidungtimkiem"));
                }
            }
        });
    }
}
