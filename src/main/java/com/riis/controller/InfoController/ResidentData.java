package com.riis.controller.InfoController;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.riis.database.DatabaseConnection;
import com.riis.model.databasemodel.ExpId;
import com.riis.model.databasemodel.Resident;

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

    public static ExpId getExpResidentIdData(int id) {
        String sql = "SELECT * FROM KebeleResidentID WHERE ResidentID = '" + id + "'";
        try (Statement st = DatabaseConnection.getInstance().createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            ExpId expId = new ExpId(rs.getInt(1), rs.getString(2), rs.getString(3));
            return expId;
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
}
