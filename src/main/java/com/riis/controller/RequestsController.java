package com.riis.controller;

import com.riis.view.Sidebar;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class RequestsController implements Controller {
    
    public RequestsController() {
    }

    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/view/Requests.fxml"));

        AnchorPane anchorPane = (AnchorPane) root;
        Sidebar.borderPane.setCenter(anchorPane);
    }

}