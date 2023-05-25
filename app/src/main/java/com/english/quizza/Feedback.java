package com.english.quizza;

public class Feedback {
    private String userName;
    private String userEmail;
    private String userMsg;

    public Feedback() {
        // Default constructor required for calls to DataSnapshot.getValue(com.english.quizza.Feedback.class)
    }

    public Feedback(String userName, String userEmail, String userMsg) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userMsg = userMsg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }
}
