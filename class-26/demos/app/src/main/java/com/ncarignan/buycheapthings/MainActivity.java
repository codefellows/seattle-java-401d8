package com.ncarignan.buycheapthings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // element by id
        // click/type
        // callback
        Button buyButton = MainActivity.this.findViewById(R.id.buybutton);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText itemNameInput = MainActivity.this.findViewById(R.id.editItemName);
                EditText itemQuantityInput = MainActivity.this.findViewById(R.id.editTextQuantity);
                EditText itemPriceInput = MainActivity.this.findViewById(R.id.editTextPriceDecimal);
                String itemName = itemNameInput.getText().toString();
                int quantity = Integer.parseInt(itemQuantityInput.getText().toString());
                float price = Float.parseFloat(itemPriceInput.getText().toString());
                System.out.println(String.format("We are going to buy %d %ss for $%f", quantity, itemName, price));

                Intent goToPaymentConfirmation = new Intent(MainActivity.this, PaymentConfirmation.class);
                goToPaymentConfirmation.putExtra("name", itemName);
                goToPaymentConfirmation.putExtra("quantity", quantity);
                goToPaymentConfirmation.putExtra("price", price);
                MainActivity.this.startActivity(goToPaymentConfirmation);
            }
        });

        Button goToCartButton = MainActivity.this.findViewById(R.id.visit_cart);
        goToCartButton.setOnClickListener((view) -> {
            System.out.println("going to cart");
            // going to another app is called an intent
            // its generic, it gets handled by something else
            // explicit (internal to the app or the domain (send to a specific page we control)
            // implicit (goes anywhere, tells android the things we want to do, android opens a choice box of everything registered that can do it)

//            Intent shareToPhoneIntent = new Intent(Intent.ACTION_SEND);
//            shareToPhoneIntent.putExtra(Intent.EXTRA_TEXT, "Sharing this to the phone y'all!");
//            // I forgot this last time
//            shareToPhoneIntent.setType("text/plain");
//            MainActivity.this.startActivity(Intent.createChooser(shareToPhoneIntent, "where do you want to send this message,huh?"));

            Intent goToCartIntent = new Intent(MainActivity.this, Cart.class);
            MainActivity.this.startActivity(goToCartIntent);
        });

    }

    // onCreate is one of six LifeCycle methods we can override.
    // onCreate gets called at a specific time:
    // when we first view the home page (when the app opens)
}