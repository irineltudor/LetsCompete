package com.example.letscompete.controller;

import com.example.letscompete.dto.SponsorDTO;
import com.example.letscompete.dto.SponsorDTO;
import com.example.letscompete.model.Sponsor;
import com.example.letscompete.service.SponsorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/sponsors")
public class SponsorController {

    private final SponsorService sponsorService;

    public SponsorController(SponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    @GetMapping
    public ResponseEntity<List<SponsorDTO>> retrieveSponsors() {return ResponseEntity.ok(sponsorService.getSponsorDTOs());}

    @GetMapping("/{sponsorId}")
    public ResponseEntity<SponsorDTO> retrieveSponsorById(@PathVariable int sponsorId){return ResponseEntity.ok(sponsorService.getSponsorDTOById(sponsorId));}

    @PostMapping
    public ResponseEntity<?> addNewSponsor(@RequestBody @Valid Sponsor sponsor){
        SponsorDTO savedSponsor = sponsorService.add(sponsor);

        System.out.printf("Sponsor with id " + savedSponsor.getSponsorId() + "created \n");

        return ResponseEntity.created(URI.create("/" + savedSponsor.getSponsorId())).body(savedSponsor);
    }

    @DeleteMapping
    public ResponseEntity<?> removeSponsorWithId(@RequestParam int sponsorId)
    {
        SponsorDTO deletedSponsor = sponsorService.deleteSponsorWithId(sponsorId);

        return ResponseEntity.ok(deletedSponsor);
    }
}
