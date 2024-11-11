package com.example.gtcarpool;

import com.google.firebase.Timestamp;

public class Request {
    String name = "";
    Timestamp date;
    String destination = "";
    String pickupLocation = "";
    String description = "";

    String uid ="";
    int image = R.drawable.a;
    public Request() {
    }
    public Request(Timestamp date, String destination, String pickupLocation, String description, int image, String name, String uid) {
        this.date = date;
        this.destination = destination;
        this.pickupLocation = pickupLocation;
        this.description = description;

        this.name = name;
        this.uid = uid;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setUid(String uid) { this.uid = uid; }
    public Timestamp getDate() {
        return date;
    }
    public String getName() {
        return name;
    }
    public String getDestination() {
        return destination;
    }
    public String getPickupLocation() {
        return pickupLocation;
    }
    public String getDescription() {
        return description;
    }
    public int getImage() {
        return image;
    }

    public String getUid() { return uid; }

}

