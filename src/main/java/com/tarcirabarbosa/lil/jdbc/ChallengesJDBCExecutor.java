package com.tarcirabarbosa.lil.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class ChallengesJDBCExecutor {
    public static void main (String... args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "hplussport",
                "postgres",
                "S0c1oloG1@");
        try {
            //Create Connection
            Connection connection = dcm.getConnection();
            //Order Data access object
            CustomerDAO customerDAO = new CustomerDAO(connection);
            customerDAO.findAllCustomerWithLimit(20).forEach(System.out::println);

            System.out.println("Paged");
            for (int i = 1; i < 3; i++) {
                System.out.println("Page number: " + i);
                customerDAO.findAllCustomerWithLimitAndOffset(10, i).forEach(System.out::println);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
