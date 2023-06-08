package com.riis.controller.BaseController;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.riis.context.UserContext;
import com.riis.controller.Controller;
import com.riis.dao.EmployeeDAO;
import com.riis.dao.EmployeeDAOImpl;
import com.riis.dao.ResidentDAO;
import com.riis.dao.ResidentDAOImpl;
import com.riis.model.databasemodel.Employee;
import com.riis.model.databasemodel.Request;
import com.riis.model.databasemodel.Resident;
import com.riis.model.viewmodel.OverviewModel;
import com.riis.utils.DateProvider;
import com.riis.utils.TextGenerator;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
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
        overviewModel.bindTotResidents(tot_residents);
        overviewModel.bindTotResidentsMale(tot_residents_male);
        overviewModel.bindTotResidentsFemale(tot_residents_female);
        overviewModel.bindLastLogin(lastLogin);
        overviewModel.bindRecentActivity(recentActivity);
        // recent activity and bar chart are remaining
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
            lastLoginVBox.getChildren().clear();
            VBox parent = (VBox) lastLoginVBox.getParent();
            lastLoginVBox.setAlignment(Pos.CENTER);
            lastLoginVBox.prefHeightProperty().bind(parent.heightProperty());
            Text never = TextGenerator.generateText("Never", "Poppins-SemiBold", 28, "#702FFC");
            lastLoginVBox.getChildren().add(never);
        } else {
            // make the last login time into a LocalDateTime object
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime lastLoginTime = LocalDateTime.parse(lastLogin, formatter);

            // get the current time
            LocalDateTime now = DateProvider.getDateTime();

            System.out.println(lastLoginTime);
            System.out.println(now);

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

        String lastLogin = calculateLastLogin(userContext.getUsername());
        overviewModel.setLastLoginText(lastLogin);
        overviewModel.setLastLoginComp(); 

    }

    public String greetingGenerator() {
        String greeting = "";
        String time = DateProvider.getTime();
        int hour = Integer.parseInt(time.split(":")[0]);
        String ap = time.split(" ")[1];
        if(hour > 6 && ap.equals("AM")) {
            greeting = "Good Morning";
        } else if(hour < 6 && ap.equals("PM")) {
            greeting = "Good Afternoon";
        } else if(hour > 6 && ap.equals("PM")) {
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
        hBox.setStyle("-fx-background-color: #F5F0F0; -fx-background-radius: 15px; -fx-border-radius: 15px; -fx-border-color: #F5F0F0; -fx-border-width: 3px");
        hBox.setPadding(new Insets(0, 10, 0, 10));

        HBox nameHolder = new HBox();
        nameHolder.setAlignment(Pos.CENTER_LEFT);

        ResidentDAO residentDAO = new ResidentDAOImpl();
        Resident resident = residentDAO.getResidentNameByID(request.getResidentID());
        String fullName = resident.getName() + " " + resident.getFName();
        Text nameText = TextGenerator.generateText(fullName,"Poppins-Regular", 18, "#702FFC");
        nameHolder.getChildren().add(nameText);

        HBox iconHolder = new HBox();
        iconHolder.setAlignment(Pos.CENTER_RIGHT);

        SVGPath svgPath = new SVGPath();
        String svgContent = "";

        if(request.getRequestType() == 0) { 
            svgContent = "M12 9v6m3-3H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z";
            svgPath.setContent(svgContent);
        } else if(request.getRequestType() == 1) {
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

    public void getView() throws Exception {
    }
}