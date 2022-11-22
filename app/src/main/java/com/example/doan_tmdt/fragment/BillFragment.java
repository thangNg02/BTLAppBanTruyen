package com.example.doan_tmdt.fragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.doan_tmdt.Adapter.ViewPagerAdapter;
import com.example.doan_tmdt.R;
import com.google.android.material.tabs.TabLayout;

public class BillFragment extends Fragment {
    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bill, container, false);

        InitWidget();
        return view;
    }

    private void InitWidget() {
        tabLayout = view.findViewById(R.id.tab_Layout);
        viewPager = view.findViewById(R.id.view_Pager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getParentFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}