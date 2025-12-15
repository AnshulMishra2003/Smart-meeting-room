package com.example.meeting;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() throws Exception {

        Class.forName("org.postgresql.Driver");

        // Read from environment variables; fallback to localhost for dev
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPass = System.getenv("DB_PASS");

        // Fallback for local development
        if (dbUrl == null || dbUrl.isEmpty()) {
            dbUrl = "jdbc:postgresql://localhost:5432/meeting_db";
        }
        if (dbUser == null || dbUser.isEmpty()) {
            dbUser = "postgres";
        }
        if (dbPass == null || dbPass.isEmpty()) {
            dbPass = "postgres";
        }

        System.out.println("Connecting to DB: " + dbUrl);
        return DriverManager.getConnection(dbUrl, dbUser, dbPass);
        
    }
}
