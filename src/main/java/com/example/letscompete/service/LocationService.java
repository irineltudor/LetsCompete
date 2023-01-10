package com.example.letscompete.service;

import com.example.letscompete.dto.LocationDTO;
import com.example.letscompete.exception.CannotDeleteEntityException;
import com.example.letscompete.model.Location;
import com.example.letscompete.repository.LocationRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<LocationDTO> getLocationDTOs(){
        List<Location> locationList = locationRepository.findAll();
        if(locationList.isEmpty())
            throw new EntityNotFoundException("There are no locations");
        return locationList.stream().map(LocationDTO::new).collect(Collectors.toList());}

    public LocationDTO getLocationDTOById(int locationId){return new LocationDTO(locationRepository.findById(locationId).orElseThrow(()->new EntityNotFoundException("Location with id " + locationId + " not found")));}

    protected Location getLocationById(int locationId){return locationRepository.findById(locationId).orElseThrow(()->new EntityNotFoundException("Location with id " + locationId + " not found"));}

    public LocationDTO add(Location location) {
        return new LocationDTO(locationRepository.save(location));
    }

    public LocationDTO changeLocationAddress(int locationId, String address){
        Location location = getLocationById(locationId);

        location.setAddress(address);
        locationRepository.save(location);

        return new LocationDTO(location);
    }

    public LocationDTO deleteLocationWithId(int locationId){
        LocationDTO location = getLocationDTOById(locationId);

        if(!location.getTournamentList().isEmpty())
            throw new CannotDeleteEntityException("Cannot delete location with id " + location.getLocationId() + "because there are tournaments in this location : " + location.getTournamentList());
        else {
            if (locationRepository.existsById(locationId)) {
                locationRepository.deleteById(locationId);
            }
        }

        return location;
    }

    public LocationDTO update(Location location) { return new LocationDTO(locationRepository.save(location));}
}
