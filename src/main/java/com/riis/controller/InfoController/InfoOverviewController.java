package com.riis.controller.InfoController;

import com.riis.controller.Controller;
import com.riis.utils.Sidebar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class InfoOverviewController implements Controller {
    @FXML
    private Label greeting;

    @FXML 
    private Label loggedInUser;

    @FXML
    private Label loggedInUserFullName;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Label tot_residents;

    @FXML
    private Label tot_residents_male;

    @FXML
    private Label tot_residents_female;

    @FXML
    private Label lastLoginNum;

    @FXML
    private Label lastLoginLetter;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private ListView<HBox> listView;

    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Info_fxml/InfoOverview.fxml"));

        AnchorPane anchorPane = (AnchorPane) root;
        Sidebar.borderPane.setCenter(anchorPane);
    }

}