package com.riis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.riis.Context.UserContext;
import com.riis.database.DatabaseConnection;
import com.riis.model.databasemodel.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
                        resultSet.getInt("RID"),
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

    @Override
    public void addRequest(Request request) throws ClassNotFoundException, SQLException {
        Connection connection = DatabaseConnection.getInstance();

        String query = "INSERT INTO Request (RID, SealedRequest, UnpaidRequest, ApprovalRequest, RequestType, RequestDate) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            pis.setInt(1, request.getResidentID());
            pis.setInt(2, request.getSealedRequest());
            pis.setInt(3, request.getUnpaidRequest());
            pis.setInt(4, request.getApprovalRequest());
            pis.setInt(5, request.getRequestType());

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            pis.setString(6, formattedDateTime);
            pis.executeUpdate();
        }
    }

    @Override
    public void updateRequest(Request request) throws ClassNotFoundException, SQLException {
        Connection connection = DatabaseConnection.getInstance();

        String query = "UPDATE Request SET ApprovalRequest = ? WHERE RequestID = ?";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            pis.setInt(1, 1);
            pis.setInt(2, request.getRequestID());
            pis.executeUpdate();
        }
    }

    @Override
    public ObservableList<Request> getPendingUnapprovedRequests() throws SQLException {
        ObservableList<Request> requests = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getInstance();
        String Query = "SELECT * FROM Request WHERE UnpaidRequest = 1 AND ApprovalRequest = 1 and SealedRequest = 0";

        try (PreparedStatement pis = connection.prepareStatement(Query);
                ResultSet resultSet = pis.executeQuery();) {

            while (resultSet.next()) {
                Request request = new Request(
                        resultSet.getInt("RequestID"),
                        resultSet.getInt("RID"),
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
    @Override
    public ObservableList<Request> getPendingSealedRequests() throws SQLException {
        ObservableList<Request> requests = FXCollections.observableArrayList();
        Connection connection = DatabaseConnection.getInstance();
        String Query = "SELECT * FROM Request WHERE UnpaidRequest = 1 AND ApprovalRequest = 1 and SealedRequest = 1";

        try (PreparedStatement pis = connection.prepareStatement(Query);
                ResultSet resultSet = pis.executeQuery();) {

            while (resultSet.next()) {
                Request request = new Request(
                        resultSet.getInt("RequestID"),
                        resultSet.getInt("RID"),
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

    @Override
    public void addToCreationPayment(Request request) throws ClassNotFoundException, SQLException {
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
    public void approveRequest(Request request) throws ClassNotFoundException, SQLException {
        Connection connection = DatabaseConnection.getInstance();

        String query = "UPDATE Request SET SealedRequest = ? WHERE RequestID = ?";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            pis.setInt(1, 1);
            pis.setInt(2, request.getRequestID());
            pis.executeUpdate();
        }
    }

    @Override
    public void addToKebeleResidentID(Request request) throws ClassNotFoundException, SQLException {
        Connection connection = DatabaseConnection.getInstance();
        String query = "INSERT INTO KebeleResidentID (ResidentID,GivenDate,ExpDate,ExpirationStatus) VALUES (?,?,?,?)";
        try (PreparedStatement pis = connection.prepareStatement(query);) {

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            LocalDateTime expDateTime = currentDateTime.plusYears(3);
            String formattedExpDateTime = expDateTime.format(formatter);

            pis.setInt(1, request.getResidentID());
            pis.setString(2, formattedDateTime);
            pis.setString(3, formattedExpDateTime);
            pis.setInt(4, 0);

            pis.executeUpdate();
        }
    }

    @Override
    public void deleteRequest(Request request) throws ClassNotFoundException, SQLException {
        Connection connection = DatabaseConnection.getInstance();

        String query = "DELETE FROM Request WHERE RequestID = ?";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            pis.setInt(1, request.getRequestID());
            pis.executeUpdate();
        }
    }
}
