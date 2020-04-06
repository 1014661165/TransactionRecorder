package com.example.wujun.transactionrecorder.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单类
 */
public class Order implements Serializable{

    private int id;
    private String createTime;
    private float totalPrice;
    private List<Item> items;
    private boolean takeOut;

    public Order() {
        items = new ArrayList<>();
    }

    public Order(int id, String createTime, float totalPrice, List<Item> items, boolean takeOut) {
        this.id = id;
        this.createTime = createTime;
        this.totalPrice = totalPrice;
        this.items = items;
        this.takeOut = takeOut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public boolean isTakeOut() {
        return takeOut;
    }

    public void setTakeOut(boolean takeOut) {
        this.takeOut = takeOut;
    }
}
