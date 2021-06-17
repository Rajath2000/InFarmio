package com.example.infarmio;

import android.app.ProgressDialog;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


//////////////////////////    Declearing all required variables ///////////////////////////////

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ArrayList<String> favratois;
    ArrayList<String> userselected;

    String Username;

    DatabaseReference itemInstence;



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
        favratois=new ArrayList<>();
        itemInstence= FirebaseDatabase.getInstance().getReference().child("AllItems");
        itemInstence.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void  onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot data : snapshot.getChildren()) {
                            String itemName = data.getValue(String.class);
                            favratois.add(itemName);

                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        //calling adaptorclass
               FavAdapter myAdapter=new FavAdapter(favratois);
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       recyclerView.setAdapter(myAdapter);

                   }
               },3000);





               //////////////////////////////////////////////////

        //When User clicks letsgo button
        //list which hods users choice
        userselected=new ArrayList<>();
        //on button action
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            private static final String TAG ="DataFRagment" ;
            @Override
            public void onClick(View v) {
                //calling function to retrive user choice from adapter class
                userselected=myAdapter.UserSelectedDetails();

                //Debug
                for (String items:userselected)
                {
                    Log.d(TAG, "onClick:  "+items);
                }
            }
        });

























        return view;
    }
}

