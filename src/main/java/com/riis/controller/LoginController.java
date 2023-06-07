package com.riis.controller;


import com.riis.Context.UserContext;
import com.riis.auth.AuthenticationManager;
import com.riis.controller.FinController.FinSidebarController;
import com.riis.controller.AdminController.AdminSidebarController;
import com.riis.controller.InfoController.InfoSidebarController;
import com.riis.controller.KebeleController.KebeleSidebarController;
import com.riis.database.DatabaseConnection;
import com.riis.utils.JAlert;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private Parent root;

    @FXML
    private TextField TextPassword;

    public Stage stage;
    private double xOffset;
    private double yOffset;
    public Label errorMessage;

    private UserContext userContext = UserContext.getInstance();
    

    public LoginController(Stage stage) {
        this.stage = stage;
    }

    public LoginController() {
        this.errorMessage = new Label("Server is not connected");
    }
    
    public void initialize() throws Exception {
        setupDragHandlers();
        handleHoverCloseButton();
        TextPassword.textProperty().bindBidirectional(password.textProperty());
        TextPassword.setVisible(false);
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
        this.stage = stage;

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        isDatabaseConnected();
    } 

    public void isDatabaseConnected() throws Exception {
        if(DatabaseConnection.checkDatabase("src\\db\\riis.db")) {
            System.out.println("Database is connected");
        } else {
            JAlert alert = new JAlert("Error", "Please contact your system administrator, Server is Down!!");
            alert.showAndWait();
            stage.close();
        }
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
            userContext.setUsername(user);
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
                case "System Administrator":
                    AdminSidebarController adminSidebarController = new AdminSidebarController(stage);
                    adminSidebarController.getView();
                    break;

            }
        } else {
            System.out.println("Login Failed");
            JAlert alert = new JAlert("Error","Invalid Username or Password");
            alert.showAlert();
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
                TextPassword.setVisible(false);
                password.setVisible(true);
                password.requestFocus();
            }
        } else if(event.getSource() == password){
            if (event.getCode()== KeyCode.ENTER){
                login();
            }
        } else if(event.getSource() == TextPassword){
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

    public void handleEyeClickedButton(){
        if (password.isVisible()) {
            password.setVisible(false);
            TextPassword.setVisible(true);
        } else {
            password.setVisible(true);
            TextPassword.setVisible(false);
        }
    }
}
