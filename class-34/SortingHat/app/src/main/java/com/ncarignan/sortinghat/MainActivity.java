package com.ncarignan.sortinghat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.api.graphql.model.ModelSubscription;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Student;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Student> students = new ArrayList<>();
    RecyclerView recyclerView;
    Handler handleStudentsArriving;
    Handler handleStudentIsHereFromSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handleStudentsArriving = new Handler(Looper.getMainLooper(), message -> {
            setupRecyclerView();
            setupSpinner();
            setupListener();
            return false;
        });

        handleStudentIsHereFromSubscription = new Handler(Looper.getMainLooper(), message -> {
            recyclerView.getAdapter().notifyItemInserted(students.size() -1);
            recyclerView.smoothScrollToPosition(students.size() -1);
            return false;
        });

        configureAws();
//        addFiveMockStudentsToAws(); // put 5 students in db
        getStudentsFromAws();
        setUpStudentSubscription();

    }

    public void setUpStudentSubscription(){
        Amplify.API.subscribe(
                ModelSubscription.onCreate(Student.class),
                onEstablish -> Log.i("Amplify", "Established"),
                created -> {
                    Log.i("Amplify", "someone enrolled");
                    Student student = created.getData();
                    students.add(student);
                    handleStudentIsHereFromSubscription.sendEmptyMessage(1);
                },
                failure -> Log.e("Amplify", failure.toString()),
                () -> Log.i("Amplify", "complete")
                );
    }

    public void getStudentsFromAws(){
        Amplify.API.query(
                ModelQuery.list(Student.class),
                success -> {
                    Log.i("Amplify", "Students came from platform 9 3/4");
                    for(Student student : success.getData()){
                        students.add(student);
                    }
                    handleStudentsArriving.sendEmptyMessage(1);
                },
                error -> Log.i("Amplify", "Students ran into the Womping Willow")
                );
    }

    public void setupListener(){
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(view -> {
            String name =((TextView) findViewById(R.id.editTextTextPersonName)).getText().toString();
            Spinner spinner = findViewById(R.id.spinner);
            String house = spinner.getSelectedItem().toString();
            System.out.println(name + " " + house);

            Student student = Student.builder().name(name).house(house).build();
            Amplify.API.mutate(
                    ModelMutation.create(student),
                    success -> Log.i("Amplify", "yay student comes to hogwarts"),
                    error-> Log.e("Amplify", error.toString()));
        });
    }

    public void setupSpinner(){
        String[] houses = {"Griffindor", "Slytherin", "Hufflepuff", "Ravenclaw"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, houses);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }


    public void retrieveStudentsFromAws(){
        // TODO: retrieve
    }

    public void addFiveMockStudentsToAws(){


        Amplify.API.mutate(
                ModelMutation.create(Student.builder().name("Hermione Granger").house("Griffindor").build()),
                success -> Log.i("Amplify", "yay added student"),
                error -> Log.e("Amplify", error.toString())
                );
        Amplify.API.mutate(
                ModelMutation.create(Student.builder().name("Ron Weasley").house("Griffindor").build()),
                success -> Log.i("Amplify", "yay added student"),
                error -> Log.e("Amplify", "boo student missed their letter")
        );
        Amplify.API.mutate(
                ModelMutation.create(Student.builder().name("Draco Malfoy").house("Slytherin").build()),
                success -> Log.i("Amplify", "yay added student"),
                error -> Log.e("Amplify", "boo student missed their letter")
        );
        Amplify.API.mutate(
                ModelMutation.create(Student.builder().name("Luna Lovegood").house("Ravenclaw").build()),
                success -> Log.i("Amplify", "yay added student"),
                error -> Log.e("Amplify", "boo student missed their letter")
        );
        Amplify.API.mutate(
                ModelMutation.create(Student.builder().name("Nute Skemander").house("Hufflepuff").build()),
                success -> Log.i("Amplify", "yay added student"),
                error -> Log.e("Amplify", "boo student missed their letter")
        );

        //TODO: send to aws
    }

    public void configureAws(){
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
        } catch (AmplifyException e) {
            e.printStackTrace();
        }
    }

    public void setupRecyclerView(){
        recyclerView = findViewById(R.id.house_roster);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new StudentAdapter(students));
    }



}