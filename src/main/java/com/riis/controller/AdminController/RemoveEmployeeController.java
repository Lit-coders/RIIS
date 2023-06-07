package com.riis.controller.AdminController;

import java.sql.SQLException;
import java.util.List;

import com.riis.controller.Controller;
import com.riis.dao.EmployeeDAO;
import com.riis.dao.EmployeeDAOImpl;
import com.riis.model.databasemodel.Employee;
import com.riis.model.viewmodel.JAlert;
import com.riis.model.viewmodel.SidebarModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RemoveEmployeeController implements Controller {

    @FXML
    private Button clear_btn;

    @FXML
    private VBox emp_list;

    @FXML
    private AnchorPane root;

    @FXML
    private Button search_btn;
    
    @FXML
    private TextField search_field;


    public RemoveEmployeeController() {
    }

    @FXML
    void clearSearchField(ActionEvent event) throws SQLException {
        if(!search_field.getText().isEmpty()){
            search_field.clear();
            search_field.requestFocus();
            emp_list.getChildren().clear();
            displayEmpList();
        }
    }
    
    @FXML
    void findEmployee(ActionEvent event) throws SQLException {
        String token = search_field.getText().toLowerCase();

        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        List<Employee> employees = employeeDAO.getAllEmployees();
        emp_list.getChildren().clear();
        boolean isFound = false;
        for(Employee employee: employees){
            if(employee.getUserName().toLowerCase().contains(token) || employee.getFirstName().toLowerCase().contains(token) || employee.getLastName().toLowerCase().contains(token) || employee.getMiddleName().toLowerCase().contains(token) || employee.getJob().toLowerCase().contains(token)){
                String fullName = employee.getFirstName() + " " + employee.getLastName() + " " + employee.getMiddleName();
                Label name_label = new Label(fullName);
                Label user_label = new Label(employee.getUserName());
                HBox box = new HBox(name_label, user_label);
                box.setOnMouseClicked(clicked ->{
                    EmployeeDetailController employeeDetail = new EmployeeDetailController();
                    try {
                        employeeDetail.getView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    employeeDetail.setEmployeeData(employee);
                });
                edit(name_label, user_label, box);
                emp_list.getChildren().add(box);
                isFound = true;
            }
        }
        
        if(!isFound){
            String emptySearch = "Searching with '" + token + "' has not found any related resuts!\n try to search with employee's names and his/her job.";
            Label token_label = new Label(emptySearch);
            token_label.setWrapText(true);
            token_label.getStyleClass().add("token-label");
            emp_list.getChildren().add(token_label);
            search_field.requestFocus();
        }
    }
    
    private void edit(Label name_label, Label user_label, HBox box) {
        name_label.getStyleClass().add("list-label");
        name_label.setAlignment(Pos.CENTER_LEFT);
        name_label.setPrefWidth(280);

        user_label.getStyleClass().add("list-label");
        user_label.setAlignment(Pos.CENTER_RIGHT);
        user_label.setPrefWidth(70);

        box.getStyleClass().add("list-hbox");
        box.setAlignment(Pos.CENTER);
    }

    private void displayEmpList() throws SQLException{
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        List<Employee> employees = employeeDAO.getAllEmployees();
        for(Employee employee: employees){
            String fullName = employee.getFirstName() + " " + employee.getLastName() + " " + employee.getMiddleName();
            Label name_label = new Label(fullName);
            Label user_label = new Label(employee.getUserName());
            HBox box = new HBox(name_label, user_label);
            box.setOnMouseClicked(event ->{
                EmployeeDetailController employeeDetail = new EmployeeDetailController();
                try {
                    employeeDetail.getView();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                employeeDetail.setEmployeeData(employee);
            });
            edit(name_label, user_label, box);
            emp_list.getChildren().add(box);
        }
    }
    
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Admin_fxml/RemoveEmployee.fxml"));

        AnchorPane anchorPane = (AnchorPane) root;
        SidebarModel.borderPane.setCenter(anchorPane);
        ScrollPane pane = (ScrollPane) anchorPane.getChildren().get(1);
        emp_list = (VBox) pane.getContent();
        displayEmpList();
    }

    public void alertMessage(String type, String message) {
        JAlert alert = new JAlert(type, message);
        alert.showAlert();
     }
}