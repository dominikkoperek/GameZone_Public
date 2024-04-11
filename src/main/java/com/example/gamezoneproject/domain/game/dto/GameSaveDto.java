package com.example.gamezoneproject.domain.game.dto;

import com.example.gamezoneproject.domain.game.gameDetails.playersRange.PlayerRange;
import com.example.gamezoneproject.domain.validation.category.CategoryNotExists;
import com.example.gamezoneproject.domain.validation.company.CompanyNotExists;
import com.example.gamezoneproject.domain.validation.file.MaxFileSize;
import com.example.gamezoneproject.domain.validation.game.NoGameDuplication;
import com.example.gamezoneproject.domain.validation.other.containsh2.ContainsH2;
import com.example.gamezoneproject.domain.validation.other.date.Date;
import com.example.gamezoneproject.domain.validation.other.illegalexpression.NoIllegalExpression;
import com.example.gamezoneproject.domain.validation.other.alphanumeric.Alphanumeric;
import com.example.gamezoneproject.domain.validation.platform.PlatformNotExists;
import com.example.gamezoneproject.domain.validation.playersrange.PlayersRange;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Simple DTO class for the Game entity.
 * This is used for saving games, and includes information such as the
 * game's title, trailer id,short description, long description, release date, list of categories names,
 * set of platforms names,list of game modes names, producer, publisher, is promoted, poster and players range.
 */
public class GameSaveDto {
    @Size(min = 2, max = 99)
    @NoGameDuplication
    @NoIllegalExpression
    private String title;
    @Size(min = 4, max = 30)
    @Alphanumeric
    private String dailymotionTrailerId;
    @NoIllegalExpression
    @Size(min = 100, max = 320)
    private String shortDescription;
    @NoIllegalExpression
    @Size(min = 200, max = 105_000)
    @ContainsH2
    private String description;
    @Date(min = 1980, max = 3000)
    private LocalDate releaseYear;
    @Size(min = 4, max = 14)
    @CategoryNotExists
    private LinkedList<String> category;
    @CategoryNotExists
    private String mainCategory;
    @Size(min = 1, max = 14)
    @PlatformNotExists
    private Set<String> platform;
    @NotEmpty
    private List<String> gameModes;
    @NotNull
    private boolean promoted;
    @NotEmpty
    @CompanyNotExists
    private String producer;
    @NotEmpty
    @CompanyNotExists
    private String publisher;
    @MaxFileSize(maxSizeMb = 1)
    private MultipartFile poster;
    @PlayersRange(rangeMin = 1, rangeMax = 2000,maxMin=1000)
    private PlayerRange playerRange;

    private MultipartFile smallPosterSuggestion;
    private MultipartFile bigPosterSuggestion;

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

    public LinkedList<String> getCategory() {
        return category;
    }

    public void setCategory(LinkedList<String> category) {
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

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public MultipartFile getSmallPosterSuggestion() {
        return smallPosterSuggestion;
    }

    public void setSmallPosterSuggestion(MultipartFile smallPosterSuggestion) {
        this.smallPosterSuggestion = smallPosterSuggestion;
    }

    public MultipartFile getBigPosterSuggestion() {
        return bigPosterSuggestion;
    }

    public void setBigPosterSuggestion(MultipartFile bigPosterSuggestion) {
        this.bigPosterSuggestion = bigPosterSuggestion;
    }
}
