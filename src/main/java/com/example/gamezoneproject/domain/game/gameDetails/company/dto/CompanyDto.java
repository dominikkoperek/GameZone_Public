package com.example.gamezoneproject.domain.game.gameDetails.company.dto;


import com.example.gamezoneproject.domain.validation.company.NoCompanyDuplication;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

/**
 *   Main DTO class for the company.
 *   It's responsible for mapping from the Company entity to DTO.
 *   This DTO contain ALL company details fields.
 */
public class CompanyDto {
    private Long id;

    private String name;

    private String shortDescription;

    private String country;
    private Boolean isProducer;
    private Boolean isPublisher;
    private String description;
    private String poster;

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public CompanyDto(Long id, String name, String shortDescription, String country, Boolean isProducer,
                      Boolean isPublisher, String description, String poster) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.country = country;
        this.isProducer = isProducer;
        this.isPublisher = isPublisher;
        this.description = description;
        this.poster = poster;
    }

    public CompanyDto() {
    }

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

    public Boolean getProducer() {
        return isProducer;
    }

    public void setProducer(Boolean producer) {
        isProducer = producer;
    }

    public Boolean getPublisher() {
        return isPublisher;
    }

    public void setPublisher(Boolean publisher) {
        isPublisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
