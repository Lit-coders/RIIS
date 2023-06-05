package com.riis.dao;

import java.util.List;

import com.riis.model.databasemodel.Request;

public interface RequestDAO {

    List<Request> getPendingUnpaidRequests() throws Exception;
    
}
