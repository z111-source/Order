package com.android.ordering.Bean;

import java.io.Serializable;

public class FoodBean implements Serializable {
    private static final long serialVersionUID = 1L; //序列化时保持FoodBean类版本的兼容性
    private String name;
    private String sales;
    private double price;
    private int img;
    private int count ;

    public FoodBean(String name, String sales, double price, int img) {
        this.name = name;
        this.sales = sales;
        this.price = price;
        this.img = img;
    }

    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}