package com.example.gamezoneproject.domain.game.gameDetails.platform;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Hibernate Interface that is responsible for all operations with GamePlatform entity in database.
 */
public interface GamePlatformRepository extends CrudRepository<GamePlatform, Long> {

    Optional<GamePlatform> findByNameIgnoreCase(String name);

    @Query("SELECT gp, COUNT(p.gameId) FROM GamePlatform gp " +
            "LEFT JOIN Platform p ON gp.id=p.platformId " +
            "WHERE (:platform IS NULL OR lower(gp.name) = lower(:platform)) "+
            "GROUP BY gp.name")
    List<Object[]> countGamesByAllPlatforms( @Param("platform") String platform);

    @Query("SELECT gp, COUNT(p.gameId) FROM GamePlatform gp " +
            "LEFT JOIN Platform p ON gp.id=p.platformId " +
            "WHERE (:platform IS NULL OR lower(gp.name) = lower(:platform)) AND " +
            "p.gameId IN(SELECT game.id FROM Game game JOIN game.category cat " +
            "WHERE cat.name IN :categories GROUP BY game.id HAVING COUNT (game.id)=:size) " +
            "GROUP BY gp.name")
    List<Object[]> countGamesByAllPlatformsAndCategories(@Param("categories") Set<String> categories,
                                                         @Param("size") int size,
                                                         @Param("platform") String platform);


}
