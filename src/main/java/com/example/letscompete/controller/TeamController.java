package com.example.letscompete.controller;


import com.example.letscompete.dto.PlayerDTO;
import com.example.letscompete.dto.TeamDTO;
import com.example.letscompete.model.Team;
import com.example.letscompete.service.TeamService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/teams")
@Api(value="Swagger2TeamController", description = "REST APIs related to Team Controller.")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<TeamDTO>> retrieveTeams() {return ResponseEntity.ok(teamService.getTeamDTOs());}


    @GetMapping("/{teamId}")
    public ResponseEntity<TeamDTO> retrieveTeamById(@PathVariable int teamId){return ResponseEntity.ok(teamService.getTeamDTOById(teamId));}

    @GetMapping("/{teamId}/players")
    public ResponseEntity<List<PlayerDTO>> retrieveTeamPLayer(@PathVariable int teamId){return ResponseEntity.ok(teamService.getTeamPlayers(teamId));}

    @PostMapping
    public ResponseEntity<TeamDTO> addNewTeam(@RequestBody @Valid Team team){
        TeamDTO savedTeam = teamService.add(team);

        System.out.printf("Team with id " + savedTeam.getTeamId() + "created \n");

        return ResponseEntity.created(URI.create("/" + savedTeam.getTeamId())).body(savedTeam);
    }

    @DeleteMapping
    public ResponseEntity<TeamDTO> removeTeamById(@RequestParam int teamId){return ResponseEntity.ok(teamService.deleteTeamWithId(teamId));}


}
