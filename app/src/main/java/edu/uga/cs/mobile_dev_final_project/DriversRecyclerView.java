package edu.uga.cs.mobile_dev_final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DriversRecyclerView extends RecyclerView.Adapter<DriversRecyclerView.MyViewHolder> {
    Context context;
    private String[] driverNames;
    private String[] driverGenders;


    public DriversRecyclerView (Context context, String[] names, String[] genders){
        driverNames = names;
        driverGenders = genders;
        this.context = context;
    }

    public DriversRecyclerView (Context context){
        driverNames = new String[]{"Fred", "Barry", "Trisha", "Liz", "Mark"};
        driverGenders = new String[]{"Male", "Male", "Female", "Female", "Male"};
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater i = LayoutInflater.from(context);
        View view = i.inflate(R.layout.recycle_view_driver_option, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(driverNames[position]);
        holder.gender.setText(driverGenders[position]);
    }

    @Override
    public int getItemCount() {
        return driverNames.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView gender;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.driverName);
            gender = itemView.findViewById(R.id.driverGender);
        }
    }
}
