package com.riis.auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.riis.database.DatabaseConnection;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class AuthenticationManager {
    public static Connection connection;

    public static String authenticate(String username, String password)
            throws ClassNotFoundException, SQLException {
        try (Connection connection = DatabaseConnection.getInstance();
                PreparedStatement preparedStatement = connection
                        .prepareStatement("SELECT password, job FROM Employee WHERE username=?")) {
            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String storedHash = resultSet.getString("password");
                    String job = resultSet.getString("job");

                    if (BCrypt.verifyer().verify(password.toCharArray(), storedHash).verified) {
                        return job;
                    }
                }
            }
        }
        return "";
    }
}
