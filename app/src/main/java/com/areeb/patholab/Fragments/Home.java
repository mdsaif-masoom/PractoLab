


package com.areeb.patholab.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.areeb.patholab.Activites.DetailsActivity;
import com.areeb.patholab.Adapters.home_Adapter;
import com.areeb.patholab.ItemClicked.HomeSelectListener;
import com.areeb.patholab.Model.pathlabModel;
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

import java.util.ArrayList;


public class Home extends Fragment implements HomeSelectListener {


    TextView welcome_user;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore fstore;
    FirebaseDatabase firebaseDatabase;
    RecyclerView home_recycle;
    ArrayList<pathlabModel> pathlabModelArrayList;
    home_Adapter adapter;


    public Home() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        welcome_user = view.findViewById(R.id.welcome_user);
        home_recycle = view.findViewById(R.id.home_recycle);


        //this function will call the method for Welcome Name;
        getHelloName();

        home_recycle.setHasFixedSize(true);
        home_recycle.setLayoutManager(new LinearLayoutManager(getContext()));
        pathlabModelArrayList = new ArrayList<>();
        adapter = new home_Adapter(pathlabModelArrayList, getContext(), this);
        home_recycle.setAdapter(adapter);


        //this method will all the labs available
        getAllLabs();


        return view;
    }


    public void getHelloName() {
        FirebaseUser current_active_user = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            DocumentReference df = fstore.collection("Users").document(current_active_user.getUid());

            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot.exists()) {
                        String active_user_name = documentSnapshot.getString("Fullname");

                        welcome_user.setText("Hello " + active_user_name);

                    } else {
                        welcome_user.setText("Hello Guest");
                    }
                }
            });

        } else {
            welcome_user.setText("Hello" + " " + "Guest");
        }


    }

    public void getAllLabs() {


        fstore.collection("Admins").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Toast.makeText(getActivity(), "some error happen ", Toast.LENGTH_SHORT).show();
                }


                for (DocumentChange dc : value.getDocumentChanges()) {

                    if (dc.getType() == DocumentChange.Type.ADDED) {
                        pathlabModelArrayList.add(dc.getDocument().toObject(pathlabModel.class));
                    }

                    adapter.notifyDataSetChanged();

                    dc.getDocument().getId();
                    Log.e("areebx", dc.getDocument().getId().toString());


                }

            }
        });


    }


    @Override
    public void onHomeItemClicked(pathlabModel pathlabModelList) {
        try{
            Toast.makeText(getActivity(), pathlabModelList.getFullname().toString(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(getActivity(),"phase 2", Toast.LENGTH_SHORT).show();
            Intent DetailsIntent = new Intent(getContext(), DetailsActivity.class);
//            Toast.makeText(getActivity(),"phase 3", Toast.LENGTH_SHORT).show();
            DetailsIntent.putExtra("Fullname", pathlabModelList.getFullname());
            DetailsIntent.putExtra("AdminEmail", pathlabModelList.getAdminEmail());
            DetailsIntent.putExtra("Phone", pathlabModelList.getPhone());
            DetailsIntent.putExtra("AdminId", pathlabModelList.getId());
//            DetailsIntent.putExtra("ProfilePic", pathlabModelList.getProfilePic());
//
//            Toast.makeText(getActivity(), "phase 3", Toast.LENGTH_SHORT).show();
            DetailsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Toast.makeText(getActivity(), "phase 4", Toast.LENGTH_SHORT).show();
            getContext().startActivity(DetailsIntent);

        }catch (Exception e ){
            Log.e("check",e.toString());

        }



    }
}