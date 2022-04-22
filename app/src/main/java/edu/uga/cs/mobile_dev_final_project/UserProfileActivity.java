package edu.uga.cs.mobile_dev_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView displayFullName, displayEmail, displayTravelPoints;
    // private TextView tv_fullName, tv_email, tv_password;
    private EditText et_changeName, et_changeEmail, et_changePassword;
    private Button btn_update;

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

        et_changeName = findViewById(R.id.profile_fullName_form);
        et_changeEmail = findViewById(R.id.profile_email_form);
        et_changePassword = findViewById(R.id.profile_password_form);

        btn_update = findViewById(R.id.button_update_user);

        if( !uid.isEmpty() ) {
            loadUserInformation();
        }

        btn_update.setOnClickListener( this );


    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.button_update_user:
                updateUserProfile();
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
                displayFullName.setText("Full Name: " + user.getFullName());
                displayEmail.setText("Email: " + user.getEmail());
                displayTravelPoints.setText("Travel Points: " + user.getTravelPoints());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfileActivity.this, "Failed to Load User Data!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateUserProfile() {
        String fullName = et_changeName.getText().toString().trim();
        String email = et_changeEmail.getText().toString().trim();
        String password = et_changePassword.getText().toString().trim();

        /*if( !_NAME.equals(fullName) ) {
            dbRef.child().child("fullName").setValue(fullName);
        }*/
    }

}