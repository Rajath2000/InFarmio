package com.example.infarmio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView userid;
    BottomNavigationView bottomNavigationView;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    Button logout;
    CircleImageView imageView;
    HelpingMethods helpingMethods;
    ProgressBar progressBar;
    String Username,Url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        //loading by default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.post_frame,new HomeFragment()).commit();


        userid=findViewById(R.id.user_id);
        imageView=findViewById(R.id.profile_image);

        //Getting user details
        mAuth = FirebaseAuth.getInstance();

        // getting current username detais
        Username=emailparser(mAuth.getCurrentUser().getEmail());


        databaseReference= FirebaseDatabase.getInstance().getReference().child("User").child(Username);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Url=snapshot.child("profileurl").getValue().toString();
                try {
                    Glide.with(UserActivity.this).asBitmap().load(Url).into(imageView);
                }
                catch (Exception e)
                {

                }
                userid.setText(Username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






//
//        progressDialog=new ProgressDialog(UserActivity.this);
//        helpingMethods=new HelpingMethods();
//

//
//       //displaying image
//
//
//        progressBar.setVisibility(View.VISIBLE);
//        Glide.with(this).asBitmap().load("https://firebasestorage.googleapis.com/v0/b/infarmio.appspot.com/o/Himanshu2000%2Fprofileimage?alt=media&token=da62a4b9-678c-42ff-8286-2782f22d4cbc").into(imageView);
//        progressBar.setVisibility(View.GONE);
//
//
//
//
//
//        //sign out
//        logout=findViewById(R.id.Logout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressDialog.setTitle("Signing out");
//                progressDialog.setMessage("Please Wait");
//                progressDialog.setCanceledOnTouchOutside(false);
//                progressDialog.show();
//                FirebaseAuth.getInstance().signOut();
//                progressDialog.dismiss();
//                Intent intent=new Intent(UserActivity.this,Login.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//









        //////////////////////Navigation Action///////////////////////////////////
        bottomNavigationView=findViewById(R.id.user_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp=null;
                switch (item.getItemId())
                {

                    case R.id.menu_home:temp=new HomeFragment();
                        break;
                    case R.id.menu_Search:temp=new SearchFragment();
                        break;
                    case R.id.menu_Post:temp=new PostFragment();
                        break;
                    case R.id.menu_Profile:temp=new ProfileFragment();
                        break;

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.post_frame,temp).commit();
                return true;
            }
        });

        ///////////////////////////////////////////////////////////////////////////
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



}