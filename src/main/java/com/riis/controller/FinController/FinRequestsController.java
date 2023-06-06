package com.riis.controller.FinController;

import java.util.List;

import com.riis.controller.BaseController.BaseRequestsController;
import com.riis.dao.RequestDAO;
import com.riis.dao.RequestDAOImpl;
import com.riis.dao.ResidentDAO;
import com.riis.dao.ResidentDAOImpl;
import com.riis.model.databasemodel.Request;
import com.riis.model.databasemodel.Resident;
import com.riis.model.viewmodel.SidebarModel;
import com.riis.utils.JAlert;
import com.riis.utils.TextGenerator;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class FinRequestsController extends BaseRequestsController {

    public void initialize() throws Exception{
        requestModel.setRequestTypeText("Pending Unpaid Requests");
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
        RequestDAO requestDAO = new RequestDAOImpl();

        List<Request> requests  = requestDAO.getPendingUnpaidRequests();

        VBox reqList = requestModel.getReqListComp();

        for(Request request : requests) {
            HBox hbox = buildHBox(request);
            reqList.getChildren().add(hbox);
        }
    }

    public HBox buildHBox(Request request) throws Exception {
        // main hbox
        HBox requestHbox = new HBox();
        requestHbox.setPadding(new Insets(0, 10, 0, 10));
        requestHbox.setPrefHeight(50);
        requestHbox.setPrefWidth(915);
        requestHbox.setStyle("-fx-background-color: #F5F0F0; -fx-background-radius: 20px; -fx-border-radius: 20px; -fx-border-color: #F5F0F0; -fx-border-width: 3px");

        
        // resident name hbox
        HBox residentName = new HBox();
        residentName.setAlignment(Pos.CENTER_LEFT);
         
        // get the resident name from the database using the resident id
        int residentId = request.getResidentID();
        ResidentDAO residentDAO = new ResidentDAOImpl();
        Resident resident = residentDAO.getResidentNameByID(residentId);
        String residentFullName = resident.getName() + " " + resident.getFName();

        Text nameText = TextGenerator.generateText(residentFullName,"Poppins-Regular", 20, "#702FFC");
        residentName.getChildren().add(nameText);

        // request date hbox
        HBox requestDate = new HBox();
        requestDate.setAlignment(Pos.CENTER_RIGHT);

        // get the request date from the request object
        String requestDateStr = request.getRequestDate();
        Text requestDateText = TextGenerator.generateText(requestDateStr,"Poppins-Regular", 20, "#702FFC");
        requestDate.getChildren().add(requestDateText);

        // set the widths of the hboxes
        residentName.prefWidthProperty().bind(requestHbox.widthProperty().divide(2));
        requestDate.prefWidthProperty().bind(requestHbox.widthProperty().divide(2));

        requestHbox.getChildren().addAll(residentName, requestDate);

        requestHbox.setOnMouseClicked(e -> {
            handleClick(requestHbox,residentFullName,request);
        });
        // handleHover(requestHbox);
        return requestHbox;
    }

    public void handleHover(HBox hbox) {
        hbox.setOnMouseEntered(e -> {
            hbox.setStyle("-fx-background-color: #F5F0F0; -fx-background-radius: 20px; -fx-border-radius: 20px; -fx-border-color: #976EEF; -fx-border-width: 3px");
        });
        hbox.setOnMouseExited(e -> {
            hbox.setStyle("-fx-background-color: #F5F0F0; -fx-background-radius: 20px; -fx-border-radius: 20px; -fx-border-color: #F5F0F0; -fx-border-width: 3px");
        });
    }

    public void handleClick(HBox hbox, String residentFullName, Request request) {
        hbox.setStyle("-fx-background-color: #976EEF; -fx-background-radius: 20px; -fx-border-radius: 20px; -fx-border-color: #976EEF; -fx-border-width: 3px");
        handleColorChange(hbox, Color.WHITE);
        int reqType = request.getRequestType();
        String message = "Are you sure you want to approve " + residentFullName + "'s ";
        if(reqType == 0) {
            message += "ID Creation request ?";
        } else if(reqType == 1) {
            message += "ID Renewal request ?";
        } else {
            message += "Lost ID Replacement request ?";
        }
        JAlert alert = new JAlert("Confirmation", message);
        alert.showAlert();
        if(alert.getResult().getText().equals("Approve")) {
            approveRequest(request);
            hbox.setStyle("-fx-background-color: #F5F0F0; -fx-background-radius: 20px; -fx-border-radius: 20px; -fx-border-color: #F5F0F0; -fx-border-width: 3px");
            handleColorChange(hbox, Color.valueOf("#702FFC"));
        } else {
            hbox.setStyle("-fx-background-color: #F5F0F0; -fx-background-radius: 20px; -fx-border-radius: 20px; -fx-border-color: #F5F0F0; -fx-border-width: 3px");
            handleColorChange(hbox, Color.valueOf("#702FFC"));
        }
    }

    public void handleColorChange(HBox hbox,Paint color) {
        for(Node node : hbox.getChildren()) {
            if(node instanceof HBox) {
                for(Node node2 : ((HBox) node).getChildren()) {
                    if(node2 instanceof Text) {
                        ((Text) node2).setFill(color);
                    }
                }
            }
        }
    }

    public void approveRequest(Request request) {
        System.out.println(request);
    }
}