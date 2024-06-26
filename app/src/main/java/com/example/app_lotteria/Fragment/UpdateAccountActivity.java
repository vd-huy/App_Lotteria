//
//package com.example.app_lotteria.Fragment;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//
//import com.example.app_lotteria.R;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//public class UpdateAccountActivity extends Fragment {
//
//    private View mView;
//    private EditText newName, newPhone;
//    private Button btnUpdateAccount;
//    private ProgressDialog progressDialog;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mView = inflater.inflate(R.layout.activity_update_account, container, false);
//
//        initUser();
//
//        btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickUpdateAccount();
//            }
//        });
//        return mView;
//    }
//
//    private void initUser() {
//        progressDialog = new ProgressDialog(getActivity());
//        newName = mView.findViewById(R.id.newName);
//        newPhone = mView.findViewById(R.id.newPhone);
//        btnUpdateAccount = mView.findViewById(R.id.btnUpdateAccount);
//    }
//
//    private void onClickUpdateAccount() {
//        String strNewAccount = newName.getText().toString().trim();
//        progressDialog.show();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        user.updateProfile(strNewAccount)
//                .addOnFailureListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//                            Toast.makeText(getActivity(),"User name update", Toast.LENGTH_SHORT).show();
//                            progressDialog.show();
//                        }
//                    }
//
//                });
//
//    }
//}
package com.example.app_lotteria.Fragment;

import android.app.ProgressDialog;
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
import androidx.fragment.app.Fragment;

import com.example.app_lotteria.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateAccountActivity extends Fragment {

    private View mView;
    private EditText Name, newName, newPhone;
    private Button btnUpdateAccount;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.activity_update_account, container, false);

        initUser();

        btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickUpdateAccount();
            }
        });
        return mView;
    }

    private void initUser() {
        progressDialog = new ProgressDialog(getActivity());
        Name = mView.findViewById(R.id.Name);
        newName = mView.findViewById(R.id.newName);
        newPhone = mView.findViewById(R.id.newPhone);
        btnUpdateAccount = mView.findViewById(R.id.btnUpdateAccount);
    }

    private void onClickUpdateAccount() {
        String strName = Name.getText().toString().trim();
        String strNewName = newName.getText().toString().trim();
        String strNewPhone = newPhone.getText().toString().trim();

        if (TextUtils.isEmpty(strNewName) || TextUtils.isEmpty(strNewPhone)) {
            Toast.makeText(getActivity(), "User update", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(getActivity(), "Account updated successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getActivity(), "Failed to update phone number", Toast.LENGTH_SHORT).show();
                                        }
                                        progressDialog.dismiss();
                                    });
                        } else {
                            Toast.makeText(getActivity(), "Failed to update profile", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}

