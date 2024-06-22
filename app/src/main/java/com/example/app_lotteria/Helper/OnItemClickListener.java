package com.example.app_lotteria.Helper;


import com.example.app_lotteria.Domain.CategoryDomain;

public interface OnItemClickListener {
    void onItemClick(CategoryDomain data, int position);
}