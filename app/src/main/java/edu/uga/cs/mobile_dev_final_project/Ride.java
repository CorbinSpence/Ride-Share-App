package edu.uga.cs.mobile_dev_final_project;

public class Ride {
    private String pickup_address;
    private String destination_address;
    private String date;

    public Ride() {}

    public Ride(String pickup_address, String destination_address, String date) {
        this.pickup_address = pickup_address;
        this.destination_address = destination_address;
        this.date = date;

    }

    public String getPickup_address() {
        return pickup_address;
    }

    public void setPickup_address(String pickup_address) {
        this.pickup_address = pickup_address;
    }

    public String getDestination_address() {
        return destination_address;
    }

    public void setDestination_address(String destination_address) {
        this.destination_address = destination_address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
