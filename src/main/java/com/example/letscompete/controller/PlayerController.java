package com.example.letscompete.controller;


import com.example.letscompete.dto.PlayerDTO;
import com.example.letscompete.model.Player;
import com.example.letscompete.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }




    @GetMapping
    public ResponseEntity<List<PlayerDTO>> retrievePlayers(){return ResponseEntity.ok().body(playerService.getPlayerDTOs());}

    @GetMapping("/{playerId}")
    public ResponseEntity<PlayerDTO> retrievePlayerById(@PathVariable int playerId) {return ResponseEntity.ok().body(playerService.getPlayerDTOById(playerId));}


    @PostMapping
    public ResponseEntity<?> addNewPlayer(@RequestBody @Valid Player player){

        PlayerDTO savedPlayer = playerService.add(player);

        System.out.printf("Player with id " + savedPlayer.getPlayerId() + "created \n");

        return ResponseEntity.created(URI.create("/" + savedPlayer.getPlayerId())).body(savedPlayer);
    }

    @PutMapping("/{playerId}/team")
    public ResponseEntity<?> replacePlayerTeam(@PathVariable int playerId,@RequestParam int teamId){return ResponseEntity.ok(playerService.changePlayerTeam(playerId,teamId));}

    @DeleteMapping("/{playerId}/team")
    public ResponseEntity<?> removeTeamFromPlayer(@PathVariable int playerId){return ResponseEntity.ok(playerService.deletePlayerTeam(playerId));}

    @GetMapping("/{playerId}/team")
    public ResponseEntity<?> getPlayerTeam(@PathVariable int playerId){return ResponseEntity.ok(playerService.getPlayerTeam(playerId));}

    @DeleteMapping
    public ResponseEntity<PlayerDTO> removePlayerWithId(@RequestParam int playerId) {

        PlayerDTO player = playerService.deletePlayerWithId(playerId);

        return ResponseEntity.ok().body(player);
    }

    @PostMapping("/bulkload")
    public ResponseEntity<String> addBulkLoadPlayer(@RequestParam int n) {return ResponseEntity.ok(playerService.bulkLoadPlayer(n));}

}
