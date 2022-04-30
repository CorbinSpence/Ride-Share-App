package edu.uga.cs.mobile_dev_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

    private RecyclerView rv;

    private FirebaseAuth mAuth;
    String uid;
    private DatabaseReference dbRef;

    // private TextView textView;
    private EditText street_pickup, city_pickup, state_pickup, zip_pickup;
    private EditText street_dest, city_dest, state_dest, zip_dest;
    private Post post;
    private User user;

    private int post_type;        // if post type 0, rider; if post type 1, driver;
    private int travel_type;      // if post type 0, in-town ride; if post type 1, out-of-town ride;
    private String date_of_ride;
    private String pickup_address;
    private String destination_address;
    private String poster_name;
    private String acceptor_name;

    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_options_page);
        rv = findViewById(R.id.driver_page_rv);
        /*DriversRecyclerView drv = new DriversRecyclerView(this);
        rv.setAdapter(drv);
        rv.setLayoutManager(new LinearLayoutManager(this));*/

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid().toString();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");

        street_pickup = findViewById(R.id.pickup_street_input);
        city_pickup = findViewById(R.id.pickup_city_input);
        state_pickup = findViewById(R.id.pickup_state_input);
        zip_pickup = findViewById(R.id.pickup_zip_input);

        street_dest = findViewById(R.id.destination_street_input);
        city_dest = findViewById(R.id.destination_city_input);
        state_dest = findViewById(R.id.destination_state_input);
        zip_dest = findViewById(R.id.destination_zip_input);
        btn_submit = findViewById(R.id.button3);

        btn_submit.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.button3:
                submitOffer();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void submitOffer() {
        post_type = 1;

        // this looks ugly lol, fix maybe later
        if( city_pickup.getText().toString().trim().equals( city_dest.getText().toString().trim() ) ) {
            travel_type = 0;
        } else {
            travel_type = 1;
        }

        pickup_address = street_pickup.getText().toString().trim() + ", " +
                city_pickup.getText().toString().trim() + ", " + state_pickup.getText().toString().trim() + " " +
                zip_pickup.getText().toString().trim();

        destination_address = street_dest.getText().toString().trim() + ", " +
                city_dest.getText().toString().trim() + ", " + state_dest.getText().toString().trim() + " " +
                zip_dest.getText().toString().trim();

        date_of_ride = Calendar.getInstance().getTime().toString();

        // load poster_name
        dbRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                poster_name = user.getFullName();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DriverOptionsPage.this, "Failed to Load User Data!", Toast.LENGTH_LONG).show();
            }
        });

        post = new Post( post_type, travel_type, date_of_ride, pickup_address, destination_address,
                poster_name, "");

        FirebaseDatabase.getInstance().getReference("Posts")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    finish();
                    Toast.makeText(DriverOptionsPage.this, "Post offer created!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(DriverOptionsPage.this, Home_Page.class));
                    // go back to home page after creating new ride offer post
                } else {
                    Toast.makeText(DriverOptionsPage.this, "Post offer failed! Try Again!", Toast.LENGTH_LONG).show();
                }
            }
        });




        /*User user = new User(email, fullName, 250);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    finish();
                    Toast.makeText(RegisterActivity.this, "User registered!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, UserProfileActivity.class));
                    // go back to login layout
                } else {
                    Toast.makeText(RegisterActivity.this, "Register Failed! Try Again!", Toast.LENGTH_LONG).show();
                }
            }
        });*/

    }

}