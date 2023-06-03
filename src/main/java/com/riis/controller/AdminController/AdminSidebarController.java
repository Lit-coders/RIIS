package com.riis.controller.AdminController;


import com.riis.controller.Controller;
import com.riis.controller.LoginController;
import com.riis.model.viewmodel.SidebarModel;

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

public class AdminSidebarController implements Controller {
    @FXML private HBox header;
    @FXML private Label titlebar;
    @FXML private Button user_btn;
    @FXML private Button logout_btn;
    @FXML private Button mini_btn;
    @FXML private Button max_btn;
    @FXML private Button close_btn;
    @FXML private VBox sidebar;
    @FXML private HBox addEmployee;
    @FXML private HBox removeEmployee;
    @FXML private Parent root;
    public Stage stage;
    public HBox previousClickedHbox = null;
    public HBox clickedHbox = addEmployee;
    public Boolean isMaximized = false;
    private double xOffset;
    private double yOffset;

    public AdminSidebarController(Stage stage) {
        this.stage = stage;
    }

    public AdminSidebarController() {
    }

    public void initialize() throws Exception {
        setupDragHandlers();
        logout_btn.setStyle("-fx-fill: #976eef;");
        SidebarModel.handleHover(close_btn);
        SidebarModel.handleHover(max_btn);
        SidebarModel.handleHover(mini_btn);
        SidebarModel.handleHover(logout_btn);
        SidebarModel.handleHover(user_btn);
        SidebarModel.titlebar = titlebar;
        checkHBox(addEmployee);
    }

    private void setupDragHandlers() {
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

    public Parent getRoot() throws Exception {
        return  FXMLLoader.load(getClass().getResource("/com/riis/fxml/Admin_fxml/AdminSidebar.fxml"));
    }

    public void getView() throws Exception {
        stage.close();
        Parent root = getRoot();
        BorderPane borderPane = (BorderPane) root;
        SidebarModel.borderPane = borderPane;
        showView(addEmployee);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin Pannel");
        stage.show();
    }

    @FXML
    private void handleHBoxClick(MouseEvent event) throws Exception {
        uncheckPreviousHBox();
        HBox clickedHBox = (HBox) event.getSource();
        showView(clickedHBox);
        previousClickedHbox = clickedHBox;
        checkHBox(clickedHBox);

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
            Label label = (Label) addEmployee.getChildren().get(1);
            SVGPath svgPath = (SVGPath) addEmployee.getChildren().get(0);
            label.setStyle("-fx-text-fill: #825FE5;");
            svgPath.setStyle("-fx-stroke: #825FE5;");
        }
    }

    public void showView(HBox clickedHBox) throws Exception {
        if(clickedHBox == addEmployee) {
            setTitlebar("Admin Panel | Add Employee");
            AddEmployeeController addEmployeeController = new AddEmployeeController();
            addEmployeeController.getView();
        } else if(clickedHBox == removeEmployee) {
            setTitlebar("Admin Panel | Remove Employee");
            RemoveEmployeeController removeEmployeeController = new RemoveEmployeeController();
            removeEmployeeController.getView();
        } 
    }

    private void setTitlebar(String title) {
        SidebarModel.titlebar.setText(title);
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

    @FXML
    private void logout() throws Exception {
        Stage stage = (Stage) logout_btn.getScene().getWindow();
         
        LoginController loginController = new LoginController(stage);
        loginController.getView();
    }
}