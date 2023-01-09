package com.example.letscompete.dto;

import com.example.letscompete.model.Sponsor;
import com.example.letscompete.model.Tournament;

import java.util.List;
import java.util.stream.Collectors;

public class SponsorDTO {

    private int sponsorId;

    private String name;

    private List<String> tournamentList;


    public SponsorDTO() {
    }

    public SponsorDTO(Sponsor sponsor) {
        this.sponsorId = sponsor.getSponsorId();
        this.name = sponsor.getName();
        this.tournamentList = sponsor.getTournamentList().stream().map(Tournament::getName).collect(Collectors.toList());
    }

    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTournamentNames() {
        return tournamentList;
    }

    public void setTournamentNames(List<String> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @Override
    public String toString() {
        return "SponsorDTO{" +
                "sponsorId=" + sponsorId +
                ", name='" + name + '\'' +
                ", tournamentIds=" + tournamentList +
                '}';
    }
}
