package com.example.app_lotteria.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.app_lotteria.Domain.User;
import com.example.app_lotteria.Helper.TinyDB;
import com.example.app_lotteria.R;
import com.example.app_lotteria.databinding.ActivityChangePasswordUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordUserActivity extends AppCompatActivity {

    private DatabaseReference usersRef;
    private String password , newPassword, confirmPassword;

    TinyDB tinyDB;

    ActivityChangePasswordUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tinyDB = new TinyDB(this);

        usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(tinyDB.getObject("User", User.class).getUserName());

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
                binding.password.setText("");
                binding.newPassword.setText("");
                binding.confirmPassword.setText("");

            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void changePassword() {
        password = binding.password.getText().toString().trim();
        newPassword = binding.newPassword.getText().toString().trim();
        confirmPassword = binding.confirmPassword.getText().toString().trim();

        if (password.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(ChangePasswordUserActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(ChangePasswordUserActivity.this, "Mật khẩu mới và mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
        } else{

            // Kiểm tra mật khẩu hiện tại
            usersRef.child("password").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String savedPassword = dataSnapshot.getValue(String.class);

                    if (savedPassword != null && savedPassword.equals(password)) {
                        usersRef.child("password").setValue(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ChangePasswordUserActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ChangePasswordUserActivity.this, "Lỗi khi đổi mật khẩu", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(ChangePasswordUserActivity.this, "Mật khẩu hiện tại không đúng", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ChangePasswordUserActivity.this, "Lỗi khi đọc dữ liệu", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}