package com.example.app_lotteria.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.app_lotteria.Activity.MainActivity;
import com.example.app_lotteria.Domain.CategoryDomain;
import com.example.app_lotteria.Fragment.OrderFragment;
import com.example.app_lotteria.Helper.OnClickSendPosition;
import com.example.app_lotteria.R;
import com.example.app_lotteria.databinding.ViewholderCategoryBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {

    ArrayList<CategoryDomain> items;
    private Context context;
    private ViewPager2 viewPager;

    private OnClickSendPosition listener;


    public CategoryAdapter(ArrayList<CategoryDomain> items,ViewPager2 viewPager) {
        this.items = items;
        this.viewPager = viewPager;
    }

    @NonNull
    @Override
    public CategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderCategoryBinding binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context),parent,false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.titleCategory.setText(items.get(position).getTitle());

        Glide.with(context)
                .load(items.get(position).getPicUrl())
                .into(holder.binding.iconCategory);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    // Chuyển dữ liệu cần thiết đến Fragment
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);

                    ViewPagerAdapter adapter = (ViewPagerAdapter) viewPager.getAdapter();
                    if (adapter!=null){
                        OrderFragment orderFragment = (OrderFragment) adapter.createFragment(1);
                        orderFragment.setArguments(bundle);
                        viewPager.setCurrentItem(1,true);
                    }


            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderCategoryBinding binding;
        public Viewholder( ViewholderCategoryBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }

    }
}
