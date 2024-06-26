package com.example.app_lotteria.Domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductDomain implements Serializable {
    private int id;
    private String  title;
    private String  description;
    private String picUrl;
    private int price;
    private int oldPrice;
    private String intro;
//    private int categoryID;
    private int NumberinCart;

    public ProductDomain() {
    }

    public ProductDomain(int id,String title, String description, String picUrl, int price, int oldPrice,String intro) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.price = price;
        this.oldPrice = oldPrice;
        this.intro = intro;
//        this.categoryID = categoryID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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
