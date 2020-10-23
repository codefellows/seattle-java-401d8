package com.ncarignan.buycheapthings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CheapThingAdapter extends RecyclerView.Adapter<CheapThingAdapter.CheapThingViewHolder> {

    public ArrayList<CheapThing> cheapThings; // what things exist (should go in the fragment)
    public ClickOnCheapThingListener listener; // what should happen when we click on them


    public CheapThingAdapter(ArrayList<CheapThing> cheapThings, ClickOnCheapThingListener listener){
        this.cheapThings = cheapThings;
        this.listener = listener;
    }

    // TODO: 1 create our viewHolder class
    // view holder deals with passing the data from java to the fragment (List Item)
    public static class CheapThingViewHolder extends RecyclerView.ViewHolder {
        public CheapThing cheapThing;
        // store view, so I can change it from any external method
        public View itemView;


        public CheapThingViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

        }
    }

    @NonNull
    @Override // This gets called when a fragment (List item) pops into existence
    public CheapThingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
// TODO: 2 start here after extending the class
        // choose which fragment(list item) to build
        // TODO: 3 go make yourself a fragment so you can choose the right fragment to inflate
        // inflate sorta == render

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_cheap_thing, parent, false);



        // TODO: 4 instantiate a viewHolder
        CheapThingViewHolder viewHolder = new CheapThingViewHolder(view);



        // TODO: Clicking 1 attach an event listener
        view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                System.out.println(viewHolder.cheapThing.thingName);
                // Goal: go to a detail page about that cheap thing
                // TODO: clicking 2: assume the Activity listener exists and try to call it
                listener.clickOnCheapThing(viewHolder.cheapThing);
            }
        });


        return viewHolder;
    }

    // TODO: clicking 3: create the activity listener interface with the method name you want
    public static interface ClickOnCheapThingListener {
        public void clickOnCheapThing(CheapThing cheapThing);
    }



    @Override // this gets called when a fragment (List item) has a java class attached to it
    public void onBindViewHolder(@NonNull CheapThingViewHolder holder, int position) { // position is the position in the arrayList
        holder.cheapThing = cheapThings.get(position);

        TextView itemNameTextView = holder.itemView.findViewById(R.id.itemName);
        TextView itemQuantityTextView = holder.itemView.findViewById(R.id.itemQuantity);
        TextView itemPriceTextView = holder.itemView.findViewById(R.id.itemPrice);
        itemNameTextView.setText(holder.cheapThing.thingName);
        itemQuantityTextView.setText(Integer.toString(holder.cheapThing.quantity));

        // NICHOLAS INFO setText takes in a string or a int, when given an int it thinks you are referring to an int in the string resource file

        itemPriceTextView.setText(Float.toString(holder.cheapThing.price));
    }

    @Override // this gets called so it knows how many fragments (list items) to put on the screen at once
    public int getItemCount() {
        return cheapThings.size(); // TODO: make this return the list length
    }



}
