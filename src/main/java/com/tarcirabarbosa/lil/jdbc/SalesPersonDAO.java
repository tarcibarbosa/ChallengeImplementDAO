package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesPersonDAO extends DataAccessObject<SalesPerson> {
    private static final String INSERT = "INSERT INTO salesperson (first_name, last_name, email) VALUES (?, ?, ?)";
    private static final String GET_ONE = "SELECT salesperson_id, first_name, last_name, email FROM salesperson WHERE salesperson_id = ?";
    private static final String UPDATE = "UPDATE salesperson SET first_name = ?, last_name = ?, email = ? WHERE salesperson_id = ?";
    private static final String DELETE = "DELETE FROM salesperson WHERE salesperson_id = ?";
    private static final String FIND_ALL = "SELECT * FROM salesperson";

    public SalesPersonDAO(Connection connection) {
        super(connection);
    }

    @Override
    public SalesPerson findById(long id) {
        SalesPerson salesPerson = new SalesPerson();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(GET_ONE)) {
            pre_statement.setLong(1, id);
            ResultSet resultSet = pre_statement.executeQuery();
            while (resultSet.next()) {
                salesPerson.setId(resultSet.getLong("salesperson_id"));
                salesPerson.setFirstName(resultSet.getString("first_name"));
                salesPerson.setLastName(resultSet.getString("last_name"));
                salesPerson.setEmail(resultSet.getString("email"));
            }
            return salesPerson;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<SalesPerson> findAll() {
        SalesPerson salesPerson = new SalesPerson();
        List<SalesPerson> listCustomer = new ArrayList<>();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = pre_statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                salesPerson.setId(resultSet.getLong("salesperson_id"));
                salesPerson.setFirstName(resultSet.getString("first_name"));
                salesPerson.setLastName(resultSet.getString("last_name"));
                salesPerson.setEmail(resultSet.getString("email"));
                listCustomer.add(salesPerson);
            }
            return listCustomer;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public SalesPerson update(SalesPerson dto) {
        SalesPerson salesPerson;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(UPDATE)) {
            pre_statement.setString(1, dto.getFirstName());
            pre_statement.setString(2, dto.getLastName());
            pre_statement.setString(3, dto.getEmail());
            pre_statement.execute();
            salesPerson = this.findById(dto.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return salesPerson;
    }

    @Override
    public SalesPerson create(SalesPerson dto) {
        SalesPerson salesPerson;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(INSERT)) {
            pre_statement.setString(1, dto.getFirstName());
            pre_statement.setString(2, dto.getLastName());
            pre_statement.setString(3, dto.getEmail());
            pre_statement.execute();
            int id = this.getLastValue(SALESPERSON_SEQUENCE);
            salesPerson = this.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return salesPerson;
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
