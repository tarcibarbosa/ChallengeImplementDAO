package com.tarcirabarbosa.lil.jdbc.util;

import java.sql.*;
import java.util.List;

public abstract class DataAccessObject <T extends DataTransferObject> {
    protected final Connection connection;
    protected final String LAST_VALUE = "SELECT last_value FROM ";
    protected final static String CUSTOMER_SEQUENCE = "hp_customer_seq";
    protected final static String PRODUCT_SEQUENCE = "hp_product_seq";
    protected final static String SALESPERSON_SEQUENCE = "hp_salesperson_seq";
    protected final static String ORDER_SEQUENCE = "hp_order_seq";
    protected final static String ORDER_ITEM_SEQUENCE = "hp_orderline_seq";

    public DataAccessObject(Connection connection) {
        super();
        this.connection = connection;
    }
    public abstract T findById(long id);
    public abstract List<T> findAll();
    public abstract T update (T dto);
    public abstract T create (T dto);
    public abstract void delete (long id);

    protected int getLastValue(String sequence) {
        int key = 0;
        String sql = LAST_VALUE + sequence;

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                key = resultSet.getInt(1);
            }
            return key;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
