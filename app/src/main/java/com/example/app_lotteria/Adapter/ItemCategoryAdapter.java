package com.example.app_lotteria.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_lotteria.Domain.CategoryDomain;
import com.example.app_lotteria.databinding.ItemCategoryBinding;

import java.util.ArrayList;

public class ItemCategoryAdapter extends RecyclerView.Adapter<ItemCategoryAdapter.Viewholder> {

    ArrayList<CategoryDomain> list;
    private Context context;

    public ItemCategoryAdapter(ArrayList<CategoryDomain> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ItemCategoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(context), parent, false);


        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCategoryAdapter.Viewholder holder, int position) {
        holder.binding.nameCategory.setText(list.get(position).getTitle());

        Glide.with(context)
                .load(list.get(position).getPicUrl())
                .into(holder.binding.imageCategory);



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ItemCategoryBinding binding;

        public Viewholder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
