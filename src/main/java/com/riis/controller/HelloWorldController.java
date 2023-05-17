package com.riis.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloWorldController {

    @FXML
    private Label label;

    @FXML
    private void handleButtonAction(ActionEvent event){
        label.setText("Hello World!");
    }

}
