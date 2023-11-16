package com.example.travelution;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    private Connection connection;

    public DB() {
        try {
            System.out.println("Connecting to MySQL Database");
            this.connection = DriverManager.getConnection(
                    System.getProperty("DB_URL"),
                    System.getProperty("DB_USERNAME"),
                    System.getProperty("DB_PASSWORD")
            );
            System.out.println("Database connected!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on connecting to database");
        }
    }

    public Connection getConnection() {
        return this.connection;
    }
}
