package com.ncarignan.sortinghat;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.api.graphql.model.ModelSubscription;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Student;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Student> students = new ArrayList<>();
    RecyclerView recyclerView;
    Handler handleStudentsArriving;
    Handler handleStudentIsHereFromSubscription;
    Handler handleCheckLoggedIn;
    String lastFileIUploadedKey;

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

        handleCheckLoggedIn = new Handler(Looper.getMainLooper(), message -> {
            if(message.arg1 == 0){
                Log.i("Amplify.login", "handler: they were not logged in");
            } else if(message.arg1 == 1){
                Log.i("Amplify.login", "handler: they were logged in");
                // TODO: display their username
                Log.i("Amplify.user" , Amplify.Auth.getCurrentUser().getUsername());

                TextView usernameView = findViewById(R.id.textViewUsername);
                usernameView.setText( Amplify.Auth.getCurrentUser().getUsername());

            } else {
                Log.i("Amplify.login", "send true or false pls");
            }
            return false;
        });

        configureAws();
//        addFiveMockStudentsToAws(); // put 5 students in db
        getStudentsFromAws();
        setUpStudentSubscription();
        getIsSignedIn();
//        addOneMockUsers();
//        verifyOneMockUser();
//        loginMockUser();
        addListenersToButtons();
//        uploadFile();

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override // This built in method we are overriding handles ALL results from when we leave the app
    protected void onActivityResult(int requestCodePotato, int resultCode, Intent data) {
        super.onActivityResult(requestCodePotato, resultCode, data);

        if(requestCodePotato == 4018){
            Log.i("Amplify.pickImage", "Got the image back from the activity");
            // This will know about the image in `data`
            // we can now send it to s3

            File fileCopy = new File(getFilesDir(), "test file");

            try {
                InputStream inStream = getContentResolver().openInputStream(data.getData());
                FileUtils.copy(inStream, new FileOutputStream(fileCopy));
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Amplify.pickImage", e.toString());
            }

            uploadFile(fileCopy, fileCopy.getName() + Math.random());
        } else if(requestCodePotato == 2){
            // this implies I ran the line somewhere
            // startActivityForResult(shareToFacebookIntent, 2);
            Log.i("Amplify.doesnotexist", "good job you shared to facebook");
        } else {
            Log.i("Amplify.pickImage", "How the heck are you talking to my app??????");
        }
    }

    public void retrieveFile(){
        Intent getPicIntent = new Intent(Intent.ACTION_GET_CONTENT);// There are several intent types that are good for files
        // Samsung vs google vs whichever phone you have, might work differently with this or another intent
        getPicIntent.setType("*/*");
        //
//        getPicIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{".png", ".jpg"});

        // These work together to make sure the pics are immediately accessible and openable locally
//        getPicIntent.addCategory(Intent.CATEGORY_OPENABLE);
//        getPicIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

//        startActivity(getPicIntent);
        startActivityForResult(getPicIntent, 4018); // Request code is just the key / name of the intent
    }

    private void downloadFile(String fileKey){
        Amplify.Storage.downloadFile(
                fileKey,
                new File(getApplicationContext().getFilesDir() + "/" + fileKey + ".txt"),
                result -> {
                    Log.i("Amplify.s3down", "Successfully downloaded: " + result.getFile().getName());
                    ImageView image = findViewById(R.id.imageLastUploaded);
                    image.setImageBitmap(BitmapFactory.decodeFile(result.getFile().getPath()));
                    // TODO: display the image
                },
                error -> Log.e("Amplify.s3down",  "Download Failure", error)
        );
    }

    public void uploadFile(File f, String key){
        lastFileIUploadedKey = key;
        Amplify.Storage.uploadFile(
                key,
                f,
                result -> {
                    Log.i("Amplify.s3", "Successfully uploaded: " + result.getKey());
                    downloadFile(key);
                },
                storageFailure -> Log.e("Amplify.s3", "Upload failed", storageFailure)
        );
    }

    private void uploadFileMock() {
        File exampleFile = new File(getApplicationContext().getFilesDir(), "WizardTest");

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(exampleFile));
            writer.append("Example file contents");
            writer.close();
        } catch (Exception exception) {
            Log.e("Amplify.s3", "Upload failed", exception);
        }

        Amplify.Storage.uploadFile(
                "WizardTest",
                exampleFile,
                result -> Log.i("Amplify.s3", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("Amplify.s3", "Upload failed", storageFailure)
        );
    }

    public void addListenersToButtons(){
        Button login = findViewById(R.id.login);
        Button signup = findViewById(R.id.signup);
        Button getPic = findViewById(R.id.getAPicButton);

        login.setOnClickListener(view -> this.startActivity(new Intent(this, SigninActivity.class)));
        signup.setOnClickListener(view -> this.startActivity(new Intent(this, Signup.class)));
        getPic.setOnClickListener((view -> retrieveFile()));
    }

    public void loginMockUser(){
        Amplify.Auth.signIn(
                "McGonagal",
                "secretcat",
                result -> {
                    Log.i("Amplify.login", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                    getIsSignedIn();
                    },
                error -> Log.e("Amplify.login", error.toString())
        );
    }

    public void verifyOneMockUser(){
        Amplify.Auth.confirmSignUp(
                "McGonagal",
                "324809",
                result -> Log.i("Amplify.confirm", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
                error -> Log.e("Amplify.confirm", error.toString())
        );
    }

    public void addOneMockUsers(){
        // TODO: we need to collect from the user: username, password and email
        Amplify.Auth.signUp(
                "McGonagal",
                "secretcat",
                AuthSignUpOptions.builder().userAttribute(AuthUserAttributeKey.email(), "nick.carignan@codefellows.com").build(),
                result -> Log.i("Amplify.signup", "Result: " + result.toString()),
                error -> Log.e("Amplify.signup", "Sign up failed", error)
        );



    }

    public void getIsSignedIn(){
        Amplify.Auth.fetchAuthSession(
                result -> {
                    Log.i("Amplify.login", result.toString());
                    Message message = new Message();

                    if(result.isSignedIn()){
                        message.arg1 = 1;
                        handleCheckLoggedIn.sendMessage(message);
                    } else {
                        message.arg1 = 0;
                        handleCheckLoggedIn.sendMessage(message);
                    }
                },
                error -> Log.e("Amplify.login", error.toString())
        );
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
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
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