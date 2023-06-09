package com.riis.controller.BaseController;

import com.riis.controller.Controller;
import com.riis.model.viewmodel.RequestModel;
import com.riis.utils.TextGenerator;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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

    public void vboxListener(String message) {
        // listen for changes in the vbox if there are any hbox children
        if(requestModel.getReqListComp().getChildren().size() == 0) {
            Text text = TextGenerator.generateText(message,"Poppins-Regular", 20, "#702FFC");
            VBox reqList = requestModel.getReqListComp();
            reqList.getChildren().add(text);
        }
        requestModel.getReqListComp().getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                if(requestModel.getReqListComp().getChildren().size() == 0) {
                    Text text = TextGenerator.generateText(message,"Poppins-Regular", 20, "#702FFC");
                    VBox reqList = requestModel.getReqListComp();
                    reqList.getChildren().add(text);
                }
            }
        });
    }

    public void getView() throws Exception {
    }
}