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
            OrderDAO orderDAO = new OrderDAO(connection);
            Order order = orderDAO.findById(1001);
            System.out.println(order);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
