package com.example.infarmio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

public class favorites_activity extends AppCompatActivity {
    String Username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);



        Username="sham2000";

        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new DataFragment()).commit();

        //calling fragment

        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new FavFragment()).commit();


    }


    //Which returns the username
    public String getUsername() {
        return Username;
    }
}