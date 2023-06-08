package com.riis.auth;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.riis.dao.EmployeeDAO;
import com.riis.dao.EmployeeDAOImpl;
import com.riis.database.DatabaseConnection;

public class AuthenticationManager {
    public static Connection connection;
    
    public static String authenticate(String username, String password) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
        EmployeeDAO emp = new EmployeeDAOImpl();
        try (Connection connection = DatabaseConnection.getInstance();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Employee WHERE username=? AND password=?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, emp.hashPassword(password));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String job = resultSet.getString("job");
                    return job;
                }
            }
        }
        return "";
    }
}
