package com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar;

import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReleaseCalendarRepository extends CrudRepository<ReleaseCalendar, Long> {
    Optional<ReleaseCalendar> findByReleaseDateAndGamePlatform(LocalDate releaseDate, String gamePlatform);

    @Query("SELECT rd  FROM ReleaseCalendar rd JOIN GameReleaseCalendar grd ON rd.id=grd.releaseCalendar.id " +
            "WHERE grd.game.id=:gameId ORDER BY ABS(DATEDIFF(DAY, rd.releaseDate,CURRENT_DATE)) limit 1")
    Optional<ReleaseCalendar> findEarliestReleaseDateFromTodayByGameId(@Param("gameId") Long gameId);
}
//    @Query("SELECT p FROM GamePlatform p " +
//            "JOIN ReleaseCalendar  rc ON p.id=rc.id" +
//            "JOIN GameRel"
//    )
//    List<GamePlatform> findPlatformsEarliestReleaseDateFromTodayByGameId(Long gameId);
//}
