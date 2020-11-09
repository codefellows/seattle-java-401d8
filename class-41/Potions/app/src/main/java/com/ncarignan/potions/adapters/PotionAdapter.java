package com.ncarignan.potions.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.amplifyframework.datastore.generated.model.Potion;
import com.ncarignan.potions.R;
import com.ncarignan.potions.activities.MainActivity;

import java.util.ArrayList;

public class PotionAdapter extends RecyclerView.Adapter<PotionAdapter.PotionViewHolder> {
    ArrayList<Potion> potions;
    CanClickOnFragment handlerActivity;

    public PotionAdapter(ArrayList<Potion> potions, CanClickOnFragment handlerActivity){
        this.handlerActivity = handlerActivity;
        this.potions = potions;
    }

    public static class PotionViewHolder extends ViewHolder {
        View fragment;
        public PotionViewHolder(@NonNull View itemView) {
            super(itemView);
            fragment = itemView;
        }
    }

    @NonNull
    @Override
    public PotionAdapter.PotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View fragment = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_potion, parent, false);
        PotionViewHolder viewHolder = new PotionViewHolder(fragment);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PotionAdapter.PotionViewHolder holder, int position) {
        Potion potion = potions.get(position);
        ((TextView) holder.fragment.findViewById(R.id.potionName)).setText(potion.getName());
        ((TextView) holder.fragment.findViewById(R.id.potionColor)).setText(potion.getColor());
        ((TextView) holder.fragment.findViewById(R.id.potionSuccess)).setText("Success : " + potion.getSuccess());

        holder.fragment.setOnClickListener(view -> {
            Log.i("Adapter.clicked", "clicked on " + potion.getName());
            handlerActivity.handleClickOnFragment(potion);
        });
    }

    @Override
    public int getItemCount() {
        return potions.size();
    }

    public interface CanClickOnFragment{
        public void handleClickOnFragment(Potion potion);
    }
}
