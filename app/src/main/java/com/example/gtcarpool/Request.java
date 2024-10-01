package com.example.gtcarpool;

public class Request {
    String name = "";
    String dateAsked = "";
    String destination = "";
    String pickupLocation = "";
    String description = "";
    int image;

    public Request(String dateAsked, String destination, String pickupLocation, String description, int image, String name) {
        this.dateAsked = dateAsked;
        this.destination = destination;
        this.pickupLocation = pickupLocation;
        this.description = description;
        this.image = image;
        this.name = name;
    }

    public void setDateAsked(String dateAsked) {
        this.dateAsked = dateAsked;
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
    public void setImage(int image) {
        this.image = image;
    }

    public String getDateAsked() {
        return dateAsked;
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

}

