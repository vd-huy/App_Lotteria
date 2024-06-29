package com.example.app_lotteria.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_lotteria.Adapter.CartAdapter;
import com.example.app_lotteria.Domain.OrderDomain;
import com.example.app_lotteria.Domain.User;
import com.example.app_lotteria.Helper.ChangeNumberItemsListener;
import com.example.app_lotteria.Helper.ManagerUser;
import com.example.app_lotteria.Helper.ManagmentCart;
import com.example.app_lotteria.Helper.TinyDB;
import com.example.app_lotteria.R;
import com.example.app_lotteria.databinding.ActivityCartBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    ActivityCartBinding binding;

    private ManagmentCart managmentCart;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private TinyDB tinyDB;

    private User user;

    DecimalFormat f = new DecimalFormat("#,###");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managmentCart = new ManagmentCart(this);
        tinyDB = new TinyDB(this);

        user = tinyDB.getObject("User", User.class);

        if (user == null) {
            startActivity(new Intent(CartActivity.this,LoginActivity.class));
        }

        initCartList();

        calculatorcart();

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.btnOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });


    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_confirm_information, null);

        TextView name = view.findViewById(R.id.name);
        TextView address = view.findViewById(R.id.address);
        TextView phone = view.findViewById(R.id.phone);

        name.setText(tinyDB.getString("name"));
        address.setText(tinyDB.getString("address"));
        phone.setText(tinyDB.getString("phone"));

        builder.setView(view)
                .setTitle("Xác nhận thông tin")
                .setPositiveButton("Xác nhận", (dialog, which) -> {
                    DatabaseReference myRef = database.getReference();
                    OrderDomain order = new OrderDomain(user.getUserName(),tinyDB.getString("name"),managmentCart.getListCart(),managmentCart.getTotalFee(),"Đang giao", tinyDB.getString("address"), tinyDB.getString("phone"));

                    myRef.child("Orders").child(user.getUserName()).child(String.valueOf(order.getIdOrder())).setValue(order);
                    Toast.makeText(CartActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    tinyDB.remove("CartList");
                    binding.emptyCart.setVisibility(View.VISIBLE);
                    binding.recyclerViewListProductCart.setVisibility(View.GONE);
                    binding.btnOrder.setVisibility(View.GONE);
                    dialog.dismiss();
                })
                .setNegativeButton("Thay đổi", (dialog, which) -> {
                    Intent intent = new Intent(getApplicationContext(),LocationActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void initCartList() {
            if (managmentCart.getListCart().isEmpty()){
                binding.emptyCart.setVisibility(View.VISIBLE);
                binding.recyclerViewListProductCart.setVisibility(View.GONE);
                binding.btnOrder.setVisibility(View.GONE);
            }else{
                binding.emptyCart.setVisibility(View.GONE);
                binding.recyclerViewListProductCart.setVisibility(View.VISIBLE);
            }

            binding.recyclerViewListProductCart.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.recyclerViewListProductCart.setAdapter(new CartAdapter(managmentCart.getListCart(), this, new ChangeNumberItemsListener() {
            @Override
            public void changed() {
                calculatorcart();
            }
        }));
    }

    private void calculatorcart() {
        binding.txtPrice.setText(String.valueOf(f.format(managmentCart.getTotalFee())) + "₫");
    }
}