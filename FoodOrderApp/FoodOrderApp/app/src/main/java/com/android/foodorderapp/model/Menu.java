package com.android.foodorderapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Menu implements Serializable {
    private String id,name;
    private double price;
    private int totalInCart;
    private String url;

    public Menu(String id, String name, double price, int totalInCart, String url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.totalInCart = totalInCart;
        this.url = url;
    }

    public Menu(String id, String name, double price, String url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public Menu() {
    }

    public int getTotalInCart() {
        return totalInCart;
    }

    public void setTotalInCart(int totalInCart) {
        this.totalInCart = totalInCart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
