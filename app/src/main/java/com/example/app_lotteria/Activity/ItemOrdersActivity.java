package com.example.app_lotteria.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_lotteria.Adapter.OrderHistoryAdapter;
import com.example.app_lotteria.Adapter.ProductOrderAdapter;
import com.example.app_lotteria.Domain.OrderDomain;
import com.example.app_lotteria.Fragment.UserFragment;
import com.example.app_lotteria.Helper.TinyDB;

import com.example.app_lotteria.databinding.ActivityItemOrdersBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class ItemOrdersActivity extends AppCompatActivity {

    ActivityItemOrdersBinding binding;

    TinyDB tinyDB;

    private String status;

    private OrderDomain orderDomain;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    DecimalFormat f = new DecimalFormat("#,###");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tinyDB = new TinyDB(ItemOrdersActivity.this);

        orderDomain = (OrderDomain) getIntent().getSerializableExtra("order");
        Log.e("onCreate: ", tinyDB.getString("role"));

        if (tinyDB.getString("role").equals("user") ){
            binding.updateControl.setVisibility(View.GONE);
        }else {
            binding.updateControl.setVisibility(View.VISIBLE);

            ArrayList<String> listStatus = new ArrayList<>();
            listStatus.add("Đã đặt hàng");
            listStatus.add("Đang giao hàng");
            listStatus.add("Giao hàng không thành công");
            listStatus.add("Giao hàng thành công");

            ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listStatus);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

            binding.dropdownStatus.setAdapter(adapter);

            binding.dropdownStatus.setSelection(getIndexSelected(binding.dropdownStatus,orderDomain.getStatus()));

            binding.dropdownStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    status = binding.dropdownStatus.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeStatusOrder(status);
                }
            });



        }

        binding.recyclerViewOrder.setLayoutManager(new LinearLayoutManager(ItemOrdersActivity.this,LinearLayoutManager.VERTICAL,false));
        binding.recyclerViewOrder.setAdapter(new ProductOrderAdapter(orderDomain.getListProduct()));

        binding.orderId.setText("#"+orderDomain.getIdOrder());
        binding.orderTime.setText(orderDomain.getTimeOrder());
        binding.orderStatus.setText(orderDomain.getStatus());
        binding.orderPrice.setText(f.format(orderDomain.getTotal()) + "₫");

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private int getIndexSelected(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    private void changeStatusOrder(String status){
        DatabaseReference myRef = database.getReference().child("Orders").child(orderDomain.getUserName()).child(orderDomain.getIdOrder());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myRef.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ItemOrdersActivity.this, "Cập nhật trạng thái thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ItemOrdersActivity.this, HistoryOrderActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ItemOrdersActivity.this, "Lỗi khi cập nhật trạng thái đơn hàng", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}