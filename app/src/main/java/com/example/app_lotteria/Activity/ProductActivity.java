package com.example.app_lotteria.Activity;

import android.content.SharedPreferences;
import android.graphics.Paint;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;

import com.example.app_lotteria.Domain.ProductDomain;

import com.example.app_lotteria.Helper.ManagerCart;
import com.example.app_lotteria.Helper.ManagerUser;
import com.example.app_lotteria.databinding.ActivityProductBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    ActivityProductBinding binding;

    ProductDomain object;

    DecimalFormat f = new DecimalFormat("#,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProductBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        object  = (ProductDomain) getIntent().getSerializableExtra("object");

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
               ArrayList<ProductDomain> list = ManagerCart.getObjectList(ProductActivity.this);
               list.add(object);
               ManagerCart.saveObjectList(ProductActivity.this,list);

                Toast.makeText(ProductActivity.this, "Add to Cart", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDetailProduct() {


//        RequestOptions options = new RequestOptions();
//        options= options.transform(new CenterCrop());

        Glide.with(this)
                .load(object.getPicUrl())
//                .apply(options)
                .into(binding.image);

        binding.name.setText(object.getTitle());
        binding.oldPrice.setText(String.valueOf(f.format(object.getOldPrice())) + "₫");
        binding.oldPrice.setPaintFlags(binding.oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        binding.price.setText(String.valueOf(f.format(object.getPrice()) + "₫"));
        binding.description.setText(object.getDescription().replace("\\n", "\n"));
        binding.intro.setText(object.getIntro());
    }

}