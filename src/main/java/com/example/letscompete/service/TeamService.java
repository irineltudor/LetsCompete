package com.example.letscompete.service;

import com.example.letscompete.dto.PlayerDTO;
import com.example.letscompete.dto.TeamDTO;
import com.example.letscompete.exception.CannotDeleteEntityException;
import com.example.letscompete.model.Team;
import com.example.letscompete.repository.TeamRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public TeamDTO getTeamDTOById(int teamId) {
        return new TeamDTO(teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with id " + teamId + " not found")));
    }

    public List<TeamDTO> getTeamDTOs() {
        List<Team> teamList = teamRepository.findAll();
        if(teamList.isEmpty())
            throw new EntityNotFoundException("There are no teams");

        return teamList.stream().map(TeamDTO::new).collect(Collectors.toList());
    }

    protected Team getTeamById(int teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with id " + teamId + " not found"));
    }

    public TeamDTO add(Team team) {
        return new TeamDTO(teamRepository.save(team));
    }

    public TeamDTO update(Team team) {
        return new TeamDTO(teamRepository.save(team));
    }


    public TeamDTO deleteTeamWithId(int teamId) {
        Team team = teamRepository.findById(teamId).orElseThrow(() -> new EntityNotFoundException("Team with id " + teamId + " not found"));
        TeamDTO teamDTO = new TeamDTO(team);


        if (!teamDTO.getPlayerList().isEmpty()) {
            throw new CannotDeleteEntityException("Cannot delete team with id " + teamId + " because the team still have some players assigned : " + teamDTO.getPlayerList());
        } else {
            if(!teamDTO.getTournamentList().isEmpty())
                throw new CannotDeleteEntityException("Cannot delete team with id " + teamId + " because the team is playing in tournaments : " + teamDTO.getTournamentList());
            else {
                teamRepository.deleteById(teamId);
            }
        }

        return teamDTO;
    }

    public List<PlayerDTO> getTeamPlayers(int teamId)
    {   Team team = getTeamById(teamId);
        List<PlayerDTO> playerDTOList;

        if(team.getPlayerList().isEmpty())
        {throw new EntityNotFoundException("Team with id " + teamId + " has no players assigned");
        }
        else {
            playerDTOList = team.getPlayerList().stream().map(PlayerDTO::new).collect(Collectors.toList());
        }

        return playerDTOList;
    }
}
