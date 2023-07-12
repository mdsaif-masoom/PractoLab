package com.areeb.patholab.Activites;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.areeb.patholab.MainActivity;
import com.areeb.patholab.Model.Appointment_Model;
import com.areeb.patholab.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.okhttp.internal.DiskLruCache;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Appointment extends AppCompatActivity {

    TimePicker timePicker;
     CalendarView datePicker;
    String Fullname, AdminEmail, Phone;
    String test;

    String patient_name, patient_email, patient_phone;
    CardView getOnboard;

    /*All Firebase intilizations*/
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore fstore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        timePicker = findViewById(R.id.time);
       datePicker = findViewById(R.id.calender);
        getOnboard = findViewById(R.id.getOnBoard);


        //intent get functions
        Fullname = getIntent().getStringExtra("Fullname").toString();
        AdminEmail = getIntent().getStringExtra("AdminEmail").toString();
        Phone = getIntent().getStringExtra("Phone").toString();


        //Firebase

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fstore = FirebaseFirestore.getInstance();

        Log.e("userxx", firebaseUser.getUid().toString());

        //Fetching data of current user ;
        fetchingCurrentUserData();


        getOnboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserAppointmentDetails();
                setGetOnboardFun();
            }

        });


    }


    private void fetchingCurrentUserData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference df = fstore.collection("Users").document(currentUser.getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                patient_name = documentSnapshot.getString("Fullname");
                patient_phone = documentSnapshot.getString("Phone");
                patient_email = documentSnapshot.getString("UserEmail");


            }
        });

    }

    private void setGetOnboardFun() {

        String current_user = firebaseUser.getDisplayName().toString();
        DocumentReference df = fstore.collection("Admins").document(getIntent().getStringExtra("AdminId").toString()).collection("Appointments").document();
        HashMap<String, Object> appointment_info = new HashMap<>();
        appointment_info.put("patient_name", patient_name);
        appointment_info.put("patient_email", patient_email);
        appointment_info.put("patient_phone", patient_phone);
        df.set(appointment_info);
        Toast.makeText(getApplicationContext(), "Appointment done ", Toast.LENGTH_SHORT).show();
        Intent doneIntent = new Intent(Appointment.this, MainActivity.class);
        startActivity(doneIntent);


    }


    private void getUserAppointmentDetails() {

        String current_user = firebaseAuth.getCurrentUser().getUid();
        Log.e("getCurrentUser", current_user.toString());
        DocumentReference df = fstore.collection("Users").document(current_user).collection("UserAppointments").document();
        HashMap<String, Object> LabAppointment_info = new HashMap<>();
        LabAppointment_info.put("LabName", Fullname);
        LabAppointment_info.put("LabEmail", AdminEmail);
        LabAppointment_info.put("LabPhone", Phone);
        df.set(LabAppointment_info);
    }


}