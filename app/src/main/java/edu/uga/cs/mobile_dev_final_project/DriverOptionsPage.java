package edu.uga.cs.mobile_dev_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class DriverOptionsPage extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "DriverOptionPage";

    private FirebaseAuth mAuth;
    private DatabaseReference refUsers;
    private DatabaseReference refOffers;
    private String uid;

    private int post_type;        // if post type 0, rider; if post type 1, driver;
    private int travel_type;      // if post type 0, in-town ride; if post type 1, out-of-town ride;
    private String date_of_ride;
    private String pickup_address;
    private String destination_address;
    private String poster_name;
    private String acceptor_name;

    private String key;


    private EditText street_pickup, city_pickup, state_pickup, zip_pickup;
    private EditText street_dest, city_dest, state_dest, zip_dest;
    private Button btn_submit;

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_options_page);

        mAuth = FirebaseAuth.getInstance();
        refUsers = FirebaseDatabase.getInstance().getReference("Users");
        refOffers = FirebaseDatabase.getInstance().getReference("OfferData");
        uid = mAuth.getCurrentUser().getUid().toString();

        street_pickup = findViewById(R.id.pickup_street_input);
        city_pickup = findViewById(R.id.pickup_city_input);
        state_pickup = findViewById(R.id.pickup_state_input);
        zip_pickup = findViewById(R.id.pickup_zip_input);

        street_dest = findViewById(R.id.destination_street_input);
        city_dest = findViewById(R.id.destination_city_input);
        state_dest = findViewById(R.id.destination_state_input);
        zip_dest = findViewById(R.id.destination_zip_input);
        btn_submit = findViewById(R.id.button3);
        // rv = findViewById(R.id.driver_page_rv);

        btn_submit.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.button3:
                submit();
                break;
        }
    }

    private void submit() {
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

        refUsers.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if( snapshot.child("pp").exists() ) {
                    Log.d(TAG, "Create new post failed: pending post");
                    Toast.makeText(DriverOptionsPage.this, "Can only have one pending post!", Toast.LENGTH_LONG).show();
                } else {
                    OfferData offer = new OfferData( uid, user.getFullName(), user.getGender(),
                            pickup_address, destination_address, date_of_ride, travel_type );

                    DatabaseReference ref = refOffers.push();
                    key = ref.getKey();

                    ref.setValue(offer).addOnCompleteListener( new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                user.setPp( new PendingPost( key, "", 1) );

                                // AAAAAAAAAAAAAAAAAAAAAA
                                Log.d(TAG, "pending post: " + user.getPp() );

                                refUsers.child(uid).setValue(user).addOnCompleteListener( new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            finish();
                                            Log.d(TAG, "changing post details: yes");
                                            Toast.makeText(DriverOptionsPage.this, "Post offer created!", Toast.LENGTH_LONG).show();

                                            // go back to home page after creating new ride offer post
                                            startActivity(new Intent(DriverOptionsPage.this, Home_Page.class));

                                        } else {} // end if-else
                                    } // end onComplete
                                }); // end refUsers addOnCompleteListener

                            } else {} // end if-else

                        } // end onComplete
                    }); // end ref addOnCompleteListener

                } // end if-else

            } // end onDataChange

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            } // end onCancelled

        }); // end refUsers addValueEventListener

    }


}