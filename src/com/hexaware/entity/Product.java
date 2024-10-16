package com.hexaware.entity;


public class Product {
    private int product_id; 
    private String name;
    private double price;
    private String description;
    private int stock_quantity; 

    // Default constructor
    public Product() {}

    // Parameterized constructor
    public Product(int product_id, String name, double price, String description, int stock_quantity) {
        this.product_id = product_id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.stock_quantity = stock_quantity;
    }

    // Getters and Setters
    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }
}
