package com.example.gamezoneproject.domain.game.dto.page;

import com.example.gamezoneproject.domain.game.dto.GameApiDto;

import java.util.List;

public class GamePageApiDto implements PageDto<GameApiDto> {
    List<GameApiDto> games;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean firstPage;
    private boolean lastPage;

    public List<GameApiDto> getGames() {
        return games;
    }

    @Override
    public void setGames(List<GameApiDto> games) {
        this.games = games;
    }

    @Override
    public int getPageNo() {
        return pageNo;
    }

    @Override
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public long getTotalElements() {
        return totalElements;
    }

    @Override
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public boolean isFirstPage() {
        return firstPage;
    }

    @Override
    public void setFirstPage(boolean firstPage) {
        this.firstPage = firstPage;
    }

    @Override
    public boolean isLastPage() {
        return lastPage;
    }

    @Override
    public void setLastPage(boolean lastPage) {
        this.lastPage = lastPage;
    }
}
