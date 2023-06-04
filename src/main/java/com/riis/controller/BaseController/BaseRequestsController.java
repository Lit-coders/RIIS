package com.riis.controller.BaseController;

import com.riis.controller.Controller;
import com.riis.model.viewmodel.RequestModel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BaseRequestsController implements Controller {

    @FXML 
    private Label requestType;

    @FXML
    private VBox req_list;

    protected RequestModel requestModel;
    
    public BaseRequestsController() {
        this.requestModel = RequestModel.getInstance();
    }

    @FXML
    public void initialize() throws Exception {
        requestModel.bindRequestType(requestType);
        requestModel.bindReqList(req_list);
    }

    public void getView() throws Exception {
    }
}