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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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
                    String user=firebaseAuth.getCurrentUser().getEmail();
                    DatabaseReference Admindb = FirebaseDatabase.getInstance().getReference().child("Admin");
                    DatabaseReference adminInstance = Admindb.child(emailparser(user));
                    adminInstance.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                String dbuser=snapshot.child("adminname").getValue().toString();

                                //if user already not logged
                                Intent intent = new Intent(splashActivity.this,AdminActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            }
                            catch (Exception e)
                            {

                                //if user already not logged
                                Intent intent = new Intent(splashActivity.this,UserActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else {
                    //if user alredy ogged
                    Intent intent = new Intent(splashActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }
            public String emailparser(String Email){
                String temp="";
                String[] split_email=Email.split("[@]");
                for(int j=0;j<=split_email.length-1;j++) {
                    temp=split_email[j];
                    break;
                }
                return temp;
            }

        },SPLASH_SCREEN_TIMEOUT);
    }

}
