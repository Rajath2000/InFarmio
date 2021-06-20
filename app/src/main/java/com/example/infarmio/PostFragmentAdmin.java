package com.example.infarmio;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
 * Use the {@link PostFragmentAdmin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragmentAdmin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostFragmentAdmin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostFragmentAdmin.
     */
    // TODO: Rename and change types and number of parameters
    public static PostFragmentAdmin newInstance(String param1, String param2) {
        PostFragmentAdmin fragment = new PostFragmentAdmin();
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


    EditText Title;
    EditText Symtomps;
    EditText Solution;
    EditText ContactNumber;
    EditText References;
    Button Browse;
    Button Submit;
    RoundedImageView imageView;
    AwesomeValidation awesomeValidation;
    ProgressDialog progressDialog;


    Uri filpath;
    Bitmap bitmap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_admin, container, false);
//
//        Title=view.findViewById(R.id.Post_title);
//        Symtomps=view.findViewById(R.id.post_symtoms);
//        Solution=view.findViewById(R.id.post_soution);
//        ContactNumber=view.findViewById(R.id.post_ContactNumber);
//        References=view.findViewById(R.id.post_refernces);
//        Browse=view.findViewById(R.id.post_browse);
//        Submit=view.findViewById(R.id.post_submit);
//        imageView=view.findViewById(R.id.post_image);
//
//
//        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
//
//
//        awesomeValidation.addValidation(getActivity(),R.id.Post_title,RegexTemplate.NOT_EMPTY,R.string.required);
//        awesomeValidation.addValidation(getActivity(),R.id.post_symtoms, RegexTemplate.NOT_EMPTY,R.string.required);
//        awesomeValidation.addValidation(getActivity(),R.id.post_soution, RegexTemplate.NOT_EMPTY,R.string.required);
//        awesomeValidation.addValidation(getActivity(),R.id.post_ContactNumber, "[5-9]{1}[0-9]{9}$",R.string.invalid_phone);
//        awesomeValidation.addValidation(getActivity(),R.id.post_refernces, RegexTemplate.NOT_EMPTY,R.string.required);
//
//
//        Browse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Toast.makeText(Signup.this, "clicked", Toast.LENGTH_SHORT).show();
//                //Asking permission from user
//                Dexter.withActivity(getActivity())
//                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                        .withListener(new PermissionListener() {
//                            @Override
//                            public void onPermissionGranted(PermissionGrantedResponse response) {
//                                //Move to Gallery page
//                                Intent intent = new Intent(Intent.ACTION_PICK);
//                                //Setting the type of file
//                                intent.setType("image/*");
//                                //Might Through Error
//
//                                //noinspection deprecation
//                                startActivityForResult(intent.createChooser(intent, "select Image File"), 1);
//
//                            }
//
//                            @Override
//                            public void onPermissionDenied(PermissionDeniedResponse response) {
//
//                            }
//
//                            @Override
//                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//                                token.continuePermissionRequest();
//                            }
//                        }).check();
//            }
//        });
//
//
//       Submit.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               if(awesomeValidation.validate()&& filpath!=null && validate() )
//               {
//                   uploadtofirebase(filpath);
//               }
//               else
//               {
//                   Toast.makeText(getActivity(), "please enter the valid Details/ensure that image is inserted", Toast.LENGTH_SHORT).show();
//               }
//           }
//       });


        return view;
    }

    //rendering selected image from galary
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode==1 && resultCode==getActivity().RESULT_OK)
//        {
//            filpath=data.getData();
//            try{
//                InputStream inputStream=getActivity().getContentResolver().openInputStream(filpath);
//                bitmap= BitmapFactory.decodeStream(inputStream);
//                imageView.setImageBitmap(bitmap);
//            }catch (Exception e)
//            {
//
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//
//    public boolean validate()
//    {
//        if( Title.getText().length()==0||
//         Symtomps.getText().length()==0||
//         Solution.getText().length()==0||
//         ContactNumber.getText().length()==0||
//         References.getText().length()==0)
//        {
//            return false;
//        }
//        else
//        {
//            return true;
//        }
//
//    }
//
//    private void uploadtofirebase(Uri filpath)
//    {
//        String Username,Catagory;
//        int postCount;
//        //get the Details From Frontend
//        String title=Title.getText().toString();
//        String symtomps=Symtomps.getText().toString();
//        String soution=Solution.getText().toString();
//        String contact=ContactNumber.getText().toString();
//        String references=References.getText().toString();
//
//
//        progressDialog=new ProgressDialog(getContext());
//        progressDialog.setTitle("Saving Changes");
//        progressDialog.setMessage("please wait");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
//
//        //getting requried detais
//        Username=FirebaseAuth.getInstance().getCurrentUser().getEmail();
//        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Admin").child(emailparser(Username));
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//        //FireStore object
//        FirebaseStorage storage=FirebaseStorage.getInstance();
//        StorageReference uploader=storage.getReference(Username).child("profileimage");
//
//
//        uploader.putFile(filpath)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            private static final String TAG ="l" ;
//
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                //FirebaseDatabase Objects
//                                DatabaseReference UserDb = FirebaseDatabase.getInstance().getReference().child("User");
//
//                                //Creating the object of USER to upload information
//                                Log.d(TAG, "onSuccess: " + uri);
//                                UserDb.child(Username).child("profileurl").setValue(uri.toString());
//                                UserDb.child(Username).child("phone").setValue(Phone).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        progressDialog.dismiss();
//                                    }
//                                });
//                            }
//                        });
//                    }
//                });
//
//
//    }
//
//    public String emailparser(String Email){
//        String temp="";
//        String[] split_email=Email.split("[@]");
//        for(int j=0;j<=split_email.length-1;j++) {
//            temp=split_email[j];
//            break;
//        }
//        return temp;
//    }


}