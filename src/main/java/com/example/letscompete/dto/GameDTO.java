package com.example.letscompete.dto;

import com.example.letscompete.model.Game;
import com.example.letscompete.model.Tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameDTO {

    private int gameId;

    private String title;

    private String genre;

    private String releaseDate;

    private List<String> tournamentList = new ArrayList<>();

    public GameDTO() {
    }

    public GameDTO(Game game) {
        this.gameId = game.getGameId();
        this.title = game.getTitle();
        this.genre = game.getGenre();
        this.releaseDate = game.getReleaseDate();
        if(!game.getTournamentList().isEmpty())
            this.tournamentList = game.getTournamentList().stream().map(Tournament::getName).collect(Collectors.toList());
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getTournamentList() {
        return tournamentList;
    }

    public void setTournamentList(List<String> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @Override
    public String toString() {
        return "GameDTO{" +
                "gameId=" + gameId +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", tournamentList=" + tournamentList +
                '}';
    }
}
