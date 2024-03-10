package com.example.gamezoneproject.domain.game;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {
    List<Game> findAllByPromotedIsTrue();
    List<Game> findAllByCategory_NameIgnoreCase(String category);
    List<Game> findAllByGamePlatform_NameIgnoreCase(String category);
    List<Game> findAllByProducer_IdAndPromotedIsTrue(Long producerId);
    List<Game> findAllByPublisher_IdAndPromotedIsTrue(Long publisherId);
    List<Game> findAllByPublisher_Id(Long publisherId);
    List<Game> findAllByProducer_Id(Long producerId);

}
