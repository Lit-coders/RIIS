package com.riis.controller.AdminController;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.riis.database.DatabaseConnection;
import com.riis.model.databasemodel.Employee;
import com.riis.model.viewmodel.JAlert;

public class Data {
    static JAlert alert;

    public static ArrayList<Employee> getEmployeeData(String sql) {
        ArrayList<Employee> employees = new ArrayList<>();
        try (Statement st = DatabaseConnection.getInstance().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Employee employee = new Employee(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                employees.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static String removeEmployeeData(String sql) {
        String response = "This Employee Is Not Deleted!";
        try (Statement st = DatabaseConnection.getInstance().createStatement()) {
            st.executeUpdate(sql);
            response = "The Employee Is Deleted Successfully!";
            return response;
        } catch (Exception e) {
            response = "There is Something Wrong with your database.";
        }
        return response;
    }

    public static String InsertEmployeeData(String sql) {
        String response = "This Employee Is Not Added!";
        try (Statement st = DatabaseConnection.getInstance().createStatement()) {
            st.executeUpdate(sql);
            response = "The Employee Is Added Successfully!";
            return response;
        } catch (Exception e) {
            response = "There is Something Wrong with your database.";
        }
        return response;
    }

    public static void alertMessage(String type, String message) {
        alert = new JAlert(type, message);
        alert.showAlert();
     }
}
