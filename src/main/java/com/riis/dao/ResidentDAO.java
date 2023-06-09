package com.riis.dao;

import java.io.InputStream;
import java.util.List;

import com.riis.model.databasemodel.Resident;

public interface ResidentDAO {
    Resident getResidentNameByID(int id) throws Exception;
    List<Resident> getAllResidents() throws Exception;      
    Resident getResidentByID(int id) throws Exception;
    InputStream getResidentImageByID(int id) throws Exception;
    String getGender(int id) throws Exception;
}
