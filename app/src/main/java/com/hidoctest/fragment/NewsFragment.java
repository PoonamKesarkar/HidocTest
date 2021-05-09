package com.hidoctest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.hidoctest.R;
import com.hidoctest.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFragment extends Fragment {


    @BindView(R.id.navigationView)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.container)
    FrameLayout container;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_fragment, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {

        FragmentManager  ft = getChildFragmentManager();
        TopHeadlinesFragment fragment = new TopHeadlinesFragment();
        ft.beginTransaction().replace(R.id.container,fragment).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_topheadings:
                        TopHeadlinesFragment fragment = new TopHeadlinesFragment();
                        ft.beginTransaction().replace(R.id.container,fragment).commit();
                        break;
                    case R.id.navigation_politics:
                        RecentNewsFragment fragment1 = new RecentNewsFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("newskey","politics");
                        fragment1.setArguments(bundle);
                        ft.beginTransaction().replace(R.id.container,fragment1).commit();
                        break;
                    case R.id.navigation_entertainment:
                        RecentNewsFragment fragment2 = new RecentNewsFragment();
                        Bundle bundle1=new Bundle();
                        bundle1.putString("newskey","entertainment");
                        fragment2.setArguments(bundle1);
                        ft.beginTransaction().replace(R.id.container,fragment2).commit();
                        break;
                }
                return true;
            }
        });
    }





}
