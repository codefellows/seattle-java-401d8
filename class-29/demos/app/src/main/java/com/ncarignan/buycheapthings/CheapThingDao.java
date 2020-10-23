package com.ncarignan.buycheapthings;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao // Database Access Object - Controller for saving and retrieving and all REST on an android database
public interface CheapThingDao {

    @Insert
    public void saveTheThing(CheapThing cheapThing);

    @Query("SELECT * FROM CheapThing")
    public List<CheapThing> getAllCheapThings();

    @Query("SELECT * FROM CheapThing ORDER BY id DESC")
    public List<CheapThing> getAllCheapThingsReversed();

}
