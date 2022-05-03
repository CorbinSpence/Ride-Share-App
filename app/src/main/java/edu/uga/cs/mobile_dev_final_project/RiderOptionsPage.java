package edu.uga.cs.mobile_dev_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private FirebaseAuth mAuth;
    private DatabaseReference refUsers;
    private DatabaseReference refRequests;
    private String uid;


    private int post_type;        // if post type 0, rider; if post type 1, driver;
    private int travel_type;      // if post type 0, in-town ride; if post type 1, out-of-town ride;
    private String date_of_ride;
    private String pickup_address;
    private String destination_address;
    private String poster_name;
    private String acceptor_name;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_options_page);

        mAuth = FirebaseAuth.getInstance();
        refUsers = FirebaseDatabase.getInstance().getReference("Users");
        refRequests = FirebaseDatabase.getInstance().getReference("RequestData");
        uid = mAuth.getCurrentUser().getUid().toString();

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
        pickup_address = street_pickup.getText().toString().trim() + ", " +
                city_pickup.getText().toString().trim() + ", " + state_pickup.getText().toString().trim() + " " +
                zip_pickup.getText().toString().trim();

        destination_address = street_dest.getText().toString().trim() + ", " +
                city_dest.getText().toString().trim() + ", " + state_dest.getText().toString().trim() + " " +
                zip_dest.getText().toString().trim();

        date_of_ride = Calendar.getInstance().getTime().toString();

        if( city_pickup.getText().toString().trim().equals( city_dest.getText().toString().trim() ) ) {
            travel_type = 0;
        } else { travel_type = 1; }

        refUsers.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if( user.getTravelPoints() < 50 ) {
                    Log.d(TAG, "Create new post failed: not enough points");
                    Toast.makeText(RiderOptionsPage.this, "You do not have enough travel points!", Toast.LENGTH_LONG).show();
                } else {
                    if( snapshot.child("pp").exists() ) {
                        Log.d(TAG, "Create new post failed: pending post");
                        Toast.makeText(RiderOptionsPage.this, "You already have a post pending!", Toast.LENGTH_LONG).show();
                    } else {
                        RequestData request = new RequestData( uid, user.getFullName(), user.getGender(),
                                pickup_address, destination_address, date_of_ride, travel_type );

                        DatabaseReference ref = refRequests.push();
                        key = ref.getKey();

                        ref.setValue(request).addOnCompleteListener( new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    user.setPp( new PendingPost( key, "", 0) );

                                    // AAAAAAAAAAAAAAAAAAAAAA
                                    Log.d(TAG, "pending post: " + user.getPp() );

                                    refUsers.child(uid).setValue(user).addOnCompleteListener( new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()) {
                                                finish();
                                                Log.d(TAG, "changing post details: yes");
                                                Toast.makeText(RiderOptionsPage.this, "Post offer created!", Toast.LENGTH_LONG).show();

                                                // go back to home page after creating new ride offer post
                                                startActivity(new Intent(RiderOptionsPage.this, Home_Page.class));

                                            } else {} // end if-else
                                        } // end onComplete
                                    }); // end refUsers addOnCompleteListener

                                } else {} // end if-else

                            } // end onComplete
                        }); // end ref addOnCompleteListener

                    } // end if-else
                }

            } // end onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            } // end onCancelled

        }); // end refUsers addValueEventListener
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
