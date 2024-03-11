package com.example.gamezoneproject.domain.game.dto;

import com.example.gamezoneproject.domain.game.gameDetails.modes.dto.GameModeDto;
import com.example.gamezoneproject.domain.game.gameDetails.modes.dto.GameModeSaveDto;
import com.example.gamezoneproject.domain.game.gameDetails.modes.gameMode.GameMode;
import com.example.gamezoneproject.domain.game.gameDetails.playersRange.PlayerRange;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class GameSaveDto {
    private String title;
    private String dailymotionTrailerId;
    private String shortDescription;
    private String description;
    private LocalDate releaseYear;
    private List<String> category;
    private Set<String> platform;
    private List<String> gameModes;
    private boolean promoted;
    private String producer;
    private String publisher;
    private MultipartFile poster;
    private PlayerRange playerRange;

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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(LocalDate releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public Set<String> getPlatform() {
        return platform;
    }

    public void setPlatform(Set<String> platform) {
        this.platform = platform;
    }

    public List<String> getGameModes() {
        return gameModes;
    }

    public void setGameModes(List<String> gameModes) {
        this.gameModes = gameModes;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public MultipartFile getPoster() {
        return poster;
    }

    public void setPoster(MultipartFile poster) {
        this.poster = poster;
    }

    public PlayerRange getPlayerRange() {
        return playerRange;
    }

    public void setPlayerRange(PlayerRange playerRange) {
        this.playerRange = playerRange;
    }
}
