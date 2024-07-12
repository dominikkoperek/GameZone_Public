package com.example.gamezoneproject.domain.game.service;

import com.example.gamezoneproject.domain.game.dto.*;
import com.example.gamezoneproject.domain.game.dto.page.GamePageApiDto;
import com.example.gamezoneproject.domain.game.dto.page.GamePageDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

 public interface GameService {
     GamePageDto findAllPromotedGames(int pageNo, int pageSize);
     Optional<GameDto> findById(Long gameId);
     List<GameApiDto> findAllGamesApi();
     GamePageDto findGamesByCategoryName(String category,int pageNo, int pageSize);
     GamePageDto findGamesByGamePlatformName(String gamePlatform,int pageNo, int pageSize);
     List<GameByCompanyDto> findAllPromotedGamesByProducerId(Long producerId);
     List<PromotedGameByCompanyDto> findAllPromotedGamesByPublisherId(Long publisherId);
     GamePageDto findAllGamesSortedByOldestReleaseDate(int pageNo, int pageSize);

     GamePageApiDto findAllGamesSortedByOldestReleaseDateApi(int pageNo, int pageSize);

     boolean isTitleAvailable(String gameTitle);
     List<GameByCompanyDto> findAllGamesByProducerId(Long producerId);
     Optional<GameSuggestionsDto> findGameByClosestPremierDate();
     Map<LocalDate, List<String>> mergeSameReleaseDates(GameDto gameDto);
     List<GameByCompanyDto> findAllGamesByPublisherId(Long publisherId);
     Optional<GameDto> findByTitle(String name);
     void addGame(GameSaveDto gameSaveDto);
     Map<String, LocalDate> mapToReleaseDateMap(List<String> platformName, List<String> releaseDate);




}
