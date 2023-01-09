package com.example.letscompete.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tournament")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tournamentId;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Type cannot be null")
    @NotEmpty(message = "type cannot be empty")
    @Pattern(regexp = "1v1|5v5", message = "Incorrect type format ( 1v1 or 5v5 )")
    @Column(name = "type")
    private String type;

    @NotNull(message = "Release date cannot be null")
    @NotEmpty(message = "Release date cannot be empty")
    @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])", message = "Incorrect date format (yyyy-mm-dd ex:2000-01-20)")
    @Column(name = "date")
    private String date;

    @NotNull(message = "Prize date cannot be null")
    @NotEmpty(message = "Prize date cannot be empty")
    @Column(name = "prize")
    private String prize;


    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;


    @ManyToMany
    @JoinTable(name = "tournament_sponsor", joinColumns = @JoinColumn(name = "tournament_id"), inverseJoinColumns = @JoinColumn(name = "sponsor_id"))
    private List<Sponsor> sponsorList = new ArrayList<>();


    @ManyToMany
    @JoinTable(name = "tournament_team", joinColumns = @JoinColumn(name = "tournament_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<Team> teamList = new ArrayList<>();


    public Tournament() {

    }

    public Tournament(String name, String type, String date,String prize) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.prize = prize;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int id) {
        this.tournamentId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Sponsor> getSponsorList() {
        return sponsorList;
    }

    public void setSponsorList(List<Sponsor> sponsorList) {
        this.sponsorList = sponsorList;
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "tournamentId=" + tournamentId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", prize='" + prize + '\'' +
                ", game=" + game +
                ", location=" + location +
                ", sponsorList=" + sponsorList +
                ", teamList=" + teamList +
                '}';
    }
}