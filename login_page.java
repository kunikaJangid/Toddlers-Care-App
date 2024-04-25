package com.example.careapp; 
 
import android.content.Intent; 
import android.os.Bundle; 
import android.text.TextUtils; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.TextView; 
import android.widget.Toast; 
 
import androidx.appcompat.app.AppCompatActivity; 
 
import com.google.firebase.auth.FirebaseAuth; 
 
import java.util.Objects; 
 
public class login_page extends AppCompatActivity { 
    EditText login_email; 
    EditText login_pass; 
    Button login; 
    TextView register_button; 
    FirebaseAuth mAuth; 
 
    @Override 
    protected void onCreate(Bundle savedInstanceState) 
{ 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.activity_login_page); 
 
        register_button = 
findViewById(R.id.register_page); 
        login_email = findViewById(R.id.login_email); 
        login_pass = findViewById(R.id.login_password); 
        login = findViewById(R.id.login); 
        mAuth = FirebaseAuth.getInstance(); 
 
        register_button.setOnClickListener(view -> 
login_page.this.startActivity(new 
Intent(login_page.this, register_page.class))); 
 
        login.setOnClickListener(view -> loginUser()); 
    } 
 
    private void loginUser() 
    { 
        String emails = 
login_email.getText().toString(); 
        String password = 
login_pass.getText().toString(); 
 
        if(TextUtils.isEmpty(emails)) 
        { 
            login_email.setError("Email Cannot be 
Empty"); 
            login_email.requestFocus(); 
        }else if (TextUtils.isEmpty(password)) 
        { 
            login_pass.setError("Password Cannot be 
Empty"); 
            login_pass.requestFocus(); 
        }else 
            { 
                
mAuth.signInWithEmailAndPassword(emails, 
password).addOnCompleteListener(task -> { 
                    if (task.isSuccessful()) { 
                        Toast.makeText(login_page.this, 
"User registered successfully", 
Toast.LENGTH_SHORT).show(); 
                        
login_page.this.startActivity(new 
Intent(login_page.this, MainActivity.class)); 
                    } else { 
                        Toast.makeText(login_page.this, 
"User unsuccessfully: " + 
Objects.requireNonNull(task.getException()).getMessage(
 ), Toast.LENGTH_SHORT).show(); 
                    } 
                }); 
            } 
    } 
}
