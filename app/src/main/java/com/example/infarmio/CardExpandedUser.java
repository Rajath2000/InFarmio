package com.example.infarmio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.android.material.chip.Chip;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardExpandedUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardExpandedUser extends Fragment {

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


    public CardExpandedUser() {
        // Required empty public constructor
    }

    public CardExpandedUser(
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
         * @return A new instance of fragment CardExpandedUser.
         */
    // TODO: Rename and change types and number of parameters
    public static CardExpandedUser newInstance(String param1, String param2) {
        CardExpandedUser fragment = new CardExpandedUser();
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
        View view = inflater.inflate(R.layout.fragment_card_expanded_user, container, false);
        CircleImageView profileImage=view.findViewById(R.id.expanded_profile_img);
        TextView Username=view.findViewById(R.id.expanded_username);
        Chip Catagory=view.findViewById(R.id.expanded_catagory);
        RoundedImageView postImage=view.findViewById(R.id.expanded_post_img);
        TextView Title=view.findViewById(R.id.expanded_title);
        TextView Problem=view.findViewById(R.id.expanded_problems);
        TextView Solution=view.findViewById(R.id.expanded_solution);
        TextView Contact=view.findViewById(R.id.expanded_contact);
        TextView References=view.findViewById(R.id.expanded_reference);


        Glide.with(getContext()).load(profileurl).into(profileImage);
        Username.setText(username);
        Catagory.setText(catagory);
        Glide.with(getContext()).load(image).into(postImage);
        Title.setText(title);
        Problem.setText(probem);
        Solution.setText(solution);
        Contact.setText(contactNumber);
        References.setText(reference);


        Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+Contact.getText().toString()));
                getContext().startActivity(i);
            }
        });

        References.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(References.getText().toString()));
                getContext().startActivity(i);
            }
        });


        return  view;
    }
    public void onBackpressed()
    {
        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.post_frame,new HomeFragment()).addToBackStack(null).commit();
    }
}