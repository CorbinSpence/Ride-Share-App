package edu.uga.cs.mobile_dev_final_project;

public class User {
    private String email;
    private String fullName;
    private int travelPoints;

    public User() {}

    public User(String mail, String name, int points) {
        email = mail;
        fullName = name;
        travelPoints = points;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public int getTravelPoints() {
        return travelPoints;
    }
}
