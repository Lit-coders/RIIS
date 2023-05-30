package com.riis.controller;

import com.riis.view.Sidebar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class NewResidentController implements Controller {
    @FXML
    private TextField Name;

    @FXML
    private TextField FName;

    @FXML
    private TextField GFName;

    @FXML
    private TextField DOB;

    @FXML
    private TextField POB;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField Sex;

    @FXML
    private TextField Citizenship;

    @FXML
    private TextField maritalStatus;

    @FXML
    private TextField Job;

    @FXML
    private TextField MName;

    @FXML
    private TextField bloodType;

    @FXML
    private TextField houseNmber;

    @FXML
    private TextField emergencyName;

    @FXML
    private TextField emergencyPhone;

    @FXML
    private TextField HOName;

    @FXML
    private TextField HOFName;

    @FXML
    private TextField HOGFName;

    @FXML
    private TextField HOPhoneNumber;

    @FXML
    private Button uploadPhoto;

    @FXML
    private Button uploadMap;

    @FXML
    private Button approveBtn;

    @FXML
    private Button clearBtn;


    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/view/NewResident.fxml"));
        AnchorPane anchorPane = (AnchorPane) root;
        Sidebar.borderPane.setCenter(anchorPane);

    }
    
}
