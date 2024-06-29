
package com.example.app_lotteria.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.app_lotteria.Activity.LoginActivity;
import com.example.app_lotteria.Activity.RegisterActivity;
import com.example.app_lotteria.R;
import com.example.app_lotteria.databinding.ActivityLoginBinding;
import com.example.app_lotteria.databinding.ActivityUpdateAccountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateAccountActivity extends AppCompatActivity {

    private View mView;
    private EditText newName, newName1, newPhone;
    private Button btnUpdateAccount;
    private ProgressDialog progressDialog;
    private ActivityUpdateAccountBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        initUser();
        binding.btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UpdateAccountActivity.this, UserFragment.class);
                startActivity(intent);
            }
        });
    }

    private void initUser() {
        progressDialog = new ProgressDialog(this);
        newName = binding.newName;
        newName1 = binding.newName1;
        newPhone = binding.newPhone;
        btnUpdateAccount = binding.btnUpdateAccount;
    }
    private void onClickUpdateAccount() {
        String strNewName = newName.getText().toString().trim();
        String strNewName1 = newName1.getText().toString().trim();
        String strNewPhone = newPhone.getText().toString().trim();


        if (TextUtils.isEmpty(strNewName) || TextUtils.isEmpty(strNewPhone)) {
            Toast.makeText(UpdateAccountActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Update user name
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(strNewName)
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Update phone number in the database
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
                            databaseReference.child("phone").setValue(strNewPhone)
                                    .addOnCompleteListener(dbTask -> {
                                        if (dbTask.isSuccessful()) {
                                            Toast.makeText(UpdateAccountActivity.this, "Account updated successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(UpdateAccountActivity.this, UserFragment.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(UpdateAccountActivity.this, "Failed to update phone number", Toast.LENGTH_SHORT).show();
                                        }
                                        progressDialog.dismiss();
                                    });
                        } else {
                            Toast.makeText(UpdateAccountActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}

