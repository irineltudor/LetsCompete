package com.example.letscompete.dto;

import com.example.letscompete.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TournamentDTO {

    private int tournamentId;

    private String name;

    private String type;

    private String date;

    private String prize;

    private String game = "";

    private String location = "";

    private List<String> sponsorList = new ArrayList<>();

    private List<String> teamList = new ArrayList<>();

    public TournamentDTO() {
    }
    public TournamentDTO(Tournament tournament) {
        this.tournamentId = tournament.getTournamentId();
        this.name = tournament.getName();
        this.type = tournament.getType();
        this.date = tournament.getDate();
        this.prize = tournament.getPrize();
        if(tournament.getGame() != null)
            this.game = tournament.getGame().getTitle();
        if(tournament.getLocation() != null)
            this.location = tournament.getLocation().getName();
        if(!tournament.getSponsorList().isEmpty())
            this.sponsorList = tournament.getSponsorList().stream().map(Sponsor::getName).collect(Collectors.toList());
        if(!tournament.getTeamList().isEmpty())
            this.teamList = tournament.getTeamList().stream().map(Team::getName).collect(Collectors.toList());
    }



    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
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

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getSponsorList() {
        return sponsorList;
    }

    public void setSponsorList(List<String> sponsorList) {
        this.sponsorList = sponsorList;
    }

    public List<String> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<String> teamList) {
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
        return "TournamentDTO{" +
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
