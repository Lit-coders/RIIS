package com.riis.controller.InfoController;

import com.riis.controller.BaseController.BaseOverviewController;
import com.riis.model.viewmodel.SidebarModel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class InfoOverviewController extends BaseOverviewController {

    public void initialize() throws Exception {
        overviewModel.setLoggedInUserTextComp();
        // other initialization will be done in the future
    }

    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Base_fxml/BaseOverview.fxml"));

        AnchorPane anchorPane = (AnchorPane) root;
        SidebarModel.borderPane.setCenter(anchorPane);
        initialize();
    }

}