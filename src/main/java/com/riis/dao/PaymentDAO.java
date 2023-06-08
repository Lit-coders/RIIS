package com.riis.dao;

import com.riis.model.databasemodel.Request;

public interface PaymentDAO {
    void addToCreationPayment(Request request) throws Exception;
    void addToRenewalPayment(Request request) throws Exception;
    void addToReplacementPayment(Request request) throws Exception;
}