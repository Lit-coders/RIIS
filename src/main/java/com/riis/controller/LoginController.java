package com.riis.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Controller { 

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    public Stage stage;

    public LoginController(Stage stage) {
        this.stage = stage;
    }

    public LoginController() {
    }

    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/view/Login.fxml"));

        Scene scene = new Scene(root);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    } 

    @FXML
    private void login() throws Exception {

        String user = username.getText();
        String pass = password.getText();
        
        // Test: Print Username and Password in the console
        System.out.println("Username: " + username.getText());
        System.out.println("Password: " + password.getText());

        if(isValidated()) {
            if (user.equals("username") && pass.equals("password")) {
                System.out.println("Login Successful");
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();

                SidebarController sidebarController = new SidebarController(stage);
                sidebarController.getView();

            } else {
                System.out.println("Login Failed");
            }
        }

    }
    
    private boolean isValidated() {
        
        if (username.getText().equals("")) {
            System.out.println("Username text field cannot be blank.");
            username.requestFocus();
        } else if (username.getText().length() < 5 || username.getText().length() > 25) {
            System.out.println("Username text field cannot be less than 5 and greater than 25 characters.");
            username.requestFocus();
        } else if (password.getText().equals("")) {
            System.out.println("Password text field cannot be blank.");
            password.requestFocus();
        } else if (password.getText().length() < 5 || password.getText().length() > 25) {
            System.out.println("Password text field cannot be less than 5 and greater than 25 characters.");
            password.requestFocus();
        } else {
            return true;
        }
        return false;
    }

}