package com.example.btlAndroidG13.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.btlAndroidG13.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class NotifyFragment extends Fragment {
    private LottieAnimationView lottie;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();;
    private TextView appname;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notify, container, false);
        InitWidget();
        animate();
        return view;
    }

    private void InitWidget() {
        appname = view.findViewById(R.id.appname);
        lottie = view.findViewById(R.id.lottie);
    }

    private void animate() {
        appname.animate().translationY(-2100).setDuration(2700).setStartDelay(0);
//        lottie.animate().translationX(2000).setDuration(2000).setStartDelay(2900);

    }

}