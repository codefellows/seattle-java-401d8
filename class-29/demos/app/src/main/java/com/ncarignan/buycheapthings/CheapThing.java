package com.ncarignan.buycheapthings;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CheapThing {

    @PrimaryKey(autoGenerate = true)
    long id;

    public String thingName;
    public int quantity;
    public float price;

    public CheapThing(String thingName, int quantity, float price) {
        this.thingName = thingName;
        this.quantity = quantity;
        this.price = price;
    }
}
