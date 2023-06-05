package com.riis.model.viewmodel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.util.Duration;

public class SidebarModel {
    public static BorderPane borderPane;
    public static Label titlebar;
    public static HBox clickedHbox;

    public static void handleHover(Button btn) {
        addToolTip(btn);
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

    public static void addToolTip(Button btn) {
        String[] btnId = btn.getId().split("_");
        String tooltipText = btnId[0].substring(0, 1).toUpperCase() + btnId[0].substring(1);
        Tooltip tooltip = new Tooltip(tooltipText);
        tooltip.setStyle("-fx-font-size: 12px;");
        tooltip.setShowDelay(Duration.seconds(0.5));
        btn.setTooltip(tooltip);
    }
}