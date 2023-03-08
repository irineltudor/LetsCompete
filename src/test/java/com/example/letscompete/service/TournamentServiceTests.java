package com.example.letscompete.service;

import com.example.letscompete.dto.GameDTO;
import com.example.letscompete.dto.SponsorDTO;
import com.example.letscompete.dto.TournamentDTO;
import com.example.letscompete.exception.CannotDeleteEntityException;
import com.example.letscompete.model.*;
import com.example.letscompete.repository.TournamentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TournamentServiceTests {

    @InjectMocks
    private TournamentService tournamentService;

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private TeamService teamService;

    @Mock
    private LocationService locationService;

    @Mock
    private GameServiceImpl gameService;

    @Test
    @DisplayName("Running add team in happy flow")
    void addTeamHappyFlow(){
        //arrange - define actions for mocks
        int teamId = 0;
        int tournamentId = 0;
        Team team = new Team("Team Liquid");
        Tournament tournament = new Tournament("Dota 2 – The International 2023","5v5","2023-10-02","2.000.000$");
        TournamentDTO tournamentDTO = new TournamentDTO(tournament);
        tournamentDTO.setTeamList(List.of(team.getName()));
        when(teamService.getTeamById(teamId)).thenReturn(team);
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        //act - calling the actual method
        TournamentDTO result = tournamentService.addTeam(tournamentId,teamId);

        //assert - see what is happening
        assertEquals(tournamentDTO.getTeamList().size(),result.getTeamList().size());
    }

    @Test
    @DisplayName("Running add team in negativeFlow")
    void addTeamNegativeFlow(){
        //arrange - define actions for mocks
        int teamId = 0;
        int tournamentId = 0;
        Team team = new Team("Team Liquid");
        Tournament tournament = new Tournament("Dota 2 – The International 2023","5v5","2023-10-02","2.000.000$");
        tournament.setTeamList(List.of(team));
        when(teamService.getTeamById(teamId)).thenReturn(team);
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        //act - calling the actual method
        try {
            TournamentDTO result = tournamentService.addTeam(tournamentId,teamId);
        }catch (RuntimeException e) {
            //assert - see what is happening
            assertEquals("Team with id " + teamId + " is already assigned to this tournament",e.getMessage());
        }
    }


    @Test
    @DisplayName("Running delete team by id in happy flow")
    void deleteTeamByIdHappyFlow(){
        //arrange - define actions for mocks
        int teamId = 0;
        int tournamentId = 0;
        Team team = new Team("Team Liquid");
        Tournament tournament = new Tournament("Dota 2 – The International 2023","5v5","2023-10-02","2.000.000$");
        tournament.setTeamList(List.of(team));
        TournamentDTO tournamentDTO = new TournamentDTO(tournament);
        tournamentDTO.setTeamList(List.of());
        when(teamService.getTeamById(teamId)).thenReturn(team);
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        //act - calling the actual method
        TournamentDTO result = tournamentService.deleteTeamById(tournamentId,teamId);

        //assert - see what is happening
        assertEquals(tournamentDTO.getTeamList().size(),result.getTeamList().size());
    }

    @Test
    @DisplayName("Running delete team by id  in negativeFlow")
    void deleteTeamByIdFlow(){
        //arrange - define actions for mocks
        int teamId = 0;
        int tournamentId = 0;
        Team team = new Team("Team Liquid");
        Tournament tournament = new Tournament("Dota 2 – The International 2023","5v5","2023-10-02","2.000.000$");
        when(teamService.getTeamById(teamId)).thenReturn(team);
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        //act - calling the actual method
        try {
            TournamentDTO result = tournamentService.deleteTeamById(tournamentId,teamId);
        }catch (CannotDeleteEntityException e) {
            //assert - see what is happening
            assertEquals("Team with id " + teamId + " is not signed for this tournament",e.getMessage());
        }
    }


    @Test
    @DisplayName("Running delete location in happy flow")
    void deleteLocationHappyFlow(){
        //arrange - define actions for mocks
        int tournamentId = 0;
        Location location = new Location("United States","Washington, D.C.");
        Tournament tournament = new Tournament("Dota 2 – The International 2023","5v5","2023-10-02","2.000.000$");
        TournamentDTO tournamentDTO = new TournamentDTO(tournament);
        tournament.setLocation(location);
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        //act - calling the actual method
        TournamentDTO result = tournamentService.deleteLocation(tournamentId);

        //assert - see what is happening
        assertEquals(tournamentDTO.getLocation(),result.getLocation());
    }

    @Test
    @DisplayName("Running delete location in negativeFlow")
    void deleteLocationFlow(){
        //arrange - define actions for mocks
        int tournamentId = 0;
        Tournament tournament = new Tournament("Dota 2 – The International 2023","5v5","2023-10-02","2.000.000$");
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        //act - calling the actual method
        try {
            TournamentDTO result = tournamentService.deleteLocation(tournamentId);
        }catch (CannotDeleteEntityException e) {
            //assert - see what is happening
            assertEquals("Tournament with id " + tournamentId + " has no location assigned",e.getMessage());
        }
    }


    @Test
    @DisplayName("Running delete game in happy flow")
    void deleteGameHappyFlow(){
        //arrange - define actions for mocks
        int tournamentId = 0;
        Game game = new Game("Dota2","MOBA","2013-07-09");
        Tournament tournament = new Tournament("Dota 2 – The International 2023","5v5","2023-10-02","2.000.000$");
        TournamentDTO tournamentDTO = new TournamentDTO(tournament);
        tournament.setGame(game);
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        //act - calling the actual method
        TournamentDTO result = tournamentService.deleteGame(tournamentId);

        //assert - see what is happening
        assertEquals(tournamentDTO.getLocation(),result.getLocation());
    }

    @Test
    @DisplayName("Running delete game in negativeFlow")
    void deleteGameFlow(){
        //arrange - define actions for mocks
        int tournamentId = 0;
        Tournament tournament = new Tournament("Dota 2 – The International 2023","5v5","2023-10-02","2.000.000$");
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        //act - calling the actual method
        try {
            TournamentDTO result = tournamentService.deleteGame(tournamentId);
        }catch (CannotDeleteEntityException e) {
            //assert - see what is happening
            assertEquals("Tournament with id " + tournamentId + " has no game assigned",e.getMessage());
        }
    }

    @Test
    @DisplayName("Running delete game in happy flow location != null")
    void changeTournamentLocationFlow1(){
        //arrange - define actions for mocks
        int tournamentId = 0;
        int locationId = 1;
        Location location1 = new Location("United States","Washington, D.C.");
        Location location2 = new Location("France","Paris");
        Tournament tournament = new Tournament("Dota 2 – The International 2023","5v5","2023-10-02","2.000.000$");
        tournament.setLocation(location1);
        TournamentDTO tournamentDTO = new TournamentDTO(tournament);
        tournamentDTO.setLocation(location2.getName());
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));
        when(locationService.getLocationById(locationId)).thenReturn(location2);

        //act - calling the actual method
        TournamentDTO result = tournamentService.changeTournamentLocation(tournamentId,locationId);

        //assert - see what is happening
        assertEquals(tournamentDTO.getLocation(),result.getLocation());
    }

    @Test
    @DisplayName("Running get tournament's game in happy flow")
    void getTournamentGameHappyFlow(){
        //arrange - define actions for mocks
        int tournamentId = 0;
        Game game = new Game("Dota2","MOBA","2013-07-09");
        Tournament tournament = new Tournament("Dota 2 – The International 2023","5v5","2023-10-02","2.000.000$");
        GameDTO gameDTO = new GameDTO(game);
        tournament.setGame(game);
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        //act - calling the actual method
        GameDTO result = tournamentService.getTournamentGame(tournamentId);

        //assert - see what is happening
        assertEquals(gameDTO.getTitle(),result.getTitle());
    }


    @Test
    @DisplayName("Running change tournament's game in happy flow")
    void changeTournamentGameHappyFlow(){
        //arrange - define actions for mocks
        int tournamentId = 0;
        int gameId = 1;
        Game game1 = new Game("Dota2","MOBA","2013-07-09");
        Game game2 = new Game("Fortnite","BattleRoyale","2017-07-21");
        Tournament tournament = new Tournament("Fortnite – The International 2023","5v5","2023-10-02","2.000.000$");
        tournament.setGame(game1);
        TournamentDTO tournamentDTO = new TournamentDTO(tournament);
        tournamentDTO.setGame(game2.getTitle());

        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));
        when(gameService.getGameById(gameId)).thenReturn(game2);

        //act - calling the actual method
        TournamentDTO result = tournamentService.changeTournamentGame(tournamentId,gameId);

        //assert - see what is happening
        assertEquals(tournamentDTO.getGame(),result.getGame());
    }


    @Test
    @DisplayName("Running get tournament's sponsors in happy flow")
    void getTournamentSponsorsHappyFlow(){
        //arrange - define actions for mocks
        int tournamentId = 0;
        Sponsor sponsor = new Sponsor("INTEL");
        Tournament tournament = new Tournament("Dota 2 – The International 2023","5v5","2023-10-02","2.000.000$");
        tournament.setSponsorList(List.of(sponsor));
        List<SponsorDTO> sponsorDTOList = List.of(new SponsorDTO(sponsor));
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        //act - calling the actual method
        List<SponsorDTO> result = tournamentService.getTournamentSponsors(tournamentId);

        //assert - see what is happening
        assertEquals(sponsorDTOList.size(),result.size());
    }


    @Test
    @DisplayName("Running get tournaments happening today in happy flow")
    void getTournamentsForTodayHappyFlow(){
        //arrange - define actions for mocks
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Tournament tournament = new Tournament("CS:GO – BLAST Paris Major 2023","1v1", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()),"1.000.000$ and redbull for life");
        List<TournamentDTO> tournamentDTOList = List.of(new TournamentDTO(tournament));
        when(tournamentRepository.findAllByDate(dtf.format(LocalDateTime.now()))).thenReturn(List.of(tournament));

        //act - calling the actual method
        List<TournamentDTO> result = tournamentService.getTournamentsForToday();

        //assert - see what is happening
        assertEquals(tournamentDTOList.size(),result.size());
    }

    @Test
    @DisplayName("Running get tournaments happening on date in happy flow")
    void getTournamentsForDateHappyFlow(){
        //arrange - define actions for mocks
        String date = "2023-11-09";
        Tournament tournament = new Tournament("CS:GO – BLAST Paris Major 2023","1v1", "2023-11-09","1.000.000$ and redbull for life");
        List<TournamentDTO> tournamentDTOList = List.of(new TournamentDTO(tournament));
        when(tournamentRepository.findAllByDate(date)).thenReturn(List.of(tournament));

        //act - calling the actual method
        List<TournamentDTO> result = tournamentService.getTournamentsForDate(date);

        //assert - see what is happening
        assertEquals(tournamentDTOList.size(),result.size());
    }

}
