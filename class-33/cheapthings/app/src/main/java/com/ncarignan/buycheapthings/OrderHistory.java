package com.ncarignan.buycheapthings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.amplifyframework.datastore.generated.model.CheapItem;

import java.util.ArrayList;

public class OrderHistory extends AppCompatActivity implements CheapThingAdapter.ClickOnCheapThingListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        ArrayList<CheapItem> orderHistory = new ArrayList<>();




        RecyclerView recyclerView = findViewById(R.id.order_history);
        LinearLayoutManager l = new LinearLayoutManager(this);

        l.canScrollHorizontally();
        l.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(l);
        recyclerView.setAdapter(new CheapThingAdapter(orderHistory, this));
    }

    @Override
    public void clickOnCheapThing(CheapItem cheapThing) {
        System.out.println("I am so glad I bought that " + cheapThing.thingName);
    }
}