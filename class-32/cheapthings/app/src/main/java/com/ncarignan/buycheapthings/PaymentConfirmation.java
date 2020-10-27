package com.ncarignan.buycheapthings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PaymentConfirmation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirmation);

        Intent intent = getIntent();
        System.out.println(intent.getExtras().getString("name"));
        System.out.println(intent.getExtras().getInt("quantity"));
        System.out.println(intent.getExtras().getFloat("price"));

        TextView itemNameView = PaymentConfirmation.this.findViewById(R.id.item_name);
        itemNameView.setText(intent.getExtras().getString("name"));
    }
}