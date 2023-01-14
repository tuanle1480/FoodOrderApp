package com.android.foodorderapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class RestaurantModel implements Serializable {

    private String name;
    private String address;
    private String image;
    private double delivery_charge;
    private Hours hours;
    private HashMap<String,Menu> menus;

    public RestaurantModel(String name, String address, String image, double delivery_charge, Hours hours, HashMap<String,Menu> menus) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.delivery_charge = delivery_charge;
        this.hours = hours;
        this.menus = menus;
    }

    public RestaurantModel(String name, String address, String image, double delivery_charge, HashMap<String,Menu> menus) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.delivery_charge = delivery_charge;
        this.menus = menus;
    }

    public RestaurantModel() {
    }

    public RestaurantModel(String name, String address, String image, double delivery_charge) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.delivery_charge = delivery_charge;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(double delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public Hours getHours() {
        return hours;
    }

    public void setHours(Hours hours) {
        this.hours = hours;
    }

    public HashMap<String,Menu> getMenus() {
        return menus;
    }

    public void setMenus(HashMap<String,Menu> menus) {
        this.menus = menus;
    }



}
