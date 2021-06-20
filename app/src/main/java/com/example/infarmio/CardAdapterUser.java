package com.example.infarmio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

import de.hdodenhof.circleimageview.CircleImageView;

public class CardAdapterUser extends FirebaseRecyclerAdapter<postmodel,CardAdapterUser.myviewholderUser> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CardAdapterUser(@NonNull FirebaseRecyclerOptions<postmodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholderUser holder, int position, @NonNull postmodel model) {
        holder.card_username.setText(model.getUsername());
        holder.card_title.setText(model.getTitle());
        holder.card_catagory.setText(model.getCatagory());
        Glide.with(holder.card_image.getContext()).load(model.getImage()).into(holder.card_image);
        Glide.with(holder.card_img.getContext()).load(model.getProfileurl()).into(holder.card_img);

        holder.card_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=(AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.post_frame,new CardExpandedUser(
                        model.getCatagory(),
                        model.getContactNumber(),
                        model.getImage(),
                        model.getPostid(),
                        model.getProbem(),
                        model.getProfileurl(),
                        model.getReference(),
                        model.getSolution(),
                        model.getTitle(),
                        model.getUsername()
                )).addToBackStack(null).commit();
            }
        });



    }

    @NonNull
    @Override
    public myviewholderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card,parent,false);
        return new myviewholderUser(view);
    }

    public class myviewholderUser extends RecyclerView.ViewHolder{
        CircleImageView card_img;
        TextView card_username;
        Chip card_catagory;
        RoundedImageView card_image;
        TextView card_title;
        MaterialCardView card_parent;

        public myviewholderUser(@NonNull View itemView) {
            super(itemView);

            card_img=itemView.findViewById(R.id.card_img);
            card_image=itemView.findViewById(R.id.card_image);
            card_username=itemView.findViewById(R.id.card_username);
            card_catagory=itemView.findViewById(R.id.card_catagory);
            card_title=itemView.findViewById(R.id.card_title);
            card_parent=itemView.findViewById(R.id.card_parent);

        }
    }
}
