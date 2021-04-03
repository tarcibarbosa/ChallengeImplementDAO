package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.DataAccessObject;
import com.tarcirabarbosa.lil.jdbc.util.ProductVariety;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO extends DataAccessObject<Product> {
    private static final String INSERT = "INSERT INTO product (code, name, price, size, variety, status) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String GET_ONE = "SELECT product_id, code, name, price, size, variety, status FROM product WHERE product_id = ?";
    private static final String FIND_ALL = "SELECT * FROM product";
    private static final String UPDATE = "UPDATE product SET code = ?, name = ?, price = ?, size = ?, variety = ?, status = ? WHERE product_id = ?";
    private static final String DELETE = "DELETE FROM product WHERE product_id = ?";

    public ProductDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Product findById(long id) {
        Product product = new Product();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(GET_ONE)) {
            pre_statement.setLong(1, id);
            ResultSet resultSet = pre_statement.executeQuery();
            while (resultSet.next()) {
                product.setId(resultSet.getLong("product_id"));
                product.setCode(resultSet.getString("code"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setProductSize(resultSet.getInt("size"));
                product.setProductVariety(resultSet.getString("variety"));
                product.setStatus(resultSet.getString("status"));
            }
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> findAll() {
        Product product = new Product();
        List<Product> listProduct = new ArrayList<>();
        try (PreparedStatement pre_statement = this.connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = pre_statement.executeQuery();
            while (resultSet.next()) {
                product.setId(resultSet.getLong("product_id"));
                product.setCode(resultSet.getString("code"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setProductSize(resultSet.getInt("size"));
                product.setProductVariety(resultSet.getString("variety"));
                product.setStatus(resultSet.getString("status"));
                listProduct.add(product);
            }
            return listProduct;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product update(Product dto) {
        Product product;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(UPDATE)) {
            pre_statement.setString(1, dto.getCode());
            pre_statement.setString(2, dto.getName());
            pre_statement.setDouble(3, dto.getPrice());
            pre_statement.setInt(4, dto.getProductSize());
            pre_statement.setString(5, dto.getProductVariety());
            pre_statement.setString(6, dto.getStatus());
            pre_statement.setLong(7, dto.getId());
            pre_statement.execute();
            product = this.findById(dto.getId());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return product;
    }

    @Override
    public Product create(Product dto) {
        Product product;
        try (PreparedStatement pre_statement = this.connection.prepareStatement(INSERT)) {
            pre_statement.setString(1, dto.getCode());
            pre_statement.setString(2, dto.getName());
            pre_statement.setDouble(3, dto.getPrice());
            pre_statement.setInt(4, dto.getProductSize());
            pre_statement.setString(5, dto.getProductVariety());
            pre_statement.setString(6, dto.getStatus());
            pre_statement.execute();
            int id = this.getLastValue(PRODUCT_SEQUENCE);
            product = this.findById(id);
            return product;
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

    /*
    private ProductSize getConstantProductSizeByValue(String size){
         Integer productId = Integer.parseInt(size);
         return Arrays.stream(ProductSize.values())
                 .filter(p -> p.getValue().equals(productId))
                 .findFirst()
                 .orElseThrow(() -> new RuntimeException("Not able to find Enum...."));
     }
    */

}
