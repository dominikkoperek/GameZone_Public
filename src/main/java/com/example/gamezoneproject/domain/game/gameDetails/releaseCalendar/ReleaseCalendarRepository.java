package com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar;

import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReleaseCalendarRepository extends CrudRepository<ReleaseCalendar, Long> {
    Optional<ReleaseCalendar> findByReleaseDateAndGamePlatform_Name(LocalDate releaseDate, String gamePlatform);

    @Query("SELECT rc  FROM ReleaseCalendar rc JOIN GameReleaseCalendar grc ON rc.id=grc.releaseCalendar.id " +
            "WHERE grc.game.id=:gameId AND YEAR(grc.releaseCalendar.releaseDate)<3000 " +
            "ORDER BY ABS(DATEDIFF(DAY, rc.releaseDate,CURRENT_DATE)) limit 1")
    Optional<ReleaseCalendar> findEarliestReleaseDateFromTodayByGameId(@Param("gameId") Long gameId);

    @Query("SELECT DISTINCT rc.gamePlatform " +
            "FROM ReleaseCalendar rc " +
            "JOIN GameReleaseCalendar grc ON grc.releaseCalendar = rc " +
            "WHERE rc.releaseDate = (SELECT MIN(rc2.releaseDate) " +
            "FROM ReleaseCalendar rc2 " +
            "WHERE rc2.releaseDate >= CURRENT_DATE) and YEAR(grc.releaseCalendar.releaseDate)<3000")
    List<GamePlatform> findPlatformsByEarliestDateByGameId(@Param("gameId")Long gameId);
}
