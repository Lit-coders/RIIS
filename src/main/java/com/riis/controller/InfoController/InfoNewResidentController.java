package com.riis.controller.InfoController;

import com.riis.database.DatabaseConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.riis.controller.Controller;
import com.riis.dao.MapHolderDAO;
import com.riis.dao.MapHolderDAOImpl;
import com.riis.dao.RequestDAO;
import com.riis.dao.RequestDAOImpl;
import com.riis.dao.ResidentDAO;
import com.riis.dao.ResidentDAOImpl;
import com.riis.model.databasemodel.MapHolder;
import com.riis.model.databasemodel.Request;
import com.riis.model.databasemodel.Resident;
import com.riis.model.viewmodel.SidebarModel;
import com.riis.utils.JAlert;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

public class InfoNewResidentController implements Controller {
    @FXML
    private TextField Name;

    @FXML
    private TextField FName;

    @FXML
    private TextField GFName;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField POB;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField sex;

    @FXML
    private TextField citizenship;

    @FXML
    private TextField marital_status;

    @FXML
    private TextField job;

    @FXML
    private TextField MName;

    @FXML
    private TextField btype;

    @FXML
    private TextField house_number;

    @FXML
    private TextField ECF;

    @FXML
    private TextField ECP;

    @FXML
    private TextField HOName;

    @FXML
    private TextField HOFName;

    @FXML
    private TextField HOGFName;

    @FXML
    private TextField HOP;

    @FXML
    private Button upload_photo;

    @FXML
    private Button upload_map;

    @FXML
    private Button approveBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private VBox R_imageHolder;

    @FXML
    private VBox H_imageHolder;

    @FXML
    private StackPane imageHolder;

    @FXML
    private StackPane mapImageHolder;

    private ImageView residentImage;

    private ImageView mapImage;

    private File file;

    private ArrayList<TextField> ResidentForm = new ArrayList<TextField>();

    private ArrayList<TextField> MapHolderForm = new ArrayList<TextField>();

    private InputStream ResidentImageBinary;

    private InputStream MapImageBinary;

    private int invalidTextfield;

