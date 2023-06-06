package com.riis.dao;

import java.util.List;


import com.riis.model.databasemodel.Request;


public interface RequestDAO {

    List<Request> getPendingUnpaidRequests() throws Exception;
    // ObservableList<Request> getPendingUnapproveRequests() throws Exception;
    void addRequest(Request request) throws Exception;
    void updateRequest(Request request) throws Exception;
    
}
