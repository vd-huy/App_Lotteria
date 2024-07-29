package com.example.app_lotteria.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.app_lotteria.Adapter.BaiThiAdapter;
import com.example.app_lotteria.Adapter.CategoryAdapter;
import com.example.app_lotteria.Domain.CategoryDomain;
import com.example.app_lotteria.Domain.DataClass;
import com.example.app_lotteria.Domain.NhanVien;
import com.example.app_lotteria.Domain.OrderDomain;
import com.example.app_lotteria.Helper.OnDeleteListener;
import com.example.app_lotteria.R;
import com.example.app_lotteria.databinding.ActivityBaiThiShowListBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;

public class BaiThiShowList extends AppCompatActivity {

    ActivityBaiThiShowListBinding binding;

    BaiThiAdapter adapter;

    ArrayList<NhanVien> list = new ArrayList<>();


     private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("nhanVien");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaiThiShowListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new BaiThiAdapter(list, BaiThiShowList.this);

        initDataClassList();

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BaiThiShowList.this, BaiThiUpload.class);
                startActivity(intent);
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.orderByChild("chucVu").equalTo("Trưởng Phòng").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot data:snapshot.getChildren()) {
                                list.add(data.getValue(NhanVien.class));
                            }

                            if (!list.isEmpty()){
                                binding.recyclerView.setLayoutManager(new LinearLayoutManager(BaiThiShowList.this,LinearLayoutManager.VERTICAL,false));
                                binding.recyclerView.setAdapter(adapter);

                            }

                            adapter.notifyDataSetChanged();
                        }else{
                            adapter.notifyDataSetChanged();
                        }                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    private void initDataClassList() {

        databaseReference.orderByChild("luong").startAt(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot data:snapshot.getChildren()) {
                        list.add(data.getValue(NhanVien.class));
                    }

                    if (!list.isEmpty()){
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(BaiThiShowList.this,LinearLayoutManager.VERTICAL,false));
                        binding.recyclerView.setAdapter(adapter);

                    }

                    adapter.notifyDataSetChanged();
                }else{
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}