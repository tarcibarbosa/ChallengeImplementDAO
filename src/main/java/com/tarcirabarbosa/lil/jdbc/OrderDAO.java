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
    private static final String GET_ORDERS_FOR_CUSTOMER_ID = "SELECT * FROM get_orders_by_customer(?)";

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
                    order.setTotalDue(resultSet.getDouble("total_due"));
                    order.setCreationDate(resultSet.getDate("creation_date"));
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

    public List<Order> getOrdersForCustomer (long customer_id) {
        List<Order> orderList = new ArrayList<>();
        Order order;
        Customer customer = new Customer();
        SalesPerson salesPerson = new SalesPerson();
        Product product = new Product();

        try (PreparedStatement pre_statement = this.connection.prepareStatement(GET_ORDERS_FOR_CUSTOMER_ID)) {
            pre_statement.setLong(1, customer_id);
            ResultSet resultSet = pre_statement.executeQuery();
            long orderId = 0;
            while (resultSet.next()) {
                long localOrderId = resultSet.getLong("order_id");
                if(orderId != localOrderId) {
                    //New object order and add in list of orders
                    order = new Order();
                    orderList.add(order);
                    order.setId(localOrderId);
                    orderId = localOrderId;
                    //ADD Customer field
                    customer.setFirstName(resultSet.getString("cust_first_name"));
                    customer.setLastName(resultSet.getString("cust_last_name"));
                    customer.setEmail(resultSet.getString("cust_email"));
                    //Populate SalesPerson field
                    salesPerson.setFirstName(resultSet.getString("sales_first_name"));
                    salesPerson.setLastName(resultSet.getString("sales_last_name"));
                    salesPerson.setEmail(resultSet.getString("sales_email"));
                    //Populate order field
                    order.setCreationDate(resultSet.getDate("creation_dt"));
                    order.setTotalDue(resultSet.getDouble("total_due"));
                    order.setOrderStatus(resultSet.getString("status"));
                    order.setCustomer(customer);
                    order.setSalesPerson(salesPerson);
                    //New list of Order item objects
                    List<OrderItem> orderItemList = new ArrayList<>();
                    order.setListOrderItem(orderItemList);
                    //Populate Product field
                    product.setCode(resultSet.getString("item_code"));
                    product.setName(resultSet.getString("item_name"));
                    product.setProductSize(resultSet.getInt("item_size"));
                    product.setProductVariety(resultSet.getString("item_variety"));
                    product.setPrice(resultSet.getDouble("item_price"));
                    //New order item object and populate field
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(resultSet.getInt("item_quanitty"));
                    order.getListOrderItem().add(orderItem);
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return orderList;
    }
}
