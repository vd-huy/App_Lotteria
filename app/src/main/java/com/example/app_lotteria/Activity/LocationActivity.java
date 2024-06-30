package com.example.app_lotteria.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_lotteria.Helper.TinyDB;
import com.example.app_lotteria.R;
import com.example.app_lotteria.databinding.ActivityLocationBinding;

public class LocationActivity extends AppCompatActivity {

    ActivityLocationBinding binding;

    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tinyDB = new TinyDB(this);

        getAddressExits();

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = binding.txtName.getText().toString();
                String address = binding.txtAddress.getText().toString();
                String phone = binding.phoneNumber.getText().toString();
                if ( name.isEmpty() || address.isEmpty()  || phone.isEmpty() ){
                    Toast.makeText(LocationActivity.this, "Vui Lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    tinyDB.putString("name", name);
                    tinyDB.putString("address", address);
                    tinyDB.putString("phone", phone);

//                    finish();

                    startActivity(new Intent(LocationActivity.this, MainActivity.class));
                }
            }
        });



    }

    public void getAddressExits(){
        if (!tinyDB.getString("address").isEmpty()){
            binding.txtName.setText(tinyDB.getString("name"));
            binding.phoneNumber.setText(tinyDB.getString("phone"));
            binding.txtAddress.setText(tinyDB.getString("address"));
        }
    }
}