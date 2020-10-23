package com.ncarignan.buycheapthings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

// TODO: clicking 4: implement the interface
public class Cart extends AppCompatActivity implements CheapThingAdapter.ClickOnCheapThingListener {

    Database database;
    // reason not to make this static in the main activity : what if we allow our app to accept intents from other apps
    // and we allow them to open other pages than "MainActivity"


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);

        database = Room.databaseBuilder(getApplicationContext(), Database.class, "ncarignan_cheap_things")
                .allowMainThreadQueries()
                .build();
        // TODO: don't pause the app

        ArrayList<CheapThing> cheapThings = (ArrayList<CheapThing>) database.cheapThingDao().getAllCheapThingsReversed();



        RecyclerView recyclerView = findViewById(R.id.buyableItemList);
        LinearLayoutManager l = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(new CheapThingAdapter(cheapThings, this));
    }

    @Override
    public void clickOnCheapThing(CheapThing cheapThing) {
        Intent intent = new Intent(Cart.this, CheapThingActivity.class);
        intent.putExtra("itemName", cheapThing.thingName);
        intent.putExtra("quantity", cheapThing.quantity);
        intent.putExtra("price", cheapThing.price);
        this.startActivity(intent);
    }
}