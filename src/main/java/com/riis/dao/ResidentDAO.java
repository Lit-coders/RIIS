package com.riis.dao;

import com.riis.model.databasemodel.Resident;

public interface ResidentDAO {
    Resident getResidentNameByID(int id) throws Exception;
}
