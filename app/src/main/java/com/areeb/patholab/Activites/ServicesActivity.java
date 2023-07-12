package com.areeb.patholab.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.areeb.patholab.R;

public class ServicesActivity extends AppCompatActivity {

    TextView AdminEmail,Phone,LabName,otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        AdminEmail = findViewById(R.id.AdminEmail);
        Phone = findViewById(R.id.Phone);
        LabName = findViewById(R.id.LabName);

        otp = findViewById(R.id.otp);

        AdminEmail.setText(getIntent().getStringExtra("AdminEmail"));
        Phone.setText(getIntent().getStringExtra("Phone"));
        LabName.setText(getIntent().getStringExtra("LabName"));
        otp.setText("otp" + " " +"-" + getIntent().getStringExtra("otp"));




    }
}