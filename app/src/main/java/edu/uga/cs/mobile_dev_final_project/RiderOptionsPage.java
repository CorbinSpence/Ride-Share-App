package edu.uga.cs.mobile_dev_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private Button request, cancel;
    private FirebaseDatabase fd = FirebaseDatabase.getInstance();
    private final String TAG = "RiderOptionPage";
    private DatabaseReference requestReference = fd.getReference("RequestData");
    private DatabaseReference userReference = fd.getReference("Users");
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private EditText street_pickup, city_pickup, state_pickup, zip_pickup;
    private EditText street_dest, city_dest, state_dest, zip_dest;
    private String pickupAddress;
    private String destinationAddress;
    //private final String uID = fba.getUid().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_options_page);

        request = findViewById(R.id.rideRequest);
        cancel = findViewById(R.id.rideCancel);
        street_pickup = findViewById(R.id.pickup_street_input);
        city_pickup = findViewById(R.id.pickup_city_input);
        state_pickup = findViewById(R.id.pickup_state_input);
        zip_pickup = findViewById(R.id.pickup_zip_input);

        street_dest = findViewById(R.id.destination_street_input);
        city_dest = findViewById(R.id.destination_city_input);
        state_dest = findViewById(R.id.destination_state_input);
        zip_dest = findViewById(R.id.destination_zip_input);

        request.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }


    //does the function for the request ride button
    private void requestRide(){
        pickupAddress = street_pickup.getText().toString().trim() + ", " +
                city_pickup.getText().toString().trim() + ", " + state_pickup.getText().toString().trim() + " " +
                zip_pickup.getText().toString().trim();

        destinationAddress = street_dest.getText().toString().trim() + ", " +
                city_dest.getText().toString().trim() + ", " + state_dest.getText().toString().trim() + " " +
                zip_dest.getText().toString().trim();

        int travelType;
        if( city_pickup.getText().toString().trim().equals( city_dest.getText().toString().trim() ) ) {
            travelType = 0;
        } else { travelType = 1; }

        String posterID = currentUser.getUid().toString();
        userReference.child(posterID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if(!snapshot.child("pp").exists()) {
                    String posterName = user.getFullName();
                    String posterGender = user.getGender();
                    //String pickupAddress = "A"; //fill out
                    //String destinationAddress = "B"; //fill out
                    String dateOfRide = Calendar.getInstance().getTime().toString();
                    RequestData requestData = new RequestData("" + posterID, posterName, posterGender, pickupAddress, destinationAddress, dateOfRide, travelType);
                    fd.getReference("RequestData").push().setValue(requestData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            user.setPp(new PendingPost(posterID, ""));
                            userReference.child(posterID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(RiderOptionsPage.this, "info added", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            });
                        }
                    });
                }else{
                    Toast.makeText(RiderOptionsPage.this, "Can only have one pending post!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
