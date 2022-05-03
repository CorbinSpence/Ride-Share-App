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

    private TextView displayFullName, displayEmail, displayTravelPoints;
    // private TextView tv_fullName, tv_email, tv_password;
    private EditText et_changeName, et_changeEmail, et_changePassword, et_currentPassword;
    private Button btn_update, btn_logout;

    private FirebaseAuth mAuth;
    private DatabaseReference dbRef;
    private String uid;
    private User user;

    private String email;
    private String password;

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
        //et_currentPassword = findViewById(R.id.profile_password_form2);

        btn_update = findViewById(R.id.button_update_user);
        btn_logout = findViewById(R.id.button_logout);

        if( !uid.isEmpty() ) {
            loadUserInformation();
        }

        btn_update.setOnClickListener( this );
        btn_logout.setOnClickListener( this );


    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.button_update_user:
                updateUserProfile();
                break;
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
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

        String fullName = et_changeName.getText().toString().trim();
        email = et_changeEmail.getText().toString().trim();
        password = et_changePassword.getText().toString().trim();
        // String currentPassword = et_currentPassword.getText().toString().trim();

        if( fullName.isEmpty() ) {
            fullName = user.getFullName();
        }

        if( email.isEmpty() ) {
            email = user.getEmail();
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_changeEmail.setError("Please use a valid email!");
            et_changeEmail.requestFocus();
            return;
        }

        User user1 = new User(email, fullName, user.getTravelPoints(), user.getGender(), user.getPp(), user.getRide());

        dbRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(user1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    // finish();
                    Toast.makeText(UserProfileActivity.this, "User updated!", Toast.LENGTH_LONG).show();
                    // startActivity(new Intent(UserProfileActivity.this, UserProfileActivity.class));
                    // go back to login layout
                } else {
                    Toast.makeText(UserProfileActivity.this, "Update Failed! Try Again!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void logoutUser() {
        finish();
        mAuth.signOut();
        startActivity(new Intent(UserProfileActivity.this, MainActivity.class));
    }

}