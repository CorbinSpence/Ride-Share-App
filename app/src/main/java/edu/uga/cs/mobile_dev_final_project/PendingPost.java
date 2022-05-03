package edu.uga.cs.mobile_dev_final_project;

public class PendingPost {
    private String postID;
    private String acceptorID;
    private int post_type; // post type 0 = rider; post type 1 = driver

    public PendingPost() {

    }

    public PendingPost(String postID, String acceptorID, int post_type) {
        this.postID = postID;
        this.acceptorID = acceptorID;
        this.post_type = post_type;
    }

    public String getAcceptorID() {
        return acceptorID;
    }

    public void setAcceptorID(String acceptorID) {
        this.acceptorID = acceptorID;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public int getPost_type() {
        return post_type;
    }

    public void setPost_type(int post_type) {
        this.post_type = post_type;
    }

}
