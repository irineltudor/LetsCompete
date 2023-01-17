package com.example.letscompete.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "location")
@ApiModel(value ="Location", description = "Here we have the model for location entity")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int locationId;


    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Column(name = "name")
    @ApiModelProperty(notes = "Name of the Location", name = "name",dataType = "String", required = true, example="France")
    private String name;


    @NotNull(message = "Address cannot be null")
    @NotEmpty(message = "Address cannot be empty")
    @Column(name = "address")
    @ApiModelProperty(notes = "Address of the Location", name = "address",dataType = "String", required = true, example="Paris")
    private String address;

    @OneToMany(mappedBy = "location", fetch = FetchType.EAGER)
    private List<Tournament> tournamentList = new ArrayList<>();

    public Location() {
    }

    public Location(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int id) {
        this.locationId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Tournament> getTournamentList() {
        return tournamentList;
    }

    public void setTournamentList(List<Tournament> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tournamentList=" + tournamentList +
                '}';
    }
}
