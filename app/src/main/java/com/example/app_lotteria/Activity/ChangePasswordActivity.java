package com.example.app_lotteria.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_lotteria.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText edtpassword, edtnewPassword, edtconfirmNewPassword;
    private Button btnChangePassword;
    private DatabaseReference usersRef;
    private String username = "USER_name";
    String stPassword , stNewPassword, stConfirmNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capnhatmatkhau);

        edtpassword = (EditText) findViewById(R.id.Password);
        edtnewPassword = (EditText) findViewById(R.id.Passwordnew);
        edtconfirmNewPassword = (EditText) findViewById(R.id.confirmPasswordnew);
        btnChangePassword = (Button) findViewById(R.id.btnCapnhatmatkhau);

        usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Trang");

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });
    }


    private void changePassword() {
         stPassword = edtpassword.getText().toString().trim();
         stNewPassword = edtnewPassword.getText().toString().trim();
         stConfirmNewPassword = edtconfirmNewPassword.getText().toString().trim();

        if (TextUtils.isEmpty(stPassword) || TextUtils.isEmpty(stNewPassword) || TextUtils.isEmpty(stConfirmNewPassword)) {
            Toast.makeText(ChangePasswordActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!stNewPassword.equals(stConfirmNewPassword)) {
            Toast.makeText(ChangePasswordActivity.this, "Mật khẩu mới và mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu hiện tại
        usersRef.child("password").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String savedPassword = dataSnapshot.getValue(String.class);

                if (savedPassword != null && savedPassword.equals(stPassword)) {
                    usersRef.child("password").setValue(stNewPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, "Lỗi khi đổi mật khẩu", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Mật khẩu hiện tại không đúng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChangePasswordActivity.this, "Lỗi khi đọc dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
