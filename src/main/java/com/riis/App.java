package com.riis;

import com.riis.controller.LoginController;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        LoginController loginController = new LoginController(stage);
        loginController.getView();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
