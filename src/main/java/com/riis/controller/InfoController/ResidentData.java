package com.riis.controller.InfoController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import com.riis.database.DatabaseConnection;
import com.riis.model.databasemodel.ExpId;
import com.riis.model.databasemodel.Resident;

import javafx.scene.control.TextField;

public class ResidentData {

    public static ArrayList<Resident> getResidentData(String sql) {
        ArrayList<Resident> residents = new ArrayList<>();
        try (Statement st = DatabaseConnection.getInstance().createStatement()) {
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                Resident resident = new Resident(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(8), rs.getString(7));
                residents.add(resident);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return residents;
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
        String sql = "SELECT ResidentId FROM Resident WHERE FName = '" + residentForm.get(0).getText() + "' AND FName = '" + residentForm.get(1).getText() + "' AND GFName = '" + residentForm.get(2).getText() + "' AND HouseNumber = '" + residentForm.get(3).getText() + "'";
        try (Connection connection = DatabaseConnection.getInstance()) {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            addNewResident(rs.getInt(1));
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error during retrieving the newly added resident's id!");
            e.printStackTrace();
        }
    }

	private static void addNewResident(int id) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd, yyyy");
        String idGivenDate = today.format(formatter);
        String idExpDate = (today.plusYears(3)).format(formatter);
        int expStatus = 0; // unExpired
        String sql = "INSERT INTO KebeleResidentID VALUES('" + id + "', '" + idGivenDate + "', '" + idExpDate + "', '" + expStatus + "')";
        try (Connection con = DatabaseConnection.getInstance()) {
            Statement st = con.createStatement();
            st.executeUpdate(sql);
        } catch (Exception e) {
            e.getStackTrace();
        }
	}

    private static void checkIdExpiration(){
        String sql = "SELECT * FROM KebeleResidentID";
        try (Connection con = DatabaseConnection.getInstance()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String idGivenDate = rs.getString(2);
                String idExpDate = rs.getString(3);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd, yyyy");
                LocalDate givenDate = LocalDate.parse(idGivenDate, formatter);
                LocalDate expDate = LocalDate.parse(idExpDate, formatter);
                double numYear = ChronoUnit.YEARS.between(givenDate, expDate);
                if(numYear >= 3){
                    String updateSql = "UPDATE KebeleResidentID SET ExpirationStatus = 1 ";
                    st.executeUpdate(updateSql);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
