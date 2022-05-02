package edu.uga.cs.mobile_dev_final_project;

public class PastRides {
    private Ride ride;

    public PastRides() {}

    public PastRides(Ride ride) {
        this.ride = ride;

    }


    public Ride getRide() {
        return ride;
    }

    public void setRide(Ride ride) {
        this.ride = ride;
    }
}
