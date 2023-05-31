package com.riis.controller;

import com.riis.utils.Sidebar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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
    private TextField sex;

    @FXML
    private TextField citizenship;

    @FXML
    private TextField marital_status;

    @FXML
    private TextField job;

    @FXML
    private TextField MName;

    @FXML
    private TextField btype;

    @FXML
    private TextField house_number;

    @FXML
    private TextField ECF;

    @FXML
    private TextField ECP;

    @FXML
    private TextField HOName;

    @FXML
    private TextField HOFName;

    @FXML
    private TextField HOGFName;

    @FXML
    private TextField HOP;

    @FXML
    private Button upload_photo;

    @FXML
    private Button upload_map;

    @FXML
    private Button approveBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private VBox R_imageHolder;

    @FXML 
    private VBox H_imageHolder;


    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/NewResident.fxml"));
        AnchorPane anchorPane = (AnchorPane) root;
        Sidebar.borderPane.setCenter(anchorPane);

    }
    
}
