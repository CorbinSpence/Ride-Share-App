package edu.uga.cs.mobile_dev_final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PastRidesRecyclerView extends RecyclerView.Adapter<PastRidesRecyclerView.MyViewHolder>{
    Context context;
    String[] partners, pickups, dests, types;

    public PastRidesRecyclerView(Context context, @NonNull ArrayList<String> partners, @NonNull ArrayList<String> pickups, @NonNull ArrayList<String> dests, @NonNull ArrayList<String> types) {
        partners.toArray(this.partners);
        pickups.toArray(this.pickups);
        dests.toArray(this.dests);
        types.toArray(this.types);
    }

    @NonNull
    @Override
    public PastRidesRecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater i = LayoutInflater.from(context);
        View view = i.inflate(R.layout.recycle_view_past_rides, parent, false);
        return new PastRidesRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.dest.setText(dests[position]);
        holder.partner.setText(partners[position]);
        holder.pickup.setText(pickups[position]);
        holder.type.setText(types[position]);
    }



    @Override
    public int getItemCount() {
        return partners.length;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView pickup, dest, partner, type;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pickup = itemView.findViewById(R.id.pastPickup);
            dest = itemView.findViewById(R.id.pastDest);
            partner = itemView.findViewById(R.id.pastPartner);
            type = itemView.findViewById(R.id.pastRideType);
        }
    }
}
