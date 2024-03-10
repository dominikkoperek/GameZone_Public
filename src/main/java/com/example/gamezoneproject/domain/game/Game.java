package com.example.gamezoneproject.domain.game;

import com.example.gamezoneproject.domain.game.gameDetails.company.Company;
import com.example.gamezoneproject.domain.game.gameDetails.modes.gameGameMode.GameGameMode;
import com.example.gamezoneproject.domain.game.gameDetails.category.Category;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String dailymotionTrailerId;
    private String shortDescription;
    private String description;
    private LocalDate releaseYear;
    @ManyToMany
    @JoinTable(name = "game_category",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private List<Category> category = new LinkedList<>();
    @ManyToOne
    @JoinColumn(name = "producer_id", referencedColumnName = "id")
    private Company producer;
    @ManyToOne
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private Company publisher;

    @ManyToMany
    @JoinTable(name = "game_platform",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id", referencedColumnName = "id")
    )
    private Set<GamePlatform> gamePlatform = new HashSet<>();

    @OneToMany(mappedBy = "game")
    private List<GameGameMode> gameModes = new LinkedList<>();

    private boolean promoted;
    private String poster;

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

    public List<GameGameMode> getGameModes() {
        return gameModes;
    }

    public void setGameModes(List<GameGameMode> gameModes) {
        this.gameModes = gameModes;
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

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }


    public Set<GamePlatform> getGamePlatform() {
        return gamePlatform;
    }

    public void setGamePlatform(Set<GamePlatform> gamePlatform) {
        this.gamePlatform = gamePlatform;
    }

    public boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(boolean promoted) {
        this.promoted = promoted;
    }

    public Company getProducer() {
        return producer;
    }

    public void setProducer(Company company) {
        this.producer = company;
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
}