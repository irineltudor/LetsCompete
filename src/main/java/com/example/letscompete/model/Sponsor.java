package com.example.letscompete.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sponsor")
@ApiModel(value ="Sponsor", description = "Here we have the model for sponsor entity")
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(readOnly = true)
    private int sponsorId;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    @ApiModelProperty(notes = "Name of the Sponsor", name = "name",dataType = "String", required = true, example="COCA COLA")
    private String name;

    @ManyToMany(mappedBy = "sponsorList", fetch = FetchType.EAGER)
    @ApiModelProperty(notes = "List of tournaments where the sponsors are funding", name = "tournament list",readOnly = true)
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
