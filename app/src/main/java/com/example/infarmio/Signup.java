package com.example.infarmio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Signup extends AppCompatActivity {
    TextView loginText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //ALL objects Initialization
        loginText=(TextView)findViewById(R.id.Signup_reditecttologin);











        //Redirect to Loginpage
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent=new Intent(Signup.this,Login.class);
                startActivity(loginIntent);
                finish();
            }
        });

    }
}