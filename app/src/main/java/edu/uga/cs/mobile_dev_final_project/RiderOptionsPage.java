package edu.uga.cs.mobile_dev_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class RiderOptionsPage extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv;
    Button request, cancel;
    FirebaseDatabase fd = FirebaseDatabase.getInstance();
    private final String TAG = "RiderOptionPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_options_page);

        ArrayList<Post> list = setListData();

        rv = findViewById(R.id.rider_page_rv);
        request = findViewById(R.id.rideRequest);
        cancel = findViewById(R.id.rideCancel);

        RidersRecyclerView rrv = new RidersRecyclerView(this, list);
        rv.setAdapter(rrv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        request.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    private ArrayList<Post> setListData(){
        DatabaseReference dbr = fd.getReference("RequestData");
        ArrayList<Post> al = new ArrayList<Post>();
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                al.clear();
                for (DataSnapshot dSnapshot : snapshot.getChildren()){
                    Log.d(TAG, "Value added: "+dSnapshot.getValue(Post.class).toString());
                    Log.d(TAG, "name of person: "+dSnapshot.getValue(Post.class).getPoster_name());
                    al.add(dSnapshot.getValue(Post.class));
                }
                Log.d(TAG, "End of for loop.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return al;
    }

    //does the function for the request ride button
    private void requestRide(){
        Random r = new Random();
        int post_type = 0;        // if post type 0, rider; if post type 1, driver;
        int travel_type = r.nextInt(2);      // if post type 0, in-town ride; if post type 1, out-of-town ride;
        String date_of_ride = Calendar.getInstance().getTime().toString();
        String pickup_address = "p_address";
        String destination_address = "d_address";
        String poster_name = "Louis";
        String acceptor_name = "";
        Post post = new Post(post_type, travel_type, date_of_ride, pickup_address, destination_address, poster_name, acceptor_name);
        fd.getReference("RequestData").push().setValue(post).addOnCompleteListener(new OnCompleteListener<Void>(){
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RiderOptionsPage.this, "info added", Toast.LENGTH_LONG).show();
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