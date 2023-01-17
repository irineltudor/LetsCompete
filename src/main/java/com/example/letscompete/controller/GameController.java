package com.example.letscompete.controller;

import com.example.letscompete.dto.GameDTO;
import com.example.letscompete.model.Game;
import com.example.letscompete.service.GameService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/games")
@Api(value="Swagger2GameController", description = "REST APIs related to Game Controller.")
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

    @GetMapping("/{title}")
    public ResponseEntity<GameDTO> retrieveGameByTitle(@PathVariable String title){return ResponseEntity.ok(gameService.getGameDTObyTitle(title));}


    @PostMapping
    public ResponseEntity<?> addNewGame(@RequestBody @Valid Game game){
        GameDTO savedGame = gameService.add(game);

        System.out.printf("Game with id " + savedGame.getGameId() + "created \n");

        return ResponseEntity.created(URI.create("/" + savedGame.getGameId())).body(savedGame);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<GameDTO>> retrieveGamesByGenre(@PathVariable String genre){return ResponseEntity.ok(gameService.getGameDTOsByGenre(genre));}

    @DeleteMapping
    public ResponseEntity<?> removeGameById(@RequestParam int gameId)
    {
        GameDTO deletedGame = gameService.deleteGameWithId(gameId);

        return ResponseEntity.ok(deletedGame);
    }

}
