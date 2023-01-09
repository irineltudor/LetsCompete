package com.example.letscompete.service;


import com.example.letscompete.dto.PlayerDTO;
import com.example.letscompete.dto.TeamDTO;
import com.example.letscompete.exception.CannotDeleteEntityException;
import com.example.letscompete.model.Player;
import com.example.letscompete.model.Team;
import com.example.letscompete.repository.PlayerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTests {
    /**
     * @InjectMocks - principle class
     * @Mocks - for rest of classes in principle
     */

    @InjectMocks
    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamService teamService;



    @Test
    @DisplayName("Running get player dtos in happy flow")
    void getPlayerDTOsHappyFlow(){
        //arrange - define actions for mocks
        Player player = new Player("firstname4","lastname4","2000-09-11");
        List<PlayerDTO> playerDTOList = List.of(new PlayerDTO(player));
        when(playerRepository.findAll()).thenReturn(List.of(player));

        //act - calling the actual method
        List<PlayerDTO> result = playerService.getPlayerDTOs();

        //assert - see what is happening
        assertEquals(playerDTOList.size(),result.size());
    }

    @Test
    @DisplayName("Running get player dtos in negative flow")
    void getPlayerDTOsNegativeFlow(){
        //arrange - define actions for mocks
        when(playerRepository.findAll()).thenReturn(List.of());

        //act - calling the actual method
        try {
            List<PlayerDTO> result = playerService.getPlayerDTOs();
        }catch (EntityNotFoundException e) {
            //assert - see what is happening
            assertEquals("There are no players",e.getMessage());
        }
    }


    @Test
    @DisplayName("Running add player in happy flow")
    void addHappyFlow(){
        //arrange - define actions for mocks
        Player player = new Player("firstname4","lastname4","2000-09-11");
        when(playerRepository.save(player)).thenReturn(player);

        //act - calling the actual method
        PlayerDTO result = playerService.add(player);

        //assert - see what is happening
        assertEquals(player.getFirstName(), result.getFirstName());
    }

    @Test
    @DisplayName("Running change player team in happy flow")
    void changePlayerTeamHappyFlow(){
        //arrange - define actions for mocks
        int playerId = 1;
        int teamId = 1;
        Player player = new Player("firstname1","lastname1","2000-09-11");
        Team team = new Team("Team1");
        player.setTeam(team);
        team.setPlayerList(List.of(player));
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(teamService.getTeamById(teamId)).thenReturn(team);

        //act - calling the actual method
        PlayerDTO result = playerService.changePlayerTeam(playerId,teamId);

        //assert - see what is happening
        assertEquals(player.getTeam().getName(),result.getTeam());
    }



    @Test
    @DisplayName("Running delete player with id in happy flow")
    void deletePlayerWithIdHappyFlow()
    {
        //arrange
        int playerId = 1;

        Player player = new Player("firstname1","lastname1","2000-09-11");
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        //act
        PlayerDTO result = playerService.deletePlayerWithId(playerId);

        //assert

        assertEquals(player.getFirstName(),result.getFirstName());

    }

    @Test
    @DisplayName("Running delete player with id in negative flow")
    void deletePlayerWithIdNegativeFlow()
    {
        //arrange
        int playerId = 1;

        Player player = new Player("firstname1","lastname1","2000-09-11");
        Team team = new Team("Team1");
        player.setTeam(team);
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        //act
        try{
            PlayerDTO result = playerService.deletePlayerWithId(playerId);
        }
        catch (CannotDeleteEntityException e)
        {
            //assert
         assertEquals("Cannot delete player with id " + player.getPlayerId() + " because player is part of a team",e.getMessage());
         verify(playerRepository, times(0)).deleteById(playerId);
        }


    }


    @Test
    @DisplayName("Running delete player team in happy flow")
    void deletePlayerTeamHappyFlow() {
        //arrange
        int playerId = 1;

        Player player = new Player("firstname1", "lastname1", "2000-09-11");
        Team team = new Team("Team1");
        player.setTeam(team);
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        //act

        PlayerDTO result = playerService.deletePlayerTeam(playerId);


        //assert
        assertEquals("",result.getTeam());

        verify(playerRepository, times(1)).save(player);

    }


    @Test
    @DisplayName("Running get player team in happy flow")
    void getPlayerTeamHappyFlow(){

        //arrange
        int playerId = 1;

        Player player = new Player("firstname1", "lastname1", "2000-09-11");
        Team team = new Team("Team1");
        player.setTeam(team);
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        //act

        TeamDTO result = playerService.getPlayerTeam(playerId);


        //assert
        assertEquals(team.getName(),result.getName());


    }


    @Test
    @DisplayName("Running get player team in negative flow")
    void getPlayerTeamNegativeFlow(){

        //arrange
        int playerId = 1;

        Player player = new Player("firstname1", "lastname1", "2000-09-11");
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));

        //act
        try {
            TeamDTO result = playerService.getPlayerTeam(playerId);
        }catch (EntityNotFoundException e)
        {
            //assert
            assertEquals("Player has no team assigned",e.getMessage());
        }

    }

    @Test
    @DisplayName("Running bulk load player happy flow")
    void bulkLoadPlayerHappyFlow(){

        //arrange
        Player player = new Player("firstname1", "lastname1", "2000-09-11");
        when(playerRepository.existsByFirstName(any())).thenReturn(false);
        when(playerRepository.save(any())).thenReturn(player);
        String ok = "OK";
        int n = 15;

        //act

        String result = playerService.bulkLoadPlayer(n);

        //assert
        assertEquals(ok,result);


    }

    @Test
    @DisplayName("Running bulk load player negative flow")
    void bulkLoadPlayerNegativeFlow(){

        //arrange
        Player player = new Player("firstname1", "lastname1", "2000-09-11");
        when(playerRepository.existsByFirstName(any())).thenReturn(true);
        String exception = "Player with firstname : firstname" + 10 + " already exist";
        int n = 15;

        RuntimeException result = assertThrows(RuntimeException.class, ()-> playerService.bulkLoadPlayer(n));

        //assert
        assertEquals(exception, result.getMessage());
        verify(playerRepository,times(0)).save(any());



    }


    //RuntimeException = assertThrow(RuntimeException.class, () -> playerService.bulkLoadPlayer(n)"
}
