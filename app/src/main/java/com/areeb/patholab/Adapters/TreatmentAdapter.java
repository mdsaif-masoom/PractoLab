package com.areeb.patholab.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.areeb.patholab.Model.treatmentList;
import com.areeb.patholab.R;
import com.google.protobuf.StringValue;

import java.util.ArrayList;

import javax.annotation.RegEx;

public class TreatmentAdapter extends  RecyclerView.Adapter<TreatmentAdapter.newViewHolder> {
    ArrayList<treatmentList> treatmentLists;
    Context context;

    public TreatmentAdapter(ArrayList<treatmentList> treatmentLists, Context context) {
        this.treatmentLists = treatmentLists;
        this.context = context;
    }

    @NonNull
    @Override
    public newViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_of_treatment,parent,false);

        return new newViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull newViewHolder holder, int position) {
        treatmentList list = treatmentLists.get(position);
        holder.treatnaame.setText(list.getTreatmentName());
//
        holder.price.setText(list.getTreatmentPrice());
        int nextPrice = Integer.parseInt(list.getTreatmentPrice());


//        int sum = 0 ;
//        if(holder.treatnaame.isChecked()){
//            sum  += list.getTreatmentPrice();
//        }
//        Log.e("sum", String.valueOf(sum));


    }

    @Override
    public int getItemCount() {
        return treatmentLists.size();
    }

    public class newViewHolder extends  RecyclerView.ViewHolder {
    CheckBox treatnaame;
        TextView price;

        public newViewHolder(@NonNull View itemView) {
            super(itemView);
            treatnaame = itemView.findViewById(R.id.treatmentNameUser);
            price = itemView.findViewById(R.id.treatmentPrice);
        }
    }
}

