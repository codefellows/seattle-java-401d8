package com.ncarignan.buycheapthings.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.amplifyframework.datastore.generated.model.CheapItem;

import java.util.List;

@Dao
public interface CheapItemDao {

    @Insert
    public void saveTheThing(CheapItem cheapItem);

    @Query("SELECT * FROM CheapItem")
    public List<CheapItem> getAllCheapThings();

    @Query("SELECT * FROM CheapItem ORDER BY id DESC")
    public List<CheapItem> getAllCheapThingsReversed();
}
