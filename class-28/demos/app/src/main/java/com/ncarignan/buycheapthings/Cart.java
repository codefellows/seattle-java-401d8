package com.ncarignan.buycheapthings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

// TODO: clicking 4: implement the interface
public class Cart extends AppCompatActivity implements CheapThingAdapter.OnInteractWithCheapThingListener{

    // Adapter's job is handling the connection between data and the view and handling all movement and interactivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart2);

        ArrayList<CheapThing> cheapThings = new ArrayList<>();
        cheapThings.add(new CheapThing("Second Hand Radiator", 1, 25.00f));
        cheapThings.add(new CheapThing("Headset", 9, 36.00f));
        cheapThings.add(new CheapThing("Lollipop", 100, 5.00f));
        cheapThings.add(new CheapThing("Espresso Maker", 2, 45.00f));
        cheapThings.add(new CheapThing("Jelly Beans", 3, 0.30f));
        cheapThings.add(new CheapThing("Microphone", 15, 65.00f));
        cheapThings.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        cheapThings.add(new CheapThing("Book", 1, 3.00f));
        cheapThings.add(new CheapThing("Second Hand Radiator", 1, 25.00f));
        cheapThings.add(new CheapThing("Headset", 9, 36.00f));
        cheapThings.add(new CheapThing("Lollipop", 100, 5.00f));
        cheapThings.add(new CheapThing("Espresso Maker", 2, 45.00f));
        cheapThings.add(new CheapThing("Jelly Beans", 3, 0.30f));
        cheapThings.add(new CheapThing("Microphone", 15, 65.00f));
        cheapThings.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        cheapThings.add(new CheapThing("Book", 1, 3.00f));
        cheapThings.add(new CheapThing("Second Hand Radiator", 1, 25.00f));
        cheapThings.add(new CheapThing("Headset", 9, 36.00f));
        cheapThings.add(new CheapThing("Lollipop", 100, 5.00f));
        cheapThings.add(new CheapThing("Espresso Maker", 2, 45.00f));
        cheapThings.add(new CheapThing("Jelly Beans", 3, 0.30f));
        cheapThings.add(new CheapThing("Microphone", 15, 65.00f));
        cheapThings.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        cheapThings.add(new CheapThing("Book", 1, 3.00f));

        RecyclerView recyclerView = findViewById(R.id.buyableItemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // TODO: 0 create and reference and setup a recyclerview
        recyclerView.setAdapter(new CheapThingAdapter(cheapThings, this));
        // TODO: 5
    }

    // TODO: clicking 5 : put content in the method
    @Override
    public void cheapThingListener(CheapThing cheapThing) {
        Intent intent = new Intent(Cart.this, CheapThingActivity.class);
        intent.putExtra("itemName", cheapThing.thingName);
        intent.putExtra("quantity", cheapThing.quantity);
        intent.putExtra("price", cheapThing.price);
        this.startActivity(intent);
    }
}