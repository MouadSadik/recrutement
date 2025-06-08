package main.java.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://aws-0-eu-west-2.pooler.supabase.com:5432/postgres";
    private static final String USER = "postgres.mlargwldfixnhgdppvje";
    private static final String PASSWORD = "Badrmane2020.";

    private DatabaseConnection() {
        
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

