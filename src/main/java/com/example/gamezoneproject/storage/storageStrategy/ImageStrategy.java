package com.example.gamezoneproject.storage.storageStrategy;

import org.springframework.beans.factory.annotation.Value;

public interface ImageStrategy {

    String setImageLocation(String location);

    int getImageTargetHeight();

    int getImageTargetWidth();

    String getImagePreparedName();


}
