package com.example.letscompete.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sponsor")
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sponsorId;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "sponsorList")
    private List<Tournament> tournamentList = new ArrayList<>();

    public Sponsor() {

    }

    public Sponsor(String name) {
        this.name = name;
    }



    public int getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(int id) {
        this.sponsorId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tournament> getTournamentList() {
        return tournamentList;
    }

    public void setTournamentList(List<Tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "id=" + sponsorId +
                ", name='" + name + '\'' +
                '}';
    }
}
