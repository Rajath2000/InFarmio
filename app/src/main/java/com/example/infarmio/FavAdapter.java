package com.example.infarmio;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.holder>{
    //Declearing the list for holding values from firebase
    ArrayList<String> favratious=new ArrayList<>();

    ////Declearing the list for holding values to send to firebase
    public ArrayList<String> Userselected=new ArrayList<>();

    //method which return users choice
    public ArrayList<String> UserSelectedDetails()
    {
        return Userselected;
    }

    //Recives all the item present in firebase
    public FavAdapter(ArrayList<String> favratious) {
        this.favratious = favratious;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.single_chip,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        //set the text for item
        holder.chip.setText(favratious.get(position));
        //initalize all variables to empty
        Userselected.add("");


        //on click on each chip
        holder.chip.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "adapter";
            @Override
            public void onClick(View v) {
                //if checked
                if(holder.chip.isChecked())
                {
                    //add the item to list
                    Log.d(TAG, "onClick: checked");
                    Userselected.set(position,favratious.get(position));

                }
                else
                {
                    //remove from ist
                    Userselected.set(position,"");
                    Log.d(TAG, "onClick: not checked");
                }


                //Debug
                for(String arrayList:Userselected)
                {
                    Log.d(TAG, "onClick: "+arrayList);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return favratious.size();
    }

    //Defing view holder class which gives information about single chip or telling adaptor display these things
    class holder extends RecyclerView.ViewHolder {
        Chip chip;
        FloatingActionButton floatbutton;
        public holder(@NonNull View itemView) {
            super(itemView);
            chip=(Chip)itemView.findViewById(R.id.chipItem);
        }
    }

}