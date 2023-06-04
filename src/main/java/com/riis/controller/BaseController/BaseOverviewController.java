package com.riis.controller.BaseController;


import com.riis.controller.Controller;
import com.riis.model.viewmodel.OverviewModel;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class BaseOverviewController implements Controller {

    @FXML
    protected Label greeting;

    @FXML 
    public Label loggedInUser;

    @FXML
    protected Label loggedInUserFullName;

    @FXML
    protected Label date;

    @FXML
    protected Label time;

    @FXML
    protected Label tot_residents;

    @FXML
    protected Label tot_residents_male;

    @FXML
    protected Label tot_residents_female;

    @FXML
    protected Label lastLoginNum;

    @FXML
    protected Label lastLoginLetter;

    @FXML
    protected CategoryAxis xAxis;

    @FXML
    protected NumberAxis yAxis;

    @FXML
    protected ListView<HBox> listView;

    protected OverviewModel overviewModel;

    public BaseOverviewController() {
        this.overviewModel = OverviewModel.getInstance();
    }

    @FXML
    public void initialize() throws Exception {
        overviewModel.bindGreeting(greeting);
        overviewModel.bindLoggedInUser(loggedInUser);
        overviewModel.bindLoggedInUserFullName(loggedInUserFullName);
        overviewModel.bindDate(date);
        overviewModel.bindTime(time);
        overviewModel.bindTotResidents(tot_residents);
        overviewModel.bindTotResidentsMale(tot_residents_male);
        overviewModel.bindTotResidentsFemale(tot_residents_female);
        overviewModel.bindLastLoginNum(lastLoginNum);
        overviewModel.bindLastLoginLetter(lastLoginLetter);
        // recent activity and bar chart are remaining 
    }

    public void getView() throws Exception {
    }   
}