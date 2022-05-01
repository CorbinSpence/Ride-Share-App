package edu.uga.cs.mobile_dev_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home_Page extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView rView;
    private ImageView car, seat;
    private Button profile;
    private ArrayList<Post> list;
    private final String TAG = "HomePage";
    private FirebaseDatabase fd = FirebaseDatabase.getInstance();
    private Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        rView = findViewById(R.id.driverView);
        car = findViewById(R.id.selectDriver);
        seat = findViewById(R.id.selectRider);
        profile = findViewById(R.id.profile_button);
        spin = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinAdapter);
        spin.setOnItemSelectedListener(new MySpinner());

        list = new ArrayList<Post>();


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
    private void setListData(String path){

        //ArrayList<Post> al = new ArrayList<Post>();
        DatabaseReference dbr = fd.getReference(path);
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dSnapshot : snapshot.getChildren()){

                    Log.d(TAG, "Value added: "+dSnapshot.getValue(Post.class).toString());
                    Log.d(TAG, "name of person: "+dSnapshot.getValue(Post.class).getPoster_name());
                    list.add(dSnapshot.getValue(Post.class));
                    show();
                }
                Log.d(TAG, "End of for loop.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // return al;
    }
    private void show() {
        //RidersRecyclerView rrv = new RidersRecyclerView(this, list);

        final RidersRecyclerView.MyClickListener mcl = new RidersRecyclerView.MyClickListener() {
            @Override
            public void onItemClick(int position) {
                if(position == 1) {
                    startActivity(new Intent(Home_Page.this, Home_Page.class));
                } else if(position == 2) {
                    startActivity(new Intent(Home_Page.this, DriverOptionsPage.class));
                }

            }
        };
        RidersRecyclerView rrv = new RidersRecyclerView(this, list, mcl);

        rView.setAdapter(rrv);
        rView.setLayoutManager(new LinearLayoutManager(this));

        //rv.setOnClickListener( this );
    }
    private class MySpinner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(adapterView.getItemAtPosition(i).equals("Rider")){
                setListData("RequestData");
            }else if(adapterView.getItemAtPosition(i).equals("Driver")){
                setListData("Posts");
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}