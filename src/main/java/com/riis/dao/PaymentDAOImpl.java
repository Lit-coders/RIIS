package com.riis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.riis.context.UserContext;
import com.riis.database.DatabaseConnection;
import com.riis.model.databasemodel.Request;

public class PaymentDAOImpl implements PaymentDAO {

    @Override
    public void addToCreationPayment(Request request) throws Exception {
        Connection connection = DatabaseConnection.getInstance();
        UserContext userContext = UserContext.getInstance();
        String query = "INSERT INTO CreatePayment (ResidentID,username,TotalFee) VALUES (?, ?, ?)";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            pis.setInt(1, request.getResidentID());
            pis.setString(2,  userContext.getUsername() );
            pis.setInt(3, 25);
            pis.executeUpdate();
        }
    }

    @Override
    public void addToRenewalPayment(Request request) throws Exception {
        Connection connection = DatabaseConnection.getInstance();
        UserContext userContext = UserContext.getInstance();
        String query = "INSERT INTO RenewalPayment (ResidentID,username,TotalFee) VALUES (?, ?, ?)";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            pis.setInt(1, request.getResidentID());
            pis.setString(2,  userContext.getUsername() );
            pis.setInt(3, 25);
            pis.executeUpdate();
        }
    }

    @Override
    public void addToReplacementPayment(Request request) throws Exception {
        Connection connection = DatabaseConnection.getInstance();
        UserContext userContext = UserContext.getInstance();
        String query = "INSERT INTO LostPayment (ResidentID,username,TotalFee) VALUES (?, ?, ?)";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            pis.setInt(1, request.getResidentID());
            pis.setString(2,  userContext.getUsername() );
            pis.setInt(3, 25);
            pis.executeUpdate();
        }
    }

}