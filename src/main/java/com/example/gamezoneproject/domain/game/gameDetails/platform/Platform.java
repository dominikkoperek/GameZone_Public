package com.example.gamezoneproject.domain.game.gameDetails.platform;

import jakarta.persistence.*;

@Entity
@Table(name = "game_platform")
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "game_id")
    private Long gameId;
    @Column(name = "platform_id")
    private Long platformId;

    public Long getId() {
        return id;
    }


    public Long getGameId() {
        return gameId;
    }


    public Long getPlatformId() {
        return platformId;
    }

}
