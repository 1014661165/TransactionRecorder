package com.example.wujun.transactionrecorder.bean;

import java.util.List;

/**
 * 订单类
 */
public class Order {

    private int id;
    private String orderId;
    private String createTime;
    private float totalPrice;
    private List<Item> items;

    public Order() {
    }

    public Order(int id, String orderId, String createTime, float totalPrice, List<Item> items) {
        this.id = id;
        this.orderId = orderId;
        this.createTime = createTime;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
}
