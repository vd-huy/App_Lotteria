package com.example.app_lotteria.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_lotteria.Activity.ItemOrdersActivity;
import com.example.app_lotteria.Domain.CategoryDomain;
import com.example.app_lotteria.Domain.OrderDomain;
import com.example.app_lotteria.R;
import com.example.app_lotteria.databinding.ViewholderHistoryOrderItemBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.Viewholder> {

    ArrayList<OrderDomain> items;
    private Context context;

    DecimalFormat f = new DecimalFormat("#,###");

    public OrderHistoryAdapter(ArrayList<OrderDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderHistoryOrderItemBinding binding = ViewholderHistoryOrderItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new Viewholder(binding);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.idOrder.setText("#"+items.get(position).getIdOrder());
        holder.binding.txtTimeOrder.setText(items.get(position).getTimeOrder());

        switch (items.get(position).getStatus()){
            case "Giao hàng thành công" :
                holder.binding.txtStatus.setText(items.get(position).getStatus());
                holder.binding.txtStatus.setBackgroundResource(R.drawable.border_success);
                holder.binding.txtStatus.setTextColor(R.color.textOrderSuccess);
                break;
            case "Giao hàng không thành công" :
                holder.binding.txtStatus.setText(items.get(position).getStatus());
                holder.binding.txtStatus.setBackgroundResource(R.drawable.order_fail);
                holder.binding.txtStatus.setTextColor(R.color.textOrderFail);
                break;
            case "Đang giao hàng" :
                holder.binding.txtStatus.setText(items.get(position).getStatus());
                holder.binding.txtStatus.setBackgroundResource(R.drawable.order_delivering);
                holder.binding.txtStatus.setTextColor(R.color.textOrderDelivering);
                break;
                case "Đã đặt hàng" :
                holder.binding.txtStatus.setText(items.get(position).getStatus());
                holder.binding.txtStatus.setBackgroundResource(R.drawable.ordered);
                holder.binding.txtStatus.setTextColor(R.color.textOrdered);
                break;
            default:
                holder.binding.txtStatus.setText(items.get(position).getStatus());
                holder.binding.txtStatus.setBackgroundResource(R.drawable.order_delivering);
                holder.binding.txtStatus.setTextColor(R.color.textOrderDelivering);
                break;
        }

        holder.binding.orderUser.setText("Người nhận : " + items.get(position).getName()  );
        holder.binding.orderAddress.setText("Giao đến : " + items.get(position).getAddress()  );
        holder.binding.orderPrice.setText("Thành tiềm : " + f.format(items.get(position).getTotal()) + "₫"  );

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemOrdersActivity.class);
                intent.putExtra("order",items.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        ViewholderHistoryOrderItemBinding binding;
        public Viewholder(ViewholderHistoryOrderItemBinding binding) {
            super(binding.getRoot());
            this.binding= binding;
        }
    }
}
