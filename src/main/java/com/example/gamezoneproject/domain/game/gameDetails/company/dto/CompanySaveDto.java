package com.example.gamezoneproject.domain.game.gameDetails.company.dto;

import com.example.gamezoneproject.domain.validation.company.NoCompanyDuplication;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class CompanySaveDto {
    private Long id;
    @NotBlank
    @Size(min = 3,max = 35)
    @NoCompanyDuplication
    private String name;
    @NotBlank
    @Size(min = 100,max = 1000)
    private String shortDescription;

    private String country;
    private boolean isProducer;
    private boolean isPublisher;
    private String description;
    private MultipartFile poster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
