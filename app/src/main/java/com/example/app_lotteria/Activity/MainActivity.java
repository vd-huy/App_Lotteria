package com.example.app_lotteria.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_lotteria.Adapter.ViewPagerAdapter;
import com.example.app_lotteria.R;
import com.example.app_lotteria.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        binding.viewPager2.setAdapter(adapter);

        //disable swiping in ViewPager2
        binding.viewPager2.setUserInputEnabled(false);


        // set text and icon tablayout

        new TabLayoutMediator(binding.tabLayout, binding.viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Trang chủ");
                        tab.setIcon(R.drawable.home);
                        break;
                    case 1:
                        tab.setText("Đặt hàng");
                        tab.setIcon(R.drawable.oder_icon);
                        break;
                    case 2:
                        tab.setText("Tài khoản");
                        tab.setIcon(R.drawable.user);
                        break;
                }
            }
        }).attach();


    }
}