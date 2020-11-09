package com.ncarignan.potions.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Switch;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Potion;
import com.ncarignan.potions.R;

public class PotionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potion_detail);

        Intent intentFromOtherPage = getIntent();
        ((TextView) findViewById(R.id.textViewPotionName)).setText(intentFromOtherPage.getStringExtra("potionName"));
        ((TextView) findViewById(R.id.textViewPotionColor)).setText(intentFromOtherPage.getStringExtra("potionColor"));
        ((Switch) findViewById(R.id.switchPotionSuccess)).setChecked(intentFromOtherPage.getBooleanExtra("potionSuccess", false));
    }

    @Override
    protected void onPause() {
        super.onPause();
        Potion[] oldPotions = new Potion[1];
        Amplify.API.query(
                ModelQuery.get(Potion.class, getIntent().getStringExtra("potionId")),
                success -> {
                    oldPotions[0] = success.getData();
                    Potion oldPotion = oldPotions[0];
                    oldPotion.name = ((TextView) findViewById(R.id.textViewPotionName)).getText().toString();
                    oldPotion.color = ((TextView) findViewById(R.id.textViewPotionColor)).getText().toString();
                    oldPotion.success = ((Switch) findViewById(R.id.switchPotionSuccess)).isChecked();
                    Amplify.API.mutate(
                            ModelMutation.update(oldPotion),
                            updated -> Log.i(MainActivity.TAG, "updated successfully"),
                            failed -> Log.e(MainActivity.TAG, failed.toString()));
                },
                failure -> {Log.e(MainActivity.TAG, failure.toString());}
        );


    }
}