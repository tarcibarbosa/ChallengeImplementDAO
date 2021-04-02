package com.tarcirabarbosa.lil.jdbc.util;

public enum OrderStatus {
    O("Opened"),
    PG("Progress"),
    CN("Canceled"),
    CL("Closed"),
    F("Finalized");
    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OrderStatus{" +
                "description='" + description + '\'' +
                '}';
    }
}
