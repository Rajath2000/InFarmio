package com.example.infarmio;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }







    CardView logout;
    CardView favratious;
    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth,mAuth;
    CircleImageView imageView;
    TextView username;
    TextInputEditText phone;
    DatabaseReference databaseReference;
    String Url;
    String Phone;
    Uri filpath;
    Bitmap bitmap;
    Button save;
    AwesomeValidation awesomeValidation;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        //id to obj
        logout=view.findViewById(R.id.logout);
        imageView=view.findViewById(R.id.profile_image);
        username=view.findViewById(R.id.profilr_name);
        phone=view.findViewById(R.id.profile_phone_text);
        save=view.findViewById(R.id.Save_button);
        favratious=view.findViewById(R.id.profile_saved_post);

        mAuth=FirebaseAuth.getInstance();
        String Username=emailparser(mAuth.getCurrentUser().getEmail());




        databaseReference= FirebaseDatabase.getInstance().getReference().child("User").child(Username);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Url=snapshot.child("profileurl").getValue().toString();
                Phone=snapshot.child("phone").getValue().toString();
                try {
                    Glide.with(getContext()).asBitmap().load(Url).into(imageView);
                }catch (Exception e)
                {

                }
                username.setText(Username);
                phone.setText(Phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Signup.this, "clicked", Toast.LENGTH_SHORT).show();
                //Asking permission from user
                Dexter.withActivity(getActivity())
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



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Validation Style
                awesomeValidation = new AwesomeValidation(ValidationStyle.TEXT_INPUT_LAYOUT);
                awesomeValidation.addValidation(getActivity(),R.id.Profile_phone, "[5-9]{1}[0-9]{9}$",R.string.invalid_phone);
                if (awesomeValidation.validate()) {
                    if (filpath != null)
                        uploadtofirebase(filpath);
                    else
                        uploadtofirebaseonlyphone();
                }
            }
        });

        favratious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference UserDb = FirebaseDatabase.getInstance().getReference().child("User").child(Username);
                UserDb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String Password;
                        String username;
                        Password=snapshot.child("password").getValue().toString();
                        username=mAuth.getCurrentUser().getEmail();
                        Intent intent = new Intent(getContext(),favorites_activity.class);
                        intent.putExtra("Username",username);
                        intent.putExtra("Password",Password);
                        startActivity(intent);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=new ProgressDialog(getContext());
                progressDialog.setTitle("Signing out");
                progressDialog.setMessage("Please Wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                FirebaseAuth.getInstance().signOut();
                progressDialog.dismiss();
                Intent intent=new Intent(getContext(),Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

       return view;
  }


    //rendering selected image from galary
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==getActivity().RESULT_OK)
        {
            filpath=data.getData();
            try{
                InputStream inputStream=getActivity().getContentResolver().openInputStream(filpath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            }catch (Exception e)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadtofirebase(Uri filpath)
    {
        //get the Details From Frontend

        String Phone=phone.getText().toString();
        String profileUrl;
        String Username=emailparser(mAuth.getCurrentUser().getEmail());

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Saving Changes");
        progressDialog.setMessage("please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //FireStore object
        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference uploader=storage.getReference(Username).child("profileimage");


        uploader.putFile(filpath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            private static final String TAG ="l" ;

                            @Override
                            public void onSuccess(Uri uri) {
                                //FirebaseDatabase Objects
                                DatabaseReference UserDb = FirebaseDatabase.getInstance().getReference().child("User");

                                    //Creating the object of USER to upload information
                                    Log.d(TAG, "onSuccess: " + uri);
                                    UserDb.child(Username).child("profileurl").setValue(uri.toString());
                                    UserDb.child(Username).child("phone").setValue(Phone).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                        }
                                    });
                            }
                        });
                    }
                });


    }

    private void uploadtofirebaseonlyphone()
    {
        //get the Details From Frontend
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Saving Changes");
        progressDialog.setMessage("please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String Phone=phone.getText().toString();
        String Username=emailparser(mAuth.getCurrentUser().getEmail());

        DatabaseReference UserDb = FirebaseDatabase.getInstance().getReference().child("User");
        UserDb.child(Username).child("phone").setValue(Phone).addOnSuccessListener(new OnSuccessListener<Void>() {
            private static final String TAG = "added";

            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "onSuccess: ");
                progressDialog.dismiss();
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