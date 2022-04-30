package edu.uga.cs.mobile_dev_final_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class RidersRecyclerView  extends RecyclerView.Adapter<RidersRecyclerView.MyViewHolder> {

    private Context context;
    private String[] riderNames;
    private String[] riderGenders;
    private final String TAG = "RiderRecyclerView";
    private MyClickListener mMyClickListener;

    public RidersRecyclerView (Context context, @NonNull ArrayList<Post> postList, MyClickListener myClickListener){
        mMyClickListener = myClickListener;
        ArrayList<String> nameList = new ArrayList<String>();
        ArrayList<String> genderList = new ArrayList<String>();
        for(Post post: postList){
            Log.d(TAG, "Name added: "+post.getPoster_name());
            Log.d(TAG, "Gender added: "+post.getGender());
            nameList.add(post.getPoster_name());
            genderList.add(post.getGender());
        }
        Log.d(TAG, "End of post loop. Array Size: "+postList.size());
        riderNames = new String[nameList.size()];
        riderGenders = new String[genderList.size()];
        nameList.toArray(riderNames);
        genderList.toArray(riderGenders);
        this.context = context;
    }

    public RidersRecyclerView (Context context){
        riderNames = new String[]{"Fred", "Barry", "Trisha", "Liz", "Mark"};
        riderGenders = new String[]{"Male", "Male", "Female", "Female", "Male"};
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater i = LayoutInflater.from(context);
        View v = i.inflate(R.layout.recycle_view_rider_option, parent,false);

        MyViewHolder vh = new MyViewHolder( v ); // don't pass in null this is just for demonstration
        int position = vh.getAdapterPosition();
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the position of this Vh
                if (mMyClickListener != null) mMyClickListener.onItemClick(position);
            }
        });


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(riderNames[position]);
        holder.gender.setText(riderGenders[position]);
    }

    @Override
    public int getItemCount() {
        return riderNames.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, gender;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.riderName);
            gender = itemView.findViewById(R.id.riderGender);
        }
    }

    public interface MyClickListener {
        void onItemClick(int position);
    }

}
