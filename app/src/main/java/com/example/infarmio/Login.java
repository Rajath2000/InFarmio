package com.example.infarmio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {
    TextView loginText;
    TextInputEditText userName,password;
    Button login;

    //declaring formvalidator objrct
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       //converting xml id to java objects
        loginText=(TextView)findViewById(R.id.Login_redirecttosignup);
        userName=findViewById(R.id.Login_Email);
        password=findViewById(R.id.Login_password);
        login=findViewById(R.id.Login_Button);

        //initalizing formvalidator objrct
        //adding the style for validation
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);

        //add validation for Email
        awesomeValidation.addValidation(this,R.id.layout_email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.layout_password, RegexTemplate.NOT_EMPTY,R.string.invalid_password);

        //Login button action
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate())
                {
                    Toast.makeText(Login.this, "Validation Sucessfull", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Login.this, "Please Enter the valid Details", Toast.LENGTH_SHORT).show();
                }
            }
        });














        //Redirect to signup
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent=new Intent(Login.this,Signup.class);
                startActivity(signUpIntent);
                finish();
            }
        });


    }
}