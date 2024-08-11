package com.example.gamezoneproject.domain.game;

import com.example.gamezoneproject.domain.game.gameDetails.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Hibernate Interface that is responsible for all operations with Game entity in database.
 */
public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {

    Page<Game> findAllBy(Pageable pageable);

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
    Page<Game> findAllByCategoryNameSortedByReleaseDateIgnoreCase(@Param("category") String category, Pageable pageable);

    /**
     * Finds all content by game platform name, ignoring case and sort them by release date.
     *
     * @param platform The name of the game platform.
     * @return A sorted Page of all content on the specified platform.
     */

    @Query("SELECT g FROM Game g JOIN GameReleaseCalendar grc ON g.id=grc.game.id  JOIN g.gamePlatform gp " +
            "WHERE LOWER(gp.name) = LOWER(:platform)" +
            "GROUP BY g.id ORDER BY min(grc.releaseCalendar.releaseDate)  DESC")
    Page<Game> findAllGamesByPlatformSortedByReleaseDate(@Param("platform") String platform, Pageable pageable);


    /**
     * Method search game by platform and lists of categories.
     *
     * @param platform   The name of the game platform.
     * @param categories List of game categories.
     * @param size       number of elements in categories.
     * @return Page with all games which have the chosen platform and contain all attached categories.
     */

    @Query("SELECT g FROM Game g " +
            "JOIN g.category c " +
            "JOIN g.gamePlatform gp " +
            "JOIN GameReleaseCalendar grc ON g.id=grc.game.id " +
            "WHERE lower(gp.name) = lower(:platform) AND g.id IN (" +
            "SELECT game.id FROM Game game JOIN game.category cat " +
            "WHERE lower(cat.name) IN :categories GROUP BY game.id HAVING COUNT(game.id) = :size)" +
            "GROUP BY g.id")
    Page<Game> findByPlatformAndCategories(@Param("platform") String platform,
                                           @Param("categories") List<String> categories,
                                           @Param("size") long size,
                                           Pageable pageable);

    List<Game> findAllByProducer_IdAndPromotedIsTrue(Long producerId);

    List<Game> findAllByPublisher_IdAndPromotedIsTrue(Long publisherId);

    List<Game> findAllByPublisher_IdOrderByReleaseDate_ReleaseDateDesc(Long publisherId);

    List<Game> findAllByProducer_IdOrderByReleaseDate_ReleaseDateDesc(Long producerId);

    Optional<Game> findByTitleIgnoreCase(String title);

    /**
     * Find all content sorted by release date
     *
     * @return List of content sorted by release date descending
     */
    @Query("SELECT g FROM Game g JOIN GameReleaseCalendar grc ON g.id=grc.game.id " +
            "WHERE  year(grc.releaseCalendar.releaseDate)<3000 " +
            "GROUP BY g.id ORDER BY min(grc.releaseCalendar.releaseDate) DESC ")
    Page<Game> findAllSortedByOldestReleaseDate(Pageable pageable);

    /**
     * Find a game where the future release year is closest to present day
     *
     * @return
     */
    @Query("SELECT g FROM Game g " +
            "JOIN GameReleaseCalendar grc ON g.id = grc.game.id " +
            "JOIN ReleaseCalendar rc ON grc.releaseCalendar.id = rc.id " +
            "WHERE rc.releaseDate >= CURRENT_DATE AND YEAR (grc.releaseCalendar.releaseDate)<3000" +
            "ORDER BY ABS(TIMESTAMPDIFF(DAY,rc.releaseDate,CURRENT_DATE)) asc limit 1 ")
    Optional<Game> findGameByClosestPremierDate();

}
