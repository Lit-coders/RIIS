package com.riis.controller.BaseController;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.riis.context.UserContext;
import com.riis.controller.Controller;
import com.riis.dao.EmployeeDAO;
import com.riis.dao.EmployeeDAOImpl;
import com.riis.dao.KebeleResidentDAO;
import com.riis.dao.KebeleResidentDAOImpl;
import com.riis.dao.ResidentDAO;
import com.riis.dao.ResidentDAOImpl;
import com.riis.model.databasemodel.Employee;
import com.riis.model.databasemodel.KebeleResident;
import com.riis.model.databasemodel.Request;
import com.riis.model.databasemodel.Resident;
import com.riis.model.viewmodel.OverviewModel;
import com.riis.utils.DateProvider;
import com.riis.utils.TextGenerator;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

public class BaseOverviewController implements Controller {

    @FXML
    protected Label greeting;

    @FXML
    public Label loggedInUser;

    @FXML
    protected Label loggedInUserFullName;

    @FXML
    protected Label date;

    @FXML
    protected Label time;

    @FXML
    protected MenuButton res_tot;

    @FXML 
    protected MenuButton gen_tot;

    @FXML
    protected Label tot_residents;

    @FXML
    protected Label tot_residents_male;

    @FXML
    protected Label tot_residents_female;

    @FXML
    protected Label lastLogin;

    @FXML
    protected CategoryAxis xAxis;

    @FXML
    protected NumberAxis yAxis;

    @FXML
    protected VBox recentActivity;

    protected OverviewModel overviewModel;

    protected UserContext userContext;

    public BaseOverviewController() {
        this.overviewModel = OverviewModel.getInstance();
        this.userContext = UserContext.getInstance();
    }

    @FXML
    public void initialize() throws Exception {
        overviewModel.bindGreeting(greeting);
        overviewModel.bindLoggedInUser(loggedInUser);
        overviewModel.bindLoggedInUserFullName(loggedInUserFullName);
        overviewModel.bindDate(date);
        overviewModel.bindTime(time);
        initializeMenuButtonsRes();
        initializeMenuButtonsGen();
        overviewModel.bindTotResidents(tot_residents);
        overviewModel.bindTotResidentsMale(tot_residents_male);
        overviewModel.bindTotResidentsFemale(tot_residents_female);
        overviewModel.bindLastLogin(lastLogin);
        overviewModel.bindRecentActivity(recentActivity);
        // recent activity and bar chart are remaining
    }
    
    public void initializeMenuButtonsRes() {
        MenuItem all = createMenuItem("All", "all");
        MenuItem today = createMenuItem("Today", "today");
        MenuItem yesterday = createMenuItem("Yesterday", "yesterday");
        MenuItem thisWeek = createMenuItem("This Week", "week");

        res_tot.getItems().addAll(all, today, yesterday, thisWeek);

        EventHandler<ActionEvent> menuItemClickHandler = e -> {
            MenuItem item = (MenuItem) e.getSource();
            String date = (String) item.getUserData();

            try {
                res_tot.setText(item.getText());
                List<KebeleResident> kebeleResidents = fetchTotResidents(date);
                overviewModel.setTotResidentsText(String.valueOf(kebeleResidents.size()));
                overviewModel.setTotResidentsTextComp();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };

        all.setOnAction(menuItemClickHandler);
        today.setOnAction(menuItemClickHandler);
        yesterday.setOnAction(menuItemClickHandler);
        thisWeek.setOnAction(menuItemClickHandler);

        Platform.runLater(() -> {
            today.fire();
        });

    }

    private MenuItem createMenuItem(String text, String date) {
        MenuItem menuItem = new MenuItem(text);
        menuItem.setUserData(date);
        return menuItem;
    }

    public void initializeMenuButtonsGen() {
        MenuItem all = createMenuItem("All", "all");
        MenuItem today = createMenuItem("Today", "today");
        MenuItem yesterday = createMenuItem("Yesterday", "yesterday");
        MenuItem thisWeek = createMenuItem("This Week", "week");
        
        gen_tot.getItems().addAll(all, today, yesterday, thisWeek);

        EventHandler<ActionEvent> menuItemClickHandler = e -> {
            MenuItem item = (MenuItem) e.getSource();
            String date = (String) item.getUserData();

            try {
                gen_tot.setText(item.getText());
                List<KebeleResident> kebeleResidents = fetchTotResidents(date);
                List<Integer> totResClassified = countKebeleResidents(kebeleResidents);
                overviewModel.setTotResidentsMaleText(totResClassified.get(0).toString());
                overviewModel.setTotResidentsMaleTextComp();
                overviewModel.setTotResidentsFemaleText(totResClassified.get(1).toString());
                overviewModel.setTotResidentsFemaleTextComp();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };

        all.setOnAction(menuItemClickHandler);
        today.setOnAction(menuItemClickHandler);
        yesterday.setOnAction(menuItemClickHandler);
        thisWeek.setOnAction(menuItemClickHandler);

        Platform.runLater(() -> {
            today.fire();
        });
    }

    public List<Integer> countKebeleResidents(List<KebeleResident> kebeleResidents) throws Exception {
        ResidentDAO residentDAO = new ResidentDAOImpl();
        List<Integer> totResClassified = new ArrayList<>(List.of(0, 0));
        for(KebeleResident kebeleResident: kebeleResidents) {
            int rid = kebeleResident.getResidentId();
            String gender = residentDAO.getGender(rid);
            if(gender.equalsIgnoreCase("male")) {
                totResClassified.set(0, totResClassified.get(0) + 1);
            } else if(gender.equalsIgnoreCase("female")){
                totResClassified.set(1, totResClassified.get(1) + 1);
            }
        }
        return totResClassified;
    }
    

    public List<KebeleResident> fetchTotResidents(String date) throws Exception {
        KebeleResidentDAO kebeleResidentDAO = new KebeleResidentDAOImpl();
        List<KebeleResident> kebeleResidents = kebeleResidentDAO.getAllKebeleResidents();
        if (date.equals("all")) {
            return kebeleResidents;
        } else {
            kebeleResidents.clear();
            if (date.equals("today")) {
                kebeleResidents.addAll(kebeleResidentDAO.getAllKebeleResidentsByDate("today"));
            } else if (date.equals("yesterday")) {
                kebeleResidents.addAll(kebeleResidentDAO.getAllKebeleResidentsByDate("yesterday"));
            } else if (date.equals("week")) {
                kebeleResidents.addAll(kebeleResidentDAO.getAllKebeleResidentsByDate("today"));
                kebeleResidents.addAll(kebeleResidentDAO.getAllKebeleResidentsByDate("week"));
            }
        }
        
        return kebeleResidents;
    }

    public String getFullName(String user) throws SQLException {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        Employee employee = employeeDAO.getEmployeeByUsername(user);
        String fullName = employee.getFirstName() + " " + employee.getLastName();
        return fullName;
    }

    public String calculateLastLogin(String user) throws SQLException {
        String simplifiedLastLogin = "";
        String lastLogin = userContext.getLastLogin();

        if (lastLogin == null) {
            HBox lastLoginBox = (HBox) overviewModel.getLastLoginComp().getParent();
            VBox lastLoginVBox = (VBox) lastLoginBox.getParent();
            if(lastLoginVBox != null) {
                lastLoginVBox.getChildren().clear();
                VBox parent = (VBox) lastLoginVBox.getParent();
                lastLoginVBox.setAlignment(Pos.CENTER);
                lastLoginVBox.prefHeightProperty().bind(parent.heightProperty());
                Text never = TextGenerator.generateText("Never", "Poppins-SemiBold", 28, "#702FFC");
                lastLoginVBox.getChildren().add(never);
            }
        } else {
            // make the last login time into a LocalDateTime object
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime lastLoginTime = LocalDateTime.parse(lastLogin, formatter);

            // get the current time
            LocalDateTime now = DateProvider.getDateTime();

            Duration duration = Duration.between(lastLoginTime, now);

            long days = duration.toDays();
            long hours = duration.toHours() % 24;
            long minutes = duration.toMinutes() % 60;
            long seconds = duration.getSeconds() % 60;

            if (days > 0) {
                simplifiedLastLogin = days + "d";
            } else if (hours > 0) {
                simplifiedLastLogin = hours + "h";
            } else if (minutes > 0) {
                simplifiedLastLogin = minutes + "m";
            } else {
                simplifiedLastLogin = seconds + "s";
            }
        }

        return simplifiedLastLogin;
    }

    public void renderStatic() throws SQLException {
        String greeting = greetingGenerator();
        overviewModel.setGreetingText(greeting + ",");
        overviewModel.setGreetingTextComp();

        String fullName = getFullName(userContext.getUsername());
        overviewModel.setLoggedInUserText(fullName.split(" ")[0]);
        overviewModel.setLoggedInUserTextComp();

        overviewModel.setLoggedInUserFullNameText(fullName);
        overviewModel.setLoggedInUserFullNameTextComp();

    }

    public String greetingGenerator() {
        String greeting = "";
        String time = DateProvider.getTime();
        int hour = Integer.parseInt(time.split(":")[0]);
        String ap = time.split(" ")[1];
        if (hour > 6 && ap.equals("AM")) {
            greeting = "Good Morning";
        } else if (hour < 6 && ap.equals("PM")) {
            greeting = "Good Afternoon";
        } else if (hour > 6 && ap.equals("PM")) {
            greeting = "Good Evening";
        } else {
            greeting = "Good Night";
        }
        return greeting;
    }

    public HBox buildHBox(Request request) throws Exception {
        HBox hBox = new HBox();
        HBox.setMargin(hBox, new Insets(0, 0, 0, 0));
        hBox.setMinHeight(40);
        hBox.setPrefWidth(overviewModel.getRecentActivityComp().getPrefWidth() - 20);
        hBox.setStyle(
                "-fx-background-color: #F5F0F0; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: #F5F0F0; -fx-border-width: 3px");
        hBox.setPadding(new Insets(0, 10, 0, 10));

        HBox nameHolder = new HBox();
        nameHolder.setAlignment(Pos.CENTER_LEFT);

        ResidentDAO residentDAO = new ResidentDAOImpl();
        Resident resident = residentDAO.getResidentNameByID(request.getResidentID());
        String fullName = resident.getName() + " " + resident.getFName();
        Text nameText = TextGenerator.generateText(fullName, "Poppins-Regular", 18, "#702FFC");
        nameHolder.getChildren().add(nameText);

        HBox iconHolder = new HBox();
        iconHolder.setAlignment(Pos.CENTER_RIGHT);

        SVGPath svgPath = new SVGPath();
        String svgContent = "";

        if (request.getRequestType() == 0) {
            svgContent = "M12 9v6m3-3H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z";
            svgPath.setContent(svgContent);
        } else if (request.getRequestType() == 1) {
            svgContent = "M12 6v6h4.5m4.5 0a9 9 0 11-18 0 9 9 0 0118 0z";
            svgPath.setContent(svgContent);
        } else {
            svgContent = "M9.879 7.519c1.171-1.025 3.071-1.025 4.242 0 1.172 1.025 1.172 2.687 0 3.712-.203.179-.43.326-.67.442-.745.361-1.45.999-1.45 1.827v.75M21 12a9 9 0 11-18 0 9 9 0 0118 0zm-9 5.25h.008v.008H12v-.008z";
            svgPath.setContent(svgContent);
        }

        svgPath.setStroke(Color.valueOf("#702FFC"));
        svgPath.setFill(Color.valueOf("#F5F0F0"));
        svgPath.setScaleX(1.2);
        svgPath.setScaleY(1.2);
        iconHolder.getChildren().add(svgPath);

        nameHolder.prefWidthProperty().bind(hBox.widthProperty().divide(2));
        iconHolder.prefWidthProperty().bind(hBox.widthProperty().divide(2));

        hBox.getChildren().addAll(nameHolder, iconHolder);

        return hBox;
    }

    public void dynamicAnimator() throws SQLException {
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.minutes(1), ev -> {
            try {
                updateLastLogin();
                updateDate();
                updateTime();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void updateLastLogin() throws SQLException {
        String lastLogin = calculateLastLogin(userContext.getUsername());
        overviewModel.setLastLoginText(lastLogin);
        overviewModel.setLastLoginComp();
    }

    public void updateDate() {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Format the date using a DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
        String formattedDate = currentDate.format(formatter);

        // Update the label text with the formatted date
        overviewModel.getDateComp().setText(formattedDate);
    }

    public void updateTime() {
        // Get the current time
        LocalTime currentTime = LocalTime.now();

        // Format the time using a DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = currentTime.format(formatter);

        // Update the label text with the formatted time
        overviewModel.getTimeComp().setText(formattedTime);
    }

    public ObservableList<Request> sortReqDate(ObservableList<Request> requests) {
        FXCollections.reverse(requests);
        return requests;
    }

    public void getView() throws Exception {
    }
}