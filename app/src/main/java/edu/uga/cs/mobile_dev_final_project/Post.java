package edu.uga.cs.mobile_dev_final_project;

public class Post {

    private int post_type;        // if post type 0, rider; if post type 1, driver;
    private int travel_type;      // if post type 0, in-town ride; if post type 1, out-of-town ride;
    private String date_of_ride;
    private String pickup_address;
    private String destination_address;
    private String poster_name;
    private String acceptor_name;

    public Post() {}

    public Post( int post_type, int travel_type, String date_of_ride, String pickup_address, String destination_address,
                 String poster_name, String acceptor_name ) {
        this.post_type = post_type;
        this.travel_type = travel_type;
        this.date_of_ride = date_of_ride;
        this.pickup_address = pickup_address;
        this.destination_address = destination_address;
        this.poster_name = poster_name;
        this.acceptor_name = acceptor_name;
    }

    public int getPost_type() {
        return post_type;
    }

    public int getTravel_type() {
        return travel_type;
    }

    public String getDate_of_ride() {
        return date_of_ride;
    }

    public String getPickup_address() {
        return pickup_address;
    }

    public String getDestination_address() {
        return destination_address;
    }

    public String getPoster_name() {
        return poster_name;
    }

    public String getAcceptor_name() {
        return acceptor_name;
    }

}
