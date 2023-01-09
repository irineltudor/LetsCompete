package com.example.letscompete.controller;


import com.example.letscompete.dto.GameDTO;
import com.example.letscompete.dto.LocationDTO;
import com.example.letscompete.model.Location;
import com.example.letscompete.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<List<LocationDTO>> retrieveLocations() {return ResponseEntity.ok(locationService.getLocationDTOs());}

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationDTO> retrieveLocationById(@PathVariable int locationId){return ResponseEntity.ok(locationService.getLocationDTOById(locationId));}

    @PostMapping
    public ResponseEntity<?> addNewLocation(@RequestBody @Valid Location location){
        LocationDTO savedLocation = locationService.add(location);

        System.out.printf("Location with id " + savedLocation.getLocationId() + "created \n");

        return ResponseEntity.created(URI.create("/" + savedLocation.getLocationId())).body(savedLocation);
    }

    @PutMapping("/{locationId}")
    public ResponseEntity<?> replaceLocationAddress(@PathVariable int locationId, @RequestParam String address){return ResponseEntity.ok(locationService.changeLocationAddress(locationId,address));}

    @DeleteMapping
    public ResponseEntity<?> removeLocationWithId(@RequestParam int locationId)
    {
        LocationDTO deletedLocation = locationService.deleteLocationWithId(locationId);

        return ResponseEntity.ok(deletedLocation);
    }
}
