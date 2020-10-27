package com.ncarignan.buycheapthings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.CheapItem;
import com.ncarignan.buycheapthings.database.Database;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CheapThingAdapter.ClickOnCheapThingListener {

    Database database;
    NotificationChannel channel;
    NotificationManager notificationManager;
    ArrayList<CheapItem> cheapThings;
    RecyclerView recyclerView;


    @Override
    public void onResume() { // this is probably the correct place for ALL rendered info

        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView address = findViewById(R.id.preferencesAddress);
        address.setText(preferences.getString("addressPotato", "Go to Settings to set an address for shipping"));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {//{6, 92, 10, 30}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureAws(); // Class 31/32
        configureDB();
        configureNotifications();

        Handler handler = new Handler(Looper.getMainLooper(),
                // job is to allow a thread to send the main thread an action or message
                new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message message) {
                        recyclerView.getAdapter().notifyDataSetChanged();
                        return false;
                    }
                });

        connectAdapterToRecyclerView(handler);
        addClickListenerToBuyButton(); // Class 32
        addClickListenerToGoToCartButton();
        addClickListenerToGoToSettingsButton();
        addClickListenerToGoToOrderHistoryButton();

        // TODO: listen for changes from anyone's phone and update the recyclerview then
        // TODO: listen for changes in dynamodb



    }

    private void configureAws(){
        try {
            // entire configuration
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());

//            // Add an item
//            CheapItem cheapThing = CheapItem.builder() // builder === constructor
//                    .thingName("Dog toy").quantity(25)
//                    .price(7.45f).build();
//            // an instance of a cheapItem in java, in our phone here
//
//            // mutation is where we send this to the internet to DynamoDb
//            // GraphQl has 2 methods that go to the internet : mutate, and query
//            // mutate is changes to the db
//            // query is READ ONLY
//            Amplify.API.mutate(ModelMutation.create(cheapThing),
//                    response -> Log.i("Amplify", "successfully added"),
//                    error -> Log.e("Amplify", error.toString()));

        } catch (AmplifyException e) {
            e.printStackTrace();
        }
    }

    private void configureDB(){
        database = Room.databaseBuilder(getApplicationContext(), Database.class, "ncarignan_cheap_things")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void configureNotifications(){
        channel = new NotificationChannel("basic", "basic", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("basic notifications");
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void connectAdapterToRecyclerView(Handler handler){
        cheapThings = new ArrayList<CheapItem>();
        recyclerView = findViewById(R.id.cart_preview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CheapThingAdapter(cheapThings, this));


        Amplify.API.query(
                ModelQuery.list(CheapItem.class),
                response -> {
                    for (CheapItem item : response.getData()) {
                        cheapThings.add(item);
                    }
                    handler.sendEmptyMessage(1);
                    Log.i("Amplify.queryitems", "Got this many items from dynamo " + cheapThings.size());


                },
                error -> Log.i("Amplify.queryitems", "Did not get items"));

        // TODO: caching: when we don't have access to the internet (in our error callback, don't just stop, use local db)
        ;
//        cheapThings = (ArrayList<CheapItem>) database.cheapItemDao().getAllCheapThingsReversed();

    }

    public void addClickListenerToBuyButton(){
        Button buyButton = MainActivity.this.findViewById(R.id.buybutton);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText itemNameInput = MainActivity.this.findViewById(R.id.editItemName);
                EditText itemQuantityInput = MainActivity.this.findViewById(R.id.editTextQuantity);
                EditText itemPriceInput = MainActivity.this.findViewById(R.id.editTextPriceDecimal);
                String itemName = itemNameInput.getText().toString();
                int quantity = Integer.parseInt(itemQuantityInput.getText().toString());
                float price = Float.parseFloat(itemPriceInput.getText().toString());
                System.out.println(String.format("We are going to buy %d %ss for $%f", quantity, itemName, price));

                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "basic")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(itemName)
                        .setContentText("Buying " + quantity + " " + itemName)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Much longer text that cannot fit one line..."))
                        .setPriority(NotificationCompat.PRIORITY_MAX);

                notificationManager.notify(89898989, builder.build());

////                // TODO: save the item to the database
//                CheapThing cheapThing = new CheapThing(itemName, quantity, price);
//                database.cheapThingDao().saveTheThing(cheapThing);


                CheapItem cheapThing = CheapItem.builder()
                        .thingName(itemName).price(price)
                        .quantity(quantity).build();

                Amplify.API.mutate(ModelMutation.create(cheapThing),
                    response -> Log.i("Amplify", "successfully added " + itemName),
                    error -> Log.e("Amplify", error.toString()));

                database.cheapItemDao().saveTheThing(cheapThing);


//                cheapThings.add(0, cheapThing);
//
//                // update the view
//                recyclerView.getAdapter().notifyItemInserted(0);
////                recyclerView.getLayoutManager().smoothScrollToPosition(0);
//                recyclerView.smoothScrollToPosition(0);


            }
        });
    }

    private void addClickListenerToGoToCartButton() {
        Button goToCartButton = MainActivity.this.findViewById(R.id.visit_cart);
        goToCartButton.setOnClickListener((view) -> {
            System.out.println("going to cart");
            Intent goToCartIntent = new Intent(MainActivity.this, Cart.class);
            MainActivity.this.startActivity(goToCartIntent);
        });
    }

    private void addClickListenerToGoToSettingsButton(){
        ImageButton goToSettingsButton = findViewById(R.id.settingsButton);
        goToSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingsPage();
            }
        });
    }

    private void addClickListenerToGoToOrderHistoryButton(){
        Button goToOrderHistory = findViewById(R.id.order_history_nav);
        goToOrderHistory.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, OrderHistory.class);
            MainActivity.this.startActivity(intent);
        });
    }








    public void openSettingsPage(){
        Intent intent = new Intent(this, UserSettings.class);
        startActivity(intent);
    }

    @Override
    public void clickOnCheapThing(CheapItem cheapThing) {

    }

    // onCreate is one of six LifeCycle methods we can override.
    // onCreate gets called at a specific time:
    // when we first view the home page (when the app opens)
}


