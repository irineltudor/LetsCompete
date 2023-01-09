package com.example.letscompete.service;

import com.example.letscompete.dto.GameDTO;
import com.example.letscompete.dto.TournamentDTO;
import com.example.letscompete.exception.CannotDeleteEntityException;
import com.example.letscompete.model.Game;
import com.example.letscompete.model.Tournament;
import com.example.letscompete.repository.GameRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameDTO> getGameDTOs() {
        List<Game> gameList = gameRepository.findAll();
        if(gameList.isEmpty())
            throw new EntityNotFoundException("There are no games");
        return gameList.stream().map(GameDTO::new).collect(Collectors.toList());
    }

    public GameDTO getGameDTOById(int gameId) {
        return new GameDTO(gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found")));
    }

    protected Game getGameById(int gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new EntityNotFoundException("Game with id " + gameId + " not found"));
    }

    public Game getGameByTitle(String title) {
        return gameRepository.findGameByTitle(title);
    }

    public GameDTO add(Game game) {
        return new GameDTO(gameRepository.save(game));
    }

    public GameDTO deleteGameWithId(int gameId) {

        GameDTO deletedGame = getGameDTOById(gameId);

        gameRepository.deleteById(gameId);


        return deletedGame;
    }

    public GameDTO deleteGameWithTitle(String title) {

        Game deletedGame = getGameByTitle(title);

        if (!deletedGame.getTournamentList().isEmpty())
            throw new CannotDeleteEntityException("Cannot delete " + deletedGame.getTitle() + " because there are tournaments with this game");
        else {

            gameRepository.deleteById(deletedGame.getGameId());

        }
        return new GameDTO(deletedGame);
    }

    public GameDTO update(Game game) {
        return new GameDTO(gameRepository.save(game));
    }
}
