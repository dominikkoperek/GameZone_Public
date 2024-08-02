package com.example.gamezoneproject.domain.game.gameDetails.platform;

import jakarta.persistence.*;

@Entity
@Table(name = "platform")
public class GamePlatform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String logoAddress;
    private String logoAddressImage;
    private String brand;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String platformDescription) {
        this.description = platformDescription;
    }

    public String getLogoAddress() {
        return logoAddress;
    }

    public void setLogoAddress(String logoAddress) {
        this.logoAddress = logoAddress;
    }

    public String getLogoAddressImage() {
        return logoAddressImage;
    }

    public void setLogoAddressImage(String logoAddressImage) {
        this.logoAddressImage = logoAddressImage;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
