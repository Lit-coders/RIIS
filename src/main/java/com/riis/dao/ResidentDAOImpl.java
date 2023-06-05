package com.riis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.riis.database.DatabaseConnection;
import com.riis.model.databasemodel.Resident;

public class ResidentDAOImpl implements ResidentDAO {
    @Override
    public Resident getResidentNameByID(int id) throws Exception {
        Resident resident = new Resident();

        Connection connection = DatabaseConnection.getInstance();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM Resident WHERE ResidentID = ?");
        ps.setInt(1, id);
        ResultSet resultSet = ps.executeQuery();

        while(resultSet.next()) {
            resident.setName(resultSet.getString("ResidentName"));
        }
        return resident;
    }
}
