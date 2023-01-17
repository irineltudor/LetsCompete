package com.example.letscompete.model;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gameId;

    @NotNull(message = "Title cannot be null")
    @NotEmpty(message = "Title cannot be empty")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Genre cannot be null")
    @NotEmpty(message = "Genre cannot be empty")
    @Column(name = "genre")
    private String genre;


    @NotNull(message = "Release date cannot be null")
    @NotEmpty(message = "Release date cannot be empty")
    @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])", message = "Incorrect date format (yyyy-MM-dd ex:2000-09-11)")
    @Column(name = "release_date")
    private String releaseDate;

    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
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
