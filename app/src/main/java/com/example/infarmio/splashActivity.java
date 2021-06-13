package com.example.infarmio;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class splashActivity extends AppCompatActivity{

    private static int SPLASH_SCREEN_TIMEOUT=1500;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_activity);
       Animation fadeout=new AlphaAnimation(1,0);
       fadeout.setInterpolator(new AccelerateInterpolator());
        fadeout.setStartOffset(300);
       //fadeout.setDuration(300);
       ImageView image=findViewById(R.id.splash_logo);
        image.setAnimation(fadeout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(splashActivity.this,Login.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_SCREEN_TIMEOUT);
    }

}
