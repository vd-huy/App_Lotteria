package com.example.app_lotteria.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_lotteria.Domain.NhanVien;
import com.example.app_lotteria.databinding.ViewholderItemBaiThiBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BaiThiAdapter extends RecyclerView.Adapter<BaiThiAdapter.Viewholder> {

    private ArrayList<NhanVien> list;
    private Context context;

    DecimalFormat f = new DecimalFormat("#,###");

    public BaiThiAdapter() {
    }

    public BaiThiAdapter(ArrayList<NhanVien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BaiThiAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderItemBaiThiBinding binding = ViewholderItemBaiThiBinding.inflate(LayoutInflater.from(context),parent,false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaiThiAdapter.Viewholder holder, int position) {
        holder.binding.nameTxt.setText(list.get(position).getHoTen());
        holder.binding.chucVu.setText("Chức Vụ : "+list.get(position).getChucVu());
        holder.binding.heSoLuong.setText("Hệ số lương : " +list.get(position).getHeSoLuong());
        holder.binding.luongCoBan.setText("lương cơ bản : " +list.get(position).getLuongCoBan());
        holder.binding.phuCapChucVU.setText("phụ cấp chức vụ : " +list.get(position).getPhuCapChucVu());
        double luong = (list.get(position).getHeSoLuong() + list.get(position).getPhuCapChucVu())*list.get(position).getLuongCoBan();
        holder.binding.luong.setText("Lương : " +luong);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        ViewholderItemBaiThiBinding binding;

        public Viewholder(ViewholderItemBaiThiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
