package com.example.app_lotteria.Domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductDomain implements Serializable {
    private String  title;
    private String  description;
    private String picUrl;
    private double price;
    private double oldPrice;
//    private int categoryID;
    private int NumberinCart;

    public ProductDomain() {
    }

    public ProductDomain(String title, String description, String picUrl, double price, double oldPrice) {
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.price = price;
        this.oldPrice = oldPrice;
//        this.categoryID = categoryID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

//    public int getCategoryID() {
//        return categoryID;
//    }
//
//    public void setCategoryID(int categoryID) {
//        this.categoryID = categoryID;
//    }

    public int getNumberinCart() {
        return NumberinCart;
    }

    public void setNumberinCart(int numberinCart) {
        NumberinCart = numberinCart;
    }
}
