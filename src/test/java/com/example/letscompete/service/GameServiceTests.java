package com.example.letscompete.service;


import com.example.letscompete.dto.GameDTO;
import com.example.letscompete.exception.CannotDeleteEntityException;
import com.example.letscompete.model.Game;
import com.example.letscompete.model.Tournament;
import com.example.letscompete.repository.GameRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTests {

    @InjectMocks
    private GameServiceImpl gameService;

    @Mock
    private GameRepository gameRepository;


    @Test
    @DisplayName("Running get game by title in happy flow")
    void getGameByTitleHappyFlow(){
        //arrange - define actions for mocks
        String title = "Dota2";
        Game game = new Game("Dota2","MOBA","2013-07-09");
        when(gameRepository.findGameByTitle(title)).thenReturn(Optional.of(game));

        //act - calling the actual method
        Game result = gameService.getGameByTitle(title);

        //assert - see what is happening
        assertEquals(game.getTitle(),result.getTitle());
    }

    @Test
    @DisplayName("Running get game by title in negativeFlow")
    void getGameByTitleNegativeFlow(){
        //arrange - define actions for mocks
        String title = "Dota2";
        Game game;
        when(gameRepository.findGameByTitle(title)).thenReturn(Optional.empty());

        //act - calling the actual method
        try {
            Game result = gameService.getGameByTitle(title);
        }catch (EntityNotFoundException e) {
            //assert - see what is happening
            assertEquals("Game with id Dota2 not found",e.getMessage());
        }
    }


    @Test
    @DisplayName("Running get game dto by title in happy flow")
    void getGameDTOByTitleHappyFlow(){
        //arrange - define actions for mocks
        String title = "Dota2";
        Game game = new Game("Dota2","MOBA","2013-07-09");
        GameDTO gameDTO = new GameDTO(game);
        when(gameRepository.findGameByTitle(title)).thenReturn(Optional.of(game));

        //act - calling the actual method
        Game result = gameService.getGameByTitle(title);

        //assert - see what is happening
        assertEquals(gameDTO.getTitle(),result.getTitle());
    }

    @Test
    @DisplayName("Running get game dto by title in negative flow")
    void getGameDTOByTitleNegativeFlow(){
        //arrange - define actions for mocks
        String title = "Dota2";
        when(gameRepository.findGameByTitle(title)).thenReturn(Optional.empty());

        //act - calling the actual method
        try {
            GameDTO result = gameService.getGameDTObyTitle(title);
        }catch (EntityNotFoundException e) {
            //assert - see what is happening
            assertEquals("Game with id Dota2 not found",e.getMessage());
        }
    }

    @Test
    @DisplayName("Running delete game with id in happy flow")
    void deleteGameWithIdHappyFlow(){
        //arrange - define actions for mocks
        int id = 0;
        Game game = new Game("Dota2","MOBA","2013-07-09");
        GameDTO gameDTO = new GameDTO(game);
        when(gameRepository.findById(id)).thenReturn(Optional.of(game));

        //act - calling the actual method
        GameDTO result = gameService.deleteGameWithId(id);

        //assert - see what is happening
        assertEquals(gameDTO.getTitle(),result.getTitle());
    }

    @Test
    @DisplayName("Running delete game with id in negative flow")
    void deleteGameWithIdNegativeFlow(){
        //arrange - define actions for mocks
        int id = 0;
        Game game = new Game("Dota2","MOBA","2013-07-09");
        game.setTournamentList(List.of(new Tournament("tournament test","1v1","2000-11-09","1$")));
        GameDTO gameDTO = new GameDTO(game);
        when(gameRepository.findById(id)).thenReturn(Optional.of(game));

        //act - calling the actual method
        try {
            GameDTO result = gameService.deleteGameWithId(id);
        }catch (CannotDeleteEntityException e) {
            //assert - see what is happening
            assertEquals("Cannot delete " + gameDTO.getTitle() + " because there are tournaments where this game is played : "  + gameDTO.getTournamentList(),e.getMessage());
        }
    }


    @Test
    @DisplayName("Running get game dtos by genre in happy flow")
    void getGameDTOsByGenreHappyFlow(){
        //arrange - define actions for mocks
        String genre = "MOBA";
        Game game = new Game("Dota2","MOBA","2013-07-09");
        List<GameDTO> gameDTOList = List.of(new GameDTO(game));

        when(gameRepository.findAllByGenre(genre)).thenReturn(Optional.of(List.of(game)));

        //act - calling the actual method
        List<GameDTO> result = gameService.getGameDTOsByGenre(genre);

        //assert - see what is happening

        assertEquals(gameDTOList.size(),result.size());
    }


}
