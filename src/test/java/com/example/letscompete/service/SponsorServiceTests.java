package com.example.letscompete.service;


import com.example.letscompete.dto.LocationDTO;
import com.example.letscompete.dto.SponsorDTO;
import com.example.letscompete.exception.CannotDeleteEntityException;
import com.example.letscompete.model.Location;
import com.example.letscompete.model.Sponsor;
import com.example.letscompete.model.Tournament;
import com.example.letscompete.repository.SponsorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SponsorServiceTests {

    @InjectMocks
    private SponsorService sponsorService;

    @Mock
    private SponsorRepository sponsorRepository;


    @Test
    @DisplayName("Running delete sponsor with id in happy flow")
    void deleteSponsorWithIdHappyFlow(){
        //arrange - define actions for mocks
        int id = 0;
        Sponsor sponsor = new Sponsor("INTEL");
        SponsorDTO sponsorDTO = new SponsorDTO(sponsor);
        when(sponsorRepository.findById(id)).thenReturn(Optional.of(sponsor));

        //act - calling the actual method
        SponsorDTO result = sponsorService.deleteSponsorWithId(id);

        //assert - see what is happening
        assertEquals(sponsorDTO.getName(),result.getName());
    }


    @Test
    @DisplayName("Running delete sponsor with id in negative flow")
    void deleteSponsorWithIdNegativeFlow(){
        //arrange - define actions for mocks
        int id = 0;
        Sponsor sponsor = new Sponsor("INTEL");
        sponsor.setTournamentList(List.of(new Tournament("tournament test","1v1","2000-11-09","1$")));
        SponsorDTO sponsorDTO = new SponsorDTO(sponsor);
        when(sponsorRepository.findById(id)).thenReturn(Optional.of(sponsor));

        //act - calling the actual method
        try {
            SponsorDTO result = sponsorService.deleteSponsorWithId(id);
        }catch (CannotDeleteEntityException e) {
            //assert - see what is happening
            assertEquals("Cannot delete sponsor with id " + sponsorDTO.getSponsorId() + " because sponsor is assigned to one or more tournaments : " + sponsorDTO.getTournamentNames(),e.getMessage());
        }
    }

    @Test
    @DisplayName("Running get sponsor by id in happy flow")
    void getSponsorByIdHappyFlow(){
        //arrange - define actions for mocks
        int id = 0;
        Sponsor sponsor = new Sponsor("INTEL");
        when(sponsorRepository.findById(id)).thenReturn(Optional.of(sponsor));

        //act - calling the actual method
        Sponsor result = sponsorService.getSponsorById(id);

        //assert - see what is happening
        assertEquals(sponsor.getName(),result.getName());
    }


    @Test
    @DisplayName("Running get sponsor by id in negative flow")
    void getSponsorByIdNegativeFlow(){
        //arrange - define actions for mocks
        int id = 0;
        when(sponsorRepository.findById(id)).thenReturn(Optional.empty());

        //act - calling the actual method
        try {
            Sponsor result = sponsorService.getSponsorById(id);
        }catch (EntityNotFoundException e) {
            //assert - see what is happening
            assertEquals("Sponsor with id " + id + " not found",e.getMessage());
        }
    }

    @Test
    @DisplayName("Running get sponsor dtos in happy flow")
    void getSponsorDTOsHappyFlow(){
        //arrange - define actions for mocks
        Sponsor sponsor = new Sponsor("INTEL");
        SponsorDTO sponsorDTO = new SponsorDTO(sponsor);
        List<SponsorDTO> sponsorDTOList = List.of(sponsorDTO);
        when(sponsorRepository.findAll()).thenReturn(List.of(sponsor));

        //act - calling the actual method
        List<SponsorDTO> result = sponsorService.getSponsorDTOs();

        //assert - see what is happening
        assertEquals(sponsorDTOList.size(),result.size());
    }


    @Test
    @DisplayName("Running get sponsor dtos in negative flow")
    void getSponsorDTOsNegativeFlow(){
        //arrange - define actions for mocks
        when(sponsorRepository.findAll()).thenReturn(List.of());

        //act - calling the actual method
        try {
            List<SponsorDTO> result = sponsorService.getSponsorDTOs();
        }catch (EntityNotFoundException e) {
            //assert - see what is happening
            assertEquals( "There are no sponsors",e.getMessage());
        }
    }
}
