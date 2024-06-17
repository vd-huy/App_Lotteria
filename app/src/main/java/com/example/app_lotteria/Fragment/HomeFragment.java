package com.example.app_lotteria.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.app_lotteria.Adapter.BestsellerHomeAdapter;
import com.example.app_lotteria.Adapter.CategoryAdapter;
import com.example.app_lotteria.Adapter.SliderAdapter;
import com.example.app_lotteria.Domain.CategoryDomain;
import com.example.app_lotteria.Domain.ProductDomain;
import com.example.app_lotteria.Domain.SliderItems;
import com.example.app_lotteria.R;
import com.example.app_lotteria.databinding.ActivityMainBinding;
import com.example.app_lotteria.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false);
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

//        binding.locationBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Your Text Here!", Toast.LENGTH_SHORT).show();
//            }
//        });

        initBanner();
        initCategory();
        initBestSeller();
    }

    private void initBestSeller() {
        DatabaseReference myRef = database.getReference("Items");

        Query query = myRef.orderByChild("categoryID").equalTo(0);
        binding.progressBarBestSeller.setVisibility(View.VISIBLE);
        ArrayList<ProductDomain> list = new ArrayList<>();


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    for (DataSnapshot data:snapshot.getChildren()) {
                        list.add(data.getValue(ProductDomain.class));
                    }

                    Toast.makeText(getContext(), ""+ list.size(), Toast.LENGTH_SHORT).show();

                    if (!list.isEmpty()){
                        binding.recyclerViewBestSeller.setLayoutManager(new GridLayoutManager(getContext(),2));

                        binding.recyclerViewBestSeller.setAdapter(new BestsellerHomeAdapter(list));
                    }

                    binding.progressBarBestSeller.setVisibility(View.GONE);

//                    for (DataSnapshot data:snapshot.getChildren()) {
//                        list.add(data.getValue(ProductDomain.class));
//                    }
//
//                    if (!list.isEmpty()){
//                        binding.recyclerViewBestSeller.setLayoutManager(new GridLayoutManager(getContext(),2));
//                        binding.recyclerViewBestSeller.setAdapter(new BestsellerHomeAdapter(list));
//                    }
//
//                    binding.progressBarBestSeller.setVisibility(View.GONE);
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
        binding.progressBarCategory.setVisibility(View.VISIBLE);

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

                        binding.recyclerViewCategory.setAdapter(new CategoryAdapter(list));

                    }

                    binding.progressBarCategory.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initBanner(){
        DatabaseReference myRef = database.getReference("Banner");
        binding.progressBarBanner.setVisibility(View.VISIBLE);

        ArrayList<SliderItems> sliderItemsArrayList =  new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot data:snapshot.getChildren()) {
                        sliderItemsArrayList.add(data.getValue(SliderItems.class));
                    }

                    banner(sliderItemsArrayList);
                    binding.progressBarBanner.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void banner (ArrayList<SliderItems> sliderItemsArrayList){
        binding.viewpagerBanner.setAdapter(new SliderAdapter(sliderItemsArrayList,binding.viewpagerBanner));

        binding.viewpagerBanner.setClipToPadding(false);
        binding.viewpagerBanner.setClipChildren(false);
        binding.viewpagerBanner.setOffscreenPageLimit(3);
        binding.viewpagerBanner.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

//        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
//        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

//        binding.viewpagerBanner.setPageTransformer(compositePageTransformer);
    }
}