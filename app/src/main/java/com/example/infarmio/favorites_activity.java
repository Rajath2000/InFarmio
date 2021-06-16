package com.example.infarmio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class favorites_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new DataFragment()).commit();
    }
}