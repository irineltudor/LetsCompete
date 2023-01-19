package com.example.letscompete.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "game")
@ApiModel(value ="Game", description = "Here we have the model for game entity")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(readOnly = true)
    private int gameId;

    @NotNull(message = "Title cannot be null")
    @NotEmpty(message = "Title cannot be empty")
    @Column(name = "title")
    @ApiModelProperty(notes = "Title of the Game", name = "title",dataType = "String", required = true, example="Dota2")
    private String title;

    @NotNull(message = "Genre cannot be null")
    @NotEmpty(message = "Genre cannot be empty")
    @Column(name = "genre")
    @ApiModelProperty(notes = "Genre of the Game", name = "genre",dataType = "String", required = true, example="MOBA")
    private String genre;


    @NotNull(message = "Release date cannot be null")
    @NotEmpty(message = "Release date cannot be empty")
    @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])", message = "Incorrect date format (yyyy-MM-dd ex:2000-01-20)")
    @Column(name = "release_date")
    @ApiModelProperty(notes = "Release date of the Game", name = "release date",dataType = "String", required = true, example="2000-01-20(yyyy-MM-dd)")
    private String releaseDate;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    @ApiModelProperty(notes = "List of tournaments where the game is played", name = "tournament list",readOnly = true)
    private List<Tournament> tournamentList = new ArrayList<>();

    public Game() {
    }

    public Game(String title, String genre, String releaseDate) {
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int id) {
        this.gameId = id;
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

    public List<Tournament> getTournamentList() {
        return tournamentList;
    }

    public void setTournamentList(List<Tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameId=" + gameId +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", tournamentList=" + tournamentList +
                '}';
    }
}
