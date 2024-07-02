package com.example.app_lotteria.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_lotteria.Domain.CategoryDomain;
import com.example.app_lotteria.databinding.ViewholderCategoryBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Viewholder> {

    ArrayList<CategoryDomain> items;
    private Context context;


    public CategoryAdapter(ArrayList<CategoryDomain> items) {
        this.items = items;
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
