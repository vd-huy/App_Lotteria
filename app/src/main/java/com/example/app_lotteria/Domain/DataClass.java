package com.example.app_lotteria.Domain;

import java.io.Serializable;

public class DataClass implements Serializable {

    private String key;

    private String imageUrl;

    private String name;

    private int count;

    private double price;

    private String note;

    public DataClass() {
    }

    public DataClass(String key, String imageUrl, String name, int count, double price, String note) {
        this.key = key;
        this.imageUrl = imageUrl;
        this.name = name;
        this.count = count;
        this.price = price;
        this.note = note;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
