package com.example.gamezoneproject.storage.storageStrategy;

import org.springframework.stereotype.Service;

@Service
public class OtherFiles implements ImageStrategy {
    private final static String LOCATION = "/pliki/";
    @Override
    public String setImageLocation(String location) {
        return location + LOCATION;
    }
    @Override
    public int getImageTargetHeight() {
        return 0;
    }

    @Override
    public int getImageTargetWidth() {
        return 0;
    }

    @Override
    public String getImagePreparedName() {
        return "";
    }
}
