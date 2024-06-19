package com.example.gamezoneproject.domain.rating;

import com.example.gamezoneproject.domain.game.Game;
import com.example.gamezoneproject.domain.game.GameRepository;
import com.example.gamezoneproject.domain.user.User;
import com.example.gamezoneproject.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public RatingService(RatingRepository ratingRepository, UserRepository userRepository, GameRepository gameRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @Transactional
    public void addOrUpdateRating(String userName, long gameId, double rating) {
        Rating ratingToSave = ratingRepository
                .findByUser_loginAndGame_Id(userName, gameId)
                .orElseGet(Rating::new);
        User user = userRepository.findByLoginIgnoreCase(userName).orElseThrow();
        Game game = gameRepository.findById(gameId).orElseThrow();
        ratingToSave.setUser(user);
        ratingToSave.setGame(game);
        ratingToSave.setRating(rating);
        ratingRepository.save(ratingToSave);
    }

    @Transactional
    public void removeRating(String userName, long gameId) {
        Rating rating = ratingRepository.findByUser_loginAndGame_Id(userName, gameId).orElseGet(Rating::new);
        ratingRepository.delete(rating);
    }

    public Optional<Double> getUserRatingForGame(String userName, long gameId) {
        return ratingRepository.findByUser_loginAndGame_Id(userName, gameId).map(Rating::getRating);
    }

    public Map<Double, Double> getAllRatesForGame(long gameId) {
        int rateCount = gameRepository.findById(gameId).orElseThrow().getRatings().size();
        List<Rating> allByGameId = ratingRepository.findAllByGame_id(gameId);
        return allByGameId.stream()
                .collect(Collectors.groupingBy(Rating::getRating,
                        Collectors.collectingAndThen(
                                Collectors.summingInt(rating -> 1),
                                count -> (count / (double)rateCount) * 100)
                ));
    }
}

