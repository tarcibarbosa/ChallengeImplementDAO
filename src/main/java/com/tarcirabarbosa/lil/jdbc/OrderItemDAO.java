package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO extends DataAccessObject<OrderItem> {
    private static final String INSERT = "INSERT INTO order_item (product.id, order.id, quantity) VALUES (?, ?, ?)";
    private static final String GET_ONE = "SELECT order_item.id, product.id, order.id, quantity FROM order_item WHERE order_item.id = ?";
    private static final String UPDATE = "UPDATE order_item SET quantity = ? WHERE order_item.id = ?";
    private static final String DELETE = "DELETE FROM order_item.id WHERE order_item.id = ?";
    private static final String FIND_ALL = "SELECT * FROM order_item";

    private static final String FIND_ALL_BY_ORDER_ID = "SELECT ol.product.id, ol.order.id, ol.quantity FROM order_item ol "+
            "JOIN product p on ol.product.id=p.id WHERE ol.order.id = ?";

    public OrderItemDAO(Connection connection) {
        super(connection);
    }

    @Override
    public OrderItem findById(long id) {
        OrderItem orderItem = null;
        ProductDAO productDAO = null;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(GET_ONE)) {
            pre_statement.setLong(1, id);
            ResultSet resultSet = pre_statement.executeQuery();
            while (resultSet.next()) {
                orderItem.setId(resultSet.getLong("orderItems.id"));
                orderItem.setProduct(productDAO.findById(resultSet.getLong("product.id")));
                orderItem.setOrder_id(resultSet.getLong("order.id"));
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
        OrderItem orderItem = null;
        ProductDAO productDAO = null;
        List<OrderItem> listOrderItem = new ArrayList<>();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = pre_statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                orderItem.setId(resultSet.getLong("order_items.id"));
                orderItem.setProduct(productDAO.findById(resultSet.getLong("product.id")));
                orderItem.setOrder_id(resultSet.getLong("order.id"));
                orderItem.setQuantity(resultSet.getInt("quantity"));
                listOrderItem.add(new OrderItem(
                        orderItem.getId(),
                        orderItem.getProduct(),
                        orderItem.getOrder_id(),
                        orderItem.getQuantity()
                ));
            }
            return listOrderItem;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public OrderItem update(OrderItem dto) {
        OrderItem orderItem = null;
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
        OrderItem orderItem = null;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(INSERT)) {
            pre_statement.setLong(1, dto.getProduct().getId());
            pre_statement.setLong(2, dto.getOrder_id());
            pre_statement.setInt(3, dto.getQuantity());
            pre_statement.execute();
            int id = this.getLastValue(PRODUCT_SEQUENCE);
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
        OrderItem orderItem = null;
        ProductDAO productDAO = null;
        List<OrderItem> listOrderItem = new ArrayList<>();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(FIND_ALL_BY_ORDER_ID)) {
            pre_statement.setLong(1, order_id);
            ResultSet resultSet = pre_statement.executeQuery(FIND_ALL_BY_ORDER_ID);
            while (resultSet.next()) {
                orderItem.setId(resultSet.getLong("order_items.id"));
                orderItem.setProduct(productDAO.findById(resultSet.getLong("product.id")));
                orderItem.setOrder_id(resultSet.getLong("order.id"));
                orderItem.setQuantity(resultSet.getInt("quantity"));
                listOrderItem.add(new OrderItem(
                        orderItem.getId(),
                        orderItem.getProduct(),
                        orderItem.getOrder_id(),
                        orderItem.getQuantity()
                ));
            }
            return listOrderItem;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