    @FXML
    public void initialize() {
        ResidentForm.add(Name);
        ResidentForm.add(FName);
        ResidentForm.add(GFName);
        // ResidentForm.add(DOB);
        ResidentForm.add(POB);
        ResidentForm.add(phoneNumber);
        ResidentForm.add(MName);
        ResidentForm.add(sex);
        ResidentForm.add(citizenship);
        ResidentForm.add(marital_status);
        ResidentForm.add(job);
        ResidentForm.add(btype);
        ResidentForm.add(house_number);
        ResidentForm.add(ECF);
        ResidentForm.add(ECP);
        MapHolderForm.add(HOName);
        MapHolderForm.add(HOFName);
        MapHolderForm.add(HOGFName);
        MapHolderForm.add(HOP);
        Platform.runLater(() -> {
            Name.requestFocus();
            datePicker.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    Boolean idDateValid = dateValidator();
                    if (idDateValid) {
                        POB.requestFocus();
                    } else {
                        JAlert alert = new JAlert("Error","Invalid Date! Please enter a valid date");
                        alert.showAlert();
                    }
                }
            });
            Scene scene = (Scene) datePicker.getScene();
            scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                if (event.getCode() == KeyCode.BACK_SPACE && datePicker.isFocused() && datePicker.getValue() == null) {
                    ResidentForm.get(2).requestFocus();
                }
            });

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
                @Override
                public String toString(LocalDate date) {
                    try{
                        if (datePicker.getValue() != null) {
                            return dateFormatter.format(datePicker.getValue());
                        } else {
                            return "";
                        }
                    } catch (Exception e) {
                        return "";
                    }
                }

                @Override
                public LocalDate fromString(String string) {
                    try{
                        if (datePicker.getValue() != null) {
                            return LocalDate.parse(string, dateFormatter);
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        return null;
                    }
                }
            };
            datePicker.setConverter(converter);
        });
    }

    @Override
    public void getView() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/riis/fxml/Info_fxml/InfoNewResident.fxml"));
        AnchorPane anchorPane = (AnchorPane) root;
        SidebarModel.borderPane.setCenter(anchorPane);
    }

    public void clearForm() {
        for (TextField field : ResidentForm) {
            field.clear();
        }
        for (TextField field : MapHolderForm) {
            field.clear();
        }
        datePicker.setValue(null);
        imageHolder.getChildren().remove(residentImage);
        mapImageHolder.getChildren().remove(mapImage);
    }

    public InputStream fileChoser() throws FileNotFoundException {
        try {
            FileChooser fileChoser = new FileChooser();
            fileChoser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            file = fileChoser.showOpenDialog(null);
            FileInputStream fis = new FileInputStream(file);
            return fis;
        } catch (Exception e) {
            return null;
        }
    }

    public void ResidentImageChoser() throws FileNotFoundException {
        ResidentImageBinary = fileChoser();
        if (file != null) {
            residentImage = new ImageView(file.toURI().toString());
            residentImage.fitWidthProperty().bind(R_imageHolder.widthProperty());
            residentImage.fitHeightProperty().bind(R_imageHolder.heightProperty());
            residentImage.toFront();
            imageHolder.getChildren().add(residentImage);
        }
    }

    public void MapImageChoser() throws FileNotFoundException {
        MapImageBinary = fileChoser();
        if (file != null) {
            mapImage = new ImageView(file.toURI().toString());
            mapImage.fitWidthProperty().bind(H_imageHolder.widthProperty());
            mapImage.fitHeightProperty().bind(H_imageHolder.heightProperty());
            mapImage.toFront();
            mapImageHolder.getChildren().add(mapImage);
        }

    }

    public void addResidentAndMapHolder() throws Exception {
        if (checkMessage()) { 
            if (!isRepeated()) {
                int RID = addResident();
                int MID  = addMapHolder();
                addResidentMapHolder(RID, MID);
                addRequest(RID);
                successMessage("Resident Added Successfully");
                mapImageHolder.getChildren().remove(mapImage);
                imageHolder.getChildren().remove(residentImage);
                clearForm();
            }
        }
    }

    public void addResidentMapHolder(int rid, int mid) throws Exception {
        MapHolderDAO residentMapHolderDAO = new MapHolderDAOImpl();
        residentMapHolderDAO.addResidentMapHolder(rid, mid);
    }

    public void addRequest(int id) throws Exception {
        RequestDAO requestDAO = new RequestDAOImpl();
        Request request = new Request();
        request.setResidentID(id);
        request.setSealedRequest(0);
        request.setApprovalRequest(0);
        request.setUnpaidRequest(1);
        request.setRequestType(0);

        requestDAO.addRequest(request);
    }

    public int addResident() throws Exception {
        Resident resident = new Resident();
        ResidentDAO residentDAO = new ResidentDAOImpl();
        
        resident.setName(ResidentForm.get(0).getText());
        resident.setFName(ResidentForm.get(1).getText());
        resident.setGFName(ResidentForm.get(2).getText());
        resident.setDOB(datePicker.getValue().toString());
        resident.setPOB(ResidentForm.get(3).getText());
        resident.setPhoneNumber(ResidentForm.get(4).getText());
        resident.setMName(ResidentForm.get(5).getText());
        resident.setSex(ResidentForm.get(6).getText());
        resident.setCitizenship(ResidentForm.get(7).getText());
        resident.setMaritalStatus(ResidentForm.get(8).getText());
        resident.setJob(ResidentForm.get(9).getText());
        resident.setBType(ResidentForm.get(10).getText());
        resident.setHouseNumber(ResidentForm.get(11).getText());
        resident.setECF(ResidentForm.get(12).getText());
        resident.setECP(ResidentForm.get(13).getText());
        byte[] residentImage = ResidentImageBinary.readAllBytes();
        resident.setResidentImage(residentImage);

        int rid = residentDAO.addResident(resident);

        return rid;
    }

    public int addMapHolder() throws Exception {
        MapHolder mapHolder = new MapHolder();
        MapHolderDAO mapHolderDAO = new MapHolderDAOImpl();

        mapHolder.setMapHolderName(MapHolderForm.get(0).getText());
        mapHolder.setMapHolderFName(MapHolderForm.get(1).getText());
        mapHolder.setMapHolderGFName(MapHolderForm.get(2).getText());
        mapHolder.setMapHolderPhoneNum(MapHolderForm.get(3).getText());
        byte[] mapImage = MapImageBinary.readAllBytes();
        mapHolder.setMapHolderPhoto(mapImage);

        int mid = mapHolderDAO.addMapHolder(mapHolder);

        return mid;
    }

    @FXML
    void handleKeyPress(KeyEvent event) throws Exception {
        for (int i = 0; i < ResidentForm.size(); i++) {
            if (event.getSource() == ResidentForm.get(i)) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (i == ResidentForm.size() - 1) {
                        MapHolderForm.get(0).requestFocus();
                    } else {
                        if (i == 2) {
                            datePicker.requestFocus();
                        } else {
                            ResidentForm.get(i + 1).requestFocus();
                        }
                    }
                } else if (event.getCode() == KeyCode.BACK_SPACE) {
                    if (ResidentForm.get(i).getText().isEmpty()) {
                        if (i == 0) {
                            ResidentForm.get(i).requestFocus();
                        } else if (i == 3) {
                            datePicker.requestFocus();
                        } else {
                            ResidentForm.get(i - 1).requestFocus();
                        }
                    }
                }
            }
        }
        for (int i = 0; i < MapHolderForm.size(); i++) {
            if (event.getSource() == MapHolderForm.get(i)) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (i == MapHolderForm.size() - 1) {
                        addResidentAndMapHolder();
                    } else {
                        MapHolderForm.get(i + 1).requestFocus();
                    }

                } else if (event.getCode() == KeyCode.BACK_SPACE) {
                    if (MapHolderForm.get(i).getText().isEmpty()) {
                        if (i == 0) {
                            ResidentForm.get(ResidentForm.size() - 1).requestFocus();
                        } else {
                            MapHolderForm.get(i - 1).requestFocus();
                        }
                    }
                }
            }
        }
    }

    public Boolean checkMessage() {
        for (TextField field : ResidentForm) {
            if(ResidentForm.indexOf(field) == 3) {
                boolean isDateValid = dateValidator();
                if(!isDateValid) {
                    JAlert alert = new JAlert("Error","Invalid Date! Please enter a valid date");
                    alert.showAlert();
                    datePicker.requestFocus();
                    return isDateValid;
                }
            }
            boolean isEmpty = emptyFieldChecker(field);
            if (!isEmpty) {
                invalidTextfield = ResidentForm.indexOf(field);
                alertMessage("Please fill all the fields");
                return isEmpty;
            } else {
                if (field == phoneNumber || field == ECP) {
                    boolean isValid = phonePatternChecker(field.getText());
                    if (!isValid) {
                        invalidTextfield = ResidentForm.indexOf(field);
                        alertMessage("Please enter a valid phone number");
                        return isValid;
                    }
                } else if (field == house_number) {
                    boolean isValid = houseNumberChecker(field.getText());
                    if (!isValid) {
                        invalidTextfield = ResidentForm.indexOf(field);
                        alertMessage("Please enter a valid house number");
                        return isValid;
                    }

                } else {
                    boolean isValid = stringPatternChecker(field.getText());
                    if (!isValid) {
                        invalidTextfield = ResidentForm.indexOf(field);
                        alertMessage("Please enter a valid Data");
                        return isValid;
                    }
                }

            }

        }

        for (TextField field : MapHolderForm) {
            boolean isEmpty = emptyFieldChecker(field);
            if (!isEmpty) {
                invalidTextfield = MapHolderForm.indexOf(field) + 17;
                alertMessage("Please fill all the fields");
                return isEmpty;
            } else {
                if (field == HOP) {
                    boolean isValid = phonePatternChecker(field.getText());
                    if (!isValid) {
                        invalidTextfield = MapHolderForm.indexOf(field) + 17;
                        alertMessage("Please enter a valid phone number");
                        return isValid;
                    }
                } else {
                    boolean isValid = stringPatternChecker(field.getText());
                    if (!isValid) {
                        invalidTextfield = MapHolderForm.indexOf(field) + 17;
                        alertMessage("Please enter a valid Data");
                        return isValid;
                    }
                }
            }
        }
        boolean hasImage = selectedImageChecker();
        if (!hasImage) {
            return hasImage;
        }

        return true;

    }

    public boolean phonePatternChecker(String phoneNumber) {
        String phonePattern = "\\+251[7,9]\\d{8}";
        if (!phoneNumber.matches(phonePattern)) {
            return false;
        }
        return true;
    }

    public boolean stringPatternChecker(String string) {
        String alphaPattern = "^[a-zA-Z\\s]*$";
        if (!string.matches(alphaPattern)) {
            return false;
        }
        return true;
    }

    public boolean emptyFieldChecker(TextField field) {
        if (field.getText().isEmpty()) {
            return false;

        }
        return true;
    }

    public boolean dateValidator() {
        LocalDate date = datePicker.getValue();
    
        if (date == null) {
            return false;
        }
    
        if (date.isAfter(LocalDate.now().minusYears(18))) {
            return false;
        }
    
        if (date.isBefore(LocalDate.of(1900, 1, 1))) {
            return false;
        }
    
        if (!date.toString().matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
    
        return true;
    }

    public boolean houseNumberChecker(String houseNumber) {
        String houseNumberPattern = "^[a-zA-Z0-9]{1,5}$";
        if (!houseNumber.matches(houseNumberPattern)) {
            return false;
        }
        return true;
    }

    public boolean selectedImageChecker() {
        if (residentImage == null) {
            alertMessage("Please select a resident image");

            return false;
        } else if (mapImage == null) {
            alertMessage("Please select a map image");
            return false;
        }
        return true;
    }

    public boolean isRepeated() {
        try {
            Connection connection = DatabaseConnection.getInstance();
            PreparedStatement ps = connection
                    .prepareStatement("Select * from Resident where Name=? and FName=? and GFName=? and phoneNumber=?");
            ps.setString(1, Name.getText());
            ps.setString(2, FName.getText());
            ps.setString(3, GFName.getText());
            ps.setString(4, phoneNumber.getText());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                alertMessage("The Resident is Already Registered");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public void alertMessage(String message) {
        JAlert alert = new JAlert("Error", message);
        alert.showAlert();
        System.out.println(invalidTextfield);
        if (invalidTextfield < 17) {
            ResidentForm.get(invalidTextfield).requestFocus();
        } else {
            MapHolderForm.get(invalidTextfield - 17).requestFocus();
        }
    }

    public void successMessage(String message) {
        JAlert alert = new JAlert("Success", message);
        alert.showAlert();
    }
}
