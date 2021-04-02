package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataTransferObject;
import com.tarcirabarbosa.lil.jdbc.util.ProductSize;
import com.tarcirabarbosa.lil.jdbc.util.ProductVariety;

public class Product implements DataTransferObject {
    private long id;
    private long code;
    private String name;
    private double price;
    private ProductSize productSize;
    private ProductVariety productVariety;

    public Product(Product product) {
        this.id = product.id;
        this.code = product.code;
        this.name = product.name;
        this.price = product.price;
        this.productSize = product.productSize;
        this.productVariety = product.productVariety;

    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
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

    public ProductSize getProductSize() {
        return productSize;
    }

    public void setProductSize(ProductSize productSize) {
        this.productSize = productSize;
    }

    public ProductVariety getProductVariety() {
        return productVariety;
    }

    public void setProductVariety(ProductVariety productVariety) {
        this.productVariety = productVariety;
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
                '}';
    }
}
