package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response<E> extends AbstractResponse {

    @Expose
    @SerializedName(value = "data")
    private E data;

    public Response(int statusCode, String message) {
        super(statusCode, message);
    }

    public Response(int statusCode, String message, E data) {
        this(statusCode, message);
        this.data = data;
    }


    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public void setResponse(int statusCode, String message, E data) {
        super.setResponse(statusCode, message);
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "statusCode=" + getStatusCode() +
                ", message='" + getMessage() + '\'' +
                ", data=" + data +
                '}';
    }
}
