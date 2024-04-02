package com.example.gamezoneproject.domain.validation.file;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class MaxFileSizeValidator implements ConstraintValidator<MaxFileSize, MultipartFile> {
    private int maxSizeMb;

    @Override
    public void initialize(MaxFileSize constraintAnnotation) {
        maxSizeMb = constraintAnnotation.maxSizeMb();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        if (file != null && !file.isEmpty()) {
            double fileSize = (file.getSize() / 1024.0) / 1024.0;
            return fileSize <= maxSizeMb;
        }
        return true;
    }
}
