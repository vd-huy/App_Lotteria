package com.example.app_lotteria.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_lotteria.Domain.ProductDomain;
import com.example.app_lotteria.Helper.ChangeNumberItemsListener;
import com.example.app_lotteria.Helper.ManagmentCart;
import com.example.app_lotteria.databinding.ViewholderProductInCartBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder>{

    ArrayList<ProductDomain> itemsDomainArrayList;
    ChangeNumberItemsListener changeNumberItemsListener;
    private ManagmentCart managmentCart;

    DecimalFormat f = new DecimalFormat("#,###");


    public CartAdapter(ArrayList<ProductDomain> itemsDomainArrayList, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.itemsDomainArrayList = itemsDomainArrayList;
        this.changeNumberItemsListener = changeNumberItemsListener;
        managmentCart = new ManagmentCart(context);
    }

    @NonNull
    @Override
    public CartAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderProductInCartBinding binding = ViewholderProductInCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.binding.titleTxt.setText(itemsDomainArrayList.get(position).getTitle());
        holder.binding.totalTxt.setText( String.valueOf(f.format(itemsDomainArrayList.get(position).getNumberinCart() * itemsDomainArrayList.get(position).getPrice())) + "₫" );
        holder.binding.numberItemTxt.setText(""+itemsDomainArrayList.get(position).getNumberinCart());

        Glide.with(holder.itemView.getContext())
                .load(itemsDomainArrayList.get(position).getPicUrl())
                .into(holder.binding.pic);

        holder.binding.plusCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managmentCart.plusItem(itemsDomainArrayList, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });

        holder.binding.minusCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managmentCart.minusItem(itemsDomainArrayList, position, new ChangeNumberItemsListener() {
                    @Override
                    public void changed() {
                        notifyDataSetChanged();
                        changeNumberItemsListener.changed();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsDomainArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ViewholderProductInCartBinding binding;
        public Viewholder(ViewholderProductInCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
