package com.example.letscompete.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
@ApiModel(value ="Team", description = "Here we have the model for team entity")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamId;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    @ApiModelProperty(notes = "Name of the Team", name = "name",dataType = "String", required = true, example="Team Liquid")
    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Player> playerList = new ArrayList<>();

    @ManyToMany(mappedBy = "teamList", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Tournament> tournamentList = new ArrayList<>();


    public Team() {

    }

    public Team(String name) {
        this.name = name;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int id) {
        this.teamId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public List<Tournament> getTournamentList() {
        return tournamentList;
    }

    public void setTournamentList(List<Tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", name='" + name + '\'' +
                ", playerList=" + playerList +
                ", tournamentList=" + tournamentList +
                '}';
    }
}
