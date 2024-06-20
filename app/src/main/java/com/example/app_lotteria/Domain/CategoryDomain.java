package com.example.app_lotteria.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class CategoryDomain implements Parcelable {

    private String title;
    private int id;
    private String picUrl;

    public CategoryDomain(String title, int id, String picUrl) {
        this.title = title;
        this.id = id;
        this.picUrl = picUrl;
    }

    public CategoryDomain() {
    }

    protected CategoryDomain(Parcel in) {
        title = in.readString();
        id = in.readInt();
        picUrl = in.readString();
    }

    public static final Creator<CategoryDomain> CREATOR = new Creator<CategoryDomain>() {
        @Override
        public CategoryDomain createFromParcel(Parcel in) {
            return new CategoryDomain(in);
        }

        @Override
        public CategoryDomain[] newArray(int size) {
            return new CategoryDomain[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeInt(id);
        parcel.writeString(picUrl);
    }
}
