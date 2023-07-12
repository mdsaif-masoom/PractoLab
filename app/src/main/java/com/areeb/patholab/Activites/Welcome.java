package com.areeb.patholab.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.areeb.patholab.MainActivity;
import com.areeb.patholab.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button welcome_btn = findViewById(R.id.welcome_btn);
        welcome_btn.setOnClickListener(new View.OnClickListener() {

            FirebaseAuth mAuth;
            FirebaseUser loginuser;
            @Override
            public void onClick(View view) {

                loginuser= FirebaseAuth.getInstance().getCurrentUser();
                if(loginuser != null){

                    Intent activity = new Intent(Welcome.this, MainActivity.class);
                    startActivity(activity);

                }else {
                    Toast.makeText(getApplicationContext(), "Please Login ", Toast.LENGTH_SHORT).show();

                    Intent pleaseLogin = new Intent(Welcome.this,Login.class);
                    startActivity(pleaseLogin);
                }




            }
        });
    }
}