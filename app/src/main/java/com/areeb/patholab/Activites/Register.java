package com.areeb.patholab.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.areeb.patholab.Fragments.Home;
import com.areeb.patholab.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText rg_fullName, rg_phone, rg_email, rg_password, rg_address,reg_gender;
    CardView register_btn;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    ProgressBar reg_pro;
    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        rg_fullName = findViewById(R.id.reg_fullname);
        rg_phone = findViewById(R.id.reg_phone_number);
        rg_email = findViewById(R.id.reg_email);
        rg_password = findViewById(R.id.reg_pass);
        rg_address = findViewById(R.id.reg_address);
        register_btn = findViewById(R.id.register_btn);
        reg_pro = findViewById(R.id.reg_pro);
        reg_gender= findViewById(R.id.reg_sex);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_fun();
            }

        });


    }

    private void register_fun() {

        Log.e("check","pahse1");
        reg_pro.setVisibility(View.VISIBLE);
        String email, password, reg_address, phone, fullname;
        email = rg_email.getText().toString();
        password = rg_password.getText().toString();
        reg_address = rg_address.getText().toString();
        phone = rg_phone.getText().toString();
        fullname = rg_fullName.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "please fill email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "please fill password", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(reg_address)) {
            Toast.makeText(getApplicationContext(), "please fill confirm_password", Toast.LENGTH_SHORT).show();
        }

        Log.e("check","pahse2");

        //creating new user logic

        try{


            Log.e("check","pahse3");
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        try{

                            Toast.makeText(getApplicationContext(), "Welcome to the family", Toast.LENGTH_SHORT).show();
                            Log.e("check","pahse4");

                            FirebaseUser appuser = mAuth.getCurrentUser();
                            DocumentReference df = fstore.collection("Users").document(appuser.getUid());
                            HashMap<String, Object> userinfo = new HashMap<>();
                            userinfo.put("Fullname", rg_fullName.getText().toString());
                            userinfo.put("UserEmail", rg_email.getText().toString());
                            userinfo.put("Phone", rg_phone.getText().toString());
                            userinfo.put("isAdmin", "0");
                            userinfo.put("UserAddress",rg_address.getText().toString());

                            userinfo.put("Usergender",reg_gender.getText().toString());

                            Log.e("check","pahse5");

                            df.set(userinfo);

                            Log.e("check","pahse6");

                            Log.e("check","pahse8");
                            fun_newStrat();

//                        } else {
//                            Log.e("check","else pahse");
//                            Toast.makeText(getApplicationContext(), "some error happen in backend", Toast.LENGTH_SHORT).show();
//
//
//                        }
                        }catch (Exception e){
                            Log.e(TAG, e.getMessage());
                        }

                        }


                }
            });

        }catch (Exception e){
            Log.e("check","exceptio phase");
            Log.e("bhai",e.toString());
        }


    }

    public void fun_newStrat(){
        Intent intent = new Intent(new Intent(Register.this, Home.class));
        Log.e("check","pahse7");
        reg_pro.setVisibility(View.GONE);
        startActivity(intent);
    }

    public void new_reg_fun(){
        reg_pro.setVisibility(View.VISIBLE);
        String email,password,confirm_pass,phone,fullname;

        try{

        }catch(Exception e ){
            Log.e("TAG",e.getMessage().toString());
        }

    }
}