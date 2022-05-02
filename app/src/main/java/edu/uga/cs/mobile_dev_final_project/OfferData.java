package edu.uga.cs.mobile_dev_final_project;

public class OfferData {

    private String posterID;
    private String posterName;
    private String posterGender;
    private String pickupAddress;
    private String destinationAddress;
    private String dateOfRide;
    private int travelType;

    public OfferData() {}

    public OfferData(String posterID, String posterName, String posterGender, String pickupAddress, String destinationAddress, String dateOfRide, int travelType) {

        this.posterID = posterID;
        this.posterName = posterName;
        this.posterGender = posterGender;
        this.pickupAddress = pickupAddress;
        this.destinationAddress = destinationAddress;
        this.dateOfRide = dateOfRide;
        this.travelType = travelType;
    }

    public String getPosterID() {
        return posterID;
    }

    public void setPosterID(String posterID) {
        this.posterID = posterID;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public String getPosterGender() {
        return posterGender;
    }

    public void setPosterGender(String posterGender) {
        this.posterGender = posterGender;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getDateOfRide() {
        return dateOfRide;
    }

    public void setDateOfRide(String dateOfRide) {
        this.dateOfRide = dateOfRide;
    }

    public int getTravelType() {
        return travelType;
    }

    public void setTravelType(int travelType) {
        this.travelType = travelType;
    }
}