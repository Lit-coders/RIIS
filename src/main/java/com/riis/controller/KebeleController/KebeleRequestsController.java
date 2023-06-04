package com.riis.controller.KebeleController;

import com.riis.controller.BaseController.BaseRequestsController;
import com.riis.model.viewmodel.SidebarModel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

public class KebeleRequestsController extends BaseRequestsController {

    public void initialize() throws Exception{
        requestModel.setRequestTypeText("Pending Approval Requests");
        requestModel.setRequestTypeComp();
    }
    
    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Base_fxml/BaseRequests.fxml"));
        
        AnchorPane anchorPane = (AnchorPane) root;
        SidebarModel.borderPane.setCenter(anchorPane);
        requestModel.setRequestTypeComp();
        initialize();
    }   
}