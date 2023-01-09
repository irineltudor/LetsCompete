package com.example.letscompete.dto;


import com.example.letscompete.model.Team;
import com.example.letscompete.model.Tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamDTO {


    private int teamId;

    private String name;

    private List<String> playerList = new ArrayList<>();

    private List<String> tournamentList = new ArrayList<>();

    public TeamDTO() {
    }

    public TeamDTO(Team team) {
        this.teamId = team.getTeamId();
        this.name = team.getName();
        if(!team.getPlayerList().isEmpty())
            this.playerList= team.getPlayerList().stream().map(player -> (player.getFirstName() + " " + player.getLastName())).collect(Collectors.toList());
        if(!team.getTournamentList().isEmpty())
            this.tournamentList = team.getTournamentList().stream().map(Tournament::getName).collect(Collectors.toList());
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<String> playerList) {
        this.playerList = playerList;
    }

    public List<String> getTournamentList() {
        return tournamentList;
    }

    public void setTournamentList(List<String> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @Override
    public String toString() {
        return "TeamDTO{" +
                "teamId=" + teamId +
                ", name='" + name + '\'' +
                ", playerList=" + playerList +
                ", tournamentList=" + tournamentList +
                '}';
    }
}
