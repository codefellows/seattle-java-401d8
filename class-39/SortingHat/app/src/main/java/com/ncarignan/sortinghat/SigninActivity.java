package com.ncarignan.sortinghat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

public class SigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        ((Button) findViewById(R.id.buttonSignin)).setOnClickListener(view ->{
            String username = ((TextView) findViewById(R.id.editTextTextusernameSignin)).getText().toString();
            String password = ((TextView) findViewById(R.id.editTextPasswordSignin)).getText().toString();

            Amplify.Auth.signIn(
                    username,
                    password,
                    result -> {
                        Log.i("Amplify.login", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                        startActivity(new Intent(SigninActivity.this, MainActivity.class));
                        },
                    error -> Log.e("Amplify.login", error.toString())
            );
        });


    }
}