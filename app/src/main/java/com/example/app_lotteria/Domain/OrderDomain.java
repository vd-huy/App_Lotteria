package com.example.app_lotteria.Domain;


import java.time.LocalTime;
import java.util.ArrayList;

public class OrderDomain {

    private String idOrder;
    private String userName;
    private String name;
    private ArrayList<ProductDomain> listProduct;
    private String status;
    private String address;
    private String phoneNumber;
    private int total;

    public OrderDomain() {
    }

    public OrderDomain( String userName, String name, ArrayList<ProductDomain> listProduct,int total, String status, String address, String phoneNumber) {
        this.idOrder = String.valueOf(System.currentTimeMillis());
        this.userName = userName;
        this.name = name;
        this.listProduct = listProduct;
        this.status = status;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.total = total;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ProductDomain> getListProduct() {
        return listProduct;
    }

    public void setListProduct(ArrayList<ProductDomain> listProduct) {
        this.listProduct = listProduct;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
