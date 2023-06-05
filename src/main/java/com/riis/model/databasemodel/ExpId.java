package com.riis.model.databasemodel;

public class ExpId {
    private int residentId;
    private String idGivenDate;
    private String idExpDate;
    private int expStatus;

    public ExpId(int id, String givenDate, String expDate, int expStatus){
        this.residentId = id;
        this.idGivenDate = givenDate;
        this.idExpDate = expDate;
        this.expStatus = expStatus;
    }
    
    public int getExpStatus(){
        return expStatus;
    }

    public int getResidentId(){
        return residentId;
    }
    
    public String getIdgivenDate() {
        return idGivenDate;
    }
    
    public String getIdExpDate(){
        return idExpDate;
    }

    public void setExpStatus(int status){
        this.expStatus = status;
    }

    public void setResidentId(int id){
        this.residentId = id;
    }

    public void setIdGivenDate(String idGivenDate){
        this.idGivenDate = idGivenDate;
    }

    public void setIdExpDate(String idExpDate){
        this.idExpDate = idExpDate;
    }
}