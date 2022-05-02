package edu.uga.cs.mobile_dev_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

public class RiderOptionsPage extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv;
    private Button request, cancel;
    private FirebaseDatabase fd = FirebaseDatabase.getInstance();
    private final String TAG = "RiderOptionPage";
    private DatabaseReference requestReference = fd.getReference("RequestData");
    private DatabaseReference userReference = fd.getReference("Users");
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    //private final String uID = fba.getUid().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_options_page);

        rv = findViewById(R.id.rider_page_rv);
        request = findViewById(R.id.rideRequest);
        cancel = findViewById(R.id.rideCancel);

        request.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }


    //does the function for the request ride button
    private void requestRide(){
        String posterID = currentUser.getUid().toString();
        userReference.child(posterID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                Random r = new Random();
                String posterName = user.getFullName();
                String posterGender = user.getGender();
                String pickupAddress = "A"; //fill out
                String destinationAddress = "B"; //fill out
                String dateOfRide = Calendar.getInstance().getTime().toString();
                int travelType = r.nextInt(2); // if post type 0, in-town ride; if post type 1, out-of-town ride;
                RequestData requestData = new RequestData( "" + posterID, posterName, posterGender, pickupAddress, destinationAddress, dateOfRide, travelType);
                fd.getReference("RequestData").push().setValue(requestData).addOnCompleteListener(new OnCompleteListener<Void>(){
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(RiderOptionsPage.this, "info added", Toast.LENGTH_LONG).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        finish();
    }

    //fills in the function of the cancel button
    private void cancelRide(){
        finish();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.rideRequest:
                requestRide();
                break;
            case R.id.rideCancel:
                cancelRide();
        }
    }


}
