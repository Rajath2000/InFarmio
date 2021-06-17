package com.example.infarmio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

public class favorites_activity extends AppCompatActivity {
    private static final String TAG ="fav_activity" ;
    String Username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);



        Username=getIntent().getStringExtra("Username");
        Log.d(TAG, "onCreate: "+Username);

        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new DataFragment()).commit();

        //calling fragment

        FavFragment favFragment=new FavFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        Bundle bundle=new Bundle();
        bundle.putString("Username",Username);

        favFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.maincontainer,favFragment).commit();



//        getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer,new FavFragment()).commit();





    }


    //Which returns the username
    public String getUsername() {
        return Username;
    }
}