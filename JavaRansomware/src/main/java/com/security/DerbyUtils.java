package com.security;

import java.sql.SQLException;

public class DerbyUtils {

    public DerbyUtils() {
    }

    public static boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        exists = e.getSQLState().equals("X0Y32");
        return exists;
    }
}