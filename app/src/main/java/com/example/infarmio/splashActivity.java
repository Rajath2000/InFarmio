package com.example.infarmio;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class splashActivity extends AppCompatActivity{

    private static int SPLASH_SCREEN_TIMEOUT=1500;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);
        firebaseAuth=FirebaseAuth.getInstance();
       Animation fadeout=new AlphaAnimation(1,0);
       fadeout.setInterpolator(new AccelerateInterpolator());
        fadeout.setStartOffset(300);
       //fadeout.setDuration(300);
       ImageView image=findViewById(R.id.splash_logo);
        image.setAnimation(fadeout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Get the current user
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if(firebaseUser!=null)
                {
                    //if user already not logged
                    Intent intent = new Intent(splashActivity.this,UserActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    //if user alredy ogged
                    Intent intent = new Intent(splashActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }

        },SPLASH_SCREEN_TIMEOUT);
    }

}
