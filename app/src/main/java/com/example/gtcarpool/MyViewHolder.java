package com.example.gtcarpool;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameView, dateView, destinationView, pickupView, description;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        nameView = itemView.findViewById(R.id.nameText);
        dateView = itemView.findViewById(R.id.dateText);
        destinationView = itemView.findViewById(R.id.destinationText);
        pickupView = itemView.findViewById(R.id.pickupLocation);
        nameView = itemView.findViewById(R.id.description);

    }
}
