package com.example.gamezoneproject.storage.storageStrategy;

import org.springframework.stereotype.Service;

@Service
public class BigPoster implements ImageStrategy {
    private final static String LOCATION = "/galeria/rekomendacje/l";
    @Override
    public String setImageLocation(String location) {
        return location + LOCATION;
    }


    @Override
    public int getImageTargetHeight() {
        return 350;
    }

    @Override
    public int getImageTargetWidth() {
        return 1100;
    }

    @Override
    public String getImagePreparedName() {
        return "_b";
    }
}
