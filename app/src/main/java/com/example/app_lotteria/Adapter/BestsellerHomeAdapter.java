package com.example.app_lotteria.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.app_lotteria.Activity.ProductActivity;
import com.example.app_lotteria.Domain.ProductDomain;
import com.example.app_lotteria.databinding.ViewholderBestsellerBinding;

import java.util.ArrayList;

public class BestsellerHomeAdapter extends RecyclerView.Adapter<BestsellerHomeAdapter.Viewholder> {

    private ArrayList<ProductDomain> listProduct;
    private Context context;

    public BestsellerHomeAdapter(ArrayList<ProductDomain> listProduct) {
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public BestsellerHomeAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderBestsellerBinding binding = ViewholderBestsellerBinding.inflate(LayoutInflater.from(context),parent,false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BestsellerHomeAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.titleProduct.setText(listProduct.get(position).getTitle());
        holder.binding.priceProduct.setText("Giá : " + listProduct.get(position).getPrice());

        Glide.with(context)
                .load(listProduct.get(position).getPicUrl())
                .into(holder.binding.imageProduct);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("object",listProduct.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderBestsellerBinding binding;
        public Viewholder(ViewholderBestsellerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
