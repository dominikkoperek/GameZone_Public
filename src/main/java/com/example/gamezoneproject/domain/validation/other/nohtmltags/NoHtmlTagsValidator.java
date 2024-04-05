package com.example.gamezoneproject.domain.validation.other.nohtmltags;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoHtmlTagsValidator implements ConstraintValidator<NoHtmlTags,String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern htmlTag = Pattern.compile("<\\w+\\s*[^>]*>");
        Matcher matcher = htmlTag.matcher(s);
        return !matcher.find();
    }
}
