package com.example.app_lotteria.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_lotteria.Domain.CategoryDomain;
import com.example.app_lotteria.Helper.OnClickSendPosition;
import com.example.app_lotteria.Helper.OnItemClickListener;
import com.example.app_lotteria.R;
import com.example.app_lotteria.databinding.ItemCategoryBinding;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemCategoryAdapter extends RecyclerView.Adapter<ItemCategoryAdapter.Viewholder> {

    ArrayList<CategoryDomain> list;
    private Context context;
    private OnItemClickListener listener;
    private int selectedPosition = 0;

    public ItemCategoryAdapter(ArrayList<CategoryDomain> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
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

        CategoryDomain data = list.get(position);
        holder.bind(data, position == selectedPosition);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyItemChanged(selectedPosition);
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(selectedPosition);
                listener.onItemClick(data,selectedPosition);
            }
        });

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

        public void bind(final CategoryDomain data, boolean isSelected) {

            itemView.setBackgroundResource(isSelected ? R.drawable.border : R.color.white);
        }
    }

}
