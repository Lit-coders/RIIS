package com.riis.model.viewmodel;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RequestModel {
    private static RequestModel instance;
    
    private Label requestType;
    private String requestTypeText;
    private VBox req_list;
    
    private RequestModel() {
    }
     
    public static RequestModel getInstance() {
        if (instance == null) {
            instance = new RequestModel();
        }
        return instance;
    }

    public Label getRequestTypeComp() {
        return requestType;
    }

    public void bindRequestType(Label requestType) {
        this.requestType = requestType;
    }

    public void setRequestTypeText(String requestTypeText) {
        this.requestTypeText = requestTypeText;
    }

    public void setRequestTypeComp() {
        this.requestType.setText(requestTypeText);
    }

    public VBox getReqListComp() {
        return req_list;
    }
    
    public void bindReqList(VBox req_list) {
        this.req_list = req_list;
    }
}