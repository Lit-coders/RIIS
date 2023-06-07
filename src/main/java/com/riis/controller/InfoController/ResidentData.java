package com.riis.controller.InfoController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.riis.database.DatabaseConnection;
import com.riis.model.databasemodel.ExpId;
import com.riis.model.databasemodel.Request;
import com.riis.model.databasemodel.Resident;

import javafx.scene.control.TextField;

public class ResidentData {

    public static ArrayList<Resident> getResidentData(String sql) {
        ArrayList<Resident> residents = new ArrayList<>();
        try (Statement st = DatabaseConnection.getInstance().createStatement()) {
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                String photoPath = "/com/riis/images/" + rs.getInt(1) + ".png";
                retrieveResidentPhoto(rs.getBytes(17), rs.getInt(1));
                Resident resident = new Resident(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), photoPath);
                residents.add(resident);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return residents;
    }

    private static void retrieveResidentPhoto(byte[] bytes, int rid) {
        String photoPath = "src/main/resources/com/riis/images/" + rid + ".png";
        byte[] imgByte = bytes;
        File file = new File(photoPath);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            if (imgByte != null) {
                fos.write(imgByte);
            } else {
                System.out.println("image byte data is null!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ExpId> getExpResidentIdData(String sql) {
        ArrayList<ExpId> expIds = new ArrayList<>();
        try (Statement st = DatabaseConnection.getInstance().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                ExpId expId = new ExpId(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));
                expIds.add(expId);
            }
            return expIds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String InsertResidentData(String sql) {
        String response = "This Resident Is Not Added!";
        try (Statement st = DatabaseConnection.getInstance().createStatement()) {
            st.executeUpdate(sql);
            response = "The Resident Is Added Successfully!";
            return response;
        } catch (Exception e) {
            response = "There is Something Wrong with your database.";
        }
        return response;
    }

    public static void getNewResidentId(ArrayList<TextField> residentForm) {
        String sql = "SELECT ResidentId FROM Resident WHERE FName = '" + residentForm.get(0).getText() + "' AND FName = '" + residentForm.get(1).getText() + "' AND GFName = '" + residentForm.get(2).getText() + "' AND HouseNumber = '" + residentForm.get(11).getText() + "'";
        try (Connection connection = DatabaseConnection.getInstance()) {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            addNewResident(rs.getInt(1));
        } catch ( SQLException e) {
            System.out.println("Error during retrieving the newly added resident's id!");
            e.printStackTrace();
        }
    }

	private static void addNewResident(int id) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd, yyyy");
        String idGivenDate = today.format(formatter);
        String idExpDate = (today.plusYears(3)).format(formatter);
        String sql = "INSERT INTO KebeleResidentID VALUES(" + id + ", '" + idGivenDate + "', '" + idExpDate + "', 0)";
        try (Connection con = DatabaseConnection.getInstance()) {
            Statement st = con.createStatement();
            st.executeUpdate(sql);
        } catch (Exception e) {
            e.getStackTrace();
        }
	}

    // private static void checkIdExpiration(){
    //     String sql = "SELECT * FROM KebeleResidentID";
    //     try (Connection con = DatabaseConnection.getInstance()) {
    //         Statement st = con.createStatement();
    //         ResultSet rs = st.executeQuery(sql);
    //         while(rs.next()){
    //             String idGivenDate = rs.getString(2);
    //             String idExpDate = rs.getString(3);
    //             DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd, yyyy");
    //             LocalDate givenDate = LocalDate.parse(idGivenDate, formatter);
    //             LocalDate expDate = LocalDate.parse(idExpDate, formatter);
    //             double numYear = ChronoUnit.YEARS.between(givenDate, expDate);
    //             if(numYear >= 3){
    //                 String updateSql = "UPDATE KebeleResidentID SET ExpirationStatus = 1 ";
    //                 st.executeUpdate(updateSql);
    //             }
    //         }
    //     } catch (Exception e) {
    //         e.getStackTrace();
    //     }
    // }

    // update request table

    public static boolean addRequest(String sql){
        try (Connection con = DatabaseConnection.getInstance()) {
            Statement st = con.createStatement();
            st.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static Request getRequestData(String sql, int id) {
        try (Connection con = DatabaseConnection.getInstance()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            if(rs.getInt(2) == id){
                Request request = new Request();
                request.setRequestDate(rs.getString(7));
                return request;
            } else {
                Request request = new Request();
                request.setResidentID(11);
                return request;
            }
        } catch (Exception e) {
            System.out.println("Requesting : Database error!");
        }
        return null;
    }
}
