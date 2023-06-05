package com.riis.model.databasemodel;

public class Resident {
    private int residentId;
    private String name;
    private String fatherName;
    private String gFatherName;
    private String phoneNumber;
    private String sex;

    public Resident(int residentId, String name, String fatherName, String gFatherName, String sex, String phone) {
        this.residentId = residentId;
        this.name = name;
        this.fatherName =  fatherName;
        this.gFatherName = gFatherName;
        this.sex = sex;
        this.phoneNumber = phone;
    }
    public int getResidentId(){
        return residentId;
    }

    public String getName() {
        return name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getGFatherName() {
        return gFatherName;
    }
    
    public String getSex() {
        return sex;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setResidentId(int id){
        this.residentId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setGFatherName(String middleName){
        this.gFatherName = middleName;
    }

    public void setPhoneNumber(String phone){
        this.phoneNumber = phone;
    }
}
