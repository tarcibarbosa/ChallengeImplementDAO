package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO extends DataAccessObject<OrderItem> {
    private static final String INSERT = "INSERT INTO order_item (product_id, order_id, quantity) VALUES (?, ?, ?)";
    private static final String GET_ONE = "SELECT order_item_id, product_id, order_id, quantity FROM order_item WHERE order_item_id = ?";
    private static final String UPDATE = "UPDATE order_item SET quantity = ? WHERE order_item_id = ?";
    private static final String DELETE = "DELETE FROM order_item_id WHERE order_item_id = ?";
    private static final String FIND_ALL = "SELECT * FROM order_item";

    private static final String FIND_ALL_BY_ORDER_ID = "SELECT order_item_id, product_id, quantity FROM order_item "+
            "WHERE order_id = ?";

    public OrderItemDAO(Connection connection) {
        super(connection);
    }

    @Override
    public OrderItem findById(long id) {
        OrderItem orderItem = new OrderItem();
        ProductDAO productDAO = new ProductDAO(this.connection);
        OrderDAO orderDAO = new OrderDAO(this.connection);
        try (PreparedStatement pre_statement = this.connection.prepareStatement(GET_ONE)) {
            pre_statement.setLong(1, id);
            ResultSet resultSet = pre_statement.executeQuery();
            while (resultSet.next()) {
                orderItem.setId(resultSet.getLong("orderItems_id"));
                orderItem.setProduct(productDAO.findById(resultSet.getLong("product_id")));
                orderItem.setOrder(orderDAO.findById(resultSet.getLong("order_id")));
                orderItem.setQuantity(resultSet.getInt("quantity"));
            }
            return orderItem;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderItem> findAll() {
        OrderItem orderItem = new OrderItem();
        ProductDAO productDAO = new ProductDAO(this.connection);
        OrderDAO orderDAO = new OrderDAO(this.connection);
        List<OrderItem> listOrderItem = new ArrayList<>();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = pre_statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                orderItem.setId(resultSet.getLong("order_items_id"));
                orderItem.setProduct(productDAO.findById(resultSet.getLong("product_id")));
                orderItem.setOrder(orderDAO.findById(resultSet.getLong("order_id")));
                orderItem.setQuantity(resultSet.getInt("quantity"));
                listOrderItem.add(orderItem);
            }
            return listOrderItem;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrderItem update(OrderItem dto) {
        OrderItem orderItem;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(UPDATE)) {
            pre_statement.setInt(1, dto.getQuantity());
            pre_statement.execute();
            orderItem = this.findById(dto.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return orderItem;
    }

    @Override
    public OrderItem create(OrderItem dto) {
        OrderItem orderItem;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(INSERT)) {
            pre_statement.setLong(1, dto.getProduct().getId());
            pre_statement.setLong(2, dto.getOrder().getId());
            pre_statement.setInt(3, dto.getQuantity());
            pre_statement.execute();
            int id = this.getLastValue(ORDER_ITEM_SEQUENCE);
            orderItem = this.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return orderItem;
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

    public List<OrderItem> findAllByOrderId(long order_id) {
        OrderItem orderItem = new OrderItem();
        ProductDAO productDAO = new ProductDAO(this.connection);
        List<OrderItem> listOrderItem = new ArrayList<>();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(FIND_ALL_BY_ORDER_ID)) {
            pre_statement.setLong(1, order_id);
            ResultSet resultSet = pre_statement.executeQuery();
            while (resultSet.next()) {
                orderItem.setId(resultSet.getLong("order_item_id"));
                orderItem.setProduct(productDAO.findById(resultSet.getLong("product_id")));
                orderItem.setQuantity(resultSet.getInt("quantity"));
                listOrderItem.add(orderItem);
            }
            return listOrderItem;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
