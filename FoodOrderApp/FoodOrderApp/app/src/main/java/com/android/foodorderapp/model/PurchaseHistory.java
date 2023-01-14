package com.android.foodorderapp.model;

import java.io.Serializable;
import java.util.HashMap;

public class PurchaseHistory implements Serializable {
    private String date;
    private HashMap<String,Menu> orders;

    public PurchaseHistory(String date, HashMap<String, Menu> orders) {
        this.date = date;
        this.orders = orders;
    }

    public PurchaseHistory() {
    }

    public PurchaseHistory(HashMap<String, Menu> orders) {
        this.orders = orders;
    }

    public PurchaseHistory(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public HashMap<String, Menu> getOrders() {
        return orders;
    }

    public void setOrders(HashMap<String, Menu> orders) {
        this.orders = orders;
    }
}
