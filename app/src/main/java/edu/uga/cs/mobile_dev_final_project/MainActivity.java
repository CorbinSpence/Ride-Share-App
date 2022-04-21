package edu.uga.cs.mobile_dev_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Arrays;
import java.util.List;

import com.firebase.ui.auth.AuthUI;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Access_User access = new Access_User();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        final EditText name = findViewById(R.id.et_username);
        final EditText pass = findViewById(R.id.et_password);

        Button btn_signin = findViewById(R.id.btn_signin);
        Button btn_register = findViewById(R.id.btn_register);

    }

}