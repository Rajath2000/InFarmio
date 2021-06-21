package com.example.infarmio;

import android.os.Bundle;

import androidx.annotation.ArrayRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "running for";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }
    RecyclerView review;
    CardAdapterUser adapter;
    ArrayList<String> fav=new ArrayList<>();
    ArrayList<String> temp=new ArrayList<>();


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        review = view.findViewById(R.id.review);
        review.setLayoutManager(new LinearLayoutManager(getContext()));



//        FirebaseRecyclerOptions<postmodel> temp=new FirebaseRecyclerOptions.Builder<postmodel>().build();

               String Username=emailparser(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                DatabaseReference allItems=FirebaseDatabase.getInstance().getReference().child("User").child(Username).child("Myfavratious");
                allItems.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data:snapshot.getChildren())
                        {
                            String itemName = data.getValue(String.class);
                            fav.add(itemName);

                        //                            adapter.notifyDataSetChanged();
//                            adapter.r
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (String item:fav)
                {
//                    Log.d(TAG, "onCreateView: "+item);
                    if(item.length()!=0)
                    {
                        temp.add(item);
                        Log.d(TAG, "run: "+item);

                    }
                }
            }
        },3000);


//             options = new FirebaseRecyclerOptions.Builder<>().;
        FirebaseRecyclerOptions<postmodel> options =  new FirebaseRecyclerOptions.Builder<postmodel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Post"), postmodel.class).build();
        adapter = new CardAdapterUser(options,temp);
        review.refreshDrawableState();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                review.setAdapter(adapter);
            }
        },3000);
        return  view;
    }



    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
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
//    public ArrayList<String> getAllFavitems()
//    {
//        ArrayList<String> AllItems=new ArrayList<>();
//
//        ArrayList<String> MyItems=new ArrayList<>();
//        String Username=emailparser(FirebaseAuth.getInstance().getCurrentUser().getEmail());
//        DatabaseReference allItems=FirebaseDatabase.getInstance().getReference().child("AllItems");
//        allItems.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot data : snapshot.getChildren()) {
//                    String itemName = data.getValue(String.class);
//                    AllItems.add(itemName);
//                    Log.d(TAG, "onDataChange Allitems: "+AllItems);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//        if(temp.contains("All Crops"))
//        {
//            AllItems.remove(0);
//            MyItems=AllItems;
//        }
//        else
//        {
//            for (String item:temp)
//            {
//                if(item.length()!=0)
//                {
//                    MyItems.add(item);
//                }
//            }
//
//        }
//
//        for (String item:MyItems)
//        {
//            Log.d(TAG, "getAllFavitems: "+item);
//        }
//
//        return MyItems;
//    }

}