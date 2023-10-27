package com.grizzly.application.models;

import java.io.Serializable;
import java.util.Date;

public class RentalRequest implements Serializable {
    private String requestID;
    private Date date;
    private Boolean approved;
    private Quote quote;
    private String approvedBy;


    public RentalRequest(String requestID, Date date, Boolean approved, Quote quote, String approvedBy) {
        this.requestID = requestID;
        this.date = date;
        this.approved = approved;
        this.quote = quote;
        this.approvedBy = approvedBy;
    }


    public RentalRequest(String requestID, Date date) {
        this(requestID, date, false, null, "");
    }


    public RentalRequest(RentalRequest other) {
        this(other.requestID, other.date, other.approved, other.quote, other.approvedBy);
    }

    //toString
    @Override
    public String toString() {
        return "RentalRequest{" +
                "requestID='" + requestID + '\'' +
                ", date=" + date +
                ", approved=" + approved +
                ", quote=" + quote +
                ", approvedBy='" + approvedBy + '\'' +
                '}';
    }


    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
}
