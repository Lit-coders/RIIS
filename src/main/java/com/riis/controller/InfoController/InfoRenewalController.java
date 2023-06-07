package com.riis.controller.InfoController;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.RunElement;

import com.riis.controller.Controller;
import com.riis.model.databasemodel.ExpId;
import com.riis.model.databasemodel.Request;
import com.riis.model.databasemodel.Resident;
import com.riis.model.viewmodel.JAlert;
import com.riis.model.viewmodel.SidebarModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InfoRenewalController implements Controller{
    int clikedIdex = 0;
    int id;
    VBox detail_box;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Label Phone_label;

    @FXML
    private Button approve_btn;

    @FXML
    private AnchorPane bocy_anchor;

    @FXML
    private Button clear_btn;

    @FXML
    private Label expd_label;

    @FXML
    private Label givend_label;

    @FXML
    private VBox id_list;

    @FXML
    private Label name_label;

    @FXML
    private ImageView resident_img;

    @FXML
    private HBox search_box;

    @FXML
    private Button search_btn;

    @FXML
    private Button expand_btn;

    @FXML
    private TextField search_field;

    @FXML
    private Label sex_label;

    @FXML
    private Label idExp_label;

    @FXML
    void expandResidentPhoto(ActionEvent event) {
        Button btn = (Button) event.getSource();
        ImageView imgView = (ImageView) btn.getGraphic();
        Image img = (Image) imgView.getImage();
        Stage stage = (Stage) btn.getScene().getWindow();

        showPopupWindow(img, stage, name_label.getText());
    }

    @FXML
    void searchResidentID(ActionEvent event) {
        if(!search_field.getText().isBlank()){
            String sql1 = "SELECT * FROM Resident";
            String token = search_field.getText().toLowerCase();
            ArrayList<Resident> residents = ResidentData.getResidentData(sql1);
            id_list.getChildren().clear();
            boolean isFound = false;
            for(Resident resident: residents){
                if(resident.getName().toLowerCase().contains(token) || resident.getFName().toLowerCase().contains(token) || resident.getGFName().toLowerCase().contains(token)){
                    String fullName = resident.getName() + " " + resident.getFName() + " " + resident.getGFName();
                    Label label = new Label(fullName);
                    edit(label);
                    id_list.getChildren().add(label);
                    isFound = true;
                    String sql  = "SELECT * FROM KebeleResidentID WHERE ResidentId = '" + resident.getResidentId() + "'";
                    ArrayList<ExpId> expIds = ResidentData.getExpResidentIdData(sql);
                    ExpId expId = expIds.get(0);
                    int expStatus = expId.getExpStatus();
                    String givenDate = expId.getIdgivenDate();
                    String expDate = expId.getIdExpDate();
                    label.setOnMouseClicked(clicked ->{
                        displayResidentDetail(resident, givenDate, expDate, label, expStatus);  
                    });
                }
            }

            if(!isFound){
                String emptySearch = "Searching with '" + token + "' has not found any related resuts!\n try to search with resident's names and his/her house number.";
                Label token_label = new Label(emptySearch);
                token_label.setWrapText(true);
                token_label.getStyleClass().add("token-label");
                id_list.getChildren().add(token_label);
                search_field.requestFocus();
            }
        }
    }

    @FXML
    void clearSearchField(ActionEvent event) {
        if(!search_field.getText().isEmpty()){
            search_field.clear();
            search_field.requestFocus();
            id_list.getChildren().clear();
            displayExpIdList();
        }
    }
    
    private void checkSelectedLabel(Label label) {
        uncheckSelectedLabel();
        label.setStyle("-fx-background-color: #956cedbc;");
        clikedIdex = id_list.getChildren().indexOf(label); 
    }

    private void uncheckSelectedLabel() {
        Label l = (Label) id_list.getChildren().get(clikedIdex);
        l.setStyle("-fx-background-color: #956ced;");
    }
    
    private void displayResidentDetail(Resident resident, String givenDate, String expDate, Label label, int expStatus){
        expand_btn.setDisable(false);
        if(expStatus == 1){
            idExp_label.setVisible(true);
        } else {
            idExp_label.setVisible(false);
        }
        id = resident.getResidentId();
        Image img = new Image(resident.getPhotoPath());
        resident_img.setImage(img);
        checkSelectedLabel(label);
        approve_btn.setDisable(false);
        String fullName = resident.getName() + " " + resident.getFName() + " " + resident.getGFName();
        name_label.setText(fullName);
        sex_label.setText(resident.getSex());
        Phone_label.setText(resident.getPhoneNumber());
        givend_label.setText(givenDate);
        expd_label.setText(expDate);
    }
    

    private void edit(Label name_label) {
        name_label.getStyleClass().add("name-label");
        name_label.setStyle("-fx-background-color: #956ced;");
        name_label.setAlignment(Pos.CENTER);
    }

    private void displayExpIdList() {
        String sql1  = "SELECT * FROM KebeleResidentID";
        ArrayList<ExpId> expIds = ResidentData.getExpResidentIdData(sql1);
        for(ExpId expId: expIds){
            int rId = expId.getResidentId();
            String givenDate = expId.getIdgivenDate();
            String expDate = expId.getIdExpDate();
            int expStatus = expId.getExpStatus();
            String sql = "SELECT * FROM Resident WHERE ResidentID = '" + rId + "'";
            ArrayList<Resident> Residents = ResidentData.getResidentData(sql);
            for(Resident resident: Residents){
                String fullName = resident.getName() + " " + resident.getFName() + " " + resident.getGFName();
                Label label = new Label(fullName);
                label.setOnMouseClicked(event ->{
                    displayResidentDetail(resident, givenDate, expDate, label, expStatus);
                });
                edit(label);
                id_list.getChildren().add(label);
            }
        }
    }

    
    private void initializeNullLabels(VBox vbox) {
        HBox fname_box = (HBox) vbox.getChildren().get(1);
        name_label = (Label) fname_box.getChildren().get(1);
        
        HBox phone_box = (HBox) vbox.getChildren().get(2);
        Phone_label = (Label) phone_box.getChildren().get(1);

        HBox sex_box = (HBox) vbox.getChildren().get(3);
        sex_label = (Label) sex_box.getChildren().get(1);

        HBox expDate_box = (HBox) vbox.getChildren().get(4);
        expd_label = (Label) expDate_box.getChildren().get(1);

        HBox givend_box = (HBox) vbox.getChildren().get(5);
        givend_label = (Label) givend_box.getChildren().get(1);
        AnchorPane aPane = (AnchorPane) approve_btn.getParent();
        detail_box = (VBox) aPane.getChildren().get(2);

        approve_btn.setOnAction(event -> {
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");
            String requestTime = dateTime.format(formatter);
            if(!isRequested(id) && isIdExpired(id)) {
                String sql = "INSERT INTO Request (RID, UnpaidRequest, RequestType, RequestDate) VALUES(" + id +", " + 0 + ", " + 0 + ", '" +  requestTime + "')";
                if(ResidentData.addRequest(sql)){
                    for(int i=1; i < detail_box.getChildren().size(); i++){
                        HBox box = (HBox) detail_box.getChildren().get(i);
                        Label label = (Label) box.getChildren().get(1);
                        label.setText("---");
                    }
                    alertMessage("Success", "successfully requested!");
                }
            }
        });
    }
    
    public boolean isIdExpired(int rid) {
        String sql = "SELECT * FROM KebeleResidentId WHERE ResidentID = " + rid + " AND ExpirationStatus = 1";
        ArrayList<ExpId> expIds = ResidentData.getExpResidentIdData(sql);
        if(expIds != null){
            for(ExpId expId: expIds){
                if(expId.getResidentId() == rid && expId.getExpStatus() != 1){
                    return false;
                } else if(expId.getResidentId() == rid && expId.getExpStatus() == 1){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isRequested(int rid) {
        String sql = "SELECT * FROM Request WHERE RID = " + rid + " AND RequestType = 0";
        Request request = ResidentData.getRequestData(sql, rid);
        if(request.getResidentID() == 11){
            System.out.println("Renewal is Not Requested for this Id");
            return false;
        } else {
            displayRequestDate(request);
            return true;
        }
    }

    private void displayRequestDate(Request request) {
        String reqsDate = request.getRequestDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");
        LocalDateTime dateTime = LocalDateTime.parse(reqsDate, formatter);
        String reqDate = requestedDate(dateTime);
        alertMessage("Error", reqDate);
    }
    
    private String requestedDate(LocalDateTime dateTime) {
        LocalDateTime today = LocalDateTime.now();
        int dateGap = (int) ChronoUnit.DAYS.between(dateTime, today);
        if(dateGap <= 1){
            double timegap = ChronoUnit.HOURS.between(dateTime, today);
            if(timegap < 1){
                int mingap = (int) ChronoUnit.MINUTES.between(dateTime, today);
                return "Already requested today, " + mingap + " minutes(s) ago.";
            } else {
                return "Already requested today, " + timegap + " hour(s) ago.";
            }
        } else if(dateGap < 7){
            return "Requested, " + dateGap + " days ago.";
        } else if(dateGap < 31){
            int weekgap = (int) ChronoUnit.WEEKS.between(dateTime, today);        
            return "Requested, " + weekgap + " week(s) ago.";
        } else if(dateGap < 365){
            int monthgap = (int) ChronoUnit.MONTHS.between(dateTime, today);        
            return "Requested, " + monthgap + " month(s) ago.";
        } else {
            int yeargap = (int) ChronoUnit.YEARS.between(dateTime, today);
            return "Requested, " + yeargap + " year(s) ago.";
        }
    }

    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Info_fxml/InfoRenewal.fxml"));

        AnchorPane anchorPane = (AnchorPane) root;
        SidebarModel.borderPane.setCenter(anchorPane);
        
        approve_btn = (Button) anchorPane.getChildren().get(3);
        idExp_label = (Label) anchorPane.getChildren().get(4);
        
        VBox vbox = (VBox) anchorPane.getChildren().get(2);
        expand_btn = (Button) vbox.getChildren().get(0);
        resident_img = (ImageView) expand_btn.getGraphic();
        initializeNullLabels(vbox);

        ScrollPane pane = (ScrollPane) anchorPane.getChildren().get(1);
        id_list = (VBox) pane.getContent();
        displayExpIdList();
    }

    public void alertMessage(String type, String message) {
        JAlert alert = new JAlert(type, message);
        alert.showAlert();
     }

    public void showPopupWindow(Image popupImage, Stage parentStage, String owner) {
        Stage popupStage = new Stage();

        Label resNamLabel = new Label(owner);
        resNamLabel.setId("expand_header");

        Button x_btn = new Button("x");
        x_btn.setId("x_btn");
        x_btn.setOnAction(event ->{
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
        popupScene.getStylesheets().add(getClass().getResource("/com/riis/css/Info_css/InfoReplace.css").toExternalForm());
        
        popupStage.initStyle(StageStyle.TRANSPARENT);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.initOwner(parentStage);
        popupStage.setScene(popupScene);
        popupStage.showAndWait();
    }
}