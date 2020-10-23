package com.ncarignan.buycheapthings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;

public class OrderHistory extends AppCompatActivity implements CheapThingAdapter.OnInteractWithCheapThingListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        ArrayList<CheapThing> orderHistory = new ArrayList<>();
        orderHistory.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        orderHistory.add(new CheapThing("Book", 1, 3.00f));

        orderHistory.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        orderHistory.add(new CheapThing("Book", 1, 3.00f));

        orderHistory.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        orderHistory.add(new CheapThing("Book", 1, 3.00f));

        orderHistory.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        orderHistory.add(new CheapThing("Book", 1, 3.00f));
        orderHistory.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        orderHistory.add(new CheapThing("Book", 1, 3.00f));

        orderHistory.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        orderHistory.add(new CheapThing("Book", 1, 3.00f));

        orderHistory.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        orderHistory.add(new CheapThing("Book", 1, 3.00f));

        orderHistory.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        orderHistory.add(new CheapThing("Book", 1, 3.00f));
        orderHistory.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        orderHistory.add(new CheapThing("Book", 1, 3.00f));

        orderHistory.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        orderHistory.add(new CheapThing("Book", 1, 3.00f));

        orderHistory.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        orderHistory.add(new CheapThing("Book", 1, 3.00f));

        orderHistory.add(new CheapThing("Chocolate Bar", 30, 1.20f));
        orderHistory.add(new CheapThing("Book", 1, 3.00f));



        RecyclerView recyclerView = findViewById(R.id.order_history);
        LinearLayoutManager l = new LinearLayoutManager(this);
        l.canScrollHorizontally();
        l.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(new CheapThingAdapter(orderHistory, this));
    }

    @Override
    public void cheapThingListener(CheapThing cheapThing) {
        System.out.println("I am so glad I bought that " + cheapThing.thingName);
    }
}