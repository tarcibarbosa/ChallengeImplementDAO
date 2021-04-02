package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataTransferObject;

public class OrderItem implements DataTransferObject {
    private long id;
    private Product product;
    private long order_id;
    private int quantity;

    public OrderItem(long id, Product product, long order_id, int quantity) {
        this.id = id;
        this.product = product;
        this.order_id = order_id;
        this.quantity = quantity;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", product=" + product +
                ", order_id=" + order_id +
                ", quantity=" + quantity +
                '}';
    }
}
