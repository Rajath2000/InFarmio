package com.example.infarmio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    TextView loginText;
    Button Signup;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //ALL objects Initialization
        loginText=(TextView)findViewById(R.id.Signup_reditecttologin);
        Signup=findViewById(R.id.SignUp_button);


        //Validation Style
        awesomeValidation=new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        //Validation Rules
        awesomeValidation.addValidation(this,R.id.Signup_layout_username, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.Signup_layout_phone,"[5-9]{1}[0-9]{9}$",R.string.invalid_phone);
        awesomeValidation.addValidation(this,R.id.Signup_layout_password, RegexTemplate.NOT_EMPTY,R.string.invalid_password);
        awesomeValidation.addValidation(this,R.id.Signup_layout_confirmpassword,R.id.Signup_layout_password,R.string.not_matching);


        //signup button action
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate())
                {

                }
                else
                {
                    Toast.makeText(Signup.this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
                }
            }
        });














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