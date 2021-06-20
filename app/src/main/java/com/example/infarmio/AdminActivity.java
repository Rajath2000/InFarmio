package com.example.infarmio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminActivity extends AppCompatActivity {

    TextView userid;
    BottomNavigationView bottomNavigationView;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    Button logout;
    CircleImageView imageView;
    HelpingMethods helpingMethods;
    ProgressBar progressBar;
    String Username,Url;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getSupportFragmentManager().beginTransaction().replace(R.id.post_frame,new HomeFragmentAdmin()).commit();


        userid=findViewById(R.id.user_id);
        imageView=findViewById(R.id.profile_image);


        //Getting user details
        mAuth = FirebaseAuth.getInstance();

        // getting current username detais
        Username=emailparser(mAuth.getCurrentUser().getEmail());




        databaseReference= FirebaseDatabase.getInstance().getReference().child("Admin").child(Username).child("profileurl");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Url=snapshot.getValue().toString();
                try {
                    Glide.with(AdminActivity.this).asBitmap().load(Url).into(imageView);
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












//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent=new Intent(AdminActivity.this,Login.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        });


        //////////////////////Navigation Action///////////////////////////////////
        bottomNavigationView=findViewById(R.id.user_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp=null;
                switch (item.getItemId())
                {

                    case R.id.menu_home:temp=new HomeFragmentAdmin();
                        break;
                    case R.id.menu_Search:temp=new SearchFragment();
                        break;
                    case R.id.menu_Post:temp=new PostFragmentAdmin();
                        break;
                    case R.id.menu_Profile:temp=new ProfileFragmentAdmin();
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