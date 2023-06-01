package com.riis.controller.InfoController;

import com.riis.controller.Controller;
import com.riis.utils.Sidebar;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class InfoRequestsController implements Controller {
    
    public InfoRequestsController() {
    }

    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Info_fxml/InfoRequests.fxml"));

        AnchorPane anchorPane = (AnchorPane) root;
        Sidebar.borderPane.setCenter(anchorPane);
    }

}