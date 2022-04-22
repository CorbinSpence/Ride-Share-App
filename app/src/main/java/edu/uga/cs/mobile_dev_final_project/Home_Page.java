package edu.uga.cs.mobile_dev_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home_Page extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rView;
    private Button btn_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btn_profile = findViewById(R.id.profile_button);

        rView = findViewById(R.id.driverView);
        DriversRecyclerView drv = new DriversRecyclerView(this);
        rView.setAdapter(drv);
        rView.setLayoutManager(new LinearLayoutManager(this));

        btn_profile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.profile_button:
                startActivity(new Intent(this, UserProfileActivity.class));
                break;
        }
    }

}