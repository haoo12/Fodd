package com.example.ecommerce.models;

// com.example.ecommerce.model.OrderItem

public class OrderItem {
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalAmount;
    private String orderStatus;

    public OrderItem(String productName, int quantity, double unitPrice, String orderStatus) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalAmount = quantity * unitPrice;
        this.orderStatus = orderStatus;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}

