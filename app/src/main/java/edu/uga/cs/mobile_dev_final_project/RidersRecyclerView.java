package edu.uga.cs.mobile_dev_final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.zip.Inflater;

public class RidersRecyclerView  extends RecyclerView.Adapter<RidersRecyclerView.MyViewHolder> {

    private Context context;
    private String[] riderNames;
    private String[] riderGenders;

    public RidersRecyclerView (Context context, String[] names, String[] genders){
        riderNames = names;
        riderGenders = genders;
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
}
