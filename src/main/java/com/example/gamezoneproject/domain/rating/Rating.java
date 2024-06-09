package com.example.gamezoneproject.domain.rating;

import com.example.gamezoneproject.domain.game.Game;
import com.example.gamezoneproject.domain.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "game_rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
    private Double rating;

    public Rating() {
    }

    public Rating(User user, Game game, Double rating) {
        this.user = user;
        this.game = game;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
