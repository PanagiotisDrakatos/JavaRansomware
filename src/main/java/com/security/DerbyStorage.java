package com.security;

import java.sql.*;

public class DerbyStorage {
    private static final String DB_URL = "jdbc:derby:encryptedKeyDB;create=true";
    private static final String TABLE_NAME = "STRING_STORE";
    private static final String COLUMN_NAME = "stored_string";

    private Connection connection;
    private static volatile DerbyStorage instance;

    private DerbyStorage() {
        if (instance != null) {
            throw new IllegalStateException("Already initialized.");
        }
        try {
            // Initialize connection
            connection = DriverManager.getConnection(DB_URL);
            // Check if table exists, create if not
            if (!tableExists()) {
                createTable();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DerbyStorage getInstance() {
        var result = instance;
        if (result == null) {
            synchronized (DerbyStorage.class) {
                result = instance;
                if (result == null) {
                    result = new DerbyStorage();
                    instance = result;
                }
            }
        }
        return result;
    }

    private boolean tableExists() throws SQLException {
        boolean exists = false;
        ResultSet rs = connection.getMetaData().getTables(null, null, TABLE_NAME, null);

        if (rs.next()) {
            exists = true;
        }

        rs.close();
        return exists;
    }

    private void createTable() throws SQLException {
        Statement stmt = connection.createStatement();
        String createTableSQL = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NAME + " VARCHAR(255))";
        stmt.executeUpdate(createTableSQL);
        stmt.close();
        System.out.println("Table created successfully.");
    }

    public void storeString(String value) throws SQLException {
        // First, check if table already has a string
        String existingString = retrieveString();

        if (existingString != null) {
            // Update the existing string
            PreparedStatement updateStmt = connection.prepareStatement(
                    "UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME + " = ?");
            updateStmt.setString(1, value);
            updateStmt.executeUpdate();
            updateStmt.close();
        } else {
            // Insert a new string
            PreparedStatement insertStmt = connection.prepareStatement(
                    "INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME + ") VALUES (?)");
            insertStmt.setString(1, value);
            insertStmt.executeUpdate();
            insertStmt.close();
        }

        System.out.println("String stored successfully: " + value);
    }

    public String retrieveString() throws SQLException {
        String retrievedString = null;
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT " + COLUMN_NAME + " FROM " + TABLE_NAME);

        if (rs.next()) {
            retrievedString = rs.getString(COLUMN_NAME);
        }

        rs.close();
        stmt.close();
        return retrievedString;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                // Shut down Derby
                try {
                    DriverManager.getConnection("jdbc:derby:;shutdown=true");
                } catch (SQLException e) {
                    // Derby always throws an exception on proper shutdown
                    if (e.getSQLState().equals("XJ015")) {
                        System.out.println("Derby shutdown successfully.");
                    } else {
                        e.printStackTrace();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
