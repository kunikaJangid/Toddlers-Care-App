package com.example.careapp; 
 
import android.annotation.SuppressLint; import android.content.Intent; import android.os.Bundle; import android.view.View; 
 
import androidx.appcompat.app.AppCompatActivity; 
 import 
com.google.android.material.bottomnavigation.BottomNavi gationView; 
import com.google.firebase.auth.FirebaseAuth; import com.google.firebase.auth.FirebaseUser; 
 
public class  MainActivity extends AppCompatActivity { 
    FirebaseAuth mAuth; 
    BottomNavigationView bottomNavigationView; 
    //CardView first_card; 
 
    @SuppressLint("NonConstantResourceId") 
    @Override 
    protected void onCreate(Bundle savedInstanceState) 
{ 
        super.onCreate(savedInstanceState);         setContentView(R.layout.activity_main);         bottomNavigationView = findViewById(R.id.bottom_navigation);         mAuth = FirebaseAuth.getInstance(); 
        //first_card = findViewById(R.id.cardiologist);          
bottomNavigationView.setSelectedItemId(R.id.home); 
         
bottomNavigationView.setOnNavigationItemReselectedListe ner(item -> { 


            switch (item.getItemId()) { 
                case R.id.home: 
                    return;                 case R.id.profile:                     startActivity(new 
Intent(getApplicationContext(), profile.class));                     overridePendingTransition(0,0); 
            } 
        }); 
    } 
 
    @Override 
    protected void onStart() {         super.onStart(); 
        FirebaseUser user = mAuth.getCurrentUser();         if(user == null) 
        { 
            startActivity(new Intent(MainActivity.this, login_page.class)); 
        } 
    }  
    public void Onclick(View view) {         startActivity(new 
Intent(MainActivity.this,doctors_list.class)); 
    } 
} 
 	
