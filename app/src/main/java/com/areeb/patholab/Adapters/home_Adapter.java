package com.areeb.patholab.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.areeb.patholab.Activites.DetailsActivity;
import com.areeb.patholab.ItemClicked.HomeSelectListener;
import com.areeb.patholab.Model.pathlabModel;
import com.areeb.patholab.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class home_Adapter extends RecyclerView.Adapter<home_Adapter.mViewHolder> {

    ArrayList<pathlabModel> pathlabModelArrayList;
    Context context;

    HomeSelectListener  homeListner;

    public home_Adapter(ArrayList<pathlabModel> pathlabModelArrayList, Context context, HomeSelectListener homelistner) {
        this.pathlabModelArrayList = pathlabModelArrayList;
        this.homeListner = homelistner;
        this.context = context;

    }

    @NonNull
    @Override
    public home_Adapter.mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.home_item, parent, false);

        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull home_Adapter.mViewHolder holder, int position) {


        pathlabModel labmodel = pathlabModelArrayList.get(position);
        holder.pathlabName.setText(labmodel.getFullname());


        //glide for image processing in recycler view
//        Glide.with(context).load(labmodel.getProfilePic().toString()).into(holder.Labimage);
        //clickListner
        holder.homeCradView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeListner.onHomeItemClicked(pathlabModelArrayList.get(holder.getAdapterPosition()));

            }
        });


    }

    @Override
    public int getItemCount() {
        return pathlabModelArrayList.size();
    }

    public static class mViewHolder extends RecyclerView.ViewHolder {

        TextView pathlabName;
        ImageView Labimage;
        CardView homeCradView;

        public mViewHolder(@NonNull View itemView) {
            super(itemView);

            pathlabName = itemView.findViewById(R.id.Labname);
            Labimage = itemView.findViewById(R.id.LabImage);
            homeCradView= itemView.findViewById(R.id.homecard);
        }
    }


}
