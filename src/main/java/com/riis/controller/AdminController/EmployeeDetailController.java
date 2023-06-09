package com.riis.controller.AdminController;

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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EmployeeDetailController implements Controller{
    public EmployeeDetailController(){
    }
    @FXML
    private Label Fname_label;

    @FXML
    private Label Lname_label;

    @FXML
    private Label Mname_label;

    @FXML
    private Label Uname_label;

    @FXML
    private Button back_btn;

    @FXML
    private AnchorPane bocy_anchor;

    @FXML
    private VBox detail_box;

    @FXML
    private HBox detail_hbox;

    @FXML
    private HBox detail_hbox1;

    @FXML
    private HBox detail_hbox2;

    @FXML
    private HBox detail_hbox3;

    @FXML
    private HBox detail_hbox31;

    @FXML
    private Label job_label;

    @FXML
    private Label name_label;

    @FXML
    private Label pass_label;

    @FXML
    private Button remove_btn;

    @FXML
    void backToEmpList(ActionEvent event) throws Exception {
        RemoveEmployeeController removeEmployeeController = new RemoveEmployeeController();
        removeEmployeeController.getView();
    }

    @FXML
    void removeEmployee(ActionEvent event) throws Exception {
        JAlert jalert = new JAlert("Confirmation", "Are you sure you want to remove the employee");
        jalert.showAlert();
        if(jalert.getResult().getText().equals("Approve")){    
            EmployeeDAO employeeDAO = new EmployeeDAOImpl();
            boolean isRemoved = employeeDAO.deleteEmployee(Uname_label.getText());
            if(isRemoved){
                JAlert alert = new JAlert("Success", "Employee has been removed");
                alert.show();
            } else {
                JAlert alert = new JAlert("Error", "Employee has not been removed");
                alert.show();
            }
            RemoveEmployeeController removeEmployeeController = new RemoveEmployeeController();
            removeEmployeeController.getView();

        }
    }

    public void setEmployeeData(Employee employee){
        String fullName = employee.getFirstName() + " " + employee.getLastName() + " " + employee.getMiddleName();
        System.out.println(fullName);
        name_label.setText(employee.getFirstName() + "'s Information");
        Fname_label.setText(employee.getFirstName());
        Lname_label.setText(employee.getLastName());
        Mname_label.setText(employee.getMiddleName());
        Uname_label.setText(employee.getUserName());
        pass_label.setText(employee.getPassword());
        job_label.setText(employee.getJob());
        
    }
    
    private void initializeNullControls(HBox hbox, VBox vbox) {
        name_label = (Label) hbox.getChildren().get(1);

        HBox fname_box = (HBox) vbox.getChildren().get(0);
        Fname_label = (Label) fname_box.getChildren().get(1);
        
        HBox lname_box = (HBox) vbox.getChildren().get(1);
        Lname_label = (Label) lname_box.getChildren().get(1);

        HBox mname_box = (HBox) vbox.getChildren().get(2);
        Mname_label = (Label) mname_box.getChildren().get(1);

        HBox uname_box = (HBox) vbox.getChildren().get(3);
        Uname_label = (Label) uname_box.getChildren().get(1);

        HBox pass_box = (HBox) vbox.getChildren().get(4);
        pass_label = (Label) pass_box.getChildren().get(1);

        HBox job_box = (HBox) vbox.getChildren().get(5);
        job_label = (Label) job_box.getChildren().get(1);
    }

    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Admin_fxml/EmployeeDetail.fxml"));

        AnchorPane anchorPane = (AnchorPane) root;
        SidebarModel.borderPane.setCenter(anchorPane);

        HBox hbox = (HBox) anchorPane.getChildren().get(0);
        VBox vbox = (VBox) anchorPane.getChildren().get(1);
        System.out.println();

        initializeNullControls(hbox, vbox);
    }

}