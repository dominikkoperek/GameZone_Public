package com.example.gamezoneproject.domain.game.gameDetails.company.dto;

import com.example.gamezoneproject.domain.validation.company.NoCompanyDuplication;
import com.example.gamezoneproject.domain.validation.country.CountryNotExists;
import com.example.gamezoneproject.domain.validation.file.MaxFileSize;
import com.example.gamezoneproject.domain.validation.other.containsh2.ContainsH2;
import com.example.gamezoneproject.domain.validation.other.illegalexpression.NoIllegalExpression;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class CompanySaveDto {
    @NotBlank
    @Size(min = 3,max = 35)
    @NoCompanyDuplication
    @NoIllegalExpression
    private String name;
    @NotBlank
    @Size(min = 100,max = 1000)
    @NoIllegalExpression
    private String shortDescription;
    @NotBlank
    @NoIllegalExpression
    @Size(min = 200, max = 105_000)
    @ContainsH2
    private String description;
    @NotBlank
    @CountryNotExists
    private String country;

    @NotNull
    private boolean isProducer;
    @NotNull
    private boolean isPublisher;
    @NotNull
    @MaxFileSize(maxSizeMb = 1)
    private MultipartFile poster;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isProducer() {
        return isProducer;
    }

    public void setProducer(boolean producer) {
        isProducer = producer;
    }

    public boolean isPublisher() {
        return isPublisher;
    }

    public void setPublisher(boolean publisher) {
        isPublisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getPoster() {
        return poster;
    }

    public void setPoster(MultipartFile poster) {
        this.poster = poster;
    }
}
