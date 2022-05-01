package edu.uga.cs.mobile_dev_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Home_Page extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView rView;
    private ImageView car, seat;
    private Button profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        rView = findViewById(R.id.driverView);
        car = findViewById(R.id.selectDriver);
        seat = findViewById(R.id.selectRider);
        profile = findViewById(R.id.profile_button);

        DriversRecyclerView drv = new DriversRecyclerView(this);
        rView.setAdapter(drv);
        rView.setLayoutManager(new LinearLayoutManager(this));
        car.setOnClickListener(this);
        seat.setOnClickListener(this);
        profile.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.selectDriver:
                startActivity(new Intent(this, DriverOptionsPage.class));
                break;
            case R.id.selectRider:
                startActivity(new Intent(this, RiderOptionsPage.class));
                break;
            case R.id.profile_button:
                startActivity(new Intent(this, UserProfileActivity.class));
                break;
        }
    }
}