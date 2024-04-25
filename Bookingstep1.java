package com.example.careapp; 
 
import android.app.AlertDialog; 
import android.content.BroadcastReceiver; 
import android.content.Context; 
import android.content.Intent; 
import android.content.IntentFilter; 
import android.os.Bundle; 
import android.view.LayoutInflater; 
import android.view.View; 
import android.view.ViewGroup; 
import android.widget.Toast; 
 
import androidx.annotation.NonNull; 
import androidx.annotation.Nullable; 
import androidx.fragment.app.Fragment; import 
androidx.localbroadcastmanager.content.LocalBroadcastMa
nager; import androidx.recyclerview.widget.GridLayoutManager; 
import androidx.recyclerview.widget.RecyclerView; 
 
import com.google.android.gms.tasks.OnCompleteListener; 
import com.google.android.gms.tasks.OnFailureListener; 
import com.google.android.gms.tasks.Task; import 
com.google.firebase.firestore.CollectionReference; 
import com.google.firebase.firestore.DocumentReference; 
import com.google.firebase.firestore.DocumentSnapshot; 
import com.google.firebase.firestore.FirebaseFirestore; 
import 
com.google.firebase.firestore.QueryDocumentSnapshot; 
import com.google.firebase.firestore.QuerySnapshot; 
 
import java.text.SimpleDateFormat; 
import java.util.ArrayList; import java.util.Calendar; 
import java.util.List; 
 
import butterknife.BindView; 
import butterknife.ButterKnife; 
import butterknife.Unbinder; import 
devs.mulham.horizontalcalendar.HorizontalCalendar; 
import devs.mulham.horizontalcalendar.HorizontalCalendarView; 
import devs.mulham.horizontalcalendar.utils.HorizontalCalendar Listener; 
import dmax.dialog.SpotsDialog; 
 
public class BookingStep1Fragment extends Fragment implements ITimeSlotLoadListener { 
 
    DocumentReference doctorDoc; 
    ITimeSlotLoadListener iTimeSlotLoadListener; 
    AlertDialog dialog; 
 
    Unbinder unbinder; 
    LocalBroadcastManager localBroadcastManager; 
 
 
    @BindView(R.id.recycle_time_slot) 
    RecyclerView recycler_time_slot; 
    @BindView(R.id.calendarView) 
    HorizontalCalendarView calendarView;     SimpleDateFormat simpleDateFormat; 
 
    BroadcastReceiver displayTimeSlot = new 
BroadcastReceiver() {         
@Override 
        public void onReceive(Context context, Intent intent) { 
            Calendar date = Calendar.getInstance();            
date.add(Calendar.DATE,0); 
            
loadAvailabelTimeSlotOfDoctor(Common.CurreentDoctor,sim pleDateFormat.format(date.getTime())); 
 
        } 
    };  
    private void loadAvailabelTimeSlotOfDoctor(String doctorId, final String bookDate) {         dialog.show(); 
 
        doctorDoc = FirebaseFirestore.getInstance() 
                .collection("Doctor") 
                .document(Common.CurreentDoctor);         doctorDoc.get().addOnCompleteListener(new 
OnCompleteListener<DocumentSnapshot>() { 
            @Override 
            public void onComplete(@NonNull Task<DocumentSnapshot> task) { 
                if(task.isSuccessful()) 
                { 
                    DocumentSnapshot documentSnapshot = task.getResult(); 
                    if(documentSnapshot.exists()){                         CollectionReference date 
=FirebaseFirestore.getInstance() 
                                .collection("Doctor") 
                                
.document(Common.CurreentDoctor) 
                                .collection(bookDate); 
 
                        date.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {                            
@Override                             public void onComplete(@NonNull Task<QuerySnapshot> task) {                                 
if 
(task.isSuccessful()) 
                                { 
                                    QuerySnapshot querySnapshot = task.getResult();                                     
if 
(querySnapshot.isEmpty()) 
                                    {                                        
iTimeSlotLoadListener.onTimeSlotLoadEmpty();                                     
}else { 
List<TimeSlot> timeSlots = new ArrayList<>(); 
for (QueryDocumentSnapshot document:task.getResult())                                             
timeSlots.add(document.toObject(TimeSlot.class)); 
                                        
iTimeSlotLoadListener.onTimeSlotLoadSuccess(timeSlots); 
                                    } 
}} 
                        }).addOnFailureListener(new 
OnFailureListener() { 
@Override                            
public void onFailure(@NonNull Exception e) {                                 
iTimeSlotLoadListener.onTimeSlotLoadFailed(e.getMessage
()); 
 
