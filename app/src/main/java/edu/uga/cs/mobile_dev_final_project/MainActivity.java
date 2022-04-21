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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private Button signIn;
    private EditText et_username;
    private EditText et_password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        et_username = findViewById(R.id.username_input);
        et_password = findViewById(R.id.password_input);

        signIn = findViewById(R.id.button_sign_in);
        register = findViewById(R.id.register_user_link);

        register.setOnClickListener(this);
        signIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.register_user_link:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.button_sign_in:
                login();
                break;
        }
    }

    public void login() {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if(username.isEmpty()) {
            et_username.setError("Username field is required!");
            et_username.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            et_password.setError("Password field is required!");
            et_password.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            et_username.setError("Please use a valid email!");
            et_username.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    // go to user page layout
                    startActivity(new Intent(MainActivity.this, PlaceHolderActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "Login failed! Try Again!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}