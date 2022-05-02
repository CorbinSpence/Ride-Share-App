package edu.uga.cs.mobile_dev_final_project;

public class PendingPost {
    private String postID;
    private String acceptorID;

    public PendingPost() {

    }

    public PendingPost(String postID, String acceptorID) {
        this.postID = postID;
        this.acceptorID = acceptorID;
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
}
