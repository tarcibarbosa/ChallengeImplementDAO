package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataAccessObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DataAccessObject<Order> {
    private static final String INSERT = "INSERT INTO orders (creation_date, total_due, order_status,"+
            " sales_person.id, customer.id) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ONE = "SELECT order_id, creation_date, total_due, status, "+
            " salesperson_id, customer_id FROM orders WHERE order_id = ?";
    private static final String UPDATE = "UPDATE orders SET order_status = ? WHERE orders_id = ?";
    private static final String DELETE = "DELETE FROM orders WHERE orders_id = ?";
    private static final String FIND_ALL = "SELECT * FROM orders";

    public OrderDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Order findById(long id) {
        Order order = new Order();
        OrderItemDAO orderItemDAO = new OrderItemDAO(this.connection);
        SalesPersonDAO salesPersonDAO = new SalesPersonDAO(this.connection);
        CustomerDAO customerDAO = new CustomerDAO(this.connection);

        try (PreparedStatement pre_statement = this.connection.prepareStatement(GET_ONE)) {
            pre_statement.setLong(1, id);
            ResultSet resultSet = pre_statement.executeQuery();
            System.out.println(resultSet);
            long orderId = 0;
            while (resultSet.next()) {
                if(orderId == 0) {
                    order.setId(resultSet.getLong(1));
                    order.setCreationDate(resultSet.getDate("creation_date"));
                    order.setTotalDue(resultSet.getDouble("total_due"));
                    order.setOrderStatus(resultSet.getString("status"));
                    order.setSalesPerson(salesPersonDAO.findById(resultSet.getLong("salesperson_id")));
                    order.setCustomer(customerDAO.findById(resultSet.getLong("customer_id")));
                    order.setListOrderItem(orderItemDAO.findAllByOrderId(id));
                    orderId = order.getId();
                }
            }

            return order;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findAll() {
        Order order = new Order();
        OrderItemDAO orderItemDAO = new OrderItemDAO(this.connection);
        SalesPersonDAO salesPersonDAO = new SalesPersonDAO(this.connection);
        CustomerDAO customerDAO = new CustomerDAO(this.connection);
        List<Order> orderList = new ArrayList<>();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = pre_statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                order.setId(resultSet.getLong("order.id"));
                order.setCreationDate(resultSet.getDate("creationDate"));
                order.setTotalDue(resultSet.getDouble("total_due"));
                order.setOrderStatus(resultSet.getString("order_status"));
                order.setSalesPerson(salesPersonDAO.findById(resultSet.getLong("sales_person.id")));
                order.setCustomer(customerDAO.findById(resultSet.getLong("customer.id")));
                order.setListOrderItem(orderItemDAO.findAllByOrderId(order.getId()));
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order update(Order dto) {
        Order order;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(UPDATE)) {
            pre_statement.setString(1, dto.getOrderStatus());
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
        Order order;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(INSERT)) {
            pre_statement.setDate(1, new Date(dto.getCreationDate().getTime()));
            pre_statement.setDouble(2, dto.getTotalDue());
            pre_statement.setString(3, dto.getOrderStatus());
            pre_statement.setLong(4, dto.getSalesPerson().getId());
            pre_statement.setLong(5, dto.getCustomer().getId());
            pre_statement.execute();
            int id = this.getLastValue(ORDER_SEQUENCE);
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
