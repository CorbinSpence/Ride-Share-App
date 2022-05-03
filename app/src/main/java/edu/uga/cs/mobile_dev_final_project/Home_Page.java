package edu.uga.cs.mobile_dev_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class Home_Page extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView rView;
    private ImageView car, seat;
    private Button profile, button2;
    private ArrayList<OfferData> list;
    private final String TAG = "HomePage";
    private FirebaseDatabase fd = FirebaseDatabase.getInstance();
    private Spinner spin;
    private FirebaseAuth mAuth;
    private String uid;
    // private User user2;
    // private PendingPost pp;

    private TextView tv;
    private int post_type;

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid().toString();

        rView = findViewById(R.id.driverView);
        car = findViewById(R.id.selectDriver);
        seat = findViewById(R.id.selectRider);
        profile = findViewById(R.id.profile_button);
        button2 = findViewById(R.id.button2);
        spin = findViewById(R.id.spinner);
        tv = findViewById(R.id.textView);

        ArrayAdapter<CharSequence> spinAdapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinAdapter);
        spin.setOnItemSelectedListener(new MySpinner());

        list = new ArrayList<OfferData>();

        DriversRecyclerView drv = new DriversRecyclerView( Home_Page.this );
        rView.setAdapter(drv);
        rView.setLayoutManager(new LinearLayoutManager(Home_Page.this ));

        car.setOnClickListener(this);
        seat.setOnClickListener(this);
        profile.setOnClickListener(this);

        loadUserInformation();
    }

    private void loadUserInformation() {
        DatabaseReference dbRef = fd.getReference("Users");

        dbRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);

                if( snapshot.child("pp").exists() ) {
                    if( snapshot.child("pp").child("acceptorID").exists() ) {
                        String temp = user.getPp().getAcceptorID();
                        if( !temp.isEmpty() ) {
                            button2.setOnClickListener(Home_Page.this);
                            button2.setVisibility(View.VISIBLE);
                        } else {
                            button2.setVisibility(View.GONE);
                        }
                    } else {
                        button2.setVisibility(View.GONE);
                    }
                } else {
                    button2.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home_Page.this, "Failed to Load User Data!", Toast.LENGTH_LONG).show();
            }
        });

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
            case R.id.button2:
                doTransaction();
                break;
        }
    }

    private void doTransaction() {
        DatabaseReference dbRef = fd.getReference("Users");

        dbRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                PendingPost pendingPost = snapshot.child("pp").getValue(PendingPost.class);
                PendingPost pending = new PendingPost( pendingPost.getPostID(), pendingPost.getAcceptorID(), pendingPost.getPost_type() );

                dbRef.child( pending.getAcceptorID() ).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user2 = snapshot.getValue(User.class);
                        // PendingPost pendingPost2 = snapshot.child("pp").getValue(PendingPost.class);

                        if(pendingPost.getPost_type() == 0) {

                            user.setTravelPoints( user.getTravelPoints() - 50 );
                            user2.setTravelPoints( user2.getTravelPoints() + 100 );

                            fd.getReference("OfferData").child(user.getPp().getPostID()).removeValue();

                            user.setPp(null);

                            dbRef.child( uid ).setValue( user );
                            dbRef.child( pendingPost.getAcceptorID() ).setValue( user2 );
                            finish();

                        } else if (pendingPost.getPost_type() == 1) {
                            user.setTravelPoints( user.getTravelPoints() + 100 );
                            user2.setTravelPoints( user2.getTravelPoints() - 50 );

                            fd.getReference("OfferData").child(user.getPp().getPostID()).removeValue();

                            user.setPp(null);

                            dbRef.child( uid ).setValue( user );
                            dbRef.child( pendingPost.getAcceptorID() ).setValue( user2 );
                            finish();

                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Home_Page.this, "Failed to Load User Data!", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home_Page.this, "Failed to Load User Data!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setListData(String path){
        if(path.equals("RequestData")) {
            post_type = 0;
        } else if(path.equals("OfferData")) {
            post_type = 1;
        }

        //ArrayList<Post> al = new ArrayList<Post>();
        DatabaseReference dbr = fd.getReference(path);
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                String[] tempArr;
                tempArr = new String[(int)snapshot.getChildrenCount()];

                final RidersRecyclerView.MyClickListener mcl = new RidersRecyclerView.MyClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Log.d(TAG, "Position clicked: "+ position);

                        Bundle bundle = new Bundle();
                        Log.d(TAG, "post key: "+ list.get(position).getPosterID());

                        Intent intent = new Intent(Home_Page.this, ConfirmationPageActivity.class);
                        bundle.putString( "postKey", "" + tempArr[position] );
                        bundle.putInt("postType", post_type);
                        intent.putExtras( bundle );
                        startActivity( intent );
                    }
                };

                int count = 0;
                for (DataSnapshot dSnapshot : snapshot.getChildren()){
                    Log.d(TAG, "Value added: "+dSnapshot.getKey());
                    Log.d(TAG, "name of person: "+dSnapshot.getValue(OfferData.class).getPosterName());
                    list.add(dSnapshot.getValue(OfferData.class));
                    tempArr[count] = dSnapshot.getKey();
                    count++;
                }

                RidersRecyclerView rrv = new RidersRecyclerView(Home_Page.this, list, mcl);
                rView.setAdapter(rrv);
                rView.setLayoutManager(new LinearLayoutManager(Home_Page.this));
                Log.d(TAG, "End of for loop.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private class MySpinner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            if(adapterView.getItemAtPosition(i).equals("Rider")){
                setListData("RequestData");
            }else if(adapterView.getItemAtPosition(i).equals("Driver")){
                setListData("OfferData");
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}



