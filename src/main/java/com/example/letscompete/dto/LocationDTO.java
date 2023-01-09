package com.example.letscompete.dto;

import com.example.letscompete.model.Location;
import com.example.letscompete.model.Tournament;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LocationDTO {


    private int locationId;

    private String name;

    private String address;

    private List<String> tournamentList = new ArrayList<>();

    public LocationDTO() {
    }

    public LocationDTO(Location location) {
        this.locationId = location.getLocationId();
        this.name = location.getName();
        this.address = location.getAddress();
        if(!location.getTournamentList().isEmpty())
            this.tournamentList = location.getTournamentList().stream().map(Tournament::getName).collect(Collectors.toList());
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
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

    public List<String> getTournamentList() {
        return tournamentList;
    }

    public void setTournamentList(List<String> tournamentList) {
        this.tournamentList = tournamentList;
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
                "locationId=" + locationId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", tournamentList=" + tournamentList +
                '}';
    }
}
