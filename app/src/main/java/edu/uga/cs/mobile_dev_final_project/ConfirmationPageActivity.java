package edu.uga.cs.mobile_dev_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ConfirmationPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_page);

        Intent intent = getIntent();
        Fragment fragment = new ConfirmationPageFragment();

        String temp = intent.getStringExtra("postKey");
        int tempInt = intent.getIntExtra("postType", 0);

        Bundle bundle = new Bundle();
        bundle.putString( "postKey", temp );
        bundle.putInt( "postType", tempInt);
        Log.d("TAG", "post key: "+ temp);
        fragment.setArguments(bundle);

        // Begin the transaction
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.fragmentContainerView, fragment);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }
}