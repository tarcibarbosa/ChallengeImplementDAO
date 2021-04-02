package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataTransferObject;

public class OrderItems implements DataTransferObject {
    private Product product;
    private int quantity;

    public OrderItems(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public long getId() {
        return 0;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
