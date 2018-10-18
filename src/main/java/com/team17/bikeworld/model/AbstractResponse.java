package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AbstractResponse {

    @Expose
    @SerializedName(value = "status_code")
    private int statusCode;

    @Expose
    @SerializedName(value = "message")
    private String message;

    public AbstractResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}