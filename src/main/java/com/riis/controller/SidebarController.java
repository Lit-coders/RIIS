package com.riis.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class SidebarController {
    @FXML private VBox myVBox;
    @FXML private HBox hbox1;
    @FXML private HBox hbox2;
    @FXML private HBox hbox3;
    @FXML private HBox hbox4;
    @FXML private HBox hbox5;
    public Stage stage;
    public HBox previousClickedHbox = null;

    public SidebarController(Stage stage) {
        this.stage = stage;
    }
    
    public SidebarController() {
    }

    public void initialize() {
        checkHBox(hbox1);
    }

    @FXML
    private void handleHBoxClick(MouseEvent event) {
        // Uncheck the previous clicked HBox
        uncheckPreviousHBox();

        // Get the clicked HBox
        HBox clickedHBox = (HBox) event.getSource();

        // Set the previous clicked HBox
        previousClickedHbox = clickedHBox;

        // Set the style of the clicked HBox
        checkHBox(clickedHBox);
        
        // Set the style of the other HBoxes
        for (Node node : myVBox.getChildren()) {
            if (node instanceof HBox && node != clickedHBox) {
                uncheckHBox((HBox) node);
            }
        }

    }

    public void render() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/view/Sidebar.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Information officer");
        stage.setMaximized(true);
        stage.show();
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
            Label label = (Label) hbox1.getChildren().get(1);
            SVGPath svgPath = (SVGPath) hbox1.getChildren().get(0);
            label.setStyle("-fx-text-fill: #825FE5;");
            svgPath.setStyle("-fx-stroke: #825FE5;");
        }
    }
}