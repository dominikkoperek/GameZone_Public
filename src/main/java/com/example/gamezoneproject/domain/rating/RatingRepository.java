package com.example.gamezoneproject.domain.rating;

import com.example.gamezoneproject.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    Optional<Rating> findByUser_loginAndGame_Id(String user_login, Long game_id);
    List<Rating> findAllByGame_id(Long game_id);
}
