package com.example.infarmio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.siyamed.shapeimageview.RoundedImageView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;

import de.hdodenhof.circleimageview.CircleImageView;

public class CardAdapter extends FirebaseRecyclerAdapter<postmodel,CardAdapter.myviewholder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CardAdapter(@NonNull FirebaseRecyclerOptions<postmodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull postmodel model) {
        holder.card_username.setText(model.getUsername());
        holder.card_title.setText(model.getTitle());
        holder.card_catagory.setText(model.getCatagory());
        Glide.with(holder.card_image.getContext()).load(model.getImage()).into(holder.card_image);
        Glide.with(holder.card_img.getContext()).load(model.getProfileurl()).into(holder.card_img);

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card,parent,false);
        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{
        CircleImageView card_img;
        TextView card_username;
        Chip card_catagory;
        RoundedImageView card_image;
        TextView card_title;
        MaterialCardView card_parent;

        public myviewholder(@NonNull View itemView) {
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
