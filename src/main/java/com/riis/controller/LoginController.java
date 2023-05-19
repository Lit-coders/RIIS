package com.riis.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController { 

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    private void login() throws Exception {

        // Test: Print Username and Password in the console
        System.out.println("Username: " + username.getText());
        System.out.println("Password: " + password.getText());

        System.out.println("Logging in");
    }

}