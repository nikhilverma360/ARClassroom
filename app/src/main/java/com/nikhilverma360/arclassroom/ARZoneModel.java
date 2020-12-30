package com.nikhilverma360.arclassroom;

public class ARZoneModel {
    private String title, description, shortURL;

    public ARZoneModel(String title, String description, String shortURL) {
        this.title = title;
        this.description = description;
        this.shortURL = shortURL;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getShortURL() {
        return shortURL;
    }
}