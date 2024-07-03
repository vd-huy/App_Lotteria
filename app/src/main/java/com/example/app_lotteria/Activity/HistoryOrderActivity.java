package com.example.app_lotteria.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_lotteria.Adapter.CategoryAdapter;
import com.example.app_lotteria.Adapter.OrderHistoryAdapter;
import com.example.app_lotteria.Domain.CategoryDomain;
import com.example.app_lotteria.Domain.OrderDomain;
import com.example.app_lotteria.Domain.User;
import com.example.app_lotteria.Fragment.UserFragment;
import com.example.app_lotteria.Helper.TinyDB;
import com.example.app_lotteria.R;
import com.example.app_lotteria.databinding.ActivityHistoryOrderBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryOrderActivity extends AppCompatActivity {

    ActivityHistoryOrderBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    TinyDB tinyDB;

    ArrayList<OrderDomain> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tinyDB = new TinyDB(HistoryOrderActivity.this);

        initHistoryOrder();


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryOrderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initHistoryOrder() {
        DatabaseReference myRef = database.getReference();

        String role = tinyDB.getObject("User", User.class).getRole();

        Log.e( "initHistoryOrder: ", role );

        list = new ArrayList<>();

        switch (role){
            case "user" :
                myRef.child("Orders").child(tinyDB.getObject("User", User.class).getUserName()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data:snapshot.getChildren()) {
                            list.add(data.getValue(OrderDomain.class));
                        }

                        if (!list.isEmpty()){
                            binding.noProduct.setVisibility(View.GONE);
                            binding.recyclerViewOrder.setLayoutManager(new LinearLayoutManager(HistoryOrderActivity.this,LinearLayoutManager.VERTICAL,false));
                            binding.recyclerViewOrder.setAdapter(new OrderHistoryAdapter(list));
                        }else {
                            binding.noProduct.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                break;
            case "admin":
                myRef.child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                            String key = dataSnapshot.getKey();
                            myRef.child("Orders").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot data:snapshot.getChildren()) {
                                        list.add(data.getValue(OrderDomain.class));
                                    }

                                    if (!list.isEmpty()){
                                        binding.noProduct.setVisibility(View.GONE);
                                        binding.recyclerViewOrder.setLayoutManager(new LinearLayoutManager(HistoryOrderActivity.this,LinearLayoutManager.VERTICAL,false));
                                        binding.recyclerViewOrder.setAdapter(new OrderHistoryAdapter(list));
                                    }else{
                                        binding.noProduct.setVisibility(View.VISIBLE);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;

            default:
                break;
        }



    }


}