package com.example.caregiverapp;

public class ImageModel {
    private String imageUrl;

    ImageModel(){}

    public ImageModel(String imageUrl){
        this.imageUrl=imageUrl;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
