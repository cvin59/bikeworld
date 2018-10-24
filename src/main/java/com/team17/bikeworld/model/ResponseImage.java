package com.team17.bikeworld.model;

import com.google.gson.annotations.Expose;

public class ResponseImage {
    @Expose
    private int uploaded;
    @Expose
    private String fileName;
    @Expose
    private String url;

    public ResponseImage(int uploaded, String fileName, String url) {
        this.uploaded = uploaded;
        this.fileName = fileName;
        this.url = url;
    }

    public int getUploaded() {
        return uploaded;
    }

    public void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
