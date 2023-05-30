package com.riis.controller;


import com.riis.utils.Sidebar;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
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
    @FXML private HBox header;
    @FXML private Label titlebar;
    @FXML private Button user_btn;
    @FXML private Button setting_btn;
    @FXML private Button mini_btn;
    @FXML private Button max_btn;
    @FXML private Button close_btn;
    @FXML private VBox sidebar;
    @FXML private HBox overview;
    @FXML private HBox requests;
    @FXML private HBox new_resident;
    @FXML private HBox id_renewal;
    @FXML private HBox replace_id;
    public Stage stage;
    public HBox previousClickedHbox = null;
    public HBox clickedHbox = overview;
    public Boolean isMaximized = false;

    public SidebarController(Stage stage) {
        this.stage = stage;
    }

    public SidebarController() {
    }

    public void initialize() throws Exception {
        checkHBox(overview);
    }

    public Parent getRoot() throws Exception {
        return  FXMLLoader.load(getClass().getResource("/com/riis/fxml/Sidebar.fxml"));
    }

    public void getView() throws Exception {
        Parent root = getRoot();
        BorderPane borderPane = (BorderPane) root;
        Sidebar.borderPane = borderPane;
        showView(overview);

        Scene scene = new Scene(root, 1300, 700);
        stage.setScene(scene);
        stage.setTitle("Information officer");
        // stage.setMaximized(true);
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
        if(clickedHBox == overview) {
            // titlebar.setText("Overview");
            OverviewController overviewController = new OverviewController();
            overviewController.getView();
        } else if(clickedHBox == requests) {
            titlebar.setText("Requests");
            RequestsController requestsController = new RequestsController();
            requestsController.getView();
        } else if(clickedHBox == new_resident) {
            titlebar.setText("New Resident Form");
            NewResidentController newResidentController = new NewResidentController();
            newResidentController.getView();
        } else if(clickedHBox == replace_id) {
            titlebar.setText("Replace Lost Id");
            ReplaceController replaceController = new ReplaceController();
            replaceController.getView();
        } else if(clickedHBox == id_renewal) {
            titlebar.setText("Renew Expired Id");
            RenewalController renewalController = new RenewalController();
            renewalController.getView();
        }
    }

    @FXML
    private void closeStage(ActionEvent event) {
        Stage stage = (Stage) close_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void maximizeStage(ActionEvent event) {
        Stage stage = (Stage) max_btn.getScene().getWindow();
        if (isMaximized) {
            stage.setMaximized(false);
            isMaximized = false;
            return;
        }
        stage.setMaximized(true);
        isMaximized = true;
    }

    @FXML
    private void minimizeStage(ActionEvent event) {
        Stage stage = (Stage) close_btn.getScene().getWindow();
        stage.setIconified(true);
    }
}