package com.areeb.patholab.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.areeb.patholab.Activites.ServicesActivity;
import com.areeb.patholab.Adapters.SlotsAdapter;

import com.areeb.patholab.ItemClicked.SlotSelectedListener;
import com.areeb.patholab.Model.SlotsModel;
import com.areeb.patholab.Model.pathlabModel;
import com.areeb.patholab.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class Slots extends Fragment implements  SlotSelectedListener {


    FirebaseAuth firebaseAuth;
    FirebaseFirestore fstore;
    FirebaseUser firebaseUser;
    RecyclerView UserAppointmentRecyclerView;
    ArrayList<SlotsModel> slotsModel;
    SlotsAdapter slotsAdapter;


    public static Slots newInstance(String param1, String param2) {
        Slots fragment = new Slots();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slots, container, false);

        //declaring firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        UserAppointmentRecyclerView = view.findViewById(R.id.AppointmentRecyclerView);


        //Recycler view functions
        UserAppointmentRecyclerView.setHasFixedSize(true);
        UserAppointmentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        slotsModel = new ArrayList<>();
        slotsAdapter = new SlotsAdapter(slotsModel,getContext(),this);

        UserAppointmentRecyclerView.setAdapter(slotsAdapter);


        getAllAppointment();


        return view;
    }


    public void getAllAppointment() {
        fstore.collection("Users").document(firebaseAuth.getCurrentUser().getUid().toString()).collection("UserAppointmentSection").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getContext(), "some error occured", Toast.LENGTH_SHORT).show();
                }

                for (DocumentChange dc : value.getDocumentChanges()) {
                    if (dc.getType() == DocumentChange.Type.ADDED) {

                        slotsModel.add(dc.getDocument().toObject(SlotsModel.class));

                    }
                    slotsAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public void onHomeItemClicked(SlotsModel slotsModellist) {
        Intent service = new Intent(getContext(), ServicesActivity.class);
        service.putExtra("LabName",slotsModellist.getLabName());
        service.putExtra("AdminEmail",slotsModellist.getAdminEmail());
        service.putExtra("Phone",slotsModellist.getPhone());
        service.putExtra("otp",slotsModellist.getOtp());

        startActivity(service);
    }
}