package com.tarcirabarbosa.lil.jdbc;

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
            //Data access object
            CustomerDAO customerDAO = new CustomerDAO(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
