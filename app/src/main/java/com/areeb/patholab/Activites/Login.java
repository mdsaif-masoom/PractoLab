package com.areeb.patholab.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.areeb.patholab.MainActivity;
import com.areeb.patholab.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {


    private EditText email;
    private EditText password;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    TextView skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.Login_email);
        password = findViewById(R.id.Login_password);
        Button login_btn = findViewById(R.id.login_btn);
        progressBar = findViewById(R.id.progress_bar);
        skip = findViewById(R.id.skip);

        LinearLayout createnew = findViewById(R.id.createNew);

        createnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startRegister = new Intent(Login.this, Register.class);
                startActivity(startRegister);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginFun();


            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,MainActivity.class));
            }
        });

    }

    public void loginFun() {
        progressBar.setVisibility(View.VISIBLE);

        String email_i = email.getText().toString();
        String password_i = password.getText().toString();

        if (TextUtils.isEmpty(email_i)) {
            Toast.makeText(getApplicationContext(), "emil is empty ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password_i)) {
            Toast.makeText(getApplicationContext(), "please fill the password", Toast.LENGTH_SHORT).show();

            return;
        }

        mAuth.signInWithEmailAndPassword(email_i, password_i).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "welcome 😁", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent (Login.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


}