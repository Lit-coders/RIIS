package com.riis.controller.AdminController;

import java.sql.SQLException;

import com.riis.controller.Controller;
import com.riis.dao.EmployeeDAO;
import com.riis.dao.EmployeeDAOImpl;
import com.riis.model.databasemodel.Employee;
import com.riis.model.viewmodel.SidebarModel;
import com.riis.utils.JAlert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class AddEmployeeController implements Controller {
    @FXML
    private TextField Fname_field;

    @FXML
    private TextField Lname_field;

    @FXML
    private TextField Mname_field;

    @FXML
    private TextField Uname_field;

    @FXML
    private Button approve_btn;

    @FXML
    private Button clear_btn;

    @FXML
    private TextField job_field;

    @FXML
    private TextField pass_field;

    @FXML
    void addEmployee(ActionEvent event) throws SQLException {
        AnchorPane pane = (AnchorPane) (clear_btn).getParent();
        VBox box = (VBox) pane.getChildren().get(1);
        
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        Employee employee = new Employee();
        
        employee.setFirstName(Fname_field.getText());
        employee.setLastName(Lname_field.getText());
        employee.setMiddleName(Mname_field.getText());
        employee.setUserName(Uname_field.getText());    
        employee.setPassword(pass_field.getText());
        employee.setJob(job_field.getText());

        if(!isEmpty(box) && isValid(box) && !isRepeated(box) && isStrong(box)){
            boolean isAdded = employeeDAO.addEmployee(employee);
            if(isAdded){
                JAlert alert = new JAlert("Success", "Employee Added Successfully!");
                alert.showAlert();
                clearAllFields(box);
            } else {
                JAlert alert = new JAlert("Error", "Employee Not Added!");
                alert.showAlert();
            }
        }
    }

    private boolean isStrong(VBox box) {
        TextField field = (TextField) box.getChildren().get(9);
        if(field.getText().length() < 5){
            alertMessage("Password Length must be greater than 5.");
            field.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isRepeated(VBox box) throws SQLException {
        TextField field = (TextField) box.getChildren().get(7);

        EmployeeDAO employeeDAO = new EmployeeDAOImpl();

        Employee employee = employeeDAO.getEmployeeByUsername(field.getText());
        if(employee.getUserName() != null) {
            alertMessage("Username already exists!");
            field.requestFocus();
            return true;
        }
        return false;
    }

    private boolean isEmpty(VBox box) {
        for(int i=1; i<box.getChildren().size(); i += 2){
            TextField field = (TextField) box.getChildren().get(i);
            if(field.getText().isBlank()){
                alertMessage("You have Empty Fields!");
                field.requestFocus();
                return true;
            }
        }
        return false;
    }

    private boolean isValid(VBox box) {
        for(int i=1; i<box.getChildren().size(); i += 2){
            TextField field = (TextField) box.getChildren().get(i);
            for(int j=0; j<field.getText().length(); j++){
                if(field.getText().charAt(j) == '\'' || field.getText().charAt(j) == '"'){
                    alertMessage("Using Quotation marks is not allowed!");
                    field.requestFocus();
                    return false;
                }
            }
            // if(i == 1 || i == 3 || i == 5 || i == 11){
            //     String alphaPattern = "^[a-zA-Z\\s]*$";
            //     if(field.getText().matches(alphaPattern)){
            //         Data.alertMessage("Error", "You have inserted invalid data!");
            //         field.requestFocus();
            //         return false;
            //     }
            // }
        }
        return true;
    }

    @FXML
    void clearAllFields(ActionEvent event) {
        AnchorPane pane = (AnchorPane) (clear_btn).getParent();
        VBox box = (VBox) pane.getChildren().get(1);
        clearAllFields(box);
    }
    
    void clearAllFields(VBox box){
        for(int i=1; i<box.getChildren().size(); i += 2){
            TextField field = (TextField) box.getChildren().get(i);
            field.clear();
        }
        TextField field = (TextField) box.getChildren().get(1);
        field.requestFocus();
    }

    public void alertMessage(String message){
        JAlert alert = new JAlert("Error",message);
        alert.showAlert();
    }

    @FXML
    void handleKeyPress (KeyEvent event){
        TextField currentField = (TextField) event.getSource();
        VBox box = (VBox) currentField.getParent();
        int totalSize = box.getChildren().size();
        int currentIndex = box.getChildren().indexOf(currentField);
        if (event.getCode() == KeyCode.ENTER){
            if (currentIndex < totalSize-2){
                box.getChildren().get(currentIndex+2).requestFocus();
            }
            else{
                approve_btn.fire();
            }

        } else if (event.getCode() == KeyCode.BACK_SPACE){
            if (currentIndex >=2 && currentField.getText().isEmpty()){
                box.getChildren().get(currentIndex-2).requestFocus();
            } 
            else{
                box.getChildren().get(currentIndex).requestFocus();
            }
        }

    }


    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Admin_fxml/AddEmployee.fxml"));
        AnchorPane anchorPane = (AnchorPane) root;
        SidebarModel.borderPane.setCenter(anchorPane);
    }
}