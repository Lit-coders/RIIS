package com.riis.dao;

import java.util.List;

import com.riis.model.databasemodel.Resident;

public interface ResidentDAO {
    Resident getResidentNameByID(int id) throws Exception;
    List<Resident> getAllResidents() throws Exception;      
    Resident getResidentByID(int id) throws Exception;
}
