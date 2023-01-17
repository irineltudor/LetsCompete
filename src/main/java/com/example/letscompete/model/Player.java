package com.example.letscompete.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "player")
@ApiModel(value ="Player", description = "Here we have the model for player entity")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int playerId;


    @NotNull(message = "First Name cannot be null")
    @NotEmpty(message = "First Name cannot be empty")
    @Column(name = "first_name")
    @ApiModelProperty(notes = "First Name of the Player", name = "first name",dataType = "String", required = true, example="Christian")
    private String firstName;

    @NotNull(message = "Last Name cannot be null")
    @NotEmpty(message = "Last Name cannot be empty")
    @Column(name = "last_name")
    @ApiModelProperty(notes = "Last Name of the Player", name = "last name",dataType = "String", required = true, example="Andreas")
    private String lastName;

    @NotNull(message = "Date Of Birth cannot be null")
    @NotEmpty(message = "Date Of Birth cannot be empty")
    @Pattern(regexp = "[0-9]{4}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])", message = "Incorrect date format (yyyy-mm-dd ex: 2000-01-20)")
    @Column(name = "dob")
    @ApiModelProperty(notes = "Date of birth of the Player", name = "dob",dataType = "String", required = true, example="2000-01-20(yyyy-MM-dd)")
    private String dob;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Player() {

    }
    public Player(int playerId, String firstName, String lastName, String dob) {
        this.playerId = playerId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dob = dob;
    }

    public Player(String firstName, String lastName, String dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int id) {
        this.playerId = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", firstname='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", dob='" + dob + '\'' +
                ", team=" + team +
                '}';
    }
}
