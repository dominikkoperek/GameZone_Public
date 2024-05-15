package com.example.gamezoneproject.domain.validation.file;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CorruptedImageExtensionValidator implements ConstraintValidator<CorruptedImageExtension, MultipartFile> {
    private final Map<String, String> mimeToExtension;

    public CorruptedImageExtensionValidator() {
        this.mimeToExtension = new HashMap<>();
        mimeToExtension.put("image/jpeg", "jpg");
        mimeToExtension.put("image/png", "png");
    }


    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                TikaConfig config = new TikaConfig();
                Metadata metadata = new Metadata();
                String mimetype = config.getDetector().detect(
                        TikaInputStream.get(multipartFile.getInputStream()), metadata).toString();
                String extensionFromName = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
                String expectedExtension = mimeToExtension.get(mimetype);
                return expectedExtension.equals(extensionFromName);
            } catch (IOException | TikaException e) {
                return false;
            }
        }
        return true;
    }

}
