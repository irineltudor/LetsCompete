package com.example.letscompete.controller;

import com.example.letscompete.dto.GameDTO;
import com.example.letscompete.model.Game;
import com.example.letscompete.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public ResponseEntity<List<GameDTO>> retrieveGames()
    {
        return ResponseEntity.ok(gameService.getGameDTOs());
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameDTO> retrieveGameById(@PathVariable int gameId){return ResponseEntity.ok(gameService.getGameDTOById(gameId));}


    @PostMapping
    public ResponseEntity<?> addNewGame(@RequestBody @Valid Game game){
        GameDTO savedGame = gameService.add(game);

        System.out.printf("Game with id " + savedGame.getGameId() + "created \n");

        return ResponseEntity.created(URI.create("/" + savedGame.getGameId())).body(savedGame);
    }


    @DeleteMapping
    public ResponseEntity<?> removeGameWithTitle(@RequestParam String title)
    {
        GameDTO deletedGame = gameService.deleteGameWithTitle(title);

        return ResponseEntity.ok(deletedGame);
    }

}
