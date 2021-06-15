package com.example.infarmio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.ContentValues.TAG;

public class Signup extends AppCompatActivity {
    TextView loginText;
    TextInputEditText Email,PhoneNumber,Password;
    Button Signup;
    FirebaseAuth authentication;
    ProgressDialog mLoadingBar;

    Uri filpath;
    Bitmap bitmap;

    CircleImageView ProfileImage;

    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //ALL objects Initialization
        loginText = (TextView) findViewById(R.id.Signup_reditecttologin);
        Email = findViewById(R.id.Signup_email);
        PhoneNumber = findViewById(R.id.Signup_phone);
        Password = findViewById(R.id.Signup_password);
        Signup = findViewById(R.id.SignUp_button);

        //Browsing Image from Gallary
        ProfileImage = (CircleImageView) findViewById(R.id.profile_image);
        ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Signup.this, "clicked", Toast.LENGTH_SHORT).show();
                //Asking permission from user
                Dexter.withActivity(Signup.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                //Move to Gallery page
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                //Setting the type of file
                                intent.setType("image/*");
                                //Might Through Error

                                //noinspection deprecation
                                startActivityForResult(intent.createChooser(intent, "select Image File"), 1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        //Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
        //Validation Rules
        awesomeValidation.addValidation(this, R.id.Signup_layout_username, "^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+$", R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.Signup_layout_phone, "[5-9]{1}[0-9]{9}$", R.string.invalid_phone);
        awesomeValidation.addValidation(this, R.id.Signup_layout_password, RegexTemplate.NOT_EMPTY, R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.Signup_layout_confirmpassword, R.id.Signup_layout_password, R.string.not_matching);


        //signup button action

            Signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Database Authentication variable
                    authentication = FirebaseAuth.getInstance();
                    //setting up dailogbox
                    mLoadingBar = new ProgressDialog(Signup.this);
                    //On form Validates Sucessfully
                    if(isconnected(Signup.this))
                    {
                        if (awesomeValidation.validate() && filpath != null) {
                            //Initalizing loading bar
                            mLoadingBar.setTitle("Registration");
                            mLoadingBar.setMessage("Please Wait");
                            mLoadingBar.setCanceledOnTouchOutside(false);
                            mLoadingBar.show();
                            //get the Details From Frontend
                            String email = Email.getText().toString();
                            String password = Password.getText().toString();
                            authentication.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                uploadtofirebase();
                                                mLoadingBar.dismiss();
                                                //Redirect to MyFavratious page
                                                Intent intent = new Intent(Signup.this,favorites.class);
                                                startActivity(intent);
                                                Toast.makeText(Signup.this, "Registraion Sucessfull", Toast.LENGTH_SHORT).show();


                                            } else {
                                                mLoadingBar.dismiss();
                                                Toast.makeText(Signup.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                        } else {
                            Toast.makeText(Signup.this, "Enter Valid Details/Please ensure imgae is inserted", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(Signup.this, "Please Check your internet Connectivity", Toast.LENGTH_SHORT).show();
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

    //rendering selected image from galary
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            filpath=data.getData();
            try{
                InputStream inputStream=getContentResolver().openInputStream(filpath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                ProfileImage.setImageBitmap(bitmap);
            }catch (Exception e)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadtofirebase()
    {
        //get the Details From Frontend
        String email=emailparser(Email.getText().toString());
        String password=Password.getText().toString();
        String phone=PhoneNumber.getText().toString();
        String profileUrl;
        ArrayList<String> Myfavratious=new ArrayList<>();
        ArrayList<Post> post=new ArrayList<>();
        ArrayList<Refrencingpost> spost=new ArrayList<>();

        //FireStore object
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference uploader=storage.getReference(email).child("profileimage");

        uploader.putFile(filpath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //FirebaseDatabase Objects
                                DatabaseReference UserDb=FirebaseDatabase.getInstance().getReference().child("User");
                                //Creating the object of USER to upload information
                                User user=new User(email,password,Myfavratious,phone,uri.toString(),post,spost);
                                UserDb.child(email).setValue(user);

                            }
                        });
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
    private boolean isconnected(Signup splashactivity) {
        Log.d(TAG, "onClick: 2");

        ConnectivityManager connectivityManager= (ConnectivityManager) splashactivity.getSystemService(Context.CONNECTIVITY_SERVICE);

        //Check for Wifi and
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
}