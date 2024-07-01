package com.example.app_lotteria.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_lotteria.Activity.ChangePasswordUserActivity;
import com.example.app_lotteria.Activity.HistoryOrderActivity;
import com.example.app_lotteria.Activity.LoginActivity;
import com.example.app_lotteria.Helper.TinyDB;
import com.example.app_lotteria.databinding.FragmentUserBinding;

public class UserFragment extends Fragment {

    FragmentUserBinding binding;

    private TinyDB tinyDB;


    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangePasswordUserActivity.class);
                startActivity(intent);
            }
        });

        binding.historyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HistoryOrderActivity.class));
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tinyDB = new TinyDB(getContext());
                tinyDB.remove("User");
                tinyDB.remove("CartList");

                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}