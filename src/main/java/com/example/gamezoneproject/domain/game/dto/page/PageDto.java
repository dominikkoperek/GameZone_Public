package com.example.gamezoneproject.domain.game.dto.page;

import com.example.gamezoneproject.domain.game.dto.GameDto;

import java.util.List;

public interface PageDto<T extends BaseGameDto> {


    void setGames(List<T> games);
    List<T> getGames();
    int getPageNo();
    void setPageNo(int pageNo);
    int getPageSize();
    void setPageSize(int pageSize);
    long getTotalElements();
    void setTotalElements(long totalElements);
    int getTotalPages();
    void setTotalPages(int totalPages);
    boolean isFirstPage();
    void setFirstPage(boolean firstPage);
    boolean isLastPage();
    void setLastPage(boolean lastPage);
}
