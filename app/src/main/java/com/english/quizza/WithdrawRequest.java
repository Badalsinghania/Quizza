package com.english.quizza;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class WithdrawRequest {
    private String userId;
    private String upiAddress;
    private String requestedBy;
    private  String amount;

    public WithdrawRequest() {

    }

    public WithdrawRequest(String userId, String upiAddress, String amount, String requestedBy) {
        this.userId = userId;
        this.amount = amount;
        this.upiAddress = upiAddress;
        this.requestedBy = requestedBy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return upiAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.upiAddress = emailAddress;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    @ServerTimestamp
    private Date createAt;

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
