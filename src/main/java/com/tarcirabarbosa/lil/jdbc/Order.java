package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.OrderStatus;

import java.util.List;

public class Order {
    private long id;
    private String creationDate;
    private Double totalDue;
    private OrderStatus orderStatusEnum;
    private SalesPerson salesPerson;
    private Customer customer;
    private List<OrderItems> orderItems;

    public Order(long id, String creationDate, Double totalDue, OrderStatus orderStatusEnum, SalesPerson salesPerson,
                 Customer customer, List<OrderItems> orderItems) {
        this.id = id;
        this.creationDate = creationDate;
        this.totalDue = totalDue;
        this.orderStatusEnum = orderStatusEnum;
        this.salesPerson = salesPerson;
        this.customer = customer;
        this.orderItems = orderItems;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public Double getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(Double totalDue) {
        this.totalDue = totalDue;
    }

    public OrderStatus getOrderStatusEnum() {
        return orderStatusEnum;
    }

    public void setOrderStatusEnum(OrderStatus orderStatusEnum) {
        this.orderStatusEnum = orderStatusEnum;
    }

    public SalesPerson getSalesPerson() {
        return salesPerson;
    }

    public void setSalesPerson(SalesPerson salesPerson) {
        this.salesPerson = salesPerson;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", creationDate='" + creationDate + '\'' +
                ", totalDue=" + totalDue +
                ", orderStatusEnum=" + orderStatusEnum +
                ", salesPerson=" + salesPerson +
                ", customer=" + customer +
                ", orderItems=" + orderItems +
                '}';
    }
}
