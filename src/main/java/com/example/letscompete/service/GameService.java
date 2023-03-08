package com.example.letscompete.service;

import com.example.letscompete.dto.GameDTO;
import com.example.letscompete.model.Game;

import java.util.List;

public interface GameService {

    List<GameDTO> getGameDTOs();
    GameDTO getGameDTOById(int gameId);
    GameDTO getGameDTObyTitle(String title);
    Game getGameById(int gameId);
    Game getGameByTitle(String title);
    GameDTO add(Game game);
    GameDTO deleteGameWithId(int gameId);
    void update(Game game);
    List<GameDTO> getGameDTOsByGenre(String genre);
}
