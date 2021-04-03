package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataTransferObject;
import com.tarcirabarbosa.lil.jdbc.util.ProductSize;
import com.tarcirabarbosa.lil.jdbc.util.ProductVariety;

public class Product implements DataTransferObject {
    private long id;
    private String code;
    private String name;
    private double price;
    private Integer productSize;
    private String productVariety;
    private String status;

    public Product(long id, String code, String name, double price, Integer productSize, String productVariety, String status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.productSize = productSize;
        this.productVariety = productVariety;
        this.status = status;
    }

    public Product() { }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getProductSize() {
        return productSize;
    }

    public void setProductSize(Integer productSize) {
        this.productSize = productSize;
    }

    public String getProductVariety() {
        return productVariety;
    }

    public void setProductVariety(String productVariety) {
        this.productVariety = productVariety;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code=" + code +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", productSize=" + productSize +
                ", productVariety=" + productVariety +
                ", status='" + status + '\'' +
                '}';
    }
}
