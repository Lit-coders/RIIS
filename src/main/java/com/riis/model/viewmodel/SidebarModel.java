package com.riis.model.viewmodel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class SidebarModel {
    public static BorderPane borderPane;
    public static Label titlebar;
    public static HBox clickedHbox;

    public static void handleHover(Button btn) {
        SVGPath icon = (SVGPath) btn.getGraphic();
        btn.setOnMouseEntered(e -> {
            icon.setFill(Color.WHITE);
            icon.setStyle("-fx-stroke: white;");
        });
        btn.setOnMouseExited(e -> {
            icon.setFill(Color.valueOf("#976eef"));
            icon.setStyle("-fx-stroke: #976eef;");
        });
    }
}