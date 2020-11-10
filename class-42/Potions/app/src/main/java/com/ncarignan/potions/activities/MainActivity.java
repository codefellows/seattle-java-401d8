package com.ncarignan.potions.activities;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Potion;
import com.amplifyframework.datastore.generated.model.Student;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.ncarignan.potions.R;
import com.ncarignan.potions.adapters.PotionAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PotionAdapter.CanClickOnFragment {
    public RecyclerView recyclerView;
    public ArrayList<Potion> potions = new ArrayList<>();
    public ArrayList<Student> students = new ArrayList<>();
    public int studentIndex = -1;
    public String targetStudentNameFromIntent = "";
    Handler handleRecyclerViewUpdate;

    public static String TAG = "hogwarts";

    FusedLocationProviderClient locationProviderClient;

    Location currentLocation;
    String addressString;

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
        askForPermissionToUseLocation();
        configureLocationServices();
        askForLocation();
    }

    // 1. Permissions in manifest
    // 2. requestPermissions
    // 3. IDEALLY BUT NOT TODAY - ask for location once (on Marshmallow apparently on the emulater it fails to update)
    // 3. Setting up a location subscription, set interval time and callback
    // 4. in the callback we will have access to a gps coordinate location
    // 5. use a geocoder to turn that into an address
    // 6. When you add a Resource(Task for you, Student for me) include the address


    public void askForPermissionToUseLocation() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 2);
    }

    public void askForLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//
//            Log.e(TAG, "PERMISSIONS WERE NOT ACTUALLY GRANTED!!");
//            return;
//        }
//        locationProviderClient.getLastLocation()
//                .addOnSuccessListener(location -> Log.i(TAG + ".locsuccess", location.toString()))
//                .addOnFailureListener(error -> Log.e(TAG + ".locFailure", error.toString()))
//                .addOnCanceledListener(() -> Log.e(TAG + ".locCancel", "it was canceled"))
//                .addOnCompleteListener(complete -> Log.i(TAG + ".locComplete", complete.toString()));

        // TODO: geocoder
        LocationRequest locationRequest;
        LocationCallback locationCallback;

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);


        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                currentLocation = locationResult.getLastLocation();
                Log.i(TAG, currentLocation.toString());

                // TODO: GeoCoding the coordinates
                Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 10);
                    Log.i(TAG, addresses.get(0).toString());
                    addressString = addresses.get(0).getAddressLine(0);
                    Log.i(TAG, addressString);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            Toast t = new Toast(this);
            t.setText("You need to accept the permissions");
            t.setDuration(Toast.LENGTH_LONG);
            t.show();
            return;
        }
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());


    }

    public void configureLocationServices(){
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        // fuses the multiple location requests into one big one, gives you the most accurate that comes back
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