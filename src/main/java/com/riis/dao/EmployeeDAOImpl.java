package com.riis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.riis.database.DatabaseConnection;
import com.riis.model.databasemodel.Employee;

public class EmployeeDAOImpl implements EmployeeDAO {
    public Employee getEmployeeByUsername(String userName) throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Employee employee = new Employee();
        String query = "SELECT * FROM employee WHERE username = ?";
        try (PreparedStatement pis = connection.prepareStatement(query); ) {
            pis.setString(1, userName);
            ResultSet resultSet = pis.executeQuery(); 
            while(resultSet.next()) {
                employee.setUserName(resultSet.getString("username"));
                employee.setFirstName(resultSet.getString("Name"));
                employee.setMiddleName(resultSet.getString("FName"));
                employee.setLastName(resultSet.getString("GFName"));
                employee.setJob(resultSet.getString("job"));
            }
        } catch (Exception e) {
            // System.err.println("Error getting employee by username");
            e.printStackTrace();
        }
        return employee;
    }       
}