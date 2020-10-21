package com.ncarignan.buycheapthings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CheapThingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheap_thing);

        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        int quantity = intent.getIntExtra("quantity", 0);
        float price = intent.getFloatExtra("price", 0);

        TextView name = findViewById(R.id.detailItemName);
        name.setText(itemName);
        TextView quantityView = findViewById(R.id.detailItemQuantity);
        quantityView.setText(Integer.toString(quantity));
        TextView priceView = findViewById(R.id.detailItemPrice);
        priceView.setText(Float.toString(price));
    }
}