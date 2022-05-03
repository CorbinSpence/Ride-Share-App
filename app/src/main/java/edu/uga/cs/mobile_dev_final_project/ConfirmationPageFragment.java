package edu.uga.cs.mobile_dev_final_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmationPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmationPageFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "ConfirmationFragment";

    private FirebaseAuth mAuth;
    private DatabaseReference refUsers;
    private DatabaseReference refOffers;
    private DatabaseReference refRequests;
    private String uid;
    private String key;

    private static final String POST_KEY = "postKey";
    private static final String POST_TYPE = "postType";
    private String postKey;
    private int postType;

    TextView tv;
    Button button;

    public ConfirmationPageFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ConfirmationPageFragment.
     */
    public static ConfirmationPageFragment newInstance() {
        ConfirmationPageFragment fragment = new ConfirmationPageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        if (b != null) {
            postKey = getArguments().getString(POST_KEY);
            postType = getArguments().getInt(POST_TYPE);
            Log.d(TAG, "HI: " + postKey);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_confirmation_page, container, false);

        mAuth = FirebaseAuth.getInstance();
        refUsers = FirebaseDatabase.getInstance().getReference("Users");
        refOffers = FirebaseDatabase.getInstance().getReference("OfferData");
        refRequests = FirebaseDatabase.getInstance().getReference("RequestData");
        uid = mAuth.getCurrentUser().getUid().toString();

        tv = v.findViewById(R.id.textView5);
        tv.setText( "postKey: " + postKey );
        button = v.findViewById(R.id.button_cnf);
        button.setOnClickListener( this );

        return v;
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId() ) {
            case R.id.button_cnf:
                iDunno();
                break;
        }
    }

    private void iDunno() {
        if( postType == 0 ) {
            refRequests.child( postKey ).addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    RequestData requestData = snapshot.getValue(RequestData.class);
                    //PendingPost pending;

                    refUsers.child(requestData.getPosterID()).child("pp").setValue( new PendingPost( postKey, uid, postType) )
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Intent intent = new Intent(getActivity(), Home_Page.class);
                                        // Toast hereeeeeeeeeeeeeeeeeeeeeeeeeeeeee
                                        startActivity( intent);
                                    } else {} // end if-else
                                }
                            });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        } else if( postType == 1 ) {
            // read data from the post clicked
            refOffers.child( postKey ).addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    OfferData offer = snapshot.getValue(OfferData.class);

                    OfferData offerData = new OfferData( offer.getPosterID(), offer.getPosterName(), offer.getPosterGender(),
                            offer.getPickupAddress(), offer.getDestinationAddress(), offer.getDateOfRide(), offer.getTravelType() );

                    refUsers.child( offerData.getPosterID() ).child("pp").setValue( new PendingPost( postKey, uid, postType) )
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Intent intent = new Intent(getActivity(), Home_Page.class);
                                        // Toast hereeeeeeeeeeeeeeeeeeeeeeeeeeeeee
                                        startActivity( intent);
                                    } else {} // end if-else
                                }
                            });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}
            });
        }

    }
}