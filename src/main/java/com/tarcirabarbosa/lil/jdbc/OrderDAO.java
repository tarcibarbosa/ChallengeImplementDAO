package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataAccessObject;
import com.tarcirabarbosa.lil.jdbc.util.OrderStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DataAccessObject<Order> {
    private static final String INSERT = "INSERT INTO order (creation_date, total_due, order_status,"+
            " sales_person.id, customer.id) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ONE = "SELECT order.id, creation_date, total_due, order_status, "+
            " sales_person.id, customer.id FROM order WHERE order.id = ?";
    private static final String UPDATE = "UPDATE order SET order_status = ? WHERE order.id = ?";
    private static final String DELETE = "DELETE FROM order WHERE order.id = ?";
    private static final String FIND_ALL = "SELECT * FROM order";

    public OrderDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Order findById(long id) {
        Order order = null;
        SalesPersonDAO salesPersonDAO = null;
        CustomerDAO customerDAO = null;
        OrderItemDAO orderItemDAO = null;

        try (PreparedStatement pre_statement = this.connection.prepareStatement(GET_ONE)) {
            pre_statement.setLong(1, id);
            ResultSet resultSet = pre_statement.executeQuery();
            while (resultSet.next()) {
                order.setId(resultSet.getLong("order.id"));
                order.setCreationDate(resultSet.getDate("creationDate"));
                order.setTotalDue(resultSet.getDouble("total_due"));
                order.setOrderStatusEnum(OrderStatus.valueOf(resultSet.getString("order_status")));
                order.setSalesPerson(salesPersonDAO.findById(resultSet.getLong("sales_person.id")));
                order.setCustomer(customerDAO.findById(resultSet.getLong("customer.id")));
                order.setListOrderItem(orderItemDAO.findAllByOrderId(order.getId()));
            }
            return order;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findAll() {
        Order order = null;
        SalesPersonDAO salesPersonDAO = null;
        CustomerDAO customerDAO = null;
        OrderItemDAO orderItemDAO = null;
        List<Order> orderList = new ArrayList<>();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = pre_statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                order.setId(resultSet.getLong("order.id"));
                order.setCreationDate(resultSet.getDate("creationDate"));
                order.setTotalDue(resultSet.getDouble("total_due"));
                order.setOrderStatusEnum(OrderStatus.valueOf(resultSet.getString("order_status")));
                order.setSalesPerson(salesPersonDAO.findById(resultSet.getLong("sales_person.id")));
                order.setCustomer(customerDAO.findById(resultSet.getLong("customer.id")));
                order.setListOrderItem(orderItemDAO.findAllByOrderId(order.getId()));
                orderList.add(new Order(
                        order.getId(),
                        order.getCreationDate(),
                        order.getTotalDue(),
                        order.getOrderStatusEnum(),
                        order.getSalesPerson(),
                        order.getCustomer(),
                        order.getListOrderItem()));
            }
            return orderList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order update(Order dto) {
        Order order = null;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(UPDATE)) {
            pre_statement.setString(1, dto.getOrderStatusEnum().toString());
            pre_statement.execute();
            order = this.findById(dto.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public Order create(Order dto) {
        Order order = null;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(INSERT)) {
            pre_statement.setDate(1, new Date(dto.getCreationDate().getTime()));
            pre_statement.setDouble(2, dto.getTotalDue());
            pre_statement.setString(3, dto.getOrderStatusEnum().toString());
            pre_statement.setLong(4, dto.getSalesPerson().getId());
            pre_statement.setLong(5, dto.getCustomer().getId());
            pre_statement.execute();
            int id = this.getLastValue(CUSTOMER_SEQUENCE);
            order = this.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return order;
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
