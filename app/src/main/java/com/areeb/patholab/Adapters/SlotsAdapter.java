package com.areeb.patholab.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.areeb.patholab.Fragments.Slots;
import com.areeb.patholab.ItemClicked.SlotSelectedListener;
import com.areeb.patholab.Model.SlotsModel;
import com.areeb.patholab.R;

import java.util.ArrayList;

public class SlotsAdapter extends RecyclerView.Adapter<SlotsAdapter.mViewHolder> {
ArrayList<SlotsModel> slotsModelList;
Context context;
SlotSelectedListener slotListner;

    public SlotsAdapter(ArrayList<SlotsModel> slotsModelList, Context context, Slots slotListner) {
        this.slotsModelList = slotsModelList;
        this.context = context;
        this.slotListner = slotListner;
    }


    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.slots_items,parent,false);
        return  new mViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {

        SlotsModel LabSlotsModel = slotsModelList.get(position);
        holder.LabName.setText(LabSlotsModel.getLabName());
        Log.e("testing1",LabSlotsModel.getLabName().toString());
        holder.service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slotListner.onHomeItemClicked(slotsModelList.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return (slotsModelList == null) ? 0 :slotsModelList.size();
    }

    public class mViewHolder extends  RecyclerView.ViewHolder{

        TextView LabName;
        CardView service;
        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            LabName = itemView.findViewById(R.id.AdminName);
            service = itemView.findViewById(R.id.service);
        }
    }
}
