package com.ncarignan.buycheapthings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheapThingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheapThingFragment extends Fragment {

    //TODO: for this fragment
    // TODO: 1 give it good variable names and the correct quantity of variables
    // TODO: thats pretty much it

    // DONE: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "itemName";
    private static final String ARG_PARAM2 = "quantity";
    private static final String ARG_PARAM3 = "price";

    // DONE: Rename and change types of parameters
    private String mItemName;
    private String mQuantity;
    private String mPrice;

    public CheapThingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param itemName Item Name.
     * @param quantity Quantity.
     * @param price Price
     * @return A new instance of fragment CheapThingFragment.
     */
    // DONE: Rename and change types and number of parameters
    public static CheapThingFragment newInstance(String itemName, String quantity, String price) {
        CheapThingFragment fragment = new CheapThingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, itemName);
        args.putString(ARG_PARAM2, quantity);
        args.putString(ARG_PARAM3, price);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItemName = getArguments().getString(ARG_PARAM1);
            mQuantity = getArguments().getString(ARG_PARAM2);
            mPrice = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cheap_thing, container, false);
    }

    // NICHOLAS NOTATION : two other lifecycle methods of a fragment in a recyclerview
    // onAttach - we will use this to set listeners
    // onDetach - useful for tracking what a user has seen / ignored etc/ removing from a queue
}