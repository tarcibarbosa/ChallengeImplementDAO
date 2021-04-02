package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemsDAO extends DataAccessObject<OrderItems> {
    private static final String INSERT = "INSERT INTO order_items (product.id, quantity) VALUES (?, ?)";
    private static final String GET_ONE = "SELECT order_items.id, product.id, quantity WHERE order_item.id = ?";
    private static final String UPDATE = "UPDATE order_items SET quantity = ? WHERE order_items.id = ?";
    private static final String DELETE = "DELETE FROM order_items.id WHERE order_items.id = ?";
    private static final String FIND_ALL = "SELECT * FROM order_items";

    public OrderItemsDAO(Connection connection) {
        super(connection);
    }

    @Override
    public OrderItems findById(long id) {
        OrderItems orderItems = null;
        ProductDAO productDAO = null;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(GET_ONE)) {
            pre_statement.setLong(1, id);
            ResultSet resultSet = pre_statement.executeQuery();
            while (resultSet.next()) {
                orderItems.setId(resultSet.getLong("orderItems.id"));
                orderItems.setProduct(productDAO.findById(resultSet.getLong("product.id")));
                orderItems.setQuantity(resultSet.getInt("quantity"));
            }
            return orderItems;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderItems> findAll() {
        OrderItems orderItems = null;
        ProductDAO productDAO = null;
        List<OrderItems> listOrderItems = new ArrayList<>();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = pre_statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                orderItems.setId(resultSet.getLong("order_items.id"));
                orderItems.setProduct(productDAO.findById(resultSet.getLong("product.id")));
                orderItems.setQuantity(resultSet.getInt("quantity"));
                listOrderItems.add(new OrderItems(
                        orderItems.getId(),
                        orderItems.getProduct(),
                        orderItems.getQuantity()
                ));
            }
            return listOrderItems;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrderItems update(OrderItems dto) {
        OrderItems orderItems = null;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(UPDATE)) {
            pre_statement.setInt(1, dto.getQuantity());
            pre_statement.execute();
            orderItems = this.findById(dto.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return orderItems;
    }

    @Override
    public OrderItems create(OrderItems dto) {
        OrderItems orderItems = null;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(INSERT)) {
            pre_statement.setLong(1, dto.getProduct().getId());
            pre_statement.setInt(2, dto.getQuantity());
            pre_statement.execute();
            int id = this.getLastValue(PRODUCT_SEQUENCE);
            orderItems = this.findById(id);
            return this.findById(orderItems.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement pre_statement = this.connection.prepareStatement(DELETE)) {
            pre_statement.setLong(1, id);
            pre_statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
