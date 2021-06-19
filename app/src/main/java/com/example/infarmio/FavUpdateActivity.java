package com.example.infarmio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

public class FavUpdateActivity extends AppCompatActivity {
    private static final String TAG ="fav_activity" ;
    String Username,Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Username=getIntent().getStringExtra("Username");
        Password=getIntent().getStringExtra("Password");
        Log.d(TAG, "onCreate: "+Username);

        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new DataFragment()).commit();

        //calling fragment

        FavFragment favFragment=new FavFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("Username",Username);
        bundle.putString("Password",Password);

        favFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.maincontainer,new FavUpdateFragment()).commit();
    }
}