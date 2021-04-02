package com.tarcirabarbosa.lil.jdbc.util;

public enum ProductSize {
    S("Small"),
    M("Medium"),
    L("Large"),
    XL("Extra Large"),
    XXL("Extra Plus Large");

    private final String description;

    ProductSize(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductSize{" +
                "description='" + description + '\'' +
                '}';
    }
}
