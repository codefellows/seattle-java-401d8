package com.ncarignan.sortinghat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

public class SignupConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_confirmation);

        ((Button) findViewById(R.id.submitConfirmation)).setOnClickListener(view -> {
            String username = ((TextView) findViewById(R.id.editTextUsernameConfirmation)).getText().toString();
            String verificationCode = ((TextView) findViewById(R.id.editTextVerificationCode)).getText().toString();

            Amplify.Auth.confirmSignUp(
                    username,
                    verificationCode,
                    result -> {
                        Log.i("Amplify.confirm", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete");
                        startActivity(new Intent(SignupConfirmationActivity.this, SigninActivity.class));
                    },
                    error -> Log.e("Amplify.confirm", error.toString())
            );
        });


    }
}