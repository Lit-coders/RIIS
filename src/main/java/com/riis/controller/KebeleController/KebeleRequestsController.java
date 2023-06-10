package com.riis.controller.KebeleController;


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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class KebeleRequestsController extends BaseRequestsController {

    public void initialize() throws Exception{
        requestModel.setRequestTypeText("Pending Approval Requests");
        requestModel.setRequestTypeComp();
        fetchRequests();
    }
    
    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Base_fxml/BaseRequests.fxml"));
        
        AnchorPane anchorPane = (AnchorPane) root;
        SidebarModel.borderPane.setCenter(anchorPane);
        requestModel.setRequestTypeComp();
        vboxListener("No approval requests for today !");
        initialize();
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
            try {
                handleClick(requestHbox,residentFullName,request);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        // handleHover(requestHbox);
        return requestHbox;
    }


    public void fetchRequests() throws Exception {
        RequestDAO requestDAO = new RequestDAOImpl();

        ObservableList<Request> requests  = requestDAO.getPendingUnapprovedRequests();
        requests = sortReqDate(requests);

        if(requests.size() == 0) {
            Text text = TextGenerator.generateText("No approval requests for today !","Poppins-Regular", 20, "#702FFC");
            VBox reqList = requestModel.getReqListComp();
            reqList.getChildren().add(text);
        }

        VBox reqList = requestModel.getReqListComp();

        for(Request request : requests) {
            HBox hbox = buildHBox(request);
            reqList.getChildren().add(hbox);
        }
    }

    public ObservableList<Request> sortReqDate(ObservableList<Request> requests) {
        FXCollections.reverse(requests);
        return requests;
    }

    public void handleClick(HBox hbox, String residentFullName, Request request) throws Exception {
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
            updateRequest(request);
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

    public void updateRequest(Request request) throws Exception {
        RequestDAO requestDAO = new RequestDAOImpl();
        requestDAO.approveRequest(request);
        String reqDate = request.getRequestDate();
        try {
            for (Node node : requestModel.getReqListComp().getChildren()) {
                if (node instanceof HBox) {
                    for (Node node2 : ((HBox) node).getChildren()) {
                        if (node2 instanceof HBox) {
                            for (Node node3 : ((HBox) node2).getChildren()) {
                                if (node3 instanceof Text) {
                                    if (((Text) node3).getText().equals(reqDate)) {
                                        removeApprovedRequest((HBox) node);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("something happened");
        }
    }

    public void removeApprovedRequest(HBox hbox) {
        VBox reqList = requestModel.getReqListComp();
        reqList.getChildren().remove(hbox);
        showAlert("Request Approved Successfully");
    }

    public void showAlert(String message) {
        JAlert alert = new JAlert("Success", message);
        alert.showAlert();

    }



}