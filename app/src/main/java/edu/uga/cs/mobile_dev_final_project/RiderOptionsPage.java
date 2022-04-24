package edu.uga.cs.mobile_dev_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class RiderOptionsPage extends AppCompatActivity {

    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_options_page);
        setContentView(R.layout.activity_driver_options_page);
        rv = findViewById(R.id.rider_page_rv);
        DriversRecyclerView drv = new DriversRecyclerView(this);
        rv.setAdapter(drv);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }
}