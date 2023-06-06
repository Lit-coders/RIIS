package com.riis.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.sqlite.SQLiteDataSource;

public class DatabaseConnection {
    private static DataSource dataSource;

    private DatabaseConnection() {

    }

    static {
        // Initialize the connection pool
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:src\\db\\riis.db");
        dataSource = sqLiteDataSource;
    }

    public static Connection getInstance() throws SQLException {
        return dataSource.getConnection();
    }

    public static boolean checkDatabase(String databaseUrl) throws ClassNotFoundException, SQLException {
        File databaseFile = new File(databaseUrl);

        if (!databaseFile.exists()) {
            System.err.println("Database file not found");
            return false;
        } else {
            try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseUrl)) {
                DatabaseMetaData metaData = connection.getMetaData();
                try {
                    metaData.getTables(null, null, "%", null);
                    return true;
                } catch (SQLException e) {
                    System.err.println("Database is not connected");
                    return false;
                }
            } catch (SQLException e) {
                System.err.println("Database is not connected");
            }
        }
        return false;
    }

}
