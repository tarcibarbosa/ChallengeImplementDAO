package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataAccessObject;
import com.tarcirabarbosa.lil.jdbc.util.ProductSize;
import com.tarcirabarbosa.lil.jdbc.util.ProductVariety;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DataAccessObject<Product> {
    private static final String INSERT = "INSERT INTO product (code, name, price, size, variety) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ONE = "SELECT product_id, code, name, price, size, variety WHERE product_id = ?";
    private static final String FIND_ALL = "SELECT * FROM product";
    private static final String UPDATE = "UPDATE product SET code = ?, name = ?, price = ?, size = ?, variety = ? WHERE product_id = ?";
    private static final String DELETE = "DELETE product WHERE product_id = ?";

    public ProductDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Product findById(long id) {
        Product product = null;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(FIND_ALL)) {
            pre_statement.setLong(1, id);
            ResultSet resultSet = pre_statement.executeQuery();
            while (resultSet.next()) {
                product.setId(resultSet.getLong("product_id"));
                product.setCode(resultSet.getLong("code"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setProductSize(ProductSize.valueOf(resultSet.getString("size")));
                product.setProductVariety(ProductVariety.valueOf(resultSet.getString("variety")));
            }
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findAll() {
        Product product = null;
        List<Product> listProduct = new ArrayList<>();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = pre_statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                product.setId(resultSet.getLong("product_id"));
                product.setCode(resultSet.getLong("code"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setProductSize(ProductSize.valueOf(resultSet.getString("size")));
                product.setProductVariety(ProductVariety.valueOf(resultSet.getString("variety")));
                listProduct.add(new Product(product));
            }
            return listProduct;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product update(Product dto) {
        Product product = null;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(UPDATE)) {
            pre_statement.setLong(1, dto.getCode());
            pre_statement.setString(2, dto.getName());
            pre_statement.setDouble(3, dto.getPrice());
            pre_statement.setString(4, dto.getProductSize().toString());
            pre_statement.setString(5, dto.getProductVariety().toString());
            pre_statement.setLong(6, dto.getId());
            pre_statement.execute();
            return this.findById(dto.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product create(Product dto) {
        Product product = null;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(INSERT)) {
            pre_statement.setLong(1, dto.getCode());
            pre_statement.setString(2, dto.getName());
            pre_statement.setDouble(3, dto.getPrice());
            pre_statement.setString(4, dto.getProductSize().toString());
            pre_statement.setString(5, dto.getProductVariety().toString());
            pre_statement.execute();
            int id = this.getLastValue(PRODUCT_SEQUENCE);
            product = this.findById(id);
            return this.findById(product.getId());
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
