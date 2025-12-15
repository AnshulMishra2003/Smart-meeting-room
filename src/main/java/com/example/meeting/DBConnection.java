package com.example.meeting;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        // Read from environment variables; fallback to localhost for dev
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASS");

        // Fallback for local development
        if (dbUrl == null || dbUrl.isEmpty()) {
            dbUrl = "jdbc:mysql://localhost:3306/meeting_db";
        }
        if (dbUser == null || dbUser.isEmpty()) {
            dbUser = "root";
        }
        if (dbPass == null || dbPass.isEmpty()) {
            dbPass = "Mysql@123";
        }

        System.out.println("Connecting to DB: " + dbUrl);
        return DriverManager.getConnection(dbUrl, dbUser, dbPass);
        
    }
}
