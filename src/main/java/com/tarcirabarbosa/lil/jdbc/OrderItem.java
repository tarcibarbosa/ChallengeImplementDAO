package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataTransferObject;

public class OrderItem implements DataTransferObject {
    private long id;
    private int quantity;
    private Product product;
    private Order order;

    public OrderItem() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", product=" + product +
                ", order=" + order +
                '}';
    }
}
