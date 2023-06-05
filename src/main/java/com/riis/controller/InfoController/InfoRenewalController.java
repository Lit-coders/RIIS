package com.riis.controller.InfoController;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

<<<<<<< HEAD
import java.util.ArrayList;
=======
import com.riis.controller.Controller;
import com.riis.model.viewmodel.SidebarModel;
>>>>>>> 4a4ce757f287d36685016edeba767c518d8af042

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
    
    private void displayExpStatus(int residentId) {
        ExpId expId = ResidentData.getExpResidentIdData(residentId);
        givend_label.setText(expId.getIdgivenDate());
        expd_label.setText(expId.getIdExpDate());
    }
    
    private void displayResidentDetail(Resident resident){
        String fullName = resident.getName() + " " + resident.getFatherName() + " " + resident.getGFatherName();
        name_label.setText(fullName);
        sex_label.setText(resident.getSex());
        Phone_label.setText(resident.getPhoneNumber());
        displayExpStatus(resident.getResidentId());
    }
    
    private void edit(Label name_label) {
        name_label.getStyleClass().add("name-label");
        name_label.setAlignment(Pos.CENTER);
    }

    private void displayExpIdList() {
        String sql = "SELECT * FROM Resident";
        ArrayList<Resident> Residents = ResidentData.getResidentData(sql);
        for(Resident resident: Residents){
            String fullName = resident.getName() + " " + resident.getFatherName() + " " + resident.getGFatherName();
            Label name_label = new Label(fullName);
            // Label ago_label = new Label();
            name_label.setOnMouseClicked(event ->{
                displayResidentDetail(resident);
            });
            edit(name_label);
            id_list.getChildren().add(name_label);
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
<<<<<<< HEAD

        VBox vbox = (VBox) anchorPane.getChildren().get(2);
        initializeNullLabels(vbox);

        ScrollPane pane = (ScrollPane) anchorPane.getChildren().get(1);
        id_list = (VBox) pane.getContent();
        displayExpIdList();
=======
>>>>>>> 4a4ce757f287d36685016edeba767c518d8af042
    }

}