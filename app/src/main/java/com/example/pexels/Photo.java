package com.example.pexels;

public class Photo {
    private String title;
    private String imageUrl;

    // Constructor
    public Photo(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    // Getter methods
    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
