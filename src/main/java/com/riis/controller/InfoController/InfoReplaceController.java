package com.riis.controller.InfoController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import com.riis.controller.Controller;
import com.riis.model.databasemodel.ExpId;
import com.riis.model.databasemodel.Request;
import com.riis.model.databasemodel.Resident;
import com.riis.model.viewmodel.SidebarModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InfoReplaceController implements Controller{
    int clikedIdex = 0;
    int id;
    VBox detail_box;

    @FXML
    private Label idExp_label;

     @FXML
    private Label Phone_label;

    @FXML
    private Button approve_btn;

    @FXML
    private AnchorPane bocy_anchor;

    @FXML
    private Button clear_btn;

    @FXML
    private Button expand_btn;

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
    private TextField search_field;

    @FXML
    private Label sex_label;

    @FXML
    void expandResidentPhoto(ActionEvent event) {
        Button btn = (Button) event.getSource();
        ImageView imgView = (ImageView) btn.getGraphic();
        Image img = (Image) imgView.getImage();
        Stage stage = (Stage) btn.getScene().getWindow();
        InfoRenewalController irc = new InfoRenewalController();
        irc.showPopupWindow(img, stage, name_label.getText());
    }

    @FXML
    void searchResidentID(ActionEvent event) {
        if(!search_field.getText().isBlank()){
            String sql1 = "SELECT * FROM Resident";
            String token = search_field.getText().toLowerCase();
            ArrayList<Resident> residents = ResidentData.getResidentData(sql1);
            id_list.getChildren().clear();
            for(Resident resident: residents){
                if(resident.getName().toLowerCase().contains(token) || resident.getFName().toLowerCase().contains(token) || resident.getGFName().toLowerCase().contains(token)){
                    String fullName = resident.getName() + " " + resident.getFName() + " " + resident.getGFName();
                    Label label = new Label(fullName);
                    edit(label);
                    id_list.getChildren().add(label);
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
        }
    }

    @FXML
    void clearSearchField(ActionEvent event) {
        if(search_field.isFocused()){
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
            String str = isRepeated(id);
            if(str.equals("no") && str != null){
                String sql = "INSERT INTO Request (RID, UnpaidRequest, RequestType, RequestDate) VALUES(" + id +", " + 0 + ", " + 0 + ", '" +  requestTime + "')";
                if(ResidentData.addRequest(sql)){
                    for(int i=1; i < detail_box.getChildren().size(); i++){
                        HBox box = (HBox) detail_box.getChildren().get(i);
                        Label label = (Label) box.getChildren().get(1);
                        label.setText("---");
                    }
                    System.out.println("successfully requested!");
                }
            } else if(str != null){
                displayRepeatedError(str);
            } else {
                System.out.println("return value from isRepeated method is null! But this Request is not Repeated!");
            }
        });
    }
    
    
    private String isRepeated(int rid) {
        String sql = "SELECT * FROM Request WHERE RID = " + rid + " AND RequestType = 0";
        Request request = ResidentData.getRequestData(sql, rid);
        String response = null;
        if(request.getResidentID() == 11){
            response = "no";
        } else {
            response = request.getRequestDate();
        }
        return response;
    }

    private void displayRepeatedError(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        String reqDate = requestedDate(dateTime);
        System.out.println(reqDate);
    }
    
    private String requestedDate(LocalDateTime dateTime) {
        LocalDateTime today = LocalDateTime.now();
        double dateGap = ChronoUnit.DAYS.between(dateTime, today);
        if(dateGap <= 1){
            return "It has been Renewed today";
        } else if(dateGap < 7){
            return "This Id was Renewed " + dateGap + " days ago.";
        } else if(dateGap < 31){
            double weekgap = ChronoUnit.WEEKS.between(dateTime, today);        
            return "This id was Renewed " + weekgap + " week(s) ago.";
        } else if(dateGap < 365){
            double monthgap = ChronoUnit.MONTHS.between(dateTime, today);        
            return "This id was Renewed " + monthgap + " month(s) ago.";
        }

        double yeargap = ChronoUnit.YEARS.between(dateTime, today);
        return "This id was Renewed " + yeargap + " year(s) ago.";
    }

    
    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Info_fxml/InfoReplace.fxml"));

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
}