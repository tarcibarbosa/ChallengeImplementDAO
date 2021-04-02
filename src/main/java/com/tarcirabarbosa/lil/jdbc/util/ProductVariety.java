package com.tarcirabarbosa.lil.jdbc.util;

public enum ProductVariety {
    FOOD("Food"),
    CLOTHING("Clothing"),
    SHOES("Shoes");

    private final String description;

    ProductVariety(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductVariety{" +
                "description='" + description + '\'' +
                '}';
    }
}
