package com.example.infarmio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    TextView userid;
    ProgressDialog progressDialog;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mAuth = FirebaseAuth.getInstance();
        userid=findViewById(R.id.Userid);

        progressDialog=new ProgressDialog(UserActivity.this);

        //getting current username detais
        userid.setText(emailparser(mAuth.getCurrentUser().getEmail()));

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