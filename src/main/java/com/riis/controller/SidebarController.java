package com.riis.controller;

import com.riis.view.Sidebar;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class SidebarController implements Controller {
    @FXML
    private VBox sidebar;
    @FXML
    private HBox overview;
    @FXML
    private HBox requests;
    @FXML
    private HBox new_resident;
    @FXML
    private HBox id_renewal;
    @FXML
    private HBox replace_id;
    public Stage stage;
    public HBox previousClickedHbox = null;
    public HBox clickedHbox = overview;

    public SidebarController(Stage stage) {
        this.stage = stage;
    }

    public SidebarController() {
    }

    public void initialize() throws Exception {
        checkHBox(overview);
    }

    public Parent getRoot() throws Exception {
        return FXMLLoader.load(getClass().getResource("/com/riis/view/Sidebar.fxml"));
    }

    public void getView() throws Exception {
        Parent root = getRoot();
        BorderPane borderPane = (BorderPane) root;
        Sidebar.borderPane = borderPane;
        showView(overview);

        Scene scene = new Scene(root,1300,700);
        stage.setScene(scene);
        stage.setTitle("Information officer");
        // stage.setMaximized(true);
        stage.show();

    }

    @FXML
    private void handleHBoxClick(MouseEvent event) throws Exception {
        // Uncheck the previous clicked HBox
        uncheckPreviousHBox();

        // Get the clicked HBox
        HBox clickedHBox = (HBox) event.getSource();

        showView(clickedHBox);

        // Set the previous clicked HBox
        previousClickedHbox = clickedHBox;

        // Set the style of the clicked HBox
        checkHBox(clickedHBox);

        // Set the style of the other HBoxes
        for (Node node : sidebar.getChildren()) {
            if (node instanceof HBox && node != clickedHBox) {
                uncheckHBox((HBox) node);
            }
        }

    }

    public void checkHBox(HBox hbox) {
        Label label = (Label) hbox.getChildren().get(1);
        SVGPath svgPath = (SVGPath) hbox.getChildren().get(0);
        hbox.setStyle("-fx-background-color: #EBEBEB; -fx-border-color: #2900CF; -fx-border-width: 0 8 0 0;");
        label.setStyle("-fx-text-fill: #2900CF;");
        svgPath.setStyle("-fx-stroke: #2900CF;");
    }

    public void uncheckHBox(HBox hbox) {
        hbox.setStyle("-fx-background-color: white;");
    }

    public void uncheckPreviousHBox() {
        if (previousClickedHbox != null) {
            Label label = (Label) previousClickedHbox.getChildren().get(1);
            SVGPath svgPath = (SVGPath) previousClickedHbox.getChildren().get(0);
            label.setStyle("-fx-text-fill: #825FE5;");
            svgPath.setStyle("-fx-stroke: #825FE5;");
        } else {
            Label label = (Label) overview.getChildren().get(1);
            SVGPath svgPath = (SVGPath) overview.getChildren().get(0);
            label.setStyle("-fx-text-fill: #825FE5;");
            svgPath.setStyle("-fx-stroke: #825FE5;");
        }
    }

    public void showView(HBox clickedHBox) throws Exception {
        if (clickedHBox == overview) {
            OverviewController overviewController = new OverviewController();
            overviewController.getView();
        } else if (clickedHBox == requests) {
            RequestsController requestsController = new RequestsController();
            requestsController.getView();
        } else if (clickedHBox == new_resident) {
            NewResidentController newResidentController = new NewResidentController();
            newResidentController.getView();
        }
    }
}