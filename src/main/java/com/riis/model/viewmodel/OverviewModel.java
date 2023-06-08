package com.riis.model.viewmodel;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class OverviewModel {
    private static OverviewModel instance;

    private Label greeting;
    private String greetingText;
    private Label loggedInUser;
    private String loggedInUserText;
    private Label loggedInUserFullName;
    private String loggedInUserFullNameText;
    private Label date;
    private String dateText;
    private Label time;
    private String timeText;
    private Label tot_residents;
    private String tot_residentsText;
    private Label tot_residents_male;
    private String tot_residents_maleText;
    private Label tot_residents_female;
    private String tot_residents_femaleText;
    private Label lastLogin; 
    private String lastLoginText;
    private VBox recentActivity;

    private OverviewModel() {
    }

    public static OverviewModel getInstance() {
        if (instance == null) {
            instance = new OverviewModel();
        }
        return instance;
    }

    public Label getGreetingComp() {
        return greeting;
    }
    
    public void bindGreeting(Label greeting) {
        this.greeting = greeting;
    }
    
    public void setGreetingText(String greeting) {
        this.greetingText = greeting;
    }
    
    public void setGreetingTextComp() {
        this.greeting.setText(greetingText);
    }
    
    public Label getCompLoggedInUserComp() {
        return loggedInUser;
    }
    
    public void bindLoggedInUser(Label loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
    
    public void setLoggedInUserText(String loggedInUser) {
        this.loggedInUserText = loggedInUser;
    }
    
    public void setLoggedInUserTextComp() {
        this.loggedInUser.setText(loggedInUserText);
    }
    
    public Label getLoggedInUserFullNameComp() {
        return loggedInUserFullName;
    }
    
    public void bindLoggedInUserFullName(Label loggedInUserFullName) {
        this.loggedInUserFullName = loggedInUserFullName;
    }
    
    public void setLoggedInUserFullNameText(String loggedInUserFullName) {
        this.loggedInUserFullNameText = loggedInUserFullName;
    }    
    
    public Label getDateComp() {
        return date;
    }
    
    public void bindDate(Label date) {
        this.date = date;
    }
    
    public void setDateText(String date) {
        this.dateText = date;
    }
    
    public Label getTimeComp() {
        return time;
    }
    
    public void bindTime(Label time) {
        this.time = time;
    }
    
    public void setTimeText(String time) {
        this.timeText = time;
    }
    
    public Label getTotResidentsComp() {
        return tot_residents;
    }
    
    public void bindTotResidents(Label totResidents) {
        this.tot_residents = totResidents;
    }
    
    public void setTotResidentsText(String totResidents) {
        this.tot_residentsText = totResidents;
    }
    
    public Label getTotResidentsMaleComp() {
        return tot_residents_male;
    }
    
    public void bindTotResidentsMale(Label totResidentsMale) {
        this.tot_residents_male = totResidentsMale;
    }
    
    public void setTotResidentsMaleText(String totResidentsMale) {
        this.tot_residents_maleText = totResidentsMale;
    }
    
    public Label getTotResidentsFemaleComp() {
        return tot_residents_female;
    }
    
    public void bindTotResidentsFemale(Label totResidentsFemale) {
        this.tot_residents_female = totResidentsFemale;
    }
    
    public void setTotResidentsFemaleText(String totResidentsFemale) {
        this.tot_residents_femaleText = totResidentsFemale;
    }
    
    public Label getLastLoginComp() {
        return lastLogin;
    }
    
    public void bindLastLogin(Label lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    public void setLastLoginText(String lastLogin) {
        this.lastLoginText = lastLogin;
    }

    public void setLastLoginComp() {
        this.lastLogin.setText(lastLoginText);
    }
    
    public void setLoggedInUserFullNameTextComp() {
        this.loggedInUserFullName.setText(loggedInUserFullNameText);
    }
    
    public void setDateTextComp() {
        this.date.setText(dateText);
    }
    
    public void setTimeTextComp() {
        this.time.setText(timeText);
    }
    
    public void setTotResidentsTextComp() {
        this.tot_residents.setText(tot_residentsText);
    }
    
    public void setTotResidentsMaleTextComp() {
        this.tot_residents_male.setText(tot_residents_maleText);
    }
    
    public void setTotResidentsFemaleTextComp() {
        this.tot_residents_female.setText(tot_residents_femaleText);
    }

    public VBox getRecentActivityComp() {
        return recentActivity;
    }

    public void bindRecentActivity(VBox recentActivity) {
        this.recentActivity = recentActivity;
    }
}
