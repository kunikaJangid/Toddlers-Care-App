 
import android.content.Intent; 
import android.os.Bundle; 
import android.text.TextUtils; 
import android.util.Log; 
import android.widget.Button; 
import android.widget.EditText; 
import android.widget.Toast; 
 
import androidx.appcompat.app.AppCompatActivity; 
 
import com.google.firebase.auth.FirebaseAuth; 
import com.google.firebase.firestore.DocumentReference; 
import com.google.firebase.firestore.FirebaseFirestore; 
 
import java.util.HashMap; 
import java.util.Map; 
import java.util.Objects; 
 
public class register_page extends AppCompatActivity 
{ 
    EditText email; 
    EditText pass; 
    EditText fname; 
    EditText phone; 
 
    Button register; 
 
    FirebaseAuth mAuth; 
    String user_id; 
    FirebaseFirestore firestore; 
 
 
 
    @Override 
    protected void onCreate(Bundle savedInstanceState) 
{ 
        super.onCreate(savedInstanceState); 
        
setContentView(R.layout.activity_register_page); 
 
        email = findViewById(R.id.email); 
        pass = findViewById(R.id.password); 
        fname = findViewById(R.id.profile_name); 
        phone = findViewById(R.id.phone_number); 
        register = findViewById(R.id.register); 
 
        mAuth = FirebaseAuth.getInstance(); 
        firestore = FirebaseFirestore.getInstance(); 
 
        register.setOnClickListener(view -> 
createUser()); 
    } 
 
    private void createUser() 
    { 
        String emails = email.getText().toString(); 
        String password = pass.getText().toString(); 
        String full_name = fname.getText().toString(); 
        String phone_number =  
phone.getText().toString(); 
 
        if(TextUtils.isEmpty(emails)) 
        { 
            email.setError("Email Cannot be Empty"); 
            email.requestFocus(); 
        }else if (TextUtils.isEmpty(password)) 
        { 
            pass.setError("Password Cannot be Empty"); 
            pass.requestFocus(); 
        }else 
            { 
                
mAuth.createUserWithEmailAndPassword(emails,password).a
 ddOnCompleteListener(task -> { 
                    if (task.isSuccessful()) 
                    { 
                        
Toast.makeText(register_page.this,"User registered 
successfully",Toast.LENGTH_SHORT).show(); 
 
                        user_id = 
Objects.requireNonNull(mAuth.getCurrentUser()).getUid()
 ; 
                        DocumentReference 
documentReference = firestore.collection("User 
data").document(user_id); 
                        Map<String, Object> user = new 
HashMap<>(); 
                        user.put("Full 
Name",full_name); 
                        user.put("Email",emails); 
                        user.put("Phone 
Number",phone_number); 
                        
documentReference.set(user).addOnCompleteListener(task1 -> Log.d("TAG","on Success: "+ user_id)); 
 
                        startActivity(new 
Intent(getApplicationContext(),MainActivity.class)); 
                    }else 
                        { 
                            
Toast.makeText(register_page.this,"User unsuccessfully: 
"+ 
Objects.requireNonNull(task.getException()).getMessage(
 ),Toast.LENGTH_SHORT).show(); 
                        } 
                }); 
            } 
    } 
} 
