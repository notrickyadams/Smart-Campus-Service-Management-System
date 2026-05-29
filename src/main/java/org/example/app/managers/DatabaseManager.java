package org.example.app.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static DatabaseManager instance;
    private Connection connection;

    // Your Supabase credentials
    private static final String URL      = "jdbc:postgresql://aws-0-eu-central-1.pooler.supabase.com:6543/postgres?user=postgres.lwaodrzfhsldjaygcmgr&password=YOUR_DB_PASSWORD&sslmode=require";

    private DatabaseManager() {
        connect();
    }

    public static DatabaseManager getInstance() {
        if (instance == null) instance = new DatabaseManager();
        return instance;
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(URL);
            System.out.println("Connected to Supabase!");
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) connect();
        } catch (SQLException e) {
            connect();
        }
        return connection;
    }
}