package com.example.gamezoneproject.domain.game;

import com.example.gamezoneproject.domain.game.gameDetails.category.Category;
import com.example.gamezoneproject.domain.game.gameDetails.category.Category_;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform;
import com.example.gamezoneproject.domain.game.gameDetails.platform.GamePlatform_;
import com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar.ReleaseCalendar;
import com.example.gamezoneproject.domain.game.gameDetails.releaseCalendar.ReleaseCalendar_;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class GameSpecs {
    public static Specification<Game> hasPlatform(String platform) {
        return (root, query, criteriaBuilder) -> {
            if (platform != null && !platform.isEmpty()) {
                Join<Game, GamePlatform> platformJoin = root.join(Game_.GAME_PLATFORM, JoinType.LEFT);
                return criteriaBuilder.equal(criteriaBuilder.lower(platformJoin.get(GamePlatform_.NAME)), platform.toLowerCase());
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Game> hasCategories(Set<String> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories != null && !categories.isEmpty()) {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Game> gameRoot = subquery.from(Game.class);
                Join<Game, Category> categoryJoin = gameRoot.join(Game_.CATEGORY);
                subquery.select(criteriaBuilder.count(categoryJoin.get(Category_.ID)))
                        .where(criteriaBuilder.equal(gameRoot.get(Game_.ID), root.get(Category_.ID)),
                                categoryJoin.get(Category_.name).in(categories));
                return criteriaBuilder.equal(subquery, categories.size());
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<Game> hasBeenReleased(Boolean released, String platform) {
        LocalDate date = LocalDate.now();

        return (root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (released != null) {
                Join<Game, ReleaseCalendar> releaseJoin = root.join(Game_.RELEASE_DATE, JoinType.INNER);
                predicates.add(released ?
                        criteriaBuilder.lessThanOrEqualTo(releaseJoin.get(ReleaseCalendar_.RELEASE_DATE), date) :
                        criteriaBuilder.greaterThan(releaseJoin.get(ReleaseCalendar_.RELEASE_DATE), date));

                if (platform != null && !platform.isEmpty()) {
                    Join<ReleaseCalendar, GamePlatform> platformJoin = releaseJoin.join(ReleaseCalendar_.GAME_PLATFORM, JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(
                            criteriaBuilder.lower(platformJoin.get(GamePlatform_.NAME)),
                            platform.toLowerCase()));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Game> filterGames(String platform, Set<String> categories, Boolean released) {
        LocalDate date = LocalDate.now();
        return (root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            // Platform filter
            if (platform != null && !platform.isEmpty()) {
                Join<Game, GamePlatform> platformJoin = root.join(Game_.GAME_PLATFORM, JoinType.LEFT);
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(platformJoin.get(GamePlatform_.NAME)), platform.toLowerCase()));
            }

            // Categories filter
            if (categories != null && !categories.isEmpty()) {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<Game> gameRoot = subquery.from(Game.class);
                Join<Game, Category> categoryJoin = gameRoot.join(Game_.CATEGORY);
                subquery.select(criteriaBuilder.count(categoryJoin.get(Category_.ID)))
                        .where(criteriaBuilder.equal(gameRoot.get(Game_.ID), root.get(Category_.ID)),
                                categoryJoin.get(Category_.name).in(categories));
                predicates.add(criteriaBuilder.equal(subquery, categories.size()));
            }

            // Release date filter
            if (released != null) {
                Join<Game, ReleaseCalendar> releaseJoin = root.join(Game_.RELEASE_DATE, JoinType.INNER);
                predicates.add(released ?
                        criteriaBuilder.lessThanOrEqualTo(releaseJoin.get(ReleaseCalendar_.RELEASE_DATE), date) :
                        criteriaBuilder.greaterThan(releaseJoin.get(ReleaseCalendar_.RELEASE_DATE), date));

                if (platform != null && !platform.isEmpty()) {
                    Join<ReleaseCalendar, GamePlatform> platformJoin = releaseJoin.join(ReleaseCalendar_.GAME_PLATFORM, JoinType.INNER);
                    predicates.add(criteriaBuilder.equal(
                            criteriaBuilder.lower(platformJoin.get(GamePlatform_.NAME)),
                            platform.toLowerCase()));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
