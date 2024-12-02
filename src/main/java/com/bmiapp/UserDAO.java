package com.bmiapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.io.File;
import java.sql.*;

public class UserDAO {
    private static final String DB_URL = "jdbc:sqlite:C:/Users/adama/Desktop/BMIcalcAppInsecure/src/main/resources/calcappinsecure.db";

    static {
        // Initialize the SQLite Driver and test the connection
        try {
            File dbFile = new File("C:/Users/adama/Desktop/BMIcalcAppInsecure/src/main/resources/calcapp.db");
            if (!dbFile.exists()) {
                throw new RuntimeException("Database file not found at: " + dbFile.getAbsolutePath());
            }
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection(DB_URL)) {
                System.out.println("Connection successful!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }

    // Method to authenticate user credentials insecurely
    public static boolean authenticate(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
            System.out.println("Executing Query: " + query);
            ResultSet rs = stmt.executeQuery(query);
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void registerUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();
            String query = "INSERT INTO users (username, password) VALUES ('" + username + "', '" + password + "')";
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
