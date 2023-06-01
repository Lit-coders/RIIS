package com.riis.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DatabaseConnection {
    private static Connection connection;
    private static Object syncObject=new Object();

    private DatabaseConnection() {

    }
    
    public static Connection createConnection() throws ClassNotFoundException, SQLException{
        String URL = "jdbc:sqlite:src\\db\\riis.db";
        return DriverManager.getConnection(URL);


    }
    public static Connection getInstance() throws ClassNotFoundException, SQLException {
        synchronized(syncObject) {
            if (connection == null) {
                connection = createConnection();
            }
            return connection;
    }
}
       
}
