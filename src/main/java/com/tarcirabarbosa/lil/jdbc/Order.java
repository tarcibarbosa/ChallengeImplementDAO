package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataTransferObject;
import com.tarcirabarbosa.lil.jdbc.util.OrderStatus;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class Order implements DataTransferObject {
    private long id;
    private Date creationDate;
    private Double totalDue;
    private OrderStatus orderStatusEnum;
    private SalesPerson salesPerson;
    private Customer customer;
    private List<OrderItem> listOrderItem;

    public Order(long id, Date creationDate, Double totalDue, OrderStatus orderStatusEnum, SalesPerson salesPerson,
                 Customer customer, List<OrderItem> listOrderItem) {
        this.id = id;
        this.creationDate = creationDate;
        this.totalDue = totalDue;
        this.orderStatusEnum = orderStatusEnum;
        this.salesPerson = salesPerson;
        this.customer = customer;
        this.listOrderItem = listOrderItem;
    }

    @Override
    public long getId() {
        return 0;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
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

    public List<OrderItem> getListOrderItem() {
        return listOrderItem;
    }

    public void setListOrderItem(List<OrderItem> listOrderItem) {
        this.listOrderItem = listOrderItem;
    }

    public String dateFormat(Date date) {
        DateFormat dateFormat = DateFormat.getDateInstance();
        return dateFormat.format(date);
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
                ", orderItems=" + listOrderItem +
                '}';
    }
}
