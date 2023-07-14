package com.riis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.riis.context.UserContext;
import com.riis.database.DatabaseConnection;
import com.riis.model.databasemodel.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RequestDAOImpl implements RequestDAO {

    @Override
    public ObservableList<Request> getPendingUnpaidRequests() throws ClassNotFoundException, SQLException {
        ObservableList<Request> requests = FXCollections.observableArrayList();
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
    public boolean addRequest(Request request) throws ClassNotFoundException, SQLException {
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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
    public void approveRequest(Request request) throws ClassNotFoundException, SQLException {
        Connection connection = DatabaseConnection.getInstance();
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        UserContext userContext = UserContext.getInstance();
        String query = "";
        String job = employeeDAO.getEmployeeByUsername(userContext.getUsername()).getJob();
        if (job.equals("Information Officer")) {
            query = "UPDATE Request SET UnpaidRequest = ? WHERE RequestID = ?";
        } else if (job.equals("Finance Officer")) {
            query = "UPDATE Request SET ApprovalRequest = ? WHERE RequestID = ?";
        } else if (job.equals("Kebele Manager")) {
            query = "UPDATE Request SET SealedRequest = ? WHERE RequestID = ?";
        }

        try (PreparedStatement pis = connection.prepareStatement(query)) {
            pis.setInt(1, 1);
            pis.setInt(2, request.getRequestID());
            pis.executeUpdate();
            updateRequestTime(request);
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

    @Override
    public Request getRequestByRID(int rid) throws SQLException {
        Request request = new Request();
        Connection connection = DatabaseConnection.getInstance();
        String Query = "SELECT * FROM Request WHERE RID = ?";
        try (PreparedStatement pis = connection.prepareStatement(Query);) {
            pis.setInt(1, rid);
            ResultSet resultSet = pis.executeQuery();
            while (resultSet.next()) {
                request.setRequestID(resultSet.getInt("RequestID"));
                request.setResidentID(resultSet.getInt("RID"));
                request.setSealedRequest(resultSet.getInt("SealedRequest"));
                request.setUnpaidRequest(resultSet.getInt("UnpaidRequest"));
                request.setApprovalRequest(resultSet.getInt("ApprovalRequest"));
                request.setRequestType(resultSet.getInt("RequestType"));
                request.setRequestDate(resultSet.getString("RequestDate"));
            }
        }
        return request;
    }

    @Override
    public void updateRequestTime(Request request) throws SQLException {
        Connection connection = DatabaseConnection.getInstance();

        String query = "UPDATE Request SET RequestDate = ? WHERE RequestID = ?";
        try (PreparedStatement pis = connection.prepareStatement(query);) {

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);

            pis.setString(1, formattedDateTime);
            pis.setInt(2, request.getRequestID());
            pis.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
