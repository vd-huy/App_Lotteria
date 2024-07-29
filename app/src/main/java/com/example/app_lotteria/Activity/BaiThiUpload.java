package com.example.app_lotteria.Activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.app_lotteria.Domain.DataClass;
import com.example.app_lotteria.Domain.NhanVien;
import com.example.app_lotteria.R;
import com.example.app_lotteria.databinding.ActivityBaiThiUploadBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class BaiThiUpload extends AppCompatActivity {

    private ActivityBaiThiUploadBinding binding;

    private Uri imageUri;

    final private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("nhanVien");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBaiThiUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.progressBar.setVisibility(View.INVISIBLE);



        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                binding.progressBar.setVisibility(View.VISIBLE);
                String name = binding.editTextName.getText().toString();
                String chucVu = binding.editTextChucVu.getText().toString();
                double hsl = Double.parseDouble(binding.editTextHSL.getText().toString());
                double lcb = Double.parseDouble(binding.editTextLuongCoBan.getText().toString());
                double phuCap = Double.parseDouble(binding.editTextPhuCap.getText().toString());
                String id = System.currentTimeMillis()+"";
                double luong = (hsl+phuCap) * lcb;

                databaseReference.child(id).setValue(new NhanVien(id,name,chucVu,hsl,lcb,phuCap,luong));

                binding.progressBar.setVisibility(View.INVISIBLE);
                finish();

            }
        });
    }


}