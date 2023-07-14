package com.riis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.riis.database.DatabaseConnection;
import com.riis.model.databasemodel.KebeleResident;
import com.riis.model.databasemodel.Resident;

public class KebeleResidentDAOImpl implements KebeleResidentDAO {
    @Override
    public List<KebeleResident> getAllKebeleResidents() throws SQLException {
        List<KebeleResident> kebeleResidents = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance();
        String query = "SELECT * FROM KebeleResident";
        try (PreparedStatement pis = connection.prepareStatement(query);
                ResultSet resultSet = pis.executeQuery()) {
            while (resultSet.next()) {
                KebeleResident kebeleResident = new KebeleResident();

                kebeleResident.setResidentId(resultSet.getInt("ResidentID"));
                kebeleResident.setIdGivenDate(resultSet.getString("GivenDate"));
                kebeleResident.setIdExpDate(resultSet.getString("ExpDate"));
                kebeleResident.setExpStatus(resultSet.getInt("ExpirationStatus"));

                kebeleResidents.add(kebeleResident);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kebeleResidents;
    }

    @Override
    public KebeleResident getKebeleResidentByKID(int kid) throws Exception {
        KebeleResident kebeleResident = new KebeleResident();
        Connection connection = DatabaseConnection.getInstance();
        String query = "SELECT * FROM KebeleResident WHERE KRID = ?";
        try (PreparedStatement pis = connection.prepareStatement(query)) {
            pis.setInt(1, kid);
            ResultSet resultSet = pis.executeQuery();
            while (resultSet.next()) {
                kebeleResident.setResidentId(resultSet.getInt("ResidentID"));
                kebeleResident.setIdGivenDate(resultSet.getString("GivenDate"));
                kebeleResident.setIdExpDate(resultSet.getString("ExpDate"));
                kebeleResident.setExpStatus(resultSet.getInt("ExpirationStatus"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kebeleResident;
    }

    @Override
    public KebeleResident getKebeleResidentByRID(int rid) throws SQLException {
        KebeleResident kebeleResident = new KebeleResident();
        Connection connection = DatabaseConnection.getInstance();
        String query = "SELECT * FROM KebeleResident WHERE ResidentID = ?";
        try (PreparedStatement pis = connection.prepareStatement(query)) {
            pis.setInt(1, rid);
            ResultSet resultSet = pis.executeQuery();
            while (resultSet.next()) {
                kebeleResident.setResidentId(resultSet.getInt("ResidentID"));
                kebeleResident.setIdGivenDate(resultSet.getString("GivenDate"));
                kebeleResident.setIdExpDate(resultSet.getString("ExpDate"));
                kebeleResident.setExpStatus(resultSet.getInt("ExpirationStatus"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kebeleResident;
    }

    @Override
    public Resident getKebeleResidentByID(int kid) throws SQLException {
        ResidentDAO residentDAO = new ResidentDAOImpl();
        Resident resident = new Resident();
        Connection connection = DatabaseConnection.getInstance();
        String query = "SELECT * FROM KebeleResident WHERE KRID = ?";
        try (PreparedStatement pis = connection.prepareStatement(query);
                ResultSet resultSet = pis.executeQuery()) {
            while (resultSet.next()) {
                int RID = resultSet.getInt("ResidentID");
                resident = residentDAO.getResidentByID(RID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resident;
    }

    @Override
    public boolean checkIdExpirationStatus(int rid) throws SQLException {
        boolean status = false;
        Connection connection = DatabaseConnection.getInstance();
        String query = "SELECT ExpirationStatus FROM KebeleResident WHERE ResidentID = ?";
        try (PreparedStatement pis = connection.prepareStatement(query)) {
            pis.setInt(1, rid);
            ResultSet resultSet = pis.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("ExpirationStatus") == 1) {
                    status = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public boolean checkIfRequestExists(int rid) throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        boolean status = false;
        String query = "SELECT RequestType FROM Request WHERE RID = ?";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            pis.setInt(1, rid);
            ResultSet resultSet = pis.executeQuery();
            while (resultSet.next()) {
                status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public void addKebeleResident(int id) throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        String query = "INSERT INTO KebeleResident (ResidentID, GivenDate, ExpDate, ExpirationStatus) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pis = connection.prepareStatement(query)) {
            pis.setInt(1, id);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDateTime = currentDateTime.format(formatter);

            pis.setString(2, formattedDateTime);
            pis.setString(3, currentDateTime.plusYears(3).format(formatter));
            pis.setInt(4, 0);

            pis.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<KebeleResident> getKebeleResidentsByToken(String token) throws SQLException {
        List<KebeleResident> kebeleResidents = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance();
        String query = "SELECT * FROM KebeleResident " +
                "INNER JOIN Resident ON KebeleResident.ResidentID = Resident.ResidentID " +
                "WHERE Resident.Name LIKE ? OR Resident.FName LIKE ? OR Resident.GFName LIKE ?";
        try (PreparedStatement pis = connection.prepareStatement(query)) {
            pis.setString(1, "%" + token + "%");
            pis.setString(2, "%" + token + "%");
            pis.setString(3, "%" + token + "%");
            ResultSet resultSet = pis.executeQuery();
            while (resultSet.next()) {
                KebeleResident kebeleResident = new KebeleResident();
                kebeleResident.setResidentId(resultSet.getInt("ResidentID"));
                kebeleResident.setIdGivenDate(resultSet.getString("GivenDate"));
                kebeleResident.setIdExpDate(resultSet.getString("ExpDate"));
                kebeleResident.setExpStatus(resultSet.getInt("ExpirationStatus"));
                kebeleResidents.add(kebeleResident);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kebeleResidents;
    }

    @Override
    public void updateKebeleResident(int id) throws Exception {
        Connection connection = DatabaseConnection.getInstance();
        String query = "UPDATE KebeleResident SET ExpirationStatus = ? , ExpDate = ? WHERE ResidentID = ?";
        try (PreparedStatement pis = connection.prepareStatement(query)) {
            pis.setInt(1, 0);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            pis.setString(2, currentDateTime.plusYears(3).format(formatter));
            pis.setInt(3, id);
            pis.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<KebeleResident> getAllKebeleResidentsByDate(String date) throws SQLException {
        List<KebeleResident> kebeleResidents = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance();
        String query = "";
        if (date.equals("today")) {
            query = "SELECT * FROM KebeleResident WHERE GivenDate  =  date('now')";
        } else if (date.equals("week")) {
            query = "SELECT * FROM KebeleResident WHERE DATE(GivenDate) >= DATE('now', 'weekday 0', '-6 days')";
        } else if (date.equals("yesterday")) {
            query = "SELECT * FROM KebeleResident WHERE GivenDate = date('now', '-1 day')";
        }
        try (PreparedStatement pis = connection.prepareStatement(query)) {
            ResultSet resultSet = pis.executeQuery();
            while (resultSet.next()) {
                KebeleResident kebeleResident = new KebeleResident();
                kebeleResident.setResidentId(resultSet.getInt("ResidentID"));
                kebeleResident.setIdGivenDate(resultSet.getString("GivenDate"));
                kebeleResident.setIdExpDate(resultSet.getString("ExpDate"));
                kebeleResident.setExpStatus(resultSet.getInt("ExpirationStatus"));
                kebeleResidents.add(kebeleResident);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kebeleResidents;
    }
}