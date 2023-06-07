package com.riis.controller.BaseController;

import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.riis.context.UserContext;
import com.riis.controller.Controller;
import com.riis.dao.EmployeeDAO;
import com.riis.dao.EmployeeDAOImpl;
import com.riis.model.databasemodel.Employee;
import com.riis.model.viewmodel.OverviewModel;
import com.riis.utils.DateProvider;
import com.riis.utils.TextGenerator;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    protected ListView<HBox> listView;

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
        // recent activity and bar chart are remaining
    }

    public String getFirstName(String user) throws SQLException {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        Employee employee = employeeDAO.getEmployeeByUsername(user);
        String userName = employee.getFirstName();
        return userName;
    }
    
    public String calculateLastLogin(String user) throws SQLException {
        String simplifiedLastLogin = "";
        String lastLogin = userContext.getLastlogin();

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

    public void getView() throws Exception {
    }
}