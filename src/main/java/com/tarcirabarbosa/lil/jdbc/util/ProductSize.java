package com.tarcirabarbosa.lil.jdbc.util;

public enum ProductSize {

    CODE_1(20, "20"), CODE_2(32, "32"), CODE_3(40, "40");

    private final Integer value;
    private final String key;

    ProductSize(Integer value, String key) {
        this.value = value;
        this.key = key;
    }

    public Integer getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "ProductSize{" +
                "value=" + value +
                ", key='" + key + '\'' +
                '}';
    }
}
