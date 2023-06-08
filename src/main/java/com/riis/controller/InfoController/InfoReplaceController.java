package com.riis.controller.InfoController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.riis.controller.BaseController.OtherServicesController;
import com.riis.dao.KebeleResidentDAO;
import com.riis.dao.KebeleResidentDAOImpl;
import com.riis.dao.RequestDAO;
import com.riis.dao.RequestDAOImpl;
import com.riis.dao.ResidentDAO;
import com.riis.dao.ResidentDAOImpl;
import com.riis.model.databasemodel.KebeleResident;
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

public class InfoReplaceController extends OtherServicesController {
    int clickedIdex = 0;
    int id;
    VBox detail_box;

    @FXML
    private Label idExp_label;

    @FXML
    private Label Phone_label;

    @FXML
    private Button approve_btn;

    @FXML
    private AnchorPane body_anchor;

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
        showPopupWindow(img, stage, name_label.getText());
    }

    
    @FXML
    void clearSearchField(ActionEvent event) throws Exception {
        if (!search_field.getText().isEmpty()) {
            search_field.clear();
            search_field.requestFocus();
            id_list.getChildren().clear();
            displayKebeleResidentsList();
        }
    }

    private void checkSelectedLabel(Label label) {
        uncheckSelectedLabel();
        label.setStyle("-fx-background-color: #956cedbc;");
        clickedIdex = id_list.getChildren().indexOf(label);
    }

    private void uncheckSelectedLabel() {
        Label l = (Label) id_list.getChildren().get(clickedIdex);
        l.setStyle("-fx-background-color: #956ced;");
    }

    private void displayResidentDetail(Resident resident, String givenDate, String expDate, Label label,
            int expStatus) throws Exception {
        ResidentDAO residentDAO = new ResidentDAOImpl();
        expand_btn.setDisable(false);
        if (expStatus == 1) {
            idExp_label.setVisible(true);
        } else {
            idExp_label.setVisible(false);
        }
        id = resident.getResidentId();
        id = resident.getResidentId();
        if(residentDAO.getResidentImageByID(id) != null){
            Image img = new Image(residentDAO.getResidentImageByID(id));
            resident_img.setImage(img);
        } else {
            resident_img.setImage(null);
        }
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

    private void displayKebeleResidentsList() throws Exception {
        KebeleResidentDAO kebeleResidentDAO = new KebeleResidentDAOImpl();
        ResidentDAO residentDAO = new ResidentDAOImpl();

        List<KebeleResident> kebeleResidents = kebeleResidentDAO.getAllKebeleResidents();
        for (KebeleResident kebeleResident : kebeleResidents) {
            int residentId = kebeleResident.getResidentId();
            String givenDate = kebeleResident.getIdgivenDate();
            String expDate = kebeleResident.getIdExpDate();
            int expStatus = kebeleResident.getExpStatus();
            Resident resident = residentDAO.getResidentByID(residentId);

            String fullName = resident.getName() + " " + resident.getFName() + " " + resident.getGFName();
            Label label = new Label(fullName);
            label.setOnMouseClicked(event -> {
                try {
                    displayResidentDetail(resident, givenDate, expDate, label, expStatus);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            edit(label);
            id_list.getChildren().add(label);
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

    }

    // add listener to search field to display all kebele residents when it is empty
    // and display searched kebele residents when it is not empty
    private void addSearchFieldListener() {
        search_field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                try {
                    id_list.getChildren().clear();
                    displayKebeleResidentsList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                String token = newValue.toLowerCase();
                try {
                    id_list.getChildren().clear();
                    displaySearchedKebeleResidents(token);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void displaySearchedKebeleResidents(String token) throws Exception {
        if (!search_field.getText().isBlank()) {
            if (search_field.getText().matches("\\d")) {
                // Search by Kebele Resident ID
                searchByKRID(token);
            } else {
                // Search by Resident Name
                searchByName(token);
            }
        }
    }

    public void searchByKRID(String token) throws Exception {
        KebeleResidentDAO kebeleResidentDAO = new KebeleResidentDAOImpl();
        ResidentDAO residentDAO = new ResidentDAOImpl();
        int KRID = Integer.parseInt(search_field.getText());
        KebeleResident kebeleResident = kebeleResidentDAO.getKebeleResidentByKID(KRID);
        if (kebeleResident == null) {
            String emptySearch = "Searching with 'KebeleResidentID: " + id +
                    "' has not found any related results!\nTry to search with the resident's name.";
            Label token_label = new Label(emptySearch);
            token_label.setWrapText(true);
            token_label.getStyleClass().add("token-label");
            id_list.getChildren().add(token_label);
            search_field.requestFocus();
        } else {
            id_list.getChildren().clear();
            Resident resident = residentDAO.getResidentByID(kebeleResident.getResidentId());
            String fullName = resident.getName() + " " + resident.getFName() + " " + resident.getGFName();
            Label label = new Label(fullName);
            edit(label);
            id_list.getChildren().add(label);
            int expStatus = kebeleResident.getExpStatus();
            String givenDate = kebeleResident.getIdgivenDate();
            String expDate = kebeleResident.getIdExpDate();
            label.setOnMouseClicked(clicked -> {
                try {
                    displayResidentDetail(resident, givenDate, expDate, label, expStatus);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void searchByName(String token) throws Exception {
        KebeleResidentDAO kebeleResidentDAO = new KebeleResidentDAOImpl();
        ResidentDAO residentDAO = new ResidentDAOImpl();
        id_list.getChildren().clear();
        List<KebeleResident> kebeleResidents = kebeleResidentDAO.getKebeleResidentsByToken(token);
        if (kebeleResidents.isEmpty()) {
            String emptySearch = "Searching with '" + token +
                    "' has not found any related results!\nTry to search with the Kebele Resident ID.";
            Label token_label = new Label(emptySearch);
            token_label.setWrapText(true);
            token_label.getStyleClass().add("token-label");
            id_list.getChildren().add(token_label);
            search_field.requestFocus();
        } else {
            for (KebeleResident kebeleResident : kebeleResidents) {
                Resident resident = residentDAO.getResidentByID(kebeleResident.getResidentId());
                String fullName = resident.getName() + " " + resident.getFName() + " " + resident.getGFName();
                Label label = new Label(fullName);
                edit(label);
                id_list.getChildren().add(label);
                int expStatus = kebeleResident.getExpStatus();
                String givenDate = kebeleResident.getIdgivenDate();
                String expDate = kebeleResident.getIdExpDate();
                label.setOnMouseClicked(clicked -> {
                    try {
                        displayResidentDetail(resident, givenDate, expDate, label, expStatus);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    public void handleApproveBtn() {
        approve_btn.setOnAction(event -> {
            try {
                if (!isRequested(id)) {
                    RequestDAO requestDAO = new RequestDAOImpl();
                    Request request = new Request(id, 0, 1, 0, 2);
                    if (requestDAO.addRequest(request)) {
                        for (int i = 1; i < detail_box.getChildren().size(); i++) {
                            HBox box = (HBox) detail_box.getChildren().get(i);
                            Label label = (Label) box.getChildren().get(1);
                            label.setText("---");
                        }
                        alertMessage("Success", "Lost ID replacement successfully requested!");
                    } else {
                        alertMessage("Error", "Lost ID replacement request failed!");
                    }
                } else {
                    RequestDAO requestDAO = new RequestDAOImpl();
                    Request request = requestDAO.getRequestByRID(id);
                    displayRequestDate(request);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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
                return "Already requested today, " + mingap + " minute(s) ago.";
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

    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Info_fxml/InfoReplace.fxml"));

        AnchorPane anchorPane = (AnchorPane) root;
        SidebarModel.borderPane.setCenter(anchorPane);

        approve_btn = (Button) anchorPane.getChildren().get(3);
        handleApproveBtn();
        
        HBox hbox = (HBox) anchorPane.getChildren().get(0);
        search_field = (TextField) hbox.getChildren().get(1);
        
        VBox vbox = (VBox) anchorPane.getChildren().get(2);
        VBox imageVBox  = (VBox) vbox.getChildren().get(0);
        expand_btn = (Button) imageVBox.getChildren().get(0);
        idExp_label = (Label) imageVBox.getChildren().get(1);
        resident_img = (ImageView) expand_btn.getGraphic();
        initializeNullLabels(vbox);

        ScrollPane pane = (ScrollPane) anchorPane.getChildren().get(1);
        id_list = (VBox) pane.getContent();
        addSearchFieldListener();
        displayKebeleResidentsList();
    }
}