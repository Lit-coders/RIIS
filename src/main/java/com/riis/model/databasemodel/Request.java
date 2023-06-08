package com.riis.model.databasemodel;

public class Request {

    private int RequestID;
    private int ResidentID;
    private int SealedRequest;
    private int UnpaidRequest;
    private int ApprovalRequest;
    private int RequestType;
    private String RequestDate;

    public Request() {
    }

    public Request(int RequestID, int ResidentID, int SealedRequest, int UnpaidRequest, int ApprovalRequest, int RequestType, String RequestDate) {
        this.RequestID = RequestID;
        this.ResidentID = ResidentID;
        this.SealedRequest = SealedRequest;
        this.UnpaidRequest = UnpaidRequest;
        this.ApprovalRequest = ApprovalRequest;
        this.RequestType = RequestType;
        this.RequestDate = RequestDate;
    }

    public Request(int ResidentID, int SealedRequest, int UnpaidRequest, int ApprovalRequest, int RequestType) {
        this.ResidentID = ResidentID;
        this.SealedRequest = SealedRequest;
        this.UnpaidRequest = UnpaidRequest;
        this.ApprovalRequest = ApprovalRequest;
        this.RequestType = RequestType;
    }

    public int getRequestID() {
        return RequestID;
    }

    public void setRequestID(int RequestID) {
        this.RequestID = RequestID;
    }
    
    public int getResidentID() {
        return ResidentID;
    }

    public void setResidentID(int ResidentID) {
        this.ResidentID = ResidentID;
    }

    public int getSealedRequest() {
        return SealedRequest;
    }

    public void setSealedRequest(int SealedRequest) {
        this.SealedRequest = SealedRequest;
    }

    public int getUnpaidRequest() {
        return UnpaidRequest;
    }

    public void setUnpaidRequest(int UnpaidRequest) {
        this.UnpaidRequest = UnpaidRequest;
    }

    public int getApprovalRequest() {
        return ApprovalRequest;
    }

    public void setApprovalRequest(int ApprovalRequest) {
        this.ApprovalRequest = ApprovalRequest;
    }

    public int getRequestType() {
        return RequestType;
    }

    public void setRequestType(int RequestType) {
        this.RequestType = RequestType;
    }

    public String getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(String RequestDate) {
        this.RequestDate = RequestDate;
    }
}
