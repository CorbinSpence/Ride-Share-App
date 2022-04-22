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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView textView;

    private EditText et_email;
    private EditText et_password;
    private EditText et_fullName;

    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        et_email = findViewById(R.id.new_email_input);
        et_password = findViewById(R.id.new_password_input);
        et_fullName = findViewById(R.id.new_fullname_input);

        register = findViewById(R.id.button_register_user);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.button_register_user:
                registerUser();
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            /*case R.id.button_sign_in:
                *//*startActivity(new Intent(this, RegisterActivity.class));*//*
                break;*/
        }
    }

    private void registerUser() {
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String fullName = et_fullName.getText().toString().trim();

        if(fullName.isEmpty()) {
            et_fullName.setError("Full Name field is required!");
            et_fullName.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            et_email.setError("Email field is required!");
            et_email.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            et_password.setError("Password field is required!");
            et_password.requestFocus();
            return;
        }

        if(password.length() < 8 || password.length() > 16) {
            et_password.setError("Password must be 8 to 16 digits long!");
            et_password.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("Please use a valid email!");
            et_email.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    User user = new User(email, fullName, 250);

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
                    });
                }
            }
        });

    }

}