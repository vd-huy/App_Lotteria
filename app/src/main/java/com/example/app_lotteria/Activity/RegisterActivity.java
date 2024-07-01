package com.example.app_lotteria.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.app_lotteria.Domain.User;
import com.example.app_lotteria.databinding.ActivityRegisterBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = String.valueOf(binding.fullName.getText());
                String userName = String.valueOf(binding.userName.getText());
                String password = String.valueOf(binding.password.getText());
                String confirmPassword = String.valueOf(binding.confirmPassword.getText());

                if (confirmPassword.isEmpty() || password.isEmpty() || userName.isEmpty() || fullName.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }else if (!password.equals(confirmPassword)){
                    Toast.makeText(RegisterActivity.this, "Mật khẩu xác nhận không chính xác ", Toast.LENGTH_SHORT).show();
                } else{
                    DatabaseReference myRef = database.getReference();
                    User user = new User(fullName,userName,password);
                    // check aviriable email regiter
                    myRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(userName)){
                                Toast.makeText(RegisterActivity.this, "userName đã được tồn tại", Toast.LENGTH_SHORT).show();
                            }else {
                                // sending data to firebase
//                                myRef.child("users").child(userName).child("fullname").setValue(user.getFullName());
//                                myRef.child("users").child(userName).child("userName").setValue(user.getUserName());
//                                myRef.child("users").child(userName).child("password").setValue(user.getPassword());

                                myRef.child("Users").child(userName).setValue(user);
                                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}