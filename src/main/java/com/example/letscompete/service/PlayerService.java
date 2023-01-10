package com.example.letscompete.service;

import com.example.letscompete.dto.PlayerDTO;
import com.example.letscompete.dto.TeamDTO;
import com.example.letscompete.exception.CannotDeleteEntityException;
import com.example.letscompete.model.Player;
import com.example.letscompete.model.Team;
import com.example.letscompete.repository.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamService teamService;

    public PlayerService(PlayerRepository playerRepository, TeamService teamService) {
        this.playerRepository = playerRepository;
        this.teamService = teamService;
    }


    public List<PlayerDTO> getPlayerDTOs() {
        List<Player> playerList = playerRepository.findAll();
        if(playerList.isEmpty())
            throw new EntityNotFoundException("There are no players");
        return playerList.stream().map(PlayerDTO::new).collect(Collectors.toList());
    }

    public PlayerDTO getPlayerDTOById(int playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player with id " + playerId + " not found"));
        return new PlayerDTO(player);
    }

    public Player getPlayerById(int playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player with id " + playerId + " not found"));
    }

    public PlayerDTO add(Player player) {
        return new PlayerDTO(playerRepository.save(player));
    }

    public PlayerDTO changePlayerTeam(int playerId, int teamId) {
        Player player = getPlayerById(playerId);
        Team newTeam = teamService.getTeamById(teamId);

        player.setTeam(newTeam);
        playerRepository.save(player);

        return new PlayerDTO(player);
    }

    public PlayerDTO deletePlayerTeam(int playerId) {
        Player player = getPlayerById(playerId);

        player.setTeam(null);
        playerRepository.save(player);

        return new PlayerDTO(player);
    }

    public PlayerDTO deletePlayerWithId(int playerId) {
        PlayerDTO player = getPlayerDTOById(playerId);

        if (!Objects.equals(player.getTeam(), ""))
            throw new CannotDeleteEntityException("Cannot delete player with id " + player.getPlayerId() + " because player is part of a team, first delete player's team");
        else {
                playerRepository.deleteById(playerId);
        }
        return player;
    }


    /*
     * ACID
     * A - atomic
     * C - consistency
     * I - isolated
     * D - durable
     */

    @Transactional
    // this will roll back everything after exception because we don't need addition if the exception is thrown
    public String bulkLoadPlayer(int n) {
        for (int i = 10; i < n; i++) {
            if (playerRepository.existsByFirstName("firstname" + i)) {
                throw new RuntimeException("Player with firstname : firstname" + i + " already exist");
            }
            Player player = new Player("firstname" + i, "lastname" + i, "2000-09-11");
            playerRepository.save(player);
        }

        return "OK";
    }

    public TeamDTO getPlayerTeam(int playerId) {
        Player player = getPlayerById(playerId);
        TeamDTO teamDTO;
        if(player.getTeam() == null)
        {
            throw new EntityNotFoundException("Player has no team assigned");
        }
        else {
            teamDTO = new TeamDTO(player.getTeam());
        }

        return teamDTO;
    }
}
