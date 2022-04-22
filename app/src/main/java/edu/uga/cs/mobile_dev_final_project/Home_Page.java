package edu.uga.cs.mobile_dev_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Home_Page extends AppCompatActivity {
    private RecyclerView rView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        rView = findViewById(R.id.driverView);
        DriversRecyclerView drv = new DriversRecyclerView(this);
        rView.setAdapter(drv);
        rView.setLayoutManager(new LinearLayoutManager(this));
    }
}