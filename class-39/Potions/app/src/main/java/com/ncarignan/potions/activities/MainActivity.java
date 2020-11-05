
package com.ncarignan.potions.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Potion;
import com.amplifyframework.datastore.generated.model.Student;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.ncarignan.potions.R;
import com.ncarignan.potions.adapters.PotionAdapter;

import java.util.ArrayList;


// What to track for a user
/*
How long they spend on the app, on the page
Where they go, where can we monopolize adds
Last page before the app closes
What they are typing

*/






public class MainActivity extends AppCompatActivity implements PotionAdapter.CanClickOnFragment {
    public RecyclerView recyclerView;
    public ArrayList<Potion> potions = new ArrayList<>();
    public ArrayList<Student> students = new ArrayList<>();

    public static String TAG = "hogwarts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureAws();
        setupRecyclerView();
        addListenersToButtons();
//        getStudentsFromAWS();
        addMockStudents();

    }

    public void addMockStudents(){
        Amplify.API.mutate(
                ModelMutation.create(Student.builder().name("Young Hagrid").build()),
                success -> Log.i(TAG, success.toString()),
                error -> Log.e(TAG, error.toString()));
    }

    public void getStudentsFromAWS(){
//        Amplify.API.query(
//                ModelQuery.list(Student.class),
//                success -> {
//                    Log.i(TAG, "success " + success.toString());
////                    for(Student s : success.getData()){
////                        Log.i(TAG, "student retrieved :" + s.getName());
////                    }
//                    },
//                failure -> Log.e(TAG, failure.toString())
//        );
    }

    public void handleBrewPotion(){

    }
    public void handleEnrollStudent(){
        String name = ((TextView) findViewById(R.id.editTextTextNewStudentName)).getText().toString();
        Amplify.API.mutate(
                ModelMutation.create( Student.builder().name(name).build()),
                success -> {Log.i(TAG +".addStudent", "success");},
                failure -> {Log.e(TAG +".addStudent", failure.toString());}
        );
    }

    public void addListenersToButtons(){
        ((Button) findViewById(R.id.buttonLogin)).setOnClickListener(view -> {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
                });

        ((Button) findViewById(R.id.buttonSignup)).setOnClickListener(view -> {
            Intent i = new Intent(this, SignupActivity.class);
            startActivity(i);
        });

        ((Button) findViewById(R.id.buttonEnroll)).setOnClickListener(view -> { handleEnrollStudent();});

        ((Button) findViewById(R.id.buttonBrewPotion)).setOnClickListener(view -> { handleBrewPotion();});

        ((ImageButton) findViewById(R.id.imageButtonNextStudent)).setOnClickListener(view -> {
            Log.i(TAG, "next student");
        });

        ((ImageButton) findViewById(R.id.imageButtonPreviousStudent)).setOnClickListener(view -> {
            Log.i(TAG, "previous student");
        });
    }

    public void setupRecyclerView(){
//        potions.add(Potion.builder().name("polyjuice").color("green").success(false).build());
//        potions.add(Potion.builder().name("firebreathing").color("red").success(true).build());
//        potions.add(Potion.builder().name("felix felicis").color("tabby orange").success(true).build());

        recyclerView = findViewById(R.id.potions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PotionAdapter(potions, this));
    }

    public void configureAws(){
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
        } catch (AmplifyException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data);
        }
    }

    @Override
    public void handleClickOnFragment(Potion potion) {
        Intent i = new Intent(this, PotionDetailActivity.class);
        // add extras
        i.putExtra("potionName", potion.getName());
        i.putExtra("potionColor", potion.getColor());
        i.putExtra("potionSuccess", potion.getSuccess());
        i.putExtra("potionId", potion.getId());
        startActivity(i);
    }
}