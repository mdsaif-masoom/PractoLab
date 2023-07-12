package com.areeb.patholab.Fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.LazyLoader;
import com.areeb.patholab.Activites.Login;
import com.areeb.patholab.MainActivity;
import com.areeb.patholab.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 *
 *
 */
public class Profile extends Fragment {


    private static final int CALL_PERMISSION_REQUEST_CODE = 112;
    CardView card1,card2 ,card3;
TextView  name ,email,phone;
//LazyLoader nprogressBar;
 FirebaseAuth firebaseAuth;
 FirebaseUser firebaseUser;
 FirebaseFirestore fstore;
CardView logout_btn;
ImageView profile_image;
 FirebaseDatabase firebaseDatabase;
 CardView callus;


    public Profile() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();


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


        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        card1 = view.findViewById(R.id.card1);


        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        logout_btn = view.findViewById(R.id.logout_btn);
        callus = view.findViewById(R.id.support);




        profile_image= view.findViewById(R.id.userImage);

//        nprogressBar= view.findViewById(R.id.nprogress);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser= firebaseAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
//
//        Glide.with(getContext()).load("http://goo.gl/gEgYUd").into(profile_image);

        FirebaseUser currentUser =  firebaseAuth.getCurrentUser();
        DocumentReference df =  fstore.collection("Users").document(currentUser.getUid());
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

//               nprogressBar.setVisibility(View.GONE);
               card1.setVisibility(View.VISIBLE);
//               card2.setVisibility(View.VISIBLE);
//               card3.setVisibility(View.VISIBLE);

               if(documentSnapshot.exists()){
                   String user_name = documentSnapshot.getString("Fullname");
                   String user_email = documentSnapshot.getString("Phone");
                   String user_phone = documentSnapshot.getString("UserEmail");

                   name.setText( user_name);
                   email.setText(user_email);
                   phone.setText(user_phone);


               }

            }
        });


        callus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//             call();

            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();

                Intent logout = new Intent(getActivity(),Login.class);
                startActivity(logout);


            }
        });




        return view;
    }

    void call() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("9162487800"));
            getActivity().startActivity(callIntent);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CALL_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }
    }
}