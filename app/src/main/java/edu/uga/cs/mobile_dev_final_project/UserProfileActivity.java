package edu.uga.cs.mobile_dev_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView displayFullName, displayEmail, displayTravelPoints, pastRide;
    private Button btn_logout;

    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private String uid;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid().toString();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");

        displayFullName = findViewById(R.id.fullName_display);
        displayEmail = findViewById(R.id.email_display);
        displayTravelPoints = findViewById(R.id.travelPoints_display);
        pastRide = findViewById(R.id.textView2);

        btn_logout = findViewById(R.id.button_logout);

        if( !uid.isEmpty() ) {
            loadUserInformation();
        }

        btn_logout.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.button_logout:
                logoutUser();
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

    private void loadUserInformation() {
        dbRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                displayFullName.setText("" + user.getFullName());
                displayEmail.setText("" + user.getEmail());
                displayTravelPoints.setText(user.getTravelPoints() + " TP");

                if( user.getRide() != null ) {
                    pastRide.setText("From: " + user.getRide().getPickup_address()
                            + "  To: " + user.getRide().getDestination_address()
                            + "  Date: " + user.getRide().getDate());
                } else {
                    pastRide.setText("No recent activity");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this, "Failed to Load User Data!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void logoutUser() {
        finish();
        mAuth.signOut();
        startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
    }

}