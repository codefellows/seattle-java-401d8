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
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.ApiOperation;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.api.graphql.model.ModelSubscription;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.CheapItem;
import com.amplifyframework.datastore.generated.model.Store;
//import com.ncarignan.buycheapthings.database.Database;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CheapThingAdapter.ClickOnCheapThingListener {

//    Database database; // class 33
    NotificationChannel channel;
    NotificationManager notificationManager;
    ArrayList<CheapItem> cheapThings;
    RecyclerView recyclerView;
    ArrayList<Store> stores;
    Handler handler;
    Handler handleSingleItemAdded;
    int storeWeAreOnIndex = 0;



    // TODO: update to related data
    // 0: back up any model files you may want to reference
    // 1: update your schema
    // 2: pushing the update to aws (amplify push) (if it fails run amplify delete then start over an amplify init)
    // 3: run the codegen to get updated models
    // 4: update any code that relies on the related data
    // We had an issue in that our database could not figure out how to store AWS related data
    // We commented out all references to database, we went through each red error and just removed them
    // 5. Update our save method
    //      5.1 grabbed one of the Store models so that we could attach it to a CheapItem when we saved it
    //      5.1 saving requires a reference to the parent, null is not ok
    //      5.2 We queried dynamodb for stores and just picked one
    // 6. We need to refactor where our main thread is called, specifically so we can update the recclerview
    // 7. DONT DO: We moved our handler around a whole bunch
    // 8 DO DO: put any code that affects the view INTO the handler,
    // then just move the call of the handler to the asycnhronous method
    // 8.5 To make this easier, move the handler's variable declaration to be global
    // 8.75 we made the API.query for stores happen at the onCreate level instead too,
    // this allowed us to avoid one more asynchronous callback when we added an item


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

        handler = new Handler(Looper.getMainLooper(),
                // job is to allow a thread to send the main thread an action or message
                new Handler.Callback() {
                    @Override
                    public boolean handleMessage(@NonNull Message message) {
                        connectAdapterToRecyclerView();
                        recyclerView.getAdapter().notifyDataSetChanged();
                        return true;
                    }
                });

        handleSingleItemAdded = new Handler(Looper.getMainLooper(),
                (message -> {
                    recyclerView.getAdapter().notifyItemInserted(cheapThings.size() - 1);
                    Toast.makeText(
                            this,
                            cheapThings.get(cheapThings.size()-1).thingName + " was added to the cart",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }));
        configureAws(handler); // Class 31/32 // as of class 33 - retrieves stores (asynchronously)
//        configureDB(); // class 33
        configureNotifications();






        addClickListenerToBuyButton(); // Class 32
        addClickListenerToGoToCartButton();
        addClickListenerToGoToSettingsButton();
        addClickListenerToGoToOrderHistoryButton();
        addClickListenersToForwardAndBackButtons();

        // TODO: listen for changes from anyone's phone and update the recyclerview then
        // TODO: listen for changes in dynamodb
        String SUBSCRIBETAG = "Amplify.subscription";
        ApiOperation subscription = Amplify.API.subscribe(
                ModelSubscription.onCreate(CheapItem.class),
                onEstablished -> Log.i(SUBSCRIBETAG, "Subscription established"),
                createdItem -> {
                    Log.i(
                            SUBSCRIBETAG,
                            "CheapItem create sub received: " + ((CheapItem) createdItem.getData()).getThingName()
                    );
                    CheapItem newItem = (CheapItem) createdItem.getData();
                    cheapThings.add(newItem);
                    handleSingleItemAdded.sendEmptyMessage(1);
                },
                onFailure -> {
                    Log.e(SUBSCRIBETAG, "Subscription failed", onFailure);
                },
                () -> Log.i(SUBSCRIBETAG, "Subscription completed")
        );



    }

    private void configureAws(Handler handler){
        try {
            // entire configuration
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());


            Amplify.API.query(
                    ModelQuery.list(Store.class),
                    response -> {
                        stores = new ArrayList<> ();
                        for(Store store : response.getData()){
                            stores.add(store);
                        }
                        handler.sendEmptyMessage(1);

                    },
                    error -> Log.e("Amplify", "failed to retrieve store")
            );


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
//        database = Room.databaseBuilder(getApplicationContext(), Database.class, "ncarignan_cheap_things")
//                .fallbackToDestructiveMigration()
//                .allowMainThreadQueries()
//                .build();
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

    private void connectAdapterToRecyclerView(){
        cheapThings = new ArrayList<CheapItem>();
        recyclerView = findViewById(R.id.cart_preview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (CheapItem item : stores.get(storeWeAreOnIndex).getCheapItems()) { // take every item from our first store (Sesame street) and make it the focus of the app
            cheapThings.add(item);
        }

        recyclerView.setAdapter(new CheapThingAdapter(cheapThings, this));



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
                Store storeForTheCheapThing = stores.get(storeWeAreOnIndex);
                CheapItem cheapThing = CheapItem.builder()
                        .thingName(itemName).price(price)
                        .quantity(quantity).foundAt(storeForTheCheapThing).build();
                storeForTheCheapThing.getCheapItems().add(cheapThing);

                Amplify.API.mutate(ModelMutation.create(cheapThing),
                        response2 -> Log.i("Amplify", "successfully added " + itemName),
                        error -> Log.e("Amplify", error.toString()));





//                database.cheapItemDao().saveTheThing(cheapThing); // class 33


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

    public void setUpThreeMockStores(){
        Store store1 = Store.builder()
                .address("123 Sesame Street")
                .name("Widgets of Mass Distinction")
                .build();

        Store store2 = Store.builder()
                .address("4321 Minis Tirith Way")
                .name("Used Threads")
                .build();

        Store store3 = Store.builder()
                .address("111 Death Star Main St.")
                .name("Build a Bear Workshop")
                .build();

        Amplify.API.mutate(ModelMutation.create(store1),
                response -> Log.i("Amplify", "added a store"),
                error -> Log.e("Amplify", "failed to add a store")
        );

        Amplify.API.mutate(ModelMutation.create(store2),
                response -> Log.i("Amplify", "added a store"),
                error -> Log.e("Amplify", "failed to add a store")
        );

        Amplify.API.mutate(ModelMutation.create(store3),
                response -> Log.i("Amplify", "added a store"),
                error -> Log.e("Amplify", "failed to add a store")
        );
    }

    public void addClickListenersToForwardAndBackButtons(){
        ImageButton backButton = findViewById(R.id.backButton);
        ImageButton forwardButton = findViewById(R.id.forwardButton);

        backButton.setOnClickListener(view -> {
            // switch recyclerview left
            if(storeWeAreOnIndex > 0){
                storeWeAreOnIndex--;
                // update view
                cheapThings.clear();
                for(CheapItem item : stores.get(storeWeAreOnIndex).getCheapItems()){
                    cheapThings.add(item);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        forwardButton.setOnClickListener(view -> {
            // switch recyclerview left
            if(storeWeAreOnIndex < stores.size() -1){
                storeWeAreOnIndex++;
                // update view
                cheapThings.clear();
                for(CheapItem item : stores.get(storeWeAreOnIndex).getCheapItems()){
                    cheapThings.add(item);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
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


