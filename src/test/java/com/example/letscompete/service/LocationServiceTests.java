package com.example.letscompete.service;


import com.example.letscompete.dto.GameDTO;
import com.example.letscompete.dto.LocationDTO;
import com.example.letscompete.exception.CannotDeleteEntityException;
import com.example.letscompete.model.Game;
import com.example.letscompete.model.Location;
import com.example.letscompete.model.Tournament;
import com.example.letscompete.repository.LocationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTests {

    @InjectMocks
    private LocationService locationService;

    @Mock
    private LocationRepository locationRepository;

    @Test
    @DisplayName("Running delete location with id in happy flow")
    void deleteLocationWithIdHappyFlow(){
        //arrange - define actions for mocks
        int id = 0;
        Location location = new Location("France","Paris");
        LocationDTO locationDTO = new LocationDTO(location);
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        //act - calling the actual method
        LocationDTO result = locationService.deleteLocationWithId(id);

        //assert - see what is happening
        assertEquals(locationDTO.getName(),result.getName());
    }


    @Test
    @DisplayName("Running delete location with id in negative flow")
    void deleteLocationWithIdNegativeFlow(){
        //arrange - define actions for mocks
        int id = 0;
        Location location = new Location("France","Paris");
        location.setTournamentList(List.of(new Tournament("tournament test","1v1","2000-11-09","1$")));
        LocationDTO locationDTO = new LocationDTO(location);
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        //act - calling the actual method
        try {
            LocationDTO result = locationService.deleteLocationWithId(id);
        }catch (CannotDeleteEntityException e) {
            //assert - see what is happening
            assertEquals("Cannot delete location with id " + locationDTO.getLocationId() + "because there are tournaments in this location : " + locationDTO.getTournamentList(),e.getMessage());
        }
    }


    @Test
    @DisplayName("Running change location address in happy flow")
    void changeLocationAddressHappyFlow(){
        //arrange - define actions for mocks
        int id = 0;
        String address = "Nisa";
        Location location = new Location("France","Paris");
        LocationDTO locationDTO = new LocationDTO(location);
        locationDTO.setAddress(address);
        when(locationRepository.findById(id)).thenReturn(Optional.of(location));

        //act - calling the actual method
        LocationDTO result = locationService.changeLocationAddress(id,address);

        //assert - see what is happening
        assertEquals(locationDTO.getName(),result.getName());
        assertEquals(locationDTO.getAddress(),result.getAddress());
    }

}
