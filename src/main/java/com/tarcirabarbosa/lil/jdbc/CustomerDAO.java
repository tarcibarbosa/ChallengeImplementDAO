package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO extends DataAccessObject<Customer> {
    private static final String INSERT = "INSERT INTO customer (first_name, last_name, email, phone," +
            "address, city, state, zipcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ONE = "SELECT customer_id, first_name, last_name, email, phone, address, city,"+
            "state, zipcode FROM customer WHERE customer_id = ?";
    private static final String UPDATE = "UPDATE customer SET first_name = ?, last_name = ?, email = ?, phone = ?," +
            "address = ?, city = ?, state = ?, zipcode = ? WHERE customer_id = ?";
    private static final String DELETE = "DELETE FROM customer_id WHERE customer_id = ?";
    private static final String FIND_ALL = "SELECT * FROM customer";
    private static final String GET_ALL_LMT = "SELECT customer_id, first_name, last_name, email, phone, address, city,"+
            "state, zipcode FROM customer ORDER BY last_name, first_name LIMIT ?";
    private static final String GET_ALL_PAGED = "SELECT customer_id, first_name, last_name, email, phone, address, city,"+
            "state, zipcode FROM customer ORDER BY last_name, first_name LIMIT ? OFFSET ?";

    public CustomerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Customer findById(long id) {
        Customer customer = new Customer();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(GET_ONE)) {
            pre_statement.setLong(1, id);
            ResultSet resultSet = pre_statement.executeQuery();
            while (resultSet.next()) {
                customer.setId(resultSet.getLong("customer_id"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setAddress(resultSet.getString("address"));
                customer.setCity(resultSet.getString("city"));
                customer.setState(resultSet.getString("state"));
                customer.setZipCode(resultSet.getString("zipcode"));
            }
            return customer;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> findAll() {
        Customer customer = new Customer();
        List<Customer> listCustomer = new ArrayList<>();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = pre_statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                customer.setId(resultSet.getLong("customer_id"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setAddress(resultSet.getString("address"));
                customer.setCity(resultSet.getString("city"));
                customer.setState(resultSet.getString("state"));
                customer.setZipCode(resultSet.getString("zipcode"));
                listCustomer.add(customer);
            }
            return listCustomer;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer update(Customer dto) {
        Customer customer;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(UPDATE)) {
            pre_statement.setString(1, dto.getFirstName());
            pre_statement.setString(2, dto.getLastName());
            pre_statement.setString(3, dto.getEmail());
            pre_statement.setString(4, dto.getPhone());
            pre_statement.setString(5, dto.getAddress());
            pre_statement.setString(6, dto.getCity());
            pre_statement.setString(7, dto.getState());
            pre_statement.setString(8, dto.getZipCode());
            pre_statement.setLong(9, dto.getId());
            pre_statement.execute();
            customer = this.findById(dto.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public Customer create(Customer dto) {
        Customer customer;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(INSERT)) {
            pre_statement.setString(1, dto.getFirstName());
            pre_statement.setString(2, dto.getLastName());
            pre_statement.setString(3, dto.getEmail());
            pre_statement.setString(4, dto.getPhone());
            pre_statement.setString(5, dto.getAddress());
            pre_statement.setString(6, dto.getCity());
            pre_statement.setString(7, dto.getState());
            pre_statement.setString(8, dto.getZipCode());
            pre_statement.execute();
            int id = this.getLastValue(CUSTOMER_SEQUENCE);
            customer = this.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return customer;
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

    public List<Customer> findAllCustomerWithLimit(long limit) {
        List<Customer> customerList = new ArrayList<>();
        Customer customer;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(GET_ALL_LMT)) {
            pre_statement.setLong(1, limit);
            ResultSet resultSet = pre_statement.executeQuery();
            while(resultSet.next()) {
                customer = new Customer();
                customer.setId(resultSet.getLong("customer_id"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setAddress(resultSet.getString("address"));
                customer.setCity(resultSet.getString("city"));
                customer.setState(resultSet.getString("state"));
                customer.setZipCode(resultSet.getString("zipcode"));
                customerList.add(customer);
            }
            return customerList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Customer> findAllCustomerWithLimitAndOffset(int limit, int pageNumber) {
        List<Customer> customerList = new ArrayList<>();
        Customer customer;
        int offset = ((pageNumber-1) * limit);
        if (limit < 1) { limit = 10; }
        try (PreparedStatement pre_statement = this.connection.prepareStatement(GET_ALL_PAGED)) {
            pre_statement.setInt(1, limit);
            pre_statement.setInt(2, offset);
            ResultSet resultSet = pre_statement.executeQuery();
            while(resultSet.next()) {
                customer = new Customer();
                customer.setId(resultSet.getLong("customer_id"));
                customer.setFirstName(resultSet.getString("first_name"));
                customer.setLastName(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setAddress(resultSet.getString("address"));
                customer.setCity(resultSet.getString("city"));
                customer.setState(resultSet.getString("state"));
                customer.setZipCode(resultSet.getString("zipcode"));
                customerList.add(customer);
            }
            return customerList;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
