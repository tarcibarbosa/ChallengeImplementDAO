package com.tarcirabarbosa.lil.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
