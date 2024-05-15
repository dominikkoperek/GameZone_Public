package com.example.gamezoneproject.storage.storageStrategy;

import org.springframework.stereotype.Service;

@Service
public class GamePoster implements ImageStrategy {
    private final static String LOCATION = "/galeria/gry/";
    @Override
    public String setImageLocation(String location) {
        return location + LOCATION;
    }


    @Override
    public int getImageTargetHeight() {
        return 600;
    }

    @Override
    public int getImageTargetWidth() {
        return 400;
    }

    @Override
    public String getImagePreparedName() {
        return "";
    }
}
