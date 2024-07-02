package com.example.app_lotteria.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_lotteria.Domain.OrderDomain;
import com.example.app_lotteria.Domain.ProductDomain;
import com.example.app_lotteria.databinding.ViewholderProductOrderBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.Viewholder> {

    private ArrayList<ProductDomain> list;
    private Context context;

    DecimalFormat f = new DecimalFormat("#,###");

    public ProductOrderAdapter(ArrayList<ProductDomain> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ProductOrderAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderProductOrderBinding binding = ViewholderProductOrderBinding.inflate(LayoutInflater.from(context),parent,false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderAdapter.Viewholder holder, int position) {
        holder.binding.nameProduct.setText(list.get(position).getTitle());
        holder.binding.numberInCart.setText(""+list.get(position).getNumberinCart());
        holder.binding.totalProduct.setText(f.format(list.get(position).getPrice()) + "₫" );

        Glide.with(context)
                .load(list.get(position).getPicUrl())
                .into(holder.binding.pic);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        ViewholderProductOrderBinding binding;

        public Viewholder(ViewholderProductOrderBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }
}
