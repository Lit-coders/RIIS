package com.riis.controller.InfoController;

import com.riis.controller.BaseController.BaseRequestsController;
import com.riis.model.viewmodel.SidebarModel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InfoRequestsController extends BaseRequestsController {

    public void initialize() throws Exception{
        requestModel.setRequestTypeText("Pending Sealed Requests");
        requestModel.setRequestTypeComp();
        fetchRequests();
    }
    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Base_fxml/BaseRequests.fxml"));

        AnchorPane anchorPane = (AnchorPane) root;
        SidebarModel.borderPane.setCenter(anchorPane);
        initialize();
    }

    public void fetchRequests() throws Exception {
        VBox reqList = requestModel.getReqListComp();

        reqList.getChildren().clear();
         
        // fill the list with sample HBox requests
        for (int i = 0; i < 15; i++) {
            HBox req = new HBox();
            req.setPrefHeight(50);
            req.setPrefWidth(915);
            req.setStyle("-fx-background-color: #F5F0F0; -fx-background-radius: 20px; -fx-border-radius: 20px; -fx-border-color: #D3D3D3; -fx-border-width: 1px;");
            reqList.getChildren().add(req);
        }
    }

}