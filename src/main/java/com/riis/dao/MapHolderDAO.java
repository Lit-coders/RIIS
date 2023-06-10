package com.riis.dao;

import com.riis.model.databasemodel.MapHolder;

public interface MapHolderDAO {
    public int addMapHolder(MapHolder mapHolder) throws Exception;
    public void addResidentMapHolder(int residentId, int mapHolderId) throws Exception;
}