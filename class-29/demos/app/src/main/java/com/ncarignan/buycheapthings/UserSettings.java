package com.ncarignan.buycheapthings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Toast;

public class UserSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);// getter
        SharedPreferences.Editor preferenceEditor = preferences.edit();

        findViewById(R.id.saveButton).setOnClickListener((view) -> {
            EditText address = findViewById(R.id.editTextTextPostalAddress);
            preferenceEditor.putString("addressPotato", address.getText().toString());
            preferenceEditor.apply();

            Toast toast = Toast.makeText(this, "You saved your address", Toast.LENGTH_LONG);

            toast.show();
        });

        findViewById(R.id.goHome).setOnClickListener((view) -> {
            Intent intent = new Intent(UserSettings.this, MainActivity.class);
            UserSettings.this.startActivity(intent);
                }
        );
        // its called shared `preferences` but it is not a settings file, its just local storage for phones
        // naming is hard

        // it is a little different than local storage in that the phone creates 3 kinds of shared preferences
        // one for every activity - more specific than localStorage on the web
        // one for every app - exactly like local storage
        // create named preferences files, supposed to be only accessible from your domain

    }
}