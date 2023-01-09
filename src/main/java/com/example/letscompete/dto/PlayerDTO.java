package com.example.letscompete.dto;

import com.example.letscompete.model.Player;

public class PlayerDTO {

    private int playerId;
    private String lastName;

    private String firstName;

    private String dob;
    private String team = "";

    public PlayerDTO() {
    }

    public PlayerDTO(Player player)
    {
        this.playerId = player.getPlayerId();
        this.lastName = player.getLastName();
        this.firstName = player.getFirstName();
        this.dob = player.getDob();
        if(player.getTeam() != null)
            this.team = player.getTeam().getName();
    }

    public PlayerDTO(int playerId, String lastName, String firstName, String dob, String team) {
        this.playerId = playerId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dob = dob;
        this.team = team;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
                "playerId=" + playerId +
                ", lastname='" + lastName + '\'' +
                ", dob='" + dob + '\'' +
                ", team='" + team + '\'' +
                '}';
    }
}
