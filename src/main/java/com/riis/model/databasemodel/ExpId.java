package com.riis.model.databasemodel;

public class ExpId {
    private int residentId;
    private String idGivenDate;
    private String idExpDate;

    public ExpId(int id, String givenDate, String expDate){
        this.residentId = id;
        this.idGivenDate = givenDate;
        this.idExpDate = expDate;
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
