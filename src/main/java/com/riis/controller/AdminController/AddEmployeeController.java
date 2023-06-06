package com.riis.controller.AdminController;

import java.util.ArrayList;

import com.riis.controller.Controller;
import com.riis.model.databasemodel.Employee;
import com.riis.model.viewmodel.SidebarModel;
import com.riis.utils.JAlert;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    void addEmployee(ActionEvent event) {
        AnchorPane pane = (AnchorPane) (clear_btn).getParent();
        VBox box = (VBox) pane.getChildren().get(1);
        if(!isEmpty(box) && isValid(box) && !isRepeated(box) && isStrong(box)){
            String sql = "INSERT INTO Employee values('" + Uname_field.getText() + "','" + pass_field.getText() + "','" + Fname_field.getText() + "','" + Lname_field.getText() + "','" + Mname_field.getText() + "','" + job_field.getText() + "')";
            String response = Data.InsertEmployeeData(sql);
            System.out.println(sql);
            System.out.println(response);
            JAlert alert = new JAlert("Success", "Employee Added Successfully!");
            alert.showAlert();;
            clearAllFields(box);
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

    private boolean isRepeated(VBox box) {
        TextField field = (TextField) box.getChildren().get(7);
        String sql = "SELECT * FROM Employee";
        ArrayList<Employee> employees = Data.getEmployeeData(sql);
        for(Employee employee: employees){
            if(field.getText().equals(employee.getUserName())){
                alertMessage("Username already exists!");
                field.requestFocus();
                return true;
            }
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


    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Admin_fxml/AddEmployee.fxml"));
        AnchorPane anchorPane = (AnchorPane) root;
        SidebarModel.borderPane.setCenter(anchorPane);
    }
    
}
