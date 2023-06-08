package com.riis.controller.BaseController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import com.riis.controller.Controller;
import com.riis.dao.KebeleResidentDAO;
import com.riis.dao.KebeleResidentDAOImpl;
import com.riis.model.databasemodel.Request;
import com.riis.utils.JAlert;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OtherServicesController implements Controller {
    private double xOffset = 0;
    private double yOffset = 0;

    public void showPopupWindow(Image popupImage, Stage parentStage, String owner) {
        Stage popupStage = new Stage();

        Label resNamLabel = new Label(owner);
        resNamLabel.setId("expand_header");

        Button x_btn = new Button("x");
        x_btn.setId("x_btn");
        x_btn.setOnAction(event -> {
            popupStage.close();
        });

        HBox titlebar = new HBox(resNamLabel, x_btn);
        titlebar.setId("titlebar");
        titlebar.setAlignment(Pos.CENTER);
        titlebar.setSpacing(10);
        titlebar.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        titlebar.setOnMouseDragged(e -> {
            popupStage.setX(e.getScreenX() - xOffset);
            popupStage.setY(e.getScreenY() - yOffset);
        });

        ImageView popupImageView = new ImageView(popupImage);
        popupImageView.setFitWidth(480);
        popupImageView.setFitHeight(480);
        VBox root = new VBox(titlebar, popupImageView);
        root.setId("root_box");
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        Scene popupScene = new Scene(root, 500, 550);
        popupScene.setFill(Color.TRANSPARENT);
        popupScene.getStylesheets()
                .add(getClass().getResource("/com/riis/css/Info_css/InfoReplace.css").toExternalForm());

        popupStage.initStyle(StageStyle.TRANSPARENT);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(parentStage);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }

    public boolean isIdExpired(int rid) throws Exception {
        KebeleResidentDAO kebeleResidentDAO = new KebeleResidentDAOImpl();
        boolean status = kebeleResidentDAO.checkIdExpirationStatus(rid);
        return status;
    }

    public boolean isRequested(int rid) throws Exception {
        KebeleResidentDAO kebeleResidentDAO = new KebeleResidentDAOImpl();
        boolean status = kebeleResidentDAO.checkIfRequestExists(rid);
        return status;
    }

    public void displayRequestDate(Request request) {
        String reqsDate = request.getRequestDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(reqsDate, formatter);
        String reqDate = requestedDate(dateTime);
        alertMessage("Error", reqDate);
    }

    public String requestedDate(LocalDateTime dateTime) {
        LocalDateTime today = LocalDateTime.now();
        int dateGap = (int) ChronoUnit.DAYS.between(dateTime, today);
        if (dateGap <= 1) {
            double timegap = ChronoUnit.HOURS.between(dateTime, today);
            if (timegap < 1) {
                int mingap = (int) ChronoUnit.MINUTES.between(dateTime, today);
                return "Already requested today, " + mingap + " minutes(s) ago.";
            } else {
                return "Already requested today, " + timegap + " hour(s) ago.";
            }
        } else if (dateGap < 7) {
            return "Requested, " + dateGap + " days ago.";
        } else if (dateGap < 31) {
            int weekgap = (int) ChronoUnit.WEEKS.between(dateTime, today);
            return "Requested, " + weekgap + " week(s) ago.";
        } else if (dateGap < 365) {
            int monthgap = (int) ChronoUnit.MONTHS.between(dateTime, today);
            return "Requested, " + monthgap + " month(s) ago.";
        } else {
            int yeargap = (int) ChronoUnit.YEARS.between(dateTime, today);
            return "Requested, " + yeargap + " year(s) ago.";
        }
    }

    public void alertMessage(String type, String message) {
        JAlert alert = new JAlert(type, message);
        alert.showAlert();
    }

    public void getView() throws Exception{     
    }
}