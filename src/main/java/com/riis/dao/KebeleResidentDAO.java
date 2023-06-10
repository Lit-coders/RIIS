package com.riis.dao;

import java.sql.SQLException;
import java.util.List;

import com.riis.model.databasemodel.KebeleResident;
import com.riis.model.databasemodel.Resident;

public interface KebeleResidentDAO {
    List<KebeleResident> getAllKebeleResidents() throws Exception;
    KebeleResident getKebeleResidentByRID(int rid) throws Exception;
    Resident getKebeleResidentByID(int kid) throws Exception;
    KebeleResident getKebeleResidentByKID(int kid) throws Exception;
    List<KebeleResident> getAllKebeleResidentsByDate(String date) throws Exception;
    void addKebeleResident(int id) throws Exception;
    void updateKebeleResident(int id) throws Exception;
    boolean checkIdExpirationStatus(int rid) throws Exception;
    boolean checkIfRequestExists(int rid) throws Exception;
    List<KebeleResident> getKebeleResidentsByToken(String token) throws SQLException;
}