package com.riis.controller;


import com.riis.auth.AuthenticationManager;
import com.riis.controller.FinController.FinSidebarController;
import com.riis.controller.InfoController.InfoSidebarController;
import com.riis.controller.KebeleController.KebeleSidebarController;
import com.riis.model.viewmodel.OverviewModel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Controller { 
    @FXML
    private HBox close_btn;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginButton;

    @FXML
    private Parent root;

    public Stage stage;
    private double xOffset;
    private double yOffset;

    private OverviewModel overviewModel = OverviewModel.getInstance();
    

    public LoginController(Stage stage) {
        this.stage = stage;
    }

    public LoginController() {
    }
    
    public void initialize() throws Exception {
        setupDragHandlers();
        handleHoverCloseButton();
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

    public void getView() throws Exception {
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    } 

    @FXML
    private void login() throws Exception {

        String user = username.getText();
        String pass = password.getText();
        
        // Test: Print Username and Password in the console
        System.out.println("Username: " + username.getText());
        System.out.println("Password: " + password.getText());
        String job = AuthenticationManager.authenticate(user, pass);

        if (!job.isEmpty()) {
            System.out.println("Login Successful");
            overviewModel.setLoggedInUserText(user);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            
            switch(job) {
                case "Information Officer":
                    InfoSidebarController sidebarController = new InfoSidebarController(stage);
                    sidebarController.getView();
                    break;
                case "Finance Officer":
                    FinSidebarController financeSidebarController = new FinSidebarController(stage);
                    financeSidebarController.getView();
                    break;
                case "Kebele Manager":
                    KebeleSidebarController kebeleSidebarController = new KebeleSidebarController(stage);
                    kebeleSidebarController.getView();
                    break;
                // case "Admin":
                //     AdminSidebarController adminSidebarController = new AdminSidebarController(stage);
                //     adminSidebarController.getView();
                //     break;

            }
        } else {
            showAlert(AlertType.ERROR, "login failed", "incorrect username or password");
            System.out.println("Login Failed");
        }
    }

    @FXML
    private void closeLoginStage(MouseEvent event) {
        Stage stage = (Stage) close_btn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleKeyPress(KeyEvent event) throws Exception {
        if(event.getSource() == username){
            if (event.getCode()== KeyCode.ENTER){
                password.requestFocus();
            }
        } else if(event.getSource() == password){
            if (event.getCode()== KeyCode.ENTER){
                login();
            }
        }
    }

    private void handleHoverCloseButton() {
        SVGPath closeIcon = (SVGPath) close_btn.getChildren().get(0);
        close_btn.setOnMouseEntered(e -> {
            closeIcon.setStyle("-fx-stroke: white;");
        });
        close_btn.setOnMouseExited(e -> {
            closeIcon.setStyle("-fx-stroke: #976eef;");
        });
    }

    private void showAlert(AlertType alertType, String title, String message){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
