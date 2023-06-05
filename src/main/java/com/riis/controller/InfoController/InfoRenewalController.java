package com.riis.controller.InfoController;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.print.PrintColor;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

import com.riis.controller.Controller;
import com.riis.model.databasemodel.ExpId;
import com.riis.model.databasemodel.Resident;
import com.riis.model.viewmodel.SidebarModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InfoRenewalController implements Controller{
    int clikedIdex = 0;
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
    private TextField search_field;

    @FXML
    private Label sex_label;

    @FXML
    void SearchExpiredId(ActionEvent event) {

    }

    @FXML
    void approveRenewal(ActionEvent event) {
        
    }

    @FXML
    void clearSearchField(ActionEvent event) {

    }
    
    // private void displayExpStatus(int residentId) {
    //     ExpId expId = ResidentData.getExpResidentIdData(residentId);
    //     givend_label.setText(expId.getIdgivenDate());
    //     expd_label.setText(expId.getIdExpDate());
    // }
    
    private void checkSelectedLabel(Label label) {
        label.setStyle("-fx-background-color: #956cedbc;");
        Label l = (Label) id_list.getChildren().get(clikedIdex);
        l.setStyle("-fx-background-color: #956ced;");
        clikedIdex = id_list.getChildren().indexOf(label);
        

        // for(int i=0; i<id_list.getChildren().size();i++){
        //     Label l = (Label) id_list.getChildren().get(i);
        //     l.setStyle("-fx-background-color: #956ced;");
        //     System.out.println(l);
        // }
        // label.setStyle("-fx-background-color: #956cedbc;"); 
    }

    private void displayResidentDetail(Resident resident, String givenDate, String expDate, Label label){
        checkSelectedLabel(label);
        approve_btn.setDisable(false);
        String fullName = resident.getName() + " " + resident.getFatherName() + " " + resident.getGFatherName();
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
            int id = expId.getResidentId();
            String givenDate = expId.getIdgivenDate();
            String expDate = expId.getIdExpDate();
            String sql = "SELECT * FROM Resident WHERE ResidentID = '" + id + "'";
            ArrayList<Resident> Residents = ResidentData.getResidentData(sql);
            for(Resident resident: Residents){
                String fullName = resident.getName() + " " + resident.getFatherName() + " " + resident.getGFatherName();
                Label label = new Label(fullName);
                label.setOnMouseClicked(event ->{
                    displayResidentDetail(resident, givenDate, expDate, label);
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
    }

    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Info_fxml/InfoRenewal.fxml"));

        AnchorPane anchorPane = (AnchorPane) root;
        SidebarModel.borderPane.setCenter(anchorPane);

        approve_btn = (Button) anchorPane.getChildren().get(3);

        VBox vbox = (VBox) anchorPane.getChildren().get(2);
        initializeNullLabels(vbox);

        ScrollPane pane = (ScrollPane) anchorPane.getChildren().get(1);
        id_list = (VBox) pane.getContent();
        displayExpIdList();
    }
}