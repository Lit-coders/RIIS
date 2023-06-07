package com.riis.controller.FinController;

import com.riis.controller.BaseController.BaseOverviewController;
import com.riis.model.viewmodel.SidebarModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class FinOverviewController extends BaseOverviewController {

    public void initialize() throws Exception {
        renderStatic();
        // other initialization will be done in the future this is for testing purpose
    }

    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Base_fxml/BaseOverview.fxml"));

        AnchorPane anchorPane = (AnchorPane) root;
        SidebarModel.borderPane.setCenter(anchorPane);
        initialize();
    }

}