package com.riis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.riis.database.DatabaseConnection;
import com.riis.model.databasemodel.MapHolder;


public class MapHolderDAOImpl implements MapHolderDAO {
    @Override
    public int addMapHolder(MapHolder mapHolder) throws SQLException {
        int rid = 0;
        Connection connection = DatabaseConnection.getInstance();
        String query = "INSERT INTO MapHolder (MapHolderName, MapHolderFName, MapHolderGFName, MapHolderPhoneNum, MapHolderPhoto) " +
               "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pis = connection.prepareStatement(query)) {
            pis.setString(1, mapHolder.getMapHolderName());
            pis.setString(2, mapHolder.getMapHolderFName());
            pis.setString(3, mapHolder.getMapHolderGFName());
            pis.setString(4, mapHolder.getMapHolderPhoneNum());
            pis.setBytes(5, mapHolder.getMapHolderPhoto());

            pis.executeUpdate();
            ResultSet resultSet = pis.getGeneratedKeys();
            if (resultSet.next()) {
                rid = resultSet.getInt(1);
            } else {
                throw new SQLException("Map Holder ID not generated");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rid;
    }

    @Override
    public void addResidentMapHolder(int residentId, int mapHolderId) throws SQLException {
        Connection connection = DatabaseConnection.getInstance();
        String query = "INSERT INTO Resident_MapHolder (RID, MID) " +
                "VALUES (?, ?)";
        try (PreparedStatement pis = connection.prepareStatement(query)) {
            pis.setInt(1, residentId);
            pis.setInt(2, mapHolderId);

            pis.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}