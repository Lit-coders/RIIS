package com.riis.dao;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    @Override
    public InputStream getResidentImageByID(int rid) throws SQLException {
        byte[] imgBytes = null;
        Connection connection = DatabaseConnection.getInstance();
        String query = "SELECT ResidentPhoto FROM Resident WHERE ResidentID = ?";
        try (PreparedStatement pis = connection.prepareStatement(query);) {
            pis.setInt(1, rid);

            ResultSet resultSet = pis.executeQuery();
            while (resultSet.next()) {
                imgBytes = resultSet.getBytes("ResidentPhoto");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(imgBytes == null) return null;

        InputStream inputStream = new ByteArrayInputStream(imgBytes);

        return inputStream;
    }

    @Override
    public String getGender(int rid) throws Exception {
        String sex = "";
        Connection connection = DatabaseConnection.getInstance();
        String query = "SELECT Sex FROM Resident WHERE ResidentID = ?";
        try (PreparedStatement pis = connection.prepareStatement(query)) {
            pis.setInt(1, rid);
            ResultSet resultSet =  pis.executeQuery();
            while(resultSet.next()) {
                sex = resultSet.getString("Sex");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sex;
    }
    
    @Override
    public int addResident(Resident resident) throws Exception {
        int rid = 0;
        Connection connection = DatabaseConnection.getInstance();
        String query = "INSERT INTO Resident (Name, FName, GFName, DOB, POB, PhoneNumber, MotherName, Sex, Citizenship, MaritalStatus, Job, BloodType, HouseNumber, ECF, ECP, ResidentPhoto) " +
               "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pis = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pis.setString(1, resident.getName());
            pis.setString(2, resident.getFName());
            pis.setString(3, resident.getGFName());
            pis.setString(4, resident.getDOB());
            pis.setString(5, resident.getPOB());
            pis.setString(6, resident.getPhoneNumber());
            pis.setString(7, resident.getMName());
            pis.setString(8, resident.getSex());
            pis.setString(9, resident.getCitizenship());
            pis.setString(10, resident.getMaritalStatus());
            pis.setString(11, resident.getJob());
            pis.setString(12, resident.getBType());
            pis.setString(13, resident.getHouseNumber());
            pis.setString(14, resident.getECF());
            pis.setString(15, resident.getECP());
            pis.setBytes(16, resident.getResidentImage());

            pis.executeUpdate();
            ResultSet resultSet = pis.getGeneratedKeys();
            if(resultSet.next()) {
                rid = resultSet.getInt(1);
            } else {
                throw new Exception("Resident ID not generated");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rid;
    }
}
