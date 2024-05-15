package com.example.gamezoneproject.domain.validation.file;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageWidthValidator implements ConstraintValidator<ImageWidth, MultipartFile> {
    private int minWidth;
    private int maxWidth;

    @Override
    public void initialize(ImageWidth constraintAnnotation) {
        maxWidth = constraintAnnotation.maxImageWidth();
        minWidth = constraintAnnotation.minImageWidth();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        if (file != null && !file.isEmpty()) {
            try {
                BufferedImage image = ImageIO.read(file.getInputStream());
                int width = image.getWidth();
                if (width < minWidth || width > maxWidth) {
                    return false;
                }
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

}
