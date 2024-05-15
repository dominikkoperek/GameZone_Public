package com.example.gamezoneproject.storage.storageStrategy;

import org.springframework.stereotype.Service;

@Service
public class CompanyPoster implements ImageStrategy {
    private final static String LOCATION = "/galeria/firmy/";
    @Override
    public String setImageLocation(String location) {
        return location + LOCATION;
    }


    @Override
    public int getImageTargetHeight() {
        return 160;
    }

    @Override
    public int getImageTargetWidth() {
        return 160;
    }

    @Override
    public String getImagePreparedName() {
        return "";
    }
}
