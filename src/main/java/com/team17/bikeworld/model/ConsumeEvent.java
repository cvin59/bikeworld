package com.team17.bikeworld.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;

public class ConsumeEvent {
    private int id;
    private String name;
    private int status;
    private String imageUrl;

    public ConsumeEvent() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ConsumeEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", imageUrl=" + imageUrl +
                '}';
    }
}
