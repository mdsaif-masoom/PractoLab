package com.areeb.patholab.BottomSheet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.areeb.patholab.MainActivity;
import com.areeb.patholab.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;


public class BookSlot extends BottomSheetDialogFragment {
    LinearLayout btnBookSlot;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firestore;
    String Fullname, AdminEmail, Phone, AdminId,otp;
    String patientName, patientEmail, patientPhone, patientAddress, patientGender, paymentMode;
    TimePicker timePicker;
    CalendarView datePicker;
    String Usertime;
    String Userdate;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_slot_dialoguesheet, container, false);
        btnBookSlot = view.findViewById(R.id.btnSlot);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        //Fecthing lab name from activity

        timePicker = view.findViewById(R.id.timepicker);
        datePicker = view.findViewById(R.id.datepicker);
//
//        Userdate = datePicker.getDayOfMonth() + " " + datePicker.getMonth() + " " + datePicker.getYear();
        Userdate = datePicker.getSelectedDates().toString();


        Fullname = this.getArguments().getString("Fullname");
        AdminEmail = this.getArguments().getString("AdminEmail");
        Phone = this.getArguments().getString("Phone");
        AdminId = this.getArguments().getString("AdminId");
        paymentMode = this.getArguments().getString("PaymentMode");
        otp = this.getArguments().getString("otp");



        /*this fetch current user details*/
        fetchCurrentUserDetails();
        Log.e("AdminId", AdminId);
        Log.e("AdminId", Fullname);
        Log.e("AdminId", Phone);
        Log.e("AdminId", AdminEmail);

        btnBookSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminAppointmentData();
                Log.e("AdminId", AdminId);
                UserAppointmentData();

                if (paymentMode.contentEquals("online")) {
                    Toast.makeText(getContext(), "online", Toast.LENGTH_SHORT).show();
                    Intent appointmentDone = new Intent(getActivity(), MainActivity.class);
//                    Toast.makeText(getContext(), "Appointment done", Toast.LENGTH_SHORT).show();
                    startActivity(appointmentDone);
                } else {
                    Intent appointmentDone = new Intent(getActivity(), MainActivity.class);
                    Toast.makeText(getContext(), "Appointment done", Toast.LENGTH_SHORT).show();
                    startActivity(appointmentDone);

                }

            }
        });

        return view;
    }

    //this  function will store the appointment information in the database
    public void AdminAppointmentData() {
        try {
            FirebaseUser currentUse = FirebaseAuth.getInstance().getCurrentUser();
            DocumentReference df = firestore.collection("Admins").document(AdminId).collection("AdminAppointmentSection").document();
            HashMap<String, Object> userInformation = new HashMap<>();
            userInformation.put("patientName", patientName);
            userInformation.put("patientEmail", patientEmail);
            userInformation.put("patientPhone", patientPhone);
            userInformation.put("UserAppointmentTime", timePicker.getCurrentHour());
            userInformation.put("UserAppointmentDate", Userdate);
            userInformation.put("UserAddress", patientAddress);
            userInformation.put("UserGender", patientGender);
            userInformation.put("PaymentMode", paymentMode);
            userInformation.put("otp",otp);
            df.set(userInformation);

        } catch (Exception e) {
            Log.e("codeRedxx", e.toString());

        }


    }

    //this function will store the information the database
    public void UserAppointmentData() {
        try {

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            DocumentReference df = firestore.collection("Users").document(currentUser.getUid()).collection("UserAppointmentSection").document();
            HashMap<String, Object> PathLabInformation = new HashMap<>();
            PathLabInformation.put("LabName", Fullname);
            PathLabInformation.put("AdminEmail", AdminEmail);
            PathLabInformation.put("Phone", Phone);
            PathLabInformation.put("otp",otp);
            df.set(PathLabInformation);

        } catch (Exception e) {
            Log.e("codeRedxx", e.toString());
        }

    }

    public void fetchCurrentUserDetails() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DocumentReference df = firestore.collection("Users").document(currentUser.getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try {
                    patientName = documentSnapshot.getString("Fullname");
                    patientEmail = documentSnapshot.getString("UserEmail");
                    patientPhone = documentSnapshot.getString("Phone");
                    patientAddress = documentSnapshot.getString("UserAddress");
                    patientGender = documentSnapshot.getString("Usergender");


                } catch (Exception e) {
                    Log.e("code red xx", e.toString());

                }
            }
        });

    }
}
