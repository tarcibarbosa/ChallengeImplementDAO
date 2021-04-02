package com.tarcirabarbosa.lil.jdbc;

import com.tarcirabarbosa.lil.jdbc.util.ProductSize;
import com.tarcirabarbosa.lil.jdbc.util.ProductVariety;

import java.sql.Connection;
import java.sql.SQLException;

public class ChallengesJDBCExecutor {
    public static void main (String... args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport",
                "postgres",
                "PASSWORD");
        try {
            //Create Connection
            Connection connection = dcm.getConnection();
            //Customer Data access object
            CustomerDAO customerDAO = new CustomerDAO(connection);
            //New Customer object populate
            Customer customer = new Customer();
            customer.setFirstName("Lula");
            customer.setLastName("Da Silva");
            customer.setEmail("lulinha_presidente@palacio.com.br");
            //CREATE customer in database
            Customer dbCustomer = customerDAO.create(customer);
            //SalesPerson Data access object
            SalesPersonDAO salesPersonDAO = new SalesPersonDAO(connection);
            //New SalesPerson object populate
            SalesPerson salesPerson = new SalesPerson();
            salesPerson.setFirstName("Kidoru");
            salesPerson.setLastName("Fast Japan Food");
            salesPerson.setEmail("sackidoru@japanfood.com");
            //CREATE Sales Person in database
            SalesPerson dnSalesPerson = salesPersonDAO.create(salesPerson);
            //Product Data access object
            ProductDAO productDAO = new ProductDAO(connection);
            //New product object populate
            Product productFood = new Product(
                    1,
                    123,
                    "katsuki de frango",
                    27.45,
                    ProductSize.S,
                    ProductVariety.FOOD
            );
            Product dbProductFood = productDAO.create(productFood);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
