package com.riis.dao;


import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import com.riis.model.databasemodel.Employee;

public interface EmployeeDAO {
    public List<Employee> getAllEmployees() throws SQLException;
    public Employee getEmployeeByUsername(String userName) throws SQLException;
    public boolean addEmployee(Employee employee) throws SQLException;
    public boolean deleteEmployee(String userName) throws SQLException;
    public void captureLoginTime(String userName) throws SQLException;
    public String getLastLogin(String userName) throws SQLException;
    public String hashPassword(String password) throws NoSuchAlgorithmException;
}