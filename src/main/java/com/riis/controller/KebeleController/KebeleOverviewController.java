package com.riis.controller.KebeleController;

import java.util.List;

import com.riis.controller.BaseController.BaseOverviewController;
import com.riis.dao.RequestDAO;
import com.riis.dao.RequestDAOImpl;
import com.riis.model.databasemodel.Request;
import com.riis.model.viewmodel.SidebarModel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class KebeleOverviewController extends BaseOverviewController {

    public void initialize() throws Exception {
        renderStatic();
        updateDate();
        updateLastLogin();
        updateTime();
        dynamicAnimator();
        fetchRecentActivity();
    }

    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Base_fxml/BaseOverview.fxml"));

        AnchorPane anchorPane = (AnchorPane) root;
        SidebarModel.borderPane.setCenter(anchorPane);
        initialize();
    }

    public void fetchRecentActivity() throws Exception {
        RequestDAO requestDAO = new RequestDAOImpl();
        List<Request> unpaidRequests = requestDAO.getPendingSealedRequests();  
        for(Request request : unpaidRequests) {
            HBox hBox = buildHBox(request);
            overviewModel.getRecentActivityComp().getChildren().add(hBox);
        }        
    }

}