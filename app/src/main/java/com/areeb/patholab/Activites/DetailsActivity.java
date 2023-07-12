package com.areeb.patholab.Activites;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.areeb.patholab.Adapters.TreatmentAdapter;
import com.areeb.patholab.BottomSheet.BookSlot;
import com.areeb.patholab.Model.treatmentList;
import com.areeb.patholab.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.DocumentCollections;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.StringTokenizer;

public class DetailsActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseFirestore fstore;
    FirebaseDatabase firebaseDatabase;
    TextView labName, labPhone, labemail, testid;
    ArrayList<treatmentList> treatmentListList;

    TreatmentAdapter treatmentadapter;
    RecyclerView TreatmentRecycle;
    LinearLayout make_appointment_btn;

    CheckBox cashOn;
    int fotp;
    String payment ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        labName = findViewById(R.id.Labname);
        labPhone = findViewById(R.id.Phone);
        labemail = findViewById(R.id.labemail);
        testid = findViewById(R.id.testId);
        make_appointment_btn = findViewById(R.id.makeAppointment);
        cashOn = findViewById(R.id.cashOn);

        int max = 10;
        int min = 1;
        int range = max - min + 1;

        String otp;

        Random rand = new Random();
        System.out.println("Random numbers...");
        for (int i = 1; i<= 10; i++) {
            int resRandom = rand.nextInt((9999 - 100) + 1) + 10;
             fotp = resRandom;
        }


        TreatmentRecycle = findViewById(R.id.TreatmentRecycler);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        labName.setText(getIntent().getStringExtra("Fullname"));
        labemail.setText(getIntent().getStringExtra("AdminEmail"));
        labPhone.setText(getIntent().getStringExtra("Phone"));
        testid.setText(getIntent().getStringExtra("AdminId"));


        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        for (int i = 0; i < 4; i++) {
//            int rand = (int)(Math.random() * range) + min;
//
//            // Output is different everytime this code is executed
//
//        }
        otp=String.format(String.valueOf(fotp));
//        otp = String.valueOf((short) Random.nextInt());
//
//    TreatmentRecycle.setHasFixedSize(true);
//    TreatmentRecycle.setLayoutManager(new LinearLayoutManager(this));
//    treatmentListList = new ArrayList<>();
//    treatmentadapter = new TreatmentAdapter(treatmentListList,this);
//    TreatmentRecycle.setAdapter(treatmentadapter);

    treatmentDoneHere();

//

        if (cashOn.isChecked()){
            payment = "offline";// it means it will redirect to Home page
        }else{
            payment =  "online";// it  will redirect to payment gateway
        }

//        Log.e("TEST",testid.toString());
        String finalOtp = otp;
        Log.e("otp",finalOtp.toString());
        make_appointment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BookSlot bookSlotSheet = new BookSlot();
                Bundle bundle = new Bundle();
                bundle.putString("Fullname", labName.getText().toString());
                bundle.putString("AdminEmail", labemail.getText().toString());
                bundle.putString("Phone", labPhone.getText().toString());
                bundle.putString("AdminId", testid.getText().toString());
                bundle.putString("PaymentMode",payment);
                bundle.putString("otp", otp);
                bookSlotSheet.setArguments(bundle);

                bookSlotSheet.show(getSupportFragmentManager(), "bookSlot");

            }
        });

    }

    private void makeAppointment() {

        if (firebaseUser != null) {
            Intent makeappointment = new Intent(DetailsActivity.this, BookSlot.class);
            makeappointment.putExtra("Fullname", labName.getText().toString());
            makeappointment.putExtra("AdminEmail", labemail.getText().toString());
            makeappointment.putExtra("Phone", labPhone.getText().toString());
            makeappointment.putExtra("AdminId", testid.getText().toString());
            startActivity(makeappointment);
        } else {
            Toast.makeText(getApplicationContext(), "Please Login", Toast.LENGTH_SHORT).show();
        }

    }


//    this function will call the the treatment done data from the database
    private void treatmentDoneHere() {
        TreatmentRecycle.setHasFixedSize(true);
        TreatmentRecycle.setLayoutManager(new LinearLayoutManager(this));
        treatmentListList = new ArrayList<>();
        treatmentadapter = new TreatmentAdapter(treatmentListList, getApplicationContext());
        TreatmentRecycle.setAdapter(treatmentadapter);

        fstore.collection("Admins").document(testid.getText().toString()).collection("Treatments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {


                for(DocumentChange dc : value.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
//                        treatmentListList = new ArrayList();
                        treatmentListList.add(dc.getDocument().toObject(treatmentList.class));
//                        treatmentadapter = new TreatmentAdapter(treatmentListList, getApplicationContext());
//                        TreatmentRecycle.setAdapter(treatmentadapter);
                        treatmentadapter.notifyDataSetChanged();
                    }

                }
            }
        });




    }


//    public void newTreatment(){
//
//        fstore.collection("Admins").document(testid.getText().toString()).collection("Treatments").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                for(DocumentChange dc: value.getDocumentChanges()){
//                    if(dc.getType()==DocumentChange.Type.ADDED){
//                        treatmentListList.add(dc.getDocument().toObject(treatmentList.class));
//                    }
//                    treatmentadapter.notifyDataSetChanged();
//                }
//            }
//        });
//
//
//
//    }
}