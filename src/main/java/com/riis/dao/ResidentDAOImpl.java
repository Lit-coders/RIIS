package com.riis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.riis.database.DatabaseConnection;
import com.riis.model.databasemodel.Resident;

public class ResidentDAOImpl implements ResidentDAO {
    @Override
    public Resident getResidentNameByID(int id) throws Exception {
        Resident resident = new Resident();

        Connection connection = DatabaseConnection.getInstance();
        PreparedStatement ps = connection.prepareStatement("SELECT Name,FName FROM Resident WHERE ResidentID = ?");
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            resident.setName(resultSet.getString("Name"));
            resident.setFName(resultSet.getString("FName"));
        }
        return resident;
    }

    @Override
    public List<Resident> getAllResidents() throws Exception {
        List<Resident> residents = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance();
        String query = "SELECT * FROM Resident";
        try (PreparedStatement pis = connection.prepareStatement(query);
                ResultSet resultSet = pis.executeQuery()) {
            while (resultSet.next()) {
                Resident resident = new Resident();

                resident.setResidentId(resultSet.getInt("ResidentID"));
                resident.setName(resultSet.getString("Name"));
                resident.setFName(resultSet.getString("FName"));
                resident.setGFName(resultSet.getString("GFName"));
                resident.setDOB(resultSet.getString("DOB"));
                resident.setPOB(resultSet.getString("POB"));
                resident.setPhoneNumber(resultSet.getString("PhoneNumber"));
                resident.setMName(resultSet.getString("MotherName"));
                resident.setSex(resultSet.getString("Sex"));
                resident.setCitizenship(resultSet.getString("Citizenship"));
                resident.setMaritalStatus(resultSet.getString("MaritalStatus"));
                resident.setJob(resultSet.getString("Job"));
                resident.setBType(resultSet.getString("BloodType"));
                resident.setHouseNumber(resultSet.getString("HouseNumber"));
                resident.setECF(resultSet.getString("ECF"));
                resident.setECP(resultSet.getString("ECP"));

                residents.add(resident);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return residents;
    }

    @Override
    public Resident getResidentByID(int rid) throws SQLException {
        Resident resident = new Resident();
        Connection connection = DatabaseConnection.getInstance();
        String query = "SELECT * FROM Resident WHERE ResidentID = ?";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            pis.setInt(1, rid);

            ResultSet resultSet = pis.executeQuery();
            while (resultSet.next()) {
                resident.setResidentId(resultSet.getInt("ResidentID"));
                resident.setName(resultSet.getString("Name"));
                resident.setFName(resultSet.getString("FName"));
                resident.setGFName(resultSet.getString("GFName"));
                resident.setDOB(resultSet.getString("DOB"));
                resident.setPOB(resultSet.getString("POB"));
                resident.setPhoneNumber(resultSet.getString("PhoneNumber"));
                resident.setMName(resultSet.getString("MotherName"));
                resident.setSex(resultSet.getString("Sex"));
                resident.setCitizenship(resultSet.getString("Citizenship"));
                resident.setMaritalStatus(resultSet.getString("MaritalStatus"));
                resident.setJob(resultSet.getString("Job"));
                resident.setBType(resultSet.getString("BloodType"));
                resident.setHouseNumber(resultSet.getString("HouseNumber"));
                resident.setECF(resultSet.getString("ECF"));
                resident.setECP(resultSet.getString("ECP"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resident;
    }

}
