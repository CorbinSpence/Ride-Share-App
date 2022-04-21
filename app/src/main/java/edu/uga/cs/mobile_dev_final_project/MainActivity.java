package edu.uga.cs.mobile_dev_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Access_User access = new Access_User();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        final EditText name = findViewById(R.id.username_input);
        final EditText pass = findViewById(R.id.password_input);

        signIn = findViewById(R.id.button_sign_in);
        register = findViewById(R.id.register_user_link);

        register.setOnClickListener(this);
        // signIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.register_user_link:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.button_sign_in:
                /*startActivity(new Intent(this, RegisterActivity.class));*/
                break;
        }
    }

}