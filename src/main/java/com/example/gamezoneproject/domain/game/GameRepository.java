package com.example.gamezoneproject.domain.game;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Hibernate Interface that is responsible for all operations with Game entity in database.
 */
public interface GameRepository extends JpaRepository<Game, Long> {


    /**
     * Finds all content that are promoted sorted by release date.
     *
     * @return A sorted list of all promoted content.
     */
    @Query("SELECT g FROM Game g JOIN GameReleaseCalendar grc ON g.id=grc.game.id  " +
            "WHERE year (grc.releaseCalendar.releaseDate)<3000 and g.promoted=true " +
            "GROUP BY g.id ORDER BY min( grc.releaseCalendar.releaseDate)  DESC")
    Page<Game> findAllByPromotedIsTrueSortedByReleaseDate(Pageable pageable);
    /**
     * Finds all content by category name, ignoring case and sort them by release date.
     *
     * @param category The name of the category.
     * @return A sorted list of all content in the specified category.
     */

    @Query("SELECT g FROM Game g JOIN GameReleaseCalendar grc ON g.id=grc.game.id  JOIN g.category ca " +
            "WHERE year (grc.releaseCalendar.releaseDate)<3000 and LOWER(ca.name) = LOWER(:category)" +
            "GROUP BY g.id ORDER BY min(grc.releaseCalendar.releaseDate)  DESC")
    Page<Game> findAllByCategoryNameSortedByReleaseDateIgnoreCase(@Param("category")String category,Pageable pageable);
    /**
     * Finds all content by game platform name, ignoring case and sort them by release date.
     *
     * @param platform The name of the game platform.
     * @return A sorted list of all content on the specified platform.
     */

    @Query("SELECT g FROM Game g JOIN GameReleaseCalendar grc ON g.id=grc.game.id  JOIN g.gamePlatform gp " +
            "WHERE year (grc.releaseCalendar.releaseDate)<3000 and LOWER(gp.name) = LOWER(:platform)" +
            "GROUP BY g.id ORDER BY min(grc.releaseCalendar.releaseDate)  DESC")
    Page<Game> findAllGamesByPlatformSortedByReleaseDate(@Param("platform")String platform,Pageable pageable);
    /**
     * Finds all content by producer ID that are promoted.
     *
     * @param producerId The ID of the producer.
     * @return A list of all promoted content by the specified producer.
     */
    List<Game> findAllByProducer_IdAndPromotedIsTrue(Long producerId);
    /**
     * Finds all content by publisher ID that are promoted.
     *
     * @param publisherId The ID of the publisher.
     * @return A list of all promoted content by the specified publisher.
     */
    List<Game> findAllByPublisher_IdAndPromotedIsTrue(Long publisherId);
    /**
     * Finds all content by publisher ID.
     *
     * @param publisherId The ID of the publisher.
     * @return A list of all content by the specified publisher.
     */
    List<Game> findAllByPublisher_IdOrderByReleaseDate_ReleaseDateDesc(Long publisherId);
    /**
     * Finds all content by producer ID.
     *
     * @param producerId The ID of the producer.
     * @return A list of all content by the specified producer.
     */
    List<Game> findAllByProducer_IdOrderByReleaseDate_ReleaseDateDesc(Long producerId);
    /**
     * Finds a game by title, ignoring case.
     *
     *
     * @param title The title of the game.
     * @return An Optional containing the game if found, or empty if not found.
     */
    Optional<Game> findByTitleIgnoreCase(String title);

    /**
     * Find all content sorted by release date
     * @return List of content sorted by release date descending
     */
    @Query("SELECT g FROM Game g JOIN GameReleaseCalendar grc ON g.id=grc.game.id " +
            "WHERE  year(grc.releaseCalendar.releaseDate)<3000 " +
            "GROUP BY g.id ORDER BY min(grc.releaseCalendar.releaseDate) DESC ")
    Page<Game> findAllSortedByOldestReleaseDate(Pageable pageable);

    /**
     * Find a game where the future release year is closest to present day
     * @return
     */
    @Query("SELECT g FROM Game g " +
            "JOIN GameReleaseCalendar grc ON g.id = grc.game.id " +
            "JOIN ReleaseCalendar rc ON grc.releaseCalendar.id = rc.id " +
            "WHERE rc.releaseDate >= CURRENT_DATE AND YEAR (grc.releaseCalendar.releaseDate)<3000" +
            "ORDER BY ABS(TIMESTAMPDIFF(DAY,rc.releaseDate,CURRENT_DATE)) asc limit 1 ")
    Optional<Game> findGameByClosestPremierDate();



}
