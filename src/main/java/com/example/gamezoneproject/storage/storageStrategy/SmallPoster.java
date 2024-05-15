package com.example.gamezoneproject.storage.storageStrategy;

import org.springframework.stereotype.Service;
@Service
public class SmallPoster implements ImageStrategy {
    private final static String LOCATION = "/galeria/rekomendacje/s/";
    @Override
    public String setImageLocation(String location) {
        return location + LOCATION;
    }
    @Override
    public int getImageTargetHeight() {
        return 100;
    }

    @Override
    public int getImageTargetWidth() {
        return 300;
    }

    @Override
    public String getImagePreparedName() {
        return "_s";
    }
}
