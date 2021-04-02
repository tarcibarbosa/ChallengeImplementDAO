package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataAccessObject;

import java.sql.Connection;
import java.util.List;

public class OrderItemsDAO extends DataAccessObject<OrderItems> {
    private static final String INSERT = "INSERT INTO order_items (product.id, quantity) VALUES (?, ?)";
    private static final String GET_ONE = "SELECT order_items.id, product.id, quantity WHERE order_item.id = ?";
    private static final String UPDATE = "UPDATE order_items SET product.id = ?, quantity = ? WHERE order_items.id = ?";
    private static final String DELETE = "DELETE FROM order_items.id WHERE order_items.id = ?";
    private static final String FIND_ALL = "SELECT * FROM order_items";

    public OrderItemsDAO(Connection connection) {
        super(connection);
    }

    @Override
    public OrderItems findById(long id) {
        return null;
    }

    @Override
    public List<OrderItems> findAll() {
        return null;
    }

    @Override
    public OrderItems update(OrderItems dto) {
        return null;
    }

    @Override
    public OrderItems create(OrderItems dto) {
        return null;
    }

    @Override
    public void delete(long id) {

    }
}
