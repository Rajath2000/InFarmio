package com.example.infarmio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class Login extends AppCompatActivity {
    TextView loginText;
    TextInputEditText userName,Password;
    Button login;
    CheckBox checkBox;
    String dbusername,dbpassword;

    //declaring formvalidator objrct
    AwesomeValidation awesomeValidation;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Check for internet Conectivity
//        if(!isconnected(Login.this)){
//            showDailog();
//        }

       //converting xml id to java objects
        loginText=(TextView)findViewById(R.id.Login_redirecttosignup);
        userName=findViewById(R.id.Login_Email);
        Password=findViewById(R.id.Login_password);
        login=findViewById(R.id.Login_Button);
        checkBox=findViewById(R.id.Login_checkadmin);

        //initalizing formvalidator objrct
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(Login.this);
        //adding the style for validation
        awesomeValidation=new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);

        //add validation for Email
        awesomeValidation.addValidation(this,R.id.layout_email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.layout_password, RegexTemplate.NOT_EMPTY,R.string.invalid_password);

        //Login button action
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isconnected(Login.this))
                {
                    if (awesomeValidation.validate()) {
                        String username = emailparser(userName.getText().toString());
                        String password = Password.getText().toString();
                        progressDialog.setTitle("Signing in");
                        progressDialog.setMessage("Please Wait");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();
                        if (checkBox.isChecked()) {
                            //Login as admin
                            DatabaseReference Admindb = FirebaseDatabase.getInstance().getReference().child("Admin");
                            try {
                                DatabaseReference adminInstance = Admindb.child(username);
                                adminInstance.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        //Data from backend
                                        try {
                                            dbusername = snapshot.child("adminname").getValue().toString();
                                            dbpassword = snapshot.child("password").getValue().toString();
                                        } catch (Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(Login.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                                        }
                                        if (username.equals(dbusername) && password.equals(dbpassword)) {
                                            firebaseAuth.signInWithEmailAndPassword(userName.getText().toString(),password)
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if(task.isSuccessful())
                                                            {
                                                                progressDialog.dismiss();
                                                                //redirect to the Home page
                                                                Toast.makeText(Login.this, "Login Sucessful as admin", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(Login.this, AdminActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }
                                                        }
                                                    });


                                        } else {
                                            progressDialog.dismiss();
                                            Toast.makeText(Login.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        progressDialog.dismiss();
                                        Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(Login.this, "Invalid Credentail", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            //Login as User
                            progressDialog.setTitle("Signing in");
                            progressDialog.setMessage("Please Wait");
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.show();
                            DatabaseReference Admindb = FirebaseDatabase.getInstance().getReference().child("User");
                            DatabaseReference userInstence = Admindb.child(username);
                            userInstence.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    try {
                                        dbusername = snapshot.child("username").getValue().toString();
                                        dbpassword = snapshot.child("password").getValue().toString();
                                        if (dbusername!=null && dbpassword!=null){
                                            firebaseAuth.signInWithEmailAndPassword(userName.getText().toString(), password)
                                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if (task.isSuccessful()) {
                                                                progressDialog.dismiss();
                                                                Toast.makeText(Login.this, "Login Sucessfull as user", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(Login.this, UserActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {
                                                                progressDialog.dismiss();
                                                                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                    });

                                        }
                                    } catch (Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(Login.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }
                    } else {
                        Toast.makeText(Login.this, "Please Enter the valid Details", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Login.this, "Check your Internet Conectivity", Toast.LENGTH_SHORT).show();
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
    public String emailparser(String Email){
        String temp="";
        String[] split_email=Email.split("[@]");
        for(int j=0;j<=split_email.length-1;j++) {
            temp=split_email[j];
            break;
        }
        return temp;
    }

    //Check the internet Connectivity
    private boolean isconnected(Login splashactivity) {
        Log.d(TAG, "onClick: 2");

        ConnectivityManager connectivityManager= (ConnectivityManager) splashactivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        //Check for Wifi and mobile network
        NetworkInfo wificon= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobilecon= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wificon!=null && wificon.isConnected())||(mobilecon!=null&&mobilecon.isConnected()))
        {
            Log.d(TAG, "onClick: internet on");
            return true;
        }
        else
        {
            Log.d(TAG, "onClick: internet off");
            return false;
        }

    }

   //useless
    private void showDailog() {
        Log.d(TAG, "onClick: Showing dailog");
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage("Please Connect to internet to Proceed Further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: start");
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        Log.d(TAG, "onClick: end");

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


    }
}