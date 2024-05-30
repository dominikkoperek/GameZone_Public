package com.example.gamezoneproject.domain.game;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Hibernate Interface that is responsible for all operations with Game entity in database.
 */
public interface GameRepository extends CrudRepository<Game, Long> {
    /**
     * Finds all games that are promoted sorted by release date.
     *
     * @return A sorted list of all promoted games.
     */
    @Query("SELECT g FROM Game g JOIN GameReleaseCalendar grc ON g.id=grc.game.id  " +
            "WHERE year (grc.releaseCalendar.releaseDate)<3000 and g.promoted=true " +
            "ORDER BY grc.releaseCalendar.releaseDate  DESC")
    List<Game> findAllByPromotedIsTrueSortedByReleaseDate();
    /**
     * Finds all games by category name, ignoring case and sort them by release date.
     *
     * @param category The name of the category.
     * @return A sorted list of all games in the specified category.
     */

    @Query("SELECT g FROM Game g JOIN GameReleaseCalendar grc ON g.id=grc.game.id  JOIN g.category ca " +
            "WHERE year (grc.releaseCalendar.releaseDate)<3000 and LOWER(ca.name) = LOWER(:category)" +
            "ORDER BY grc.releaseCalendar.releaseDate  DESC")
    List<Game> findAllByCategoryNameSortedByReleaseDateIgnoreCase(@Param("category")String category);
    /**
     * Finds all games by game platform name, ignoring case and sort them by release date.
     *
     * @param platform The name of the game platform.
     * @return A sorted list of all games on the specified platform.
     */
    @Query("SELECT g FROM Game g JOIN GameReleaseCalendar grc ON g.id=grc.game.id  JOIN g.gamePlatform gp " +
            "WHERE year (grc.releaseCalendar.releaseDate)<3000 and LOWER(gp.name) = LOWER(:platform)" +
            "ORDER BY grc.releaseCalendar.releaseDate  DESC")
    List<Game> findAllGamesByPlatformSortedByReleaseDate(@Param("platform")String platform);
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
     * Find all games sorted by release date
     * @return List of games sorted by release date descending
     */
    @Query("SELECT g,min(grc.releaseCalendar.releaseDate) FROM Game g JOIN GameReleaseCalendar grc ON g.id=grc.game.id " +
            "WHERE  year (grc.releaseCalendar.releaseDate)<3000 " +
            "GROUP BY g.id ORDER BY min(grc.releaseCalendar.releaseDate) DESC")
    List<Game> findAllSortedByOldestReleaseDate();

    /**
     * Find a game where the future release year is closest to present day
     * @return
     */
    @Query("SELECT g FROM Game g " +
            "JOIN GameReleaseCalendar grc ON g.id = grc.game.id " +
            "JOIN ReleaseCalendar rc ON grc.releaseCalendar.id = rc.id " +
            "WHERE rc.releaseDate >= CURRENT_DATE AND YEAR (grc.releaseCalendar.releaseDate)<3000" +
            "ORDER BY ABS(DATEDIFF(DAY, rc.releaseDate,CURRENT_DATE)) asc limit 1 ")
    Optional<Game> findGameByClosestPremierDate();



}
