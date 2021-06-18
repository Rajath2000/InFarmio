package com.example.infarmio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.core.AsyncEventListener;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "Username";
    private static final String ARG_PARAM2 = "Password";

    // TODO: Rename and change types of parameters
    public String Username;
    public String Password;



//////////////////////////    Declearing all required variables ///////////////////////////////

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ArrayList<String> favratois;
    ArrayList<String> myfavratios;



    DatabaseReference itemInstence;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    DatabaseReference userrefrence;


////////////////////////////////////////////////////////////////////////////////////////////////

    public FavFragment() {
        // Required empty public constructor
        //recives the username
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavFragment newInstance(String param1, String param2) {
        FavFragment fragment = new FavFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Username = getArguments().getString(ARG_PARAM1);
            Password =getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        //Converting fragment id to object
        recyclerView=view.findViewById(R.id.review);
        floatingActionButton=view.findViewById(R.id.letsgo);


        //Manager to dispay chips in grid view
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);


        //////////////////Trying with threads////////////////////////////////////
        //Getting favratious list from firebase
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        favratois=new ArrayList<>();
        itemInstence= FirebaseDatabase.getInstance().getReference().child("AllItems");
        itemInstence.addValueEventListener(new ValueEventListener() {
            @Override
            public void  onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot data : snapshot.getChildren()) {
                    String itemName = data.getValue(String.class);
                    favratois.add(itemName);

                }

                //calling adaptorclass
                FavAdapter myAdapter=new FavAdapter(favratois);
                progressDialog.dismiss();
                recyclerView.setAdapter(myAdapter);

                firebaseAuth=FirebaseAuth.getInstance();

                myfavratios=new ArrayList<>();
                //on button action
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    private static final String TAG ="DataFRagment" ;
                    @Override
                    public void onClick(View v) {
                        //calling function to retrive user choice from adapter class
                        myfavratios=myAdapter.UserSelectedDetails();
                        Log.d(TAG, "saving: "+Username);
                        Log.d(TAG,"saving"+Password);
                        userrefrence=FirebaseDatabase.getInstance().getReference().child("User").child(emailparser(Username)).child("Myfavratious");
                        progressDialog.setTitle("Loging in");
                        progressDialog.setMessage("Please Wait");
                        progressDialog.show();
                        userrefrence.setValue(myfavratios).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                firebaseAuth.signInWithEmailAndPassword(Username,Password)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()) {
                                                    progressDialog.dismiss();
                                                    Intent intent = new Intent(getContext(), UserActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                            }
                        });


                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









        //////////////////////////////////////////////////

        //When User clicks letsgo button
        //list which hods users choice

//        firebaseAuth=FirebaseAuth.getInstance();
//
//        myfavratios=new ArrayList<>();
//        //on button action
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            private static final String TAG ="DataFRagment" ;
//            @Override
//            public void onClick(View v) {
//                //calling function to retrive user choice from adapter class
//                myfavratios=myAdapter.UserSelectedDetails();
//                Log.d(TAG, "saving: "+Username);
//               userrefrence=FirebaseDatabase.getInstance().getReference().child("User").child(Username).child("Myfavratious");
//               userrefrence.setValue(myfavratios);
//                Intent intent=new Intent(getContext(),UserActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//
//            }
//        });

























        return view;
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