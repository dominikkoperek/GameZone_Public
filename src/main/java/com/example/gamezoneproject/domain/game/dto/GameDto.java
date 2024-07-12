package com.example.gamezoneproject.domain.game.dto;

import com.example.gamezoneproject.domain.company.Company;
import com.example.gamezoneproject.domain.game.dto.page.BaseGameDto;
import com.example.gamezoneproject.domain.game.gameDetails.category.Category;
import com.example.gamezoneproject.domain.game.gameDetails.modes.GameMode;
import com.example.gamezoneproject.domain.game.gameDetails.playersRange.PlayerRange;

import java.time.LocalDate;
import java.util.*;

/**
 * Main DTO class for the Game entity.
 * It's responsible for mapping from the Game entity to DTO.
 * This DTO contain ALL game details fields.
 */
public class GameDto extends BaseGameDto {
    private Long id;
    private String title;
    private String dailymotionTrailerId;
    private String shortDescription;
    private String description;
    private Map<String, LocalDate> releaseYear;
    private List<Category> category;
    private Map<String, String> platform;
    private List<GameMode> gameModes;
    private boolean promoted;
    private Company producer;
    private Company publisher;
    private String poster;
    private PlayerRange playerRange;
    private double averageRating;
    private int ratingCount;

    public GameDto(Long id, String title, String dailymotionTrailerId, String shortDescription, String description,
                   Map<String, LocalDate> releaseYear, List<Category> category, Map<String, String> platform,
                   List<GameMode> gameModes, boolean promoted, Company producer, Company publisher, String poster,
                   PlayerRange playerRange, double averageRating, int ratingCount) {
        this.id = id;
        this.title = title;
        this.dailymotionTrailerId = dailymotionTrailerId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.releaseYear = releaseYear;
        this.category = category;
        this.platform = platform;
        this.gameModes = gameModes;
        this.promoted = promoted;
        this.producer = producer;
        this.publisher = publisher;
        this.poster = poster;
        this.playerRange = playerRange;
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public List<GameMode> getGameModes() {
        return gameModes;
    }

    public void setGameModes(List<GameMode> gameModes) {
        this.gameModes = gameModes;
    }

    public Map<String, String> getPlatform() {
        return new TreeMap<>(platform);
    }

    public void setPlatform(Map<String, String> platform) {
        this.platform = platform;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDailymotionTrailerId() {
        return dailymotionTrailerId;
    }

    public void setDailymotionTrailerId(String dailymotionTrailerId) {
        this.dailymotionTrailerId = dailymotionTrailerId;
    }

    public Map<String, LocalDate> getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Map<String, LocalDate> releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company getProducer() {
        return producer;
    }

    public void setProducer(Company producer) {
        this.producer = producer;
    }

    public Company getPublisher() {
        return publisher;
    }

    public void setPublisher(Company publisher) {
        this.publisher = publisher;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public PlayerRange getPlayerRange() {
        return playerRange;
    }

    public void setPlayerRange(PlayerRange playerRange) {
        this.playerRange = playerRange;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }
}
