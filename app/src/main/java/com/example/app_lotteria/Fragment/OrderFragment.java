package com.example.app_lotteria.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.app_lotteria.Adapter.BestsellerHomeAdapter;
import com.example.app_lotteria.Adapter.ItemCategoryAdapter;
import com.example.app_lotteria.Domain.CategoryDomain;
import com.example.app_lotteria.Domain.ProductDomain;
import com.example.app_lotteria.Helper.OnItemClickListener;
import com.example.app_lotteria.R;
import com.example.app_lotteria.databinding.FragmentOrderBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OrderFragment extends Fragment implements OnItemClickListener{

    private FragmentOrderBinding binding;
    ArrayList<CategoryDomain>  listCategory;
    ItemCategoryAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        initCategory();
        adapter = new ItemCategoryAdapter(listCategory,this);

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


        initProduct();

    }

    private void initProduct() {
        DatabaseReference myRef = database.getReference("Items");

        Query query = myRef.orderByChild("categoryID").equalTo(0);
        binding.progressBarProduct.setVisibility(View.VISIBLE);
        ArrayList<ProductDomain> list = new ArrayList<>();


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    for (DataSnapshot data:snapshot.getChildren()) {
                        list.add(data.getValue(ProductDomain.class));
                    }

                    if (!list.isEmpty()){
                        binding.recyclerViewProduct.setLayoutManager(new GridLayoutManager(getContext(),2));

                        binding.recyclerViewProduct.setAdapter(new BestsellerHomeAdapter(list));
                    }

                    binding.progressBarProduct.setVisibility(View.GONE);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initCategory() {
        DatabaseReference myRef = database.getReference("Category");

        listCategory = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data:snapshot.getChildren()) {
                        listCategory.add(data.getValue(CategoryDomain.class));
                    }

                    if (!listCategory.isEmpty()) {
                        binding.recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

                        binding.recyclerViewCategory.setAdapter(adapter);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onItemClick(CategoryDomain data, int position) {
        DatabaseReference myRef = database.getReference("Items");

        Query query = myRef.orderByChild("categoryID").equalTo(data.getId());
        binding.recyclerViewProduct.setVisibility(View.GONE);
        binding.progressBarProduct.setVisibility(View.VISIBLE);
        ArrayList<ProductDomain> list = new ArrayList<>();


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    for (DataSnapshot data:snapshot.getChildren()) {
                        list.add(data.getValue(ProductDomain.class));
                    }

                    if (!list.isEmpty()){
                        binding.recyclerViewProduct.setLayoutManager(new GridLayoutManager(getContext(),2));

                        binding.recyclerViewProduct.setAdapter(new BestsellerHomeAdapter(list));
                    }

                    binding.progressBarProduct.setVisibility(View.GONE);
                    binding.recyclerViewProduct.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Không tìm thấy sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });
    }
}