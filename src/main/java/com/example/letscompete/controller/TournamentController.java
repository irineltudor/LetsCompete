package com.example.letscompete.controller;


import com.example.letscompete.dto.*;
import com.example.letscompete.model.Tournament;
import com.example.letscompete.service.TournamentService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tournaments")
@Api(value="Swagger2TournamentController", description = "REST APIs related to Tournament Controller.")
public class TournamentController {
    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public ResponseEntity<List<TournamentDTO>> retrieveTournaments() {return ResponseEntity.ok(tournamentService.getTournamentDTOs());}

    @PostMapping
    public ResponseEntity<?> addNewTournament(@RequestBody @Valid Tournament tournament){
        TournamentDTO savedTournament = tournamentService.add(tournament);

        System.out.printf("Tournament with id " + savedTournament.getTournamentId() + "created \n");

        return ResponseEntity.created(URI.create("/" + savedTournament.getTournamentId())).body(savedTournament);
    }


    @GetMapping("/{tournamentId}")
    public ResponseEntity<TournamentDTO> retrieveTournamentById(@PathVariable int tournamentId){return ResponseEntity.ok(tournamentService.getTournamentDTOById(tournamentId));}

    @DeleteMapping
    public ResponseEntity<TournamentDTO> deleteTournament(@RequestParam int tournamentId){return ResponseEntity.ok(tournamentService.deleteTournament(tournamentId));}


    @GetMapping("/{tournamentId}/teams")
    public ResponseEntity<List<TeamDTO>> retrieveTournamentTeams(@PathVariable int tournamentId){return ResponseEntity.ok(tournamentService.getTournamentTeams(tournamentId));}

    @PutMapping("/{tournamentId}/teams")
    public ResponseEntity<TeamDTO> addTournamentTeam(@PathVariable int tournamentId,@RequestParam int teamId){return ResponseEntity.ok(tournamentService.addTeam(tournamentId,teamId));}

    @DeleteMapping("/{tournamentId}/teams")
    public ResponseEntity<TeamDTO> removeTournamentTeam(@PathVariable int tournamentId,@RequestParam int teamId){return ResponseEntity.ok(tournamentService.deleteTeamById(tournamentId,teamId));}


    @GetMapping("/{tournamentId}/location")
    public ResponseEntity<LocationDTO> retrieveTournamentLocation(@PathVariable int tournamentId){return ResponseEntity.ok(tournamentService.getTournamentLocation(tournamentId));}

    @PutMapping("/{tournamentId}/location")
    public ResponseEntity<LocationDTO> replaceTournamentLocation(@PathVariable int tournamentId,@RequestParam int locationId){return ResponseEntity.ok(tournamentService.changeTournamentLocation(tournamentId,locationId));}

    @DeleteMapping("/{tournamentId}/location")
    public ResponseEntity<?> removeTournamentLocation(@PathVariable int tournamentId){return ResponseEntity.ok(tournamentService.deleteLocation(tournamentId));}


    @GetMapping("/{tournamentId}/game")
    public ResponseEntity<GameDTO> retrieveTournamentGame(@PathVariable int tournamentId){return ResponseEntity.ok(tournamentService.getTournamentGame(tournamentId));}

    @PutMapping("/{tournamentId}/game")
    public ResponseEntity<GameDTO> replaceTournamentGame(@PathVariable int tournamentId,@RequestParam int gameId){return ResponseEntity.ok(tournamentService.changeTournamentGame(tournamentId,gameId));}

    @DeleteMapping("/{tournamentId}/game")
    public ResponseEntity<GameDTO> removeTournamentGame(@PathVariable int tournamentId){return ResponseEntity.ok(tournamentService.deleteGame(tournamentId));}


    @GetMapping("/{tournamentId}/sponsors")
    public ResponseEntity<List<SponsorDTO>> retrieveTournamentSponsors(@PathVariable int tournamentId){return ResponseEntity.ok(tournamentService.getTournamentSponsors(tournamentId));}

    @PutMapping("/{tournamentId}/sponsors")
    public ResponseEntity<SponsorDTO> addTournamentSponsor(@PathVariable int tournamentId,@RequestParam int sponsorId){return ResponseEntity.ok(tournamentService.addTournamentSponsor(tournamentId,sponsorId));}

    @DeleteMapping("/{tournamentId}/sponsors")
    public ResponseEntity<SponsorDTO> removeTournamentSponsor(@PathVariable int tournamentId, @RequestParam int sponsorId){return ResponseEntity.ok(tournamentService.deleteSponsorById(tournamentId,sponsorId));}


    @GetMapping("/on/today")
    public ResponseEntity<List<TournamentDTO>> retrieveTournamentsForToday(){return ResponseEntity.ok(tournamentService.getTournamentsForToday());}

    @GetMapping("/on/{date}")
    public ResponseEntity<List<TournamentDTO>> retrieveTournamentsForDate(@PathVariable String date){return ResponseEntity.ok(tournamentService.getTournamentsForDate(date));}

}


