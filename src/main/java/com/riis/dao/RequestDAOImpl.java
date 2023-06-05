package com.riis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.riis.database.DatabaseConnection;
import com.riis.model.databasemodel.Request;

public class RequestDAOImpl implements RequestDAO {

    @Override
    public List<Request> getPendingUnpaidRequests() throws ClassNotFoundException, SQLException {
        List<Request> requests = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance();

        String query = "SELECT * FROM Request WHERE UnpaidRequest = 1 AND ApprovalRequest = 0 and SealedRequest = 0";
        try (PreparedStatement pis = connection.prepareStatement(query);
                ResultSet resultSet = pis.executeQuery();) {

            while (resultSet.next()) {
                Request request = new Request(
                        resultSet.getInt("RequestID"),
                        resultSet.getInt("ResidentID"),
                        resultSet.getInt("SealedRequest"),
                        resultSet.getInt("UnpaidRequest"),
                        resultSet.getInt("ApprovalRequest"),
                        resultSet.getInt("RequestType"),
                        resultSet.getString("RequestDate"));
                requests.add(request);
            }
            return requests;
        }

    }
}
