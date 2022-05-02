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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Random;

public class RiderOptionsPage extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rv;
    Button request, cancel;
    FirebaseDatabase fd = FirebaseDatabase.getInstance();
    private final String TAG = "RiderOptionPage";
    DatabaseReference dbr = fd.getReference("RequestData");

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
        Random r = new Random();
        int post_type = 0;        // if post type 0, rider; if post type 1, driver;
        int travel_type = r.nextInt(2);      // if post type 0, in-town ride; if post type 1, out-of-town ride;
        String date_of_ride = Calendar.getInstance().getTime().toString();
        String pickup_address = "p_address";
        String destination_address = "d_address";
        String poster_name = "Louis";
        String acceptor_name = "";
        RequestData requestData = new RequestData( "" + FirebaseAuth.getInstance().getCurrentUser().getUid(), poster_name, "Female", pickup_address, destination_address, date_of_ride, travel_type);
        fd.getReference("RequestData").push().setValue(requestData).addOnCompleteListener(new OnCompleteListener<Void>(){
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
