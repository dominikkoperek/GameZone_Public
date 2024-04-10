package com.example.gamezoneproject.domain.game;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

/**
 * Hibernate Interface that is responsible for all operations with Game entity in database.
 */
public interface GameRepository extends CrudRepository<Game, Long> {
    /**
     * Finds all games that are promoted.
     *
     * @return A list of all promoted games.
     */
    List<Game> findAllByPromotedIsTrue();
    /**
     * Finds all games by category name, ignoring case.
     *
     * @param category The name of the category.
     * @return A list of all games in the specified category.
     */
    List<Game> findAllByCategory_NameIgnoreCase(String category);
    /**
     * Finds all games by game platform name, ignoring case.
     *
     * @param category The name of the game platform.
     * @return A list of all games on the specified platform.
     */
    List<Game> findAllByGamePlatform_NameIgnoreCase(String category);
    /**
     * Finds all games by producer ID that are promoted.
     *
     * @param producerId The ID of the producer.
     * @return A list of all promoted games by the specified producer.
     */
    List<Game> findAllByProducer_IdAndPromotedIsTrue(Long producerId);
    /**
     * Finds all games by publisher ID that are promoted.
     *
     * @param publisherId The ID of the publisher.
     * @return A list of all promoted games by the specified publisher.
     */
    List<Game> findAllByPublisher_IdAndPromotedIsTrue(Long publisherId);
    /**
     * Finds all games by publisher ID.
     *
     * @param publisherId The ID of the publisher.
     * @return A list of all games by the specified publisher.
     */
    List<Game> findAllByPublisher_Id(Long publisherId);
    /**
     * Finds all games by producer ID.
     *
     * @param producerId The ID of the producer.
     * @return A list of all games by the specified producer.
     */
    List<Game> findAllByProducer_Id(Long producerId);
    /**
     * Finds a game by title, ignoring case.
     *
     * @param title The title of the game.
     * @return An Optional containing the game if found, or empty if not found.
     */
    Optional<Game> findByTitleIgnoreCase(String title);

    /**
     * Find a game where the releaseYear is closest to present day
     * @return
     */

    @Query("SELECT e FROM Game e ORDER BY ABS(DATEDIFF(DAY, e.releaseYear, CURDATE())) LIMIT 1")
    Optional<Game> findGameByClosestReleaseDate();
}
