package com.riis.controller.BaseController;

import com.riis.controller.Controller;
import com.riis.controller.LoginController;
import com.riis.model.viewmodel.SidebarModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class BaseSidebarController implements Controller {
    @FXML protected HBox header;
    @FXML protected Label titlebar;
    @FXML protected Button user_btn;
    @FXML protected Button logout_btn;
    @FXML protected Button mini_btn;
    @FXML protected Button max_btn;
    @FXML protected Button close_btn;
    @FXML protected VBox sidebar;
    @FXML protected HBox overview;
    @FXML protected HBox requests;
    @FXML protected HBox new_resident;
    @FXML protected HBox id_renewal;
    @FXML protected HBox replace_id;
    @FXML protected Parent root;
    protected Stage stage;
    protected HBox previousClickedHbox = null;
    protected HBox clickedHbox = null;
    protected Boolean isMaximized = false;
    protected double xOffset;
    protected double yOffset;

    public void initialize() throws Exception {
        setupDragHandlers();
        logout_btn.setStyle("-fx-fill: #976eef;");
        SidebarModel.handleHover(close_btn);
        SidebarModel.handleHover(max_btn);
        SidebarModel.handleHover(mini_btn);
        SidebarModel.handleHover(logout_btn);
        SidebarModel.handleHover(user_btn);
        SidebarModel.titlebar = titlebar;
        checkHBox(overview);
    }

    protected void setupDragHandlers() {
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            Stage stage = (Stage) root.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    public BaseSidebarController(Stage stage) {
        this.stage = stage;
    }

    public BaseSidebarController() {
    }

    public void getView() throws Exception {
    }

    @FXML
    protected void handleHBoxClick(MouseEvent event) throws Exception {
        // Uncheck the previous clicked HBox
        uncheckPreviousHBox();

        // Get the clicked HBox
        HBox clickedHBox = (HBox) event.getSource();

        // Set the clicked HBox
        SidebarModel.clickedHbox = clickedHBox;

        showView();

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

    public void showView() throws Exception {

    }

    protected void setTitlebar(String title) {
        SidebarModel.titlebar.setText(title);
    }

    @FXML
    protected void closeStage(ActionEvent event) {
        Stage stage = (Stage) close_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void maximizeStage(ActionEvent event) {
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
    protected void minimizeStage(ActionEvent event) {
        Stage stage = (Stage) close_btn.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    protected void logout() throws Exception {
        Stage stage = (Stage) logout_btn.getScene().getWindow();
         
        LoginController loginController = new LoginController(stage);
        loginController.getView();
    }
}