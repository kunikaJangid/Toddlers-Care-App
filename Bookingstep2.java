 
import android.content.BroadcastReceiver; 
import android.content.Context; 
import android.content.Intent; 
import android.content.IntentFilter; 
import android.os.Bundle; 
import android.util.Log; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.TextView; 
import android.widget.Toast; 
 
import androidx.annotation.NonNull; 
import androidx.fragment.app.Fragment; 
import 
androidx.localbroadcastmanager.content.LocalBroadcastMa
 nager; 
 
import com.google.android.gms.tasks.OnCompleteListener; 
import com.google.android.gms.tasks.OnFailureListener; 
import com.google.android.gms.tasks.OnSuccessListener; 
import com.google.android.gms.tasks.Task; 
import com.google.firebase.firestore.DocumentReference; 
import com.google.firebase.firestore.FirebaseFirestore; 
 
import java.text.SimpleDateFormat; 
import java.util.Calendar; 
 
import butterknife.BindView; 
import butterknife.ButterKnife; 
import butterknife.OnClick; 
import butterknife.Unbinder; 
 
 
public class BookingStep2Fragment extends Fragment { 
 
    SimpleDateFormat simpleDateFormat; 
    LocalBroadcastManager localBroadcastManager; 
    Unbinder unbinder; 
    @BindView(R.id.txt_booking_berber_text) 
    TextView txt_booking_berber_text; 
    @BindView(R.id.txt_booking_time_text) 
    TextView txt_booking_time_text; 
    @BindView(R.id.txt_booking_type) 
    TextView txt_booking_type; 
    @BindView(R.id.txt_booking_phone) 
    TextView txt_booking_phone; 
 
    @OnClick(R.id.btn_confirm) 
    void confirmeApointement(){ 
        ApointementInformation apointementInformation = 
new ApointementInformation(); 
        
apointementInformation.setApointementType(Common.Curren
 taappointementatype); 
        
apointementInformation.setDoctorId(Common.CurreentDocto
 r); 
        
apointementInformation.setDoctorName(Common.CurrentDoct
 orName); 
        
apointementInformation.setPatientName(Common.CurrentUse
 rName); 
        
apointementInformation.setPatientId(Common.CurrentUseri
 d); 
        
apointementInformation.setChemin("Doctor/"+Common.Curre
 entDoctor+"/"+Common.simpleFormat.format(Common.current
 Date.getTime())+"/"+String.valueOf(Common.currentTimeSl
 ot)); 
        apointementInformation.setType("Checked"); 
        apointementInformation.setTime(new 
StringBuilder(Common.convertTimeSlotToString(Common.cur
 rentTimeSlot)) 
                .append("at") 
                
.append(simpleDateFormat.format(Common.currentDate.getT
ime())).toString()); 
        
apointementInformation.setSlot(Long.valueOf(Common.curr
 entTimeSlot)); 
 
        DocumentReference bookingDate = 
FirebaseFirestore.getInstance() 
                .collection("Doctor") 
                .document(Common.CurreentDoctor) 
                
.collection(Common.simpleFormat.format(Common.currentDa
 te.getTime())) 
                
.document(String.valueOf(Common.currentTimeSlot)); 
 
        bookingDate.set(apointementInformation) 
                .addOnSuccessListener(new 
OnSuccessListener<Void>() { 
                    @Override 
                    public void onSuccess(Void aVoid) { 
                        getActivity().finish(); 
                        
Toast.makeText(getContext(),"Success!",Toast.LENGTH_SHO
 RT).show(); 
                        Common.currentTimeSlot = -1; 
                        Common.currentDate = 
Calendar.getInstance(); 
                        Common.step = 0; 
                    } 
                }).addOnFailureListener(new 
OnFailureListener() { 
            @Override 
            public void onFailure(@NonNull Exception e) 
{ 
                
Toast.makeText(getContext(),""+e.getMessage(),Toast.LEN
 GTH_SHORT).show(); 
            } 
        }).addOnCompleteListener(new 
OnCompleteListener<Void>() { 
            @Override 
            public void onComplete(@NonNull Task<Void> 
task) { 
                
FirebaseFirestore.getInstance().collection("Doctor").do
 cument(Common.CurreentDoctor) 
                        
.collection("apointementrequest").document(apointementI
 nformation.getTime().replace("/","_")).set(apointementI
 nformation); 
                
FirebaseFirestore.getInstance().collection("Patient").d
 ocument(apointementInformation.getPatientId()).collecti
 on("calendar") 
                        
.document(apointementInformation.getTime().replace("/",
 "_")).set(apointementInformation); 
 
            } 
        }); 
 
// 
    } 
 
 
    BroadcastReceiver confirmBookingReceiver = new 
BroadcastReceiver() { 
        @Override 
        public void onReceive(Context context, Intent 
intent) { 
            Log.e("TAG", "onReceive: heave been 
receiver" ); 
            setData(); 
        } 
    }; 
 
 
    private void setData() { 
        
txt_booking_berber_text.setText(Common.CurrentDoctorNam
 e); 
        txt_booking_time_text.setText(new 
StringBuilder(Common.convertTimeSlotToString(Common.cur
 rentTimeSlot)) 
        .append("at") 
        
.append(simpleDateFormat.format(Common.currentDate.getT
 ime()))); 
        txt_booking_phone.setText(Common.CurrentPhone); 
        
txt_booking_type.setText(Common.Currentaappointementaty
 pe); 
    } 
 
    public BookingStep3Fragment() { 
        // Required empty public constructor 
    } 
 
 
    public static BookingStep3Fragment 
newInstance(String param1, String param2) { 
        BookingStep3Fragment fragment = new 
BookingStep3Fragment(); 
 
        return fragment; 
    } 
 
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        simpleDateFormat = new 
SimpleDateFormat("dd/MM/yyyy"); 
        localBroadcastManager = 
LocalBroadcastManager.getInstance(getContext()); 
 
        
localBroadcastManager.registerReceiver(confirmBookingRe
 ceiver,new IntentFilter(Common.KEY_CONFIRM_BOOKING)); 
    } 
 
    @Override 
    public void onDestroy() { 
        
localBroadcastManager.unregisterReceiver(confirmBooking
 Receiver); 
        super.onDestroy(); 
    } 
 
    static BookingStep3Fragment instance; 
    public  static  BookingStep3Fragment getInstance(){ 
        if(instance == null ) 
            instance = new BookingStep3Fragment(); 
        return instance; 
    } 
 
    @Override 
    public View onCreateView(LayoutInflater inflater, 
ViewGroup container, 
                             Bundle savedInstanceState) 
{ 
        // Inflate the layout for this fragment 
        
super.onCreateView(inflater,container,savedInstanceStat
 e); 
 
        View itemView = 
inflater.inflate(R.layout.fragment_booking_step3, 
container, false); 
        unbinder = ButterKnife.bind(this,itemView); 
 
        return itemView; 
    } 
} 
 
