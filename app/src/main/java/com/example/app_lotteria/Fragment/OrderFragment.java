package com.example.app_lotteria.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_lotteria.Adapter.CategoryAdapter;
import com.example.app_lotteria.Adapter.ItemCategoryAdapter;
import com.example.app_lotteria.Domain.CategoryDomain;
import com.example.app_lotteria.R;
import com.example.app_lotteria.databinding.FragmentHomeBinding;
import com.example.app_lotteria.databinding.FragmentOrderBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OrderFragment extends Fragment {

    private FragmentOrderBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentOrderBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initCategory();
    }

    private void initCategory() {
        DatabaseReference myRef = database.getReference("Category");

        ArrayList<CategoryDomain> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data:snapshot.getChildren()) {
                        list.add(data.getValue(CategoryDomain.class));
                    }

                    if (!list.isEmpty()){
                        binding.recyclerViewCategory.setLayoutManager(new GridLayoutManager(getContext(),2));

                        binding.recyclerViewCategory.setAdapter(new ItemCategoryAdapter(list));

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}