package com.example.gtcarpool;

import android.content.ClipData;
import android.content.Context;
import android.media.RouteListingPreference;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Request> requests;

    public MyAdapter(List<RouteListingPreference.Item> items, Context context) {
        this.requests = requests;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameView.setText(requests.get(position).getName());
        holder.dateView.setText(requests.get(position).getDateAsked());
        holder.imageView.setImageResource(requests.get(position).getImage());
        holder.destinationView.setText(requests.get(position).getDestination());
        holder.pickupView.setText(requests.get(position).getPickupLocation());
        holder.descriptionView.setText(requests.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return requests.size();
    }
}
