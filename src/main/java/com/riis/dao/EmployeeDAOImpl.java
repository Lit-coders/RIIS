package com.riis.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.riis.database.DatabaseConnection;
import com.riis.model.databasemodel.Employee;
import com.riis.utils.DateProvider;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public Employee getEmployeeByUsername(String userName) throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        Employee employee = new Employee();
        String query = "SELECT * FROM employee WHERE username = ?";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            pis.setString(1, userName);
            ResultSet resultSet = pis.executeQuery();
            while (resultSet.next()) {
                employee.setUserName(resultSet.getString("username"));
                employee.setFirstName(resultSet.getString("Name"));
                employee.setMiddleName(resultSet.getString("FName"));
                employee.setLastName(resultSet.getString("GFName"));
                employee.setJob(resultSet.getString("job"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public boolean addEmployee(Employee employee) throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        String query = "INSERT INTO employee (username, password, Name, FName, GFName, job) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pis = connection.prepareStatement(query)) {
            pis.setString(1, employee.getUserName());
            pis.setString(2, hashPassword(employee.getPassword()));
            pis.setString(3, employee.getFirstName());
            pis.setString(4, employee.getMiddleName());
            pis.setString(5, employee.getLastName());
            pis.setString(6, employee.getJob());
            pis.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT * FROM employee";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            ResultSet resultSet = pis.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setUserName(resultSet.getString("username"));
                employee.setFirstName(resultSet.getString("Name"));
                employee.setMiddleName(resultSet.getString("FName"));
                employee.setLastName(resultSet.getString("GFName"));
                employee.setJob(resultSet.getString("job"));
                employees.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public boolean deleteEmployee(String userName) throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        String query = "DELETE FROM employee WHERE username = ?";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            pis.setString(1, userName);
            pis.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void captureLoginTime(String userName) throws SQLException {
        Connection connection = DatabaseConnection.getInstance();

        String now = DateProvider.getDateTimeString();

        String query = "INSERT INTO EmployeeLastLogin (u_name, LastLogin) VALUES (?, ?)";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            pis.setString(1, userName);
            pis.setString(2, now);
            pis.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLastLogin(String userName) throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        String query = "SELECT LastLogin FROM EmployeeLastLogin WHERE u_name = ? ORDER BY LastLogin DESC LIMIT 1";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            pis.setString(1, userName);
            ResultSet resultSet = pis.executeQuery();
            while (resultSet.next()) {
                return resultSet.getString("LastLogin");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hashedBytes = digest.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        String hashedPassoword = sb.toString();        
        return hashedPassoword;
    }
}