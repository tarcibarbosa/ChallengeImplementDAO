package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataAccessObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesPersonDAO extends DataAccessObject<SalesPerson> {
    private static final String INSERT = "INSERT INTO sales_person (first_name, last_name, email) VALUES (?, ?, ?)";
    private static final String GET_ONE = "SELECT sales_person.id, first_name, last_name, email WHERE sales_person.id = ?";
    private static final String UPDATE = "UPDATE sales_person SET first_name = ?, last_name = ?, email = ? WHERE sales_person.id = ?";
    private static final String DELETE = "DELETE FROM sales_person.id WHERE sales_person.id = ?";
    private static final String FIND_ALL = "SELECT * FROM sales_person";

    public SalesPersonDAO(Connection connection) {
        super(connection);
    }

    @Override
    public SalesPerson findById(long id) {
        SalesPerson salesPerson = null;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(GET_ONE)) {
            pre_statement.setLong(1, id);
            ResultSet resultSet = pre_statement.executeQuery();
            while (resultSet.next()) {
                salesPerson.setId(resultSet.getLong("sales_person.id"));
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
        SalesPerson salesPerson = null;
        List<SalesPerson> listCustomer = new ArrayList<>();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = pre_statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                salesPerson.setId(resultSet.getLong("sales_person.id"));
                salesPerson.setFirstName(resultSet.getString("first_name"));
                salesPerson.setLastName(resultSet.getString("last_name"));
                salesPerson.setEmail(resultSet.getString("email"));
                listCustomer.add(new SalesPerson(
                        salesPerson.getId(),
                        salesPerson.getFirstName(),
                        salesPerson.getLastName(),
                        salesPerson.getEmail()));
            }
            return listCustomer;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public SalesPerson update(SalesPerson dto) {
        SalesPerson salesPerson = null;
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
        SalesPerson salesPerson = null;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(INSERT)) {
            pre_statement.setString(1, dto.getFirstName());
            pre_statement.setString(2, dto.getLastName());
            pre_statement.setString(3, dto.getEmail());
            pre_statement.execute();
            int id = this.getLastValue(CUSTOMER_SEQUENCE);
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
