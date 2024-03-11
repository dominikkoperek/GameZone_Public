package com.example.gamezoneproject.domain.game.gameDetails.playersRange;

import jakarta.persistence.Embeddable;

@Embeddable
public class PlayerRange {
    private Integer minPlayers;
    private Integer maxPlayers;

    public Integer getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(Integer minPlayers) {
        this.minPlayers = minPlayers;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
}
