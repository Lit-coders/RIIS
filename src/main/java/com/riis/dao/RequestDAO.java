package com.riis.dao;

import java.util.List;


import com.riis.model.databasemodel.Request;

import javafx.collections.ObservableList;


public interface RequestDAO {

    List<Request> getPendingUnpaidRequests() throws Exception;
    ObservableList<Request> getPendingUnapprovedRequests() throws Exception;
    ObservableList<Request> getPendingSealedRequests() throws Exception;
    boolean addRequest(Request request) throws Exception;
    void approveRequest(Request request) throws Exception;
    void deleteRequest(Request request) throws Exception;
    Request getRequestByRID(int rid) throws Exception;
    void updateRequestTime(Request request) throws Exception;
}
