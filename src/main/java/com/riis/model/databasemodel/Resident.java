package com.riis.model.databasemodel;

public class Resident {
    private int residentId;
    private String name;
    private String fName;
    private String gFName;
    private String dob;
    private String pob;
    private String phoneNumber;
    private String sex;
    private String citizenship;
    private String maritalStatus;
    private String job;
    private String mName;
    private String bType;
    private String houseNumber;
    private String ecf;
    private String ecp;
    private String photoPath;

    public Resident() {
    }
    
    public Resident(int id, String name, String fName, String gFName, String dob, String pob, String phoneNumber, String mName,
    String sex, String citizenship, String maritalStatus, String job,
    String bType, String houseNumber, String ecf, String ecp, String photoPath) {
        this.residentId = id;
        this.name = name;
        this.fName = fName;
        this.gFName = gFName;
        this.dob = dob;
        this.pob = pob;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
        this.citizenship = citizenship;
        this.maritalStatus = maritalStatus;
        this.job = job;
        this.mName = mName;
        this.bType = bType;
        this.houseNumber = houseNumber;
        this.ecf = ecf;
        this.ecp = ecp;
        this.photoPath = photoPath;
    }

    public Resident(int residentId, String name, String fatherName, String gFatherName, String sex, String phone) {
        this.residentId = residentId;
        this.name = name;
        this.fName = fatherName;
        this.gFName = gFatherName;
        this.sex = sex;
        this.phoneNumber = phone;
    }
    
    // Getters and Setters
    
    public int getResidentId(){
        return residentId;
    }

    public void setResidentId(int id){
         this.residentId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFName() {
        return fName;
    }

    public void setFName(String fName) {
        this.fName = fName;
    }

    public String getGFName() {
        return gFName;
    }

    public void setGFName(String gFName) {
        this.gFName = gFName;
    }

    public String getDOB() {
        return dob;
    }

    public void setDOB(String dob) {
        this.dob = dob;
    }

    public String getPOB() {
        return pob;
    }

    public void setPOB(String pob) {
        this.pob = pob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getMName() {
        return mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }

    public String getBType() {
        return bType;
    }

    public void setBType(String bType) {
        this.bType = bType;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getECF() {
        return ecf;
    }

    public void setECF(String ecf) {
        this.ecf = ecf;
    }

    public String getECP() {
        return ecp;
    }

    public void setECP(String ecp) {
        this.ecp = ecp;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String path) {
        this.photoPath = path;
    }
}

