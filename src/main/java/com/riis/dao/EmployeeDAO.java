package com.riis.dao;


import java.sql.SQLException;

import com.riis.model.databasemodel.Employee;

public interface EmployeeDAO {
    public Employee getEmployeeByUsername(String userName) throws SQLException;
}