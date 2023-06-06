package com.riis.model.viewmodel;

import javafx.scene.control.Label;

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
    private Label lastLoginNum;
    private String lastLoginNumText;
    private Label lastLoginLetter; 
    private String lastLoginLetterText;

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

    public String getGreetingText() {
        return greetingText;
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

    public String getLoggedInUserText() {
        return loggedInUserText;
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
    
    public void setLoggedInUserFullNameText(String fullName) {
        this.loggedInUserFullName.setText(fullName);
    }

    public String getLoggedInUserFullNameText() {
        return loggedInUserFullNameText;
    }
    
    public Label getDateComp() {
        return date;
    }
    
    public void bindDate(Label date) {
        this.date = date;
    }
    
    public void setDateText(String date) {
        this.date.setText(date);
    }

    public String getDateText() {
        return dateText;
    }
    
    public Label getTimeComp() {
        return time;
    }
    
    public void bindTime(Label time) {
        this.time = time;
    }
    
    public void setTimeText(String time) {
        this.time.setText(time);
    }

    public String getTimeText() {
        return timeText;
    }
    
    public Label getTotResidentsComp() {
        return tot_residents;
    }
    
    public void bindTotResidents(Label totResidents) {
        this.tot_residents = totResidents;
    }
    
    public void setTotResidentsText(String count) {
        this.tot_residents.setText(count);
    }

    public String getTotResidentsText() {
        return tot_residentsText;
    }
    
    public Label getTotResidentsMaleComp() {
        return tot_residents_male;
    }
    
    public void bindTotResidentsMale(Label totResidentsMale) {
        this.tot_residents_male = totResidentsMale;
    }
    
    public void setTotResidentsMaleText(String count) {
        this.tot_residents_male.setText(count);
    }

    public String getTotResidentsMaleText() {
        return tot_residents_maleText;
    }
    
    public Label getTotResidentsFemaleComp() {
        return tot_residents_female;
    }
    
    public void bindTotResidentsFemale(Label totResidentsFemale) {
        this.tot_residents_female = totResidentsFemale;
    }
    
    public void setTotResidentsFemaleText(String count) {
        this.tot_residents_female.setText(count);
    }

    public String getTotResidentsFemaleText() {
        return tot_residents_femaleText;
    }
    
    public Label getLastLoginNumComp() {
        return lastLoginNum;
    }
    
    public void bindLastLoginNum(Label lastLoginNum) {
        this.lastLoginNum = lastLoginNum;
    }
    
    public void setLastLoginNumText(String num) {
        this.lastLoginNum.setText(num);
    }

    public String getLastLoginNumText() {
        return lastLoginNumText;
    }
    
    public Label getLastLoginLetterComp() {
        return lastLoginLetter;
    }
    
    public void bindLastLoginLetter(Label lastLoginLetter) {
        this.lastLoginLetter = lastLoginLetter;
    }
    
    public void setLastLoginLetterText(String letter) {
        this.lastLoginLetter.setText(letter);
    }

    public String getLastLoginLetterText() {
        return lastLoginLetterText;
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
    
    public void setLastLoginNumTextComp() {
        this.lastLoginNum.setText(lastLoginNumText);
    }
    
    public void setLastLoginLetterTextComp() {
        this.lastLoginLetter.setText(lastLoginLetterText);
    }
    

}
