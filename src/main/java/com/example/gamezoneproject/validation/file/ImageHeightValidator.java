package com.example.gamezoneproject.validation.file;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageHeightValidator implements ConstraintValidator<ImageHeight, MultipartFile> {
    private int minHeight;
    private int maxHeight;
    @Override
    public void initialize(ImageHeight constraintAnnotation) {
        maxHeight = constraintAnnotation.maxImageHeight();
        minHeight = constraintAnnotation.minImageHeight();
    }
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        if(file != null && !file.isEmpty()) {
            try {
                BufferedImage image = ImageIO.read(file.getInputStream());
                int Height = image.getHeight();
                if (Height < minHeight || Height > maxHeight) {
                    return false;
                }
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }


}
