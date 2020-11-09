
package com.ncarignan.potions.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
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
    public int studentIndex = -1;
    public String targetStudentNameFromIntent = "";
    Handler handleRecyclerViewUpdate;

    public static String TAG = "hogwarts";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handleRecyclerViewUpdate = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                ((TextView) findViewById(R.id.textViewStudentDisplayedName))
                        .setText(students.get(studentIndex).getName());
                updateRecyclerView();
                return false;
            }
        });

        configureAws();
        setupRecyclerView();
        addListenersToButtons();
        getStudentsFromAWS();
//        addMockStudents();
        parseIntentFilter();



    }

    public void updateRecyclerView(){
        potions.clear();
        for(Potion p : students.get(studentIndex).potions){
            potions.add(p);
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public void parseIntentFilter(){
        Intent intent = getIntent();

        if(intent.getType() != null && intent.getType().equals("text/plain")){

            targetStudentNameFromIntent = intent.getStringExtra(Intent.EXTRA_TEXT);
            Log.i(TAG, "searching for " + targetStudentNameFromIntent);
            int counter = 0;
            for(Student s : students){
                if(s.name.toLowerCase().equals(targetStudentNameFromIntent.toLowerCase())){
                    Log.i(TAG, "found student match from intent filter");
                    studentIndex = counter;
                    updateRecyclerView();
                }
                counter ++;
            }
        }
    }

    public void addMockStudents(){
        Amplify.API.mutate(
                ModelMutation.create(Student.builder().name("Young Hagrid").build()),
                success -> Log.i(TAG, success.toString()),
                error -> Log.e(TAG, error.toString()));
    }

    public void getStudentsFromAWS(){
        Amplify.API.query(
                ModelQuery.list(Student.class),
                success -> {
                    Log.i(TAG, "success " + success.toString());
                    for(Student s : success.getData()){
                        Log.i(TAG, "student retrieved :" + s.getName());
                        students.add(s);
                    }

                    int counter = 0;
                    for(Student s : students){
                        if(s.name.toLowerCase().equals(targetStudentNameFromIntent.toLowerCase())){
                            Log.i(TAG, "found student match from intent filter");
                            studentIndex = counter;
                            handleRecyclerViewUpdate.sendEmptyMessage(1);
                        }
                        counter ++;
                    }
                    },
                failure -> Log.e(TAG, failure.toString())
        );
    }

    public void handleBrewPotion(){
        // need the amplify class / builder check
        // need to reference the TextEdits in the xml
        String name = ((TextView) findViewById(R.id.editTextTextPotionName)).getText().toString();
        String color = ((TextView) findViewById(R.id.editTextTextPotionColor)).getText().toString();
        boolean success = ((Switch) findViewById(R.id.switchIsSuccess)).isChecked();
        // put name, color, success through builder
        Potion potion = Potion.builder().name(name).color(color).success(success).student(students.get(studentIndex)).build();
        // TODO: pick a student who is in the recyclerview
        // IF I am just on the "all students" tab, use the first student

        // upload it to amplify - Amplify.API.mutate(mutator, success, failure)
        Amplify.API.mutate(ModelMutation.create(potion),
                win -> Log.i(TAG, "uploaded potion"),
                error -> Log.e(TAG, error.toString()));
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
            i.putExtra("cool", "beans");
            startActivity(i);
        });

        ((Button) findViewById(R.id.buttonEnroll)).setOnClickListener(view -> { handleEnrollStudent();});

        ((Button) findViewById(R.id.buttonBrewPotion)).setOnClickListener(view -> { handleBrewPotion();});

        ((ImageButton) findViewById(R.id.imageButtonNextStudent)).setOnClickListener(view -> {
            Log.i(TAG, "next student Not actually but yeah");
            if(students.size() -1 >= studentIndex + 1){
                studentIndex++;
                ((TextView) findViewById(R.id.textViewStudentDisplayedName))
                        .setText(students.get(studentIndex).getName());
                updateRecyclerView();
            }

        });

        ((ImageButton) findViewById(R.id.imageButtonPreviousStudent)).setOnClickListener(view -> {
            Log.i(TAG, "previous student Not actually but yeah");
            Log.i(TAG, "" + students.size());
            if(0 <= studentIndex - 1){
                studentIndex--;
                ((TextView) findViewById(R.id.textViewStudentDisplayedName))
                        .setText(students.get(studentIndex).getName());
                updateRecyclerView();
            }
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