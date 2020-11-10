package com.ncarignan.potions.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ncarignan.potions.R;

public class ReceiveOwlMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_owl_message);

        Intent intent = getIntent();
        Log.i(MainActivity.TAG, intent.getType());
        if(intent.getType().equals("text/plain")){
            String owlMessage = intent.getStringExtra(Intent.EXTRA_TEXT);

            Log.i(MainActivity.TAG +".Intent", owlMessage);
            ((TextView) findViewById(R.id.textViewOwl)).setText(owlMessage);
        } else {
            Log.i(MainActivity.TAG, "got a picture");
            ((TextView) findViewById(R.id.textViewOwl)).setText("thanks for the picture");
        }


    }
}