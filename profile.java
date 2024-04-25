package com.example.careapp; 
 
import android.annotation.SuppressLint; 
import android.content.Intent; 
import android.os.Bundle; 
import android.widget.Button; 
 
import androidx.appcompat.app.AppCompatActivity; 
 
import 
com.google.android.material.bottomnavigation.BottomNavi
 gationView; 
import com.google.firebase.auth.FirebaseAuth; 
 
public class profile extends AppCompatActivity { 
    Button logout; 
    FirebaseAuth mAuth; 
 
    BottomNavigationView bottomNavigationView; 
    @SuppressLint("NonConstantResourceId") 
    @Override 
    protected void onCreate(Bundle savedInstanceState) 
{ 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_profile); 
 
        logout = findViewById(R.id.logout); 
        mAuth = FirebaseAuth.getInstance(); 
        logout.setOnClickListener(view -> { 
            mAuth.signOut(); 
            startActivity(new Intent(profile.this, 
login_page.class)); 
        }); 
 
 
        bottomNavigationView = 
findViewById(R.id.bottom_navigation); 
 
        
bottomNavigationView.setSelectedItemId(R.id.profile); 
 
        
bottomNavigationView.setOnNavigationItemReselectedListe
 ner(item -> { 
            switch (item.getItemId()) { 
                case R.id.home: 
                    startActivity(new 
Intent(getApplicationContext(),MainActivity.class)); 
                    overridePendingTransition(0,0); 
                    return; 
                case R.id.profile: 
                    return; 
                default: 
                    throw new 
IllegalStateException("Unexpected value: " + 
item.getItemId()); 
            } 
        }); 
    } 
} 
