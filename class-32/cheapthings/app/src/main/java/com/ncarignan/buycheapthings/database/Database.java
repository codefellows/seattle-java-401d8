package com.ncarignan.buycheapthings.database;

import androidx.room.RoomDatabase;

import com.amplifyframework.datastore.generated.model.CheapItem;

@androidx.room.Database(entities = {CheapItem.class}, version = 2)
public abstract class Database extends RoomDatabase {
    public abstract CheapItemDao cheapItemDao();
}
