package com.areeb.patholab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.areeb.patholab.Fragments.Home;
import com.areeb.patholab.Fragments.Profile;
import com.areeb.patholab.Fragments.Slots;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView nav;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nav = findViewById(R.id.navnbar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Home()).commit();
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch ((item.getItemId())){
                    case R.id.homefragment:
                        fragment = new Home();
                        break;

                    case R.id.slotsFragments:
                        fragment = new Slots();
                        break;

                    case R.id.profilefragments:


                        if(firebaseUser == null){
                            Toast.makeText(getApplicationContext(), "please Login", Toast.LENGTH_SHORT).show();
                            break;
                        }
                            fragment = new Profile();
                            break;




                }
                Loadfragment(fragment);

                return true;
            }
        });
    }

    private void Loadfragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
    }
}