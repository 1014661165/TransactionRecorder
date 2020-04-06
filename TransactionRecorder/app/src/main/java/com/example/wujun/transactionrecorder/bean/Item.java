package com.example.wujun.transactionrecorder.bean;

import java.io.Serializable;

/**
 * 物品类
 */
public class Item implements Serializable{

    private int id;
    private String name;
    private float price;

    public Item() {
    }

    public Item(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Item clone(){
        return new Item(id, name, price);
    }
}
