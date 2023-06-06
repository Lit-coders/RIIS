package com.riis.dao;

import java.util.List;


import com.riis.model.databasemodel.Request;

import javafx.collections.ObservableList;


public interface RequestDAO {

    List<Request> getPendingUnpaidRequests() throws Exception;
    ObservableList<Request> getPendingUnapprovedRequests() throws Exception;
    void addRequest(Request request) throws Exception;
    void updateRequest(Request request) throws Exception;
    void addToCreationPayment(Request request) throws Exception;
    void approveRequest(Request request) throws Exception;
    
}
