package com.riis.model.databasemodel;

public class Employee {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private String job;

    public Employee() {
    }

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Employee(String username, String password, String firstName, String lastName, String middleName, String job) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.job = job;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getJob(){
        return job;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName){
        this.middleName = middleName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setJob(String job){
        this.job = job;
    }
}
