package com.riis.model.viewmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.StageStyle;

public class JAlert extends Alert {
    public String alertType;
    public String message;
    public DialogPane dialogPane;
    public boolean isValid = true;

    public JAlert(String alertType, String message) {
        super(AlertType.NONE);
        initStyle(StageStyle.TRANSPARENT);
        setHeaderText(null);
        setGraphic(null);
        this.message = message;
        this.alertType = alertType;
        this.dialogPane = getDialogPane();
        getDialogPane().getScene().setFill(null);

        try {
            if(alertType.equalsIgnoreCase("Warning") || alertType.equalsIgnoreCase("Confirmation")) {
                createTwoButtonDialog();
            } else {
                createOneButtonDialog();
            }
        } catch (Exception e) {
            System.err.println("Invalid alert type");
            isValid = false;
        }
    }

    public void createOneButtonDialog() {
        VBox vbox = buildVBox(alertType);     
        dialogPane.setContent(vbox);
        dialogPane.getButtonTypes().clear();

        List<String> btnData = List.of("OK");
        List<ButtonType> returnedBtnType = buttonGenerator(btnData);
        
        dialogPane.getButtonTypes().addAll(returnedBtnType.get(0));
        centerButtons(dialogPane);
    }

    public void createTwoButtonDialog() {
        VBox vbox = buildVBox(alertType);     
        dialogPane.setContent(vbox);
        dialogPane.getButtonTypes().clear();

        switch(alertType) {
            case "Warning":
                List<String> dispText = List.of("Yes", "No");
                List<ButtonType> returnedBtnType = buttonGenerator(dispText);
                dialogPane.getButtonTypes().addAll(returnedBtnType.get(0), returnedBtnType.get(1));
                break;
            case "Confirmation":
                List<String> dispText2 = List.of("Approve", "Deny");
                List<ButtonType> returnedBtnType2 = buttonGenerator(dispText2);
                dialogPane.getButtonTypes().addAll(returnedBtnType2.get(0), returnedBtnType2.get(1));
                break;
            default:
                System.err.println("Invalid alert type");
        }
        centerButtons(dialogPane);
    }

    public List<ButtonType> buttonGenerator(List<String> displayText) {
        List<ButtonType> buttonTypes = new ArrayList<>();
        List<String> btnData = new ArrayList<>(Arrays.asList("OK", "Cancel"));
        for (String text : displayText) {
            ButtonType button = new ButtonType(btnData.get(displayText.indexOf(text)));
            button = new ButtonType(text);
            buttonTypes.add(button);
        }
        return buttonTypes;
    }

    public VBox buildVBox(String alertType) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        String iconPath = "/com/riis/icons/" + alertType.toLowerCase() +".png";
        ImageView icon = new ImageView(iconPath);
        icon.setFitHeight(50);
        icon.setFitWidth(50);

        Text text = new Text(message);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(Color.valueOf("#976EEF"));
        text.setWrappingWidth(300);
        text.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        VBox.setMargin(icon, new Insets(10, 0, 10, 0));
        
        vbox.getChildren().addAll(icon, text);
        vbox.prefWidthProperty().bind(dialogPane.widthProperty());

        dialogPane.getStylesheets().add("/com/riis/css/Utils_css/Jalert.css");
        return vbox;
    }

    private void centerButtons(DialogPane dialogPane) {
        Region spacer = new Region();
        ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        dialogPane.applyCss();
        HBox hboxDialogPane = (HBox) dialogPane.lookup(".container");
        hboxDialogPane.getChildren().add(spacer);
    }

    public void showAlert() {
        if(isValid) {
            showAndWait();
        }
    }
}