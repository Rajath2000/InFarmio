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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.android.gms.tasks.OnSuccessListener;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String catagory;
    String contactNumber;
    String image;
    String postid;
    String probem;
    String profileurl;
    String reference;
    String solution;
    String title;
    String username;

    ProgressDialog progressDialog;


    AwesomeValidation awesomeValidation;

    public PostEditFragment() {
        // Required empty public constructor
    }

    public PostEditFragment(
            String catagory,
            String contactNumber,
            String image,
            String postid,
            String probem,
            String profileurl,
            String reference,
            String solution,
            String title,
            String username) {

        this.catagory=catagory;
        this.contactNumber=contactNumber;
        this.image=image;
        this.postid=postid;
        this.probem=probem;
        this.profileurl=profileurl;
        this.reference=reference;
        this.solution=solution;
        this.title=title;
        this.username=username;


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostEditFragment newInstance(String param1, String param2) {
        PostEditFragment fragment = new PostEditFragment();
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



    EditText Post_title;
    EditText post_symtoms;
    EditText post_soution;
    EditText post_ContactNumber;
    EditText post_refernces;
    RoundedImageView post_image;
    Button Submit;
    Button Browse;


    Uri filpath;
    Bitmap bitmap;




    FirebaseStorage storage;
    StorageReference uploader;
    String uniquepostid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_edit, container, false);
        Post_title=view.findViewById(R.id.Post_title);
         post_symtoms=view.findViewById(R.id.post_symtoms);
        post_soution=view.findViewById(R.id.post_soution);
        post_ContactNumber=view.findViewById(R.id.post_ContactNumber);
         post_refernces=view.findViewById(R.id.post_refernces);
         post_image=view.findViewById(R.id.post_image);
         Submit=view.findViewById(R.id.post_submit);
         Browse=view.findViewById(R.id.post_browse);



        Post_title.setText(title);
        post_symtoms.setText(probem);
        post_soution.setText(solution);
        post_ContactNumber.setText(contactNumber);
        post_refernces.setText(reference);
        Glide.with(getContext()).load(image).into(post_image);

        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);


        awesomeValidation.addValidation(getActivity(),R.id.Post_title, RegexTemplate.NOT_EMPTY,R.string.required);
        awesomeValidation.addValidation(getActivity(),R.id.post_symtoms, RegexTemplate.NOT_EMPTY,R.string.required);
        awesomeValidation.addValidation(getActivity(),R.id.post_soution, RegexTemplate.NOT_EMPTY,R.string.required);
        awesomeValidation.addValidation(getActivity(),R.id.post_ContactNumber, "[5-9]{1}[0-9]{9}$",R.string.invalid_phone);
        awesomeValidation.addValidation(getActivity(),R.id.post_refernces, RegexTemplate.NOT_EMPTY,R.string.required);



        Browse.setOnClickListener(new View.OnClickListener() {
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
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation.validate() && validate() )
                {
                    if(filpath!=null)
                    uploadtofirebase(filpath);
                    else
                     uploadtofirebasewithoutuploadingfile();
                }
                else
                {
                    Toast.makeText(getActivity(), "please enter the valid Details/ensure that image is inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
    public boolean validate()
    {
        if( Post_title.getText().length()==0||
                post_symtoms.getText().length()==0||
                post_soution.getText().length()==0||
                post_ContactNumber.getText().length()==0||
                post_refernces.getText().length()==0)
        {
            return false;
        }
        else
        {
            return true;
        }

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
                post_image.setImageBitmap(bitmap);
            }catch (Exception e)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadtofirebase(Uri filpath)
    {
        String Username;
//        final int postcount=0;
        //get the Details From Frontend

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Saving Changes");
        progressDialog.setMessage("please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //getting requried detais
        Username= FirebaseAuth.getInstance().getCurrentUser().getEmail();


        //FireStore object
        storage= FirebaseStorage.getInstance();
        uploader=storage.getReference(username).child("post"+postid);
        uploader.putFile(filpath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            private static final String TAG ="l" ;

                            @Override
                            public void onSuccess(Uri uri) {
                                //FirebaseDatabase Objects
                                try {
                                    DatabaseReference adminInstance = FirebaseDatabase.getInstance().getReference().child("Admin").child(username).child("post").child("post" + postid);
                                    DatabaseReference postDb = FirebaseDatabase.getInstance().getReference().child("Post").child("post"+postid);
                                    //Creating the object of admin to upload information
//                                    postid=postid;
//                                    profileurl=profileurl;
//                                    username=username;
                                    title=Post_title.getText().toString();
                                    probem=post_symtoms.getText().toString();
                                    solution=post_soution.getText().toString();
                                    contactNumber=post_ContactNumber.getText().toString();
                                    reference=post_refernces.getText().toString();
                                    image=uri.toString();
                                    Post post=new Post(postid,profileurl,username,title,probem,solution,catagory,contactNumber,reference,image);
                                    adminInstance.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            progressDialog.dismiss();
                                            postDb.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            });

                                        }
                                    });

                                }
                                catch (Exception e)
                                {
                                }

                            }
                        });
                    }
                });

    }


    public void uploadtofirebasewithoutuploadingfile()
    {
        String Username;
//        final int postcount=0;
        //get the Details From Frontend

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Saving Changes");
        progressDialog.setMessage("please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        try {
            DatabaseReference adminInstance = FirebaseDatabase.getInstance().getReference().child("Admin").child(username).child("post").child("post" + postid);
            DatabaseReference postDb = FirebaseDatabase.getInstance().getReference().child("Post").child("post"+postid);
            //Creating the object of admin to upload information
//                                    postid=postid;
//                                    profileurl=profileurl;
//                                    username=username;
            title=Post_title.getText().toString();
            probem=post_symtoms.getText().toString();
            solution=post_soution.getText().toString();
            contactNumber=post_ContactNumber.getText().toString();
            reference=post_refernces.getText().toString();
            Post post=new Post(postid,profileurl,username,title,probem,solution,catagory,contactNumber,reference,image);
            adminInstance.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressDialog.dismiss();
                    postDb.setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                }
            });

        }
        catch (Exception e)
        {
        }


    }


















    public void onBackpressed()
    {
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.post_frame,new HomeFragment()).addToBackStack(null).commit();
    }
}