package com.ncarignan.buycheapthings;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {CheapThing.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract CheapThingDao cheapThingDao();
}
