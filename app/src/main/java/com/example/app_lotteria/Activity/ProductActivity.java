package com.example.app_lotteria.Activity;

import android.content.SharedPreferences;
import android.graphics.Paint;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;

import com.example.app_lotteria.Domain.ProductDomain;

import com.example.app_lotteria.Helper.ManagmentCart;
import com.example.app_lotteria.databinding.ActivityProductBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    ActivityProductBinding binding;

    ProductDomain object;

    private int numberOder = 1;

    private ManagmentCart managmentCart;

    DecimalFormat f = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProductBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        object  = (ProductDomain) getIntent().getSerializableExtra("object");
        managmentCart = new ManagmentCart(this);

        getDetailProduct();

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                object.setNumberinCart(numberOder);
                managmentCart.insertFood(object);
            }
        });

    }

    private void getDetailProduct() {

        Glide.with(this)
                .load(object.getPicUrl())
                .into(binding.image);

        binding.name.setText(object.getTitle());
        binding.oldPrice.setText(String.valueOf(f.format(object.getOldPrice())) + "₫");
        binding.oldPrice.setPaintFlags(binding.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        binding.price.setText(String.valueOf(f.format(object.getPrice()) + "₫"));
        binding.description.setText(object.getDescription().replace("\\n", "\n"));
        binding.intro.setText(object.getIntro());
    }

}