package com.example.infarmio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView userid;
    ProgressDialog progressDialog;
    Button logout;
    ImageView imageView;
    HelpingMethods helpingMethods;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mAuth = FirebaseAuth.getInstance();
        userid=findViewById(R.id.Userid);
        imageView=findViewById(R.id.Userprofile);
        progressBar=findViewById(R.id.loading);

        progressDialog=new ProgressDialog(UserActivity.this);
        helpingMethods=new HelpingMethods();

        //getting current username detais
        userid.setText(emailparser(mAuth.getCurrentUser().getEmail()));

//displaying image


        progressBar.setVisibility(View.VISIBLE);
        Glide.with(this).asBitmap().load("https://firebasestorage.googleapis.com/v0/b/infarmio.appspot.com/o/Himanshu2000%2Fprofileimage?alt=media&token=da62a4b9-678c-42ff-8286-2782f22d4cbc").into(imageView);
        progressBar.setVisibility(View.GONE);





        //sign out
        logout=findViewById(R.id.Logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("Signing out");
                progressDialog.setMessage("Please Wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                FirebaseAuth.getInstance().signOut();
                progressDialog.dismiss();
                Intent intent=new Intent(UserActivity.this,Login.class);
                startActivity(intent);
                finish();
            }
        });


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