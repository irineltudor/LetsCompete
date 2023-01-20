package com.example.letscompete.service;


import com.example.letscompete.dto.LocationDTO;
import com.example.letscompete.dto.PlayerDTO;
import com.example.letscompete.dto.TeamDTO;
import com.example.letscompete.exception.CannotDeleteEntityException;
import com.example.letscompete.model.Location;
import com.example.letscompete.model.Player;
import com.example.letscompete.model.Team;
import com.example.letscompete.model.Tournament;
import com.example.letscompete.repository.TeamRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTests {
    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;



    @Test
    @DisplayName("Running delete team with id in happy flow")
    void deleteTeamWithIdHappyFlow(){
        //arrange - define actions for mocks
        int id = 0;
        Team team = new Team("Team Liquid");
        TeamDTO teamDTO = new TeamDTO(team);
        when(teamRepository.findById(id)).thenReturn(Optional.of(team));

        //act - calling the actual method
        TeamDTO result = teamService.deleteTeamWithId(id);

        //assert - see what is happening
        assertEquals(teamDTO.getName(),result.getName());
    }


    @Test
    @DisplayName("Running delete team with id in negative flow player list not empty")
    void deleteTeamWithIdNegativeFlowPlayerList(){
        //arrange - define actions for mocks
        int id = 0;
        Team team = new Team("Team Liquid");
        team.setPlayerList(List.of(new Player("Ivan","Ivanov","2000-09-11")));
        TeamDTO teamDTO = new TeamDTO(team);
        when(teamRepository.findById(id)).thenReturn(Optional.of(team));
        //act - calling the actual method
        try {
            TeamDTO result = teamService.deleteTeamWithId(id);
        }catch (CannotDeleteEntityException e) {
            //assert - see what is happening
            assertEquals("Cannot delete team with id " + id + " because the team still have some players assigned : " + teamDTO.getPlayerList(),e.getMessage());
        }

    }

    @Test
    @DisplayName("Running delete team with id in negative flow tournament list not empty")
    void deleteTeamWithIdNegativeFlowTournamentList() {
        //arrange - define actions for mocks
        int id = 0;
        Team team = new Team("Team Liquid");
        team.setTournamentList(List.of(new Tournament("Dota 2 – The International 2023","5v5","2023-10-02","2.000.000$")));
        TeamDTO teamDTO = new TeamDTO(team);
        when(teamRepository.findById(id)).thenReturn(Optional.of(team));
        //act - calling the actual method
        try {
            TeamDTO result = teamService.deleteTeamWithId(id);
        } catch (CannotDeleteEntityException e) {
            //assert - see what is happening
            assertEquals("Cannot delete team with id " + id + " because the team is playing in tournaments : " + teamDTO.getTournamentList(), e.getMessage());
        }
    }


    @Test
    @DisplayName("Running get team players in happy flow")
    void getTeamPlayersHappyFlow(){
        //arrange - define actions for mocks
        int id = 0;
        Team team = new Team("Team Liquid");
        team.setPlayerList(List.of(new Player("Ivan","Ivanov","2000-09-11")));
        TeamDTO teamDTO = new TeamDTO(team);
        when(teamRepository.findById(id)).thenReturn(Optional.of(team));

        //act - calling the actual method
        List<PlayerDTO> result = teamService.getTeamPlayers(id);

        //assert - see what is happening
        assertEquals(teamDTO.getPlayerList().size(),result.size());
    }


    @Test
    @DisplayName("Running get team players in negative flow")
    void getTeamPlayersNegativeFlow(){
        //arrange - define actions for mocks
        int id = 0;
        Team team = new Team("Team Liquid");
        TeamDTO teamDTO = new TeamDTO(team);
        when(teamRepository.findById(id)).thenReturn(Optional.of(team));
        //act - calling the actual method
        try {
            List<PlayerDTO> result = teamService.getTeamPlayers(id);
        }catch (EntityNotFoundException e) {
            //assert - see what is happening
            assertEquals("Team with id " + id + " has no players assigned",e.getMessage());
        }

    }


    @Test
    @DisplayName("Running get team by id in happy flow")
    void getTeamByIdHappyFlow(){
        //arrange - define actions for mocks
        int id = 0;
        Team team = new Team("Team Liquid");
        when(teamRepository.findById(id)).thenReturn(Optional.of(team));

        //act - calling the actual method
        Team result = teamService.getTeamById(id);

        //assert - see what is happening
        assertEquals(team.getName(),result.getName());
    }


    @Test
    @DisplayName("Running get team by id in negative flow")
    void getTeamByIdNegativeFlow(){
        //arrange - define actions for mocks
        int id = 0;
        when(teamRepository.findById(id)).thenReturn(Optional.empty());
        //act - calling the actual method
        try {
            Team result = teamService.getTeamById(id);
        }catch (EntityNotFoundException e) {
            //assert - see what is happening
            assertEquals("Team with id " + id + " not found",e.getMessage());
        }

    }

    @Test
    @DisplayName("Running get team dtos in happy flow")
    void getTeamDTOsHappyFlow(){
        //arrange - define actions for mocks
        int id = 0;
        Team team = new Team("Team Liquid");
        List<TeamDTO> teamDTOList = List.of(new TeamDTO(team));
        when(teamRepository.findAll()).thenReturn(List.of(team));

        //act - calling the actual method
        List<TeamDTO> result = teamService.getTeamDTOs();

        //assert - see what is happening
        assertEquals(teamDTOList.size(),result.size());
    }


    @Test
    @DisplayName("Running get team dtos in negative flow")
    void getTeamDTOsNegativeFlow(){
        //arrange - define actions for mocks
        int id = 0;
        when(teamRepository.findById(id)).thenReturn(Optional.empty());
        //act - calling the actual method
        try {
            Team result = teamService.getTeamById(id);
        }catch (EntityNotFoundException e) {
            //assert - see what is happening
            assertEquals("Team with id " + id + " not found",e.getMessage());
        }

    }

}
