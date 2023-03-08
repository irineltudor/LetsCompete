package com.example.letscompete.service;

import com.example.letscompete.config.Log;
import com.example.letscompete.dto.GameDTO;
import com.example.letscompete.exception.CannotDeleteEntityException;
import com.example.letscompete.model.Game;
import com.example.letscompete.repository.GameRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    @Log
    public List<GameDTO> getGameDTOs() {
        List<Game> gameList = gameRepository.findAll();
        if(gameList.isEmpty())
            throw new EntityNotFoundException("There are no games");
        return gameList.stream().map(GameDTO::new).collect(Collectors.toList());
    }

    @Override
    public GameDTO getGameDTOById(int gameId) {
        return new GameDTO(gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found")));
    }

    @Override
    public GameDTO getGameDTObyTitle(String title){
        return new GameDTO(gameRepository.findGameByTitle(title).orElseThrow(() -> new EntityNotFoundException("Game with id " + title + " not found")));
    }

    @Override
    public Game getGameById(int gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found"));
    }

    @Override
    public Game getGameByTitle(String title) {
        return gameRepository.findGameByTitle(title).orElseThrow(() -> new EntityNotFoundException("Game with id " + title + " not found"));
    }

    @Override
    public GameDTO add(Game game) {
        return new GameDTO(gameRepository.save(game));
    }

    @Override
    public GameDTO deleteGameWithId(int gameId) {

        GameDTO deletedGame = getGameDTOById(gameId);
        if (!deletedGame.getTournamentList().isEmpty())
            throw new CannotDeleteEntityException("Cannot delete " + deletedGame.getTitle() + " because there are tournaments where this game is played : "  + deletedGame.getTournamentList());
        else {
            gameRepository.deleteById(gameId);
        }

        return deletedGame;
    }

    @Override
    public void update(Game game) {
        gameRepository.save(game);
    }

    @Override
    public List<GameDTO> getGameDTOsByGenre(String genre) {
        List<Game> gameList = gameRepository.findAllByGenre(genre).orElseThrow(() -> new EntityNotFoundException("There are no games with genre : " + genre));
        return gameList.stream().map(GameDTO::new).collect(Collectors.toList());
    }
}
