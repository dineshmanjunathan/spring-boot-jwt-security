package com.g2.api.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        String username = "root";
        String password = "India@123";
        String conURL = "jdbc:mysql://172.16.14.190:3306/RFP";
        Connection conn = null;
        try {
            //Class.forName("org.postgresql.Driver");
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(conURL, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
