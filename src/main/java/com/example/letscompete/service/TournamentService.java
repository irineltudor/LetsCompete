package com.example.letscompete.service;


import com.example.letscompete.dto.*;
import com.example.letscompete.exception.CannotDeleteEntityException;
import com.example.letscompete.exception.DateNotValidException;
import com.example.letscompete.model.*;
import com.example.letscompete.repository.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final SponsorService sponsorService;
    private final GameService gameService;
    private final LocationService locationService;

    private final TeamService teamService;

    public TournamentService(TournamentRepository tournamentRepository, SponsorService sponsorService, GameService gameService, LocationService locationService, TeamService teamService) {
        this.tournamentRepository = tournamentRepository;
        this.sponsorService = sponsorService;
        this.gameService = gameService;
        this.locationService = locationService;
        this.teamService = teamService;
    }


    public List<TournamentDTO> getTournamentDTOs() {
        List<Tournament> tournamentList = tournamentRepository.findAll();
        if(tournamentList.isEmpty())
            throw new EntityNotFoundException("There are no tournaments");
        return tournamentList.stream().map(TournamentDTO::new).collect(Collectors.toList());
    }

    public TournamentDTO getTournamentDTOById(int tournamentId) {
        return new TournamentDTO(tournamentRepository.findById(tournamentId).orElseThrow(() -> new EntityNotFoundException("Tournament with id " + tournamentId + " not found")));
    }

    public Tournament getTournamentById(int tournamentId) {
        return tournamentRepository.findById(tournamentId).orElseThrow(() -> new EntityNotFoundException("Tournament with id " + tournamentId + " not found"));
    }


    public List<TeamDTO> getTournamentTeams(int tournamentId) {

        List<TeamDTO> teamDTOList;

        if (!tournamentRepository.existsById(tournamentId)) {
            throw new EntityNotFoundException("Tournament with id " + tournamentId + " not found");
        } else {
            teamDTOList = getTournamentById(tournamentId).getTeamList().stream().map(TeamDTO::new).collect(Collectors.toList());
        }
        return teamDTOList;
    }

    public TournamentDTO add(Tournament tournament) {
        return new TournamentDTO(tournamentRepository.save(tournament));
    }

    public TournamentDTO addTeam(int tournamentId, int teamId) {
        Tournament tournament = getTournamentById(tournamentId);
        Team team = teamService.getTeamById(teamId);
        TournamentDTO tournamentDTO = new TournamentDTO();

        if(tournament.getTeamList().stream().anyMatch(o -> o.getTeamId() == teamId))
        {
            throw new RuntimeException("Team with id " + teamId + " is already assigned to this tournament");
        }
        else {
            if (tournament.getName() != null || team.getName() != null) {
                List<Team> teamList = tournament.getTeamList();
                teamList.add(team);
                tournament.setTeamList(teamList);
                tournamentRepository.save(tournament);
                tournamentDTO = new TournamentDTO(tournament);
            }
        }

        return tournamentDTO;

    }

    public TournamentDTO deleteTeamById(int tournamentId, int teamId) {
        Tournament tournament = getTournamentById(tournamentId);
        Team team = teamService.getTeamById(teamId);
        TournamentDTO tournamentDTO;


        if (tournament.getTeamList().stream().noneMatch(o -> o.getTeamId() == teamId)) {
            throw new CannotDeleteEntityException("Team with id " + teamId + " is not signed for this tournament");
        } else {

            List<Tournament> tournamentList = team.getTournamentList().stream().filter(tournament1 -> tournament1.getTournamentId() != tournament.getTournamentId()).collect(Collectors.toList());
            List<Team> teamList = tournament.getTeamList().stream().filter(team1 -> team1.getTeamId() != team.getTeamId()).collect(Collectors.toList());

            team.setTournamentList(tournamentList);
            teamService.update(team);

            tournament.setTeamList(teamList);
            tournamentRepository.save(tournament);

            tournamentDTO = new TournamentDTO(tournament);

        }


        return tournamentDTO;
    }

    public TournamentDTO deleteLocation(int tournamentId) {
        TournamentDTO tournamentDTO;
        Tournament tournament = getTournamentById(tournamentId);


        if (tournament.getLocation() == null) {
            throw new CannotDeleteEntityException("Tournament with id " + tournament + " has no location assigned");
        } else {
            Location location = tournament.getLocation();

            tournament.setLocation(null);

            location.setTournamentList(location.getTournamentList().stream().filter(tournament1 -> tournament1.getTournamentId() != tournamentId).collect(Collectors.toList()));

            tournamentRepository.save(tournament);

            tournamentDTO = new TournamentDTO(tournament);
        }


        return tournamentDTO;
    }

    public TournamentDTO deleteGame(int tournamentId) {
        TournamentDTO tournamentDTO;
        Tournament tournament = getTournamentById(tournamentId);

        if (tournament.getGame() == null) {
            throw new CannotDeleteEntityException("Tournament with id " + tournament + " has no game assigned");
        } else {
            Game game = tournament.getGame();
            tournament.setGame(null);
            game.setTournamentList(game.getTournamentList().stream().filter(tournament1 -> tournament1.getTournamentId() != tournamentId).collect(Collectors.toList()));
            tournamentRepository.save(tournament);
            tournamentDTO = new TournamentDTO(tournament);
        }


        return tournamentDTO;

    }

    public TournamentDTO deleteSponsorById(int tournamentId, int sponsorId) {
        TournamentDTO tournamentDTO;
        Tournament tournament = getTournamentById(tournamentId);
        Sponsor sponsor = sponsorService.getSponsorById(sponsorId);

        if (tournament.getSponsorList().stream().noneMatch(o -> o.getSponsorId() == sponsorId)) {
            throw new CannotDeleteEntityException("Sponsor with id " + sponsorId + " is not signed for this tournament");
        } else {
            List<Sponsor> sponsorList = tournament.getSponsorList().stream().filter(sponsor1 -> sponsor1.getSponsorId() != sponsorId).collect(Collectors.toList());
            sponsor.setTournamentList(sponsor.getTournamentList().stream().filter(tournament1 -> tournament1.getTournamentId() != tournamentId).collect(Collectors.toList()));

            tournament.setSponsorList(sponsorList);
            tournamentRepository.save(tournament);
            tournamentDTO = new TournamentDTO(tournament);
        }

        return tournamentDTO;
    }


    public TournamentDTO deleteTournament(int tournamentId) {
        TournamentDTO tournamentDTO = new TournamentDTO();
        Tournament tournament = getTournamentById(tournamentId);


        if (!tournament.getSponsorList().isEmpty() || !tournament.getTeamList().isEmpty() || tournament.getLocation() != null || tournament.getGame() != null) {
            throw new CannotDeleteEntityException("Cannot delete tournament with id " + tournamentId + " because the tournament still have some sponsors, teams, location or game assigned");
        } else {
            tournamentRepository.deleteById(tournamentId);
            tournamentDTO = new TournamentDTO(tournament);
        }


        return tournamentDTO;
    }

    public LocationDTO getTournamentLocation(int tournamentId) {
        LocationDTO locationDTO;
        Tournament tournament = getTournamentById(tournamentId);
        locationDTO = new LocationDTO(tournament.getLocation());

        return locationDTO;
    }

    public TournamentDTO changeTournamentLocation(int tournamentId, int locationId) {
        TournamentDTO tournamentDTO;
        Tournament tournament = getTournamentById(tournamentId);
        Location newLocation = locationService.getLocationById(locationId);
        Location oldLocation = tournament.getLocation();

        if(oldLocation != null) {
            oldLocation.setTournamentList(oldLocation.getTournamentList().stream().filter(tournament1 -> tournament1.getTournamentId() != tournamentId).collect(Collectors.toList()));
            locationService.update(oldLocation);
        }
        tournament.setLocation(newLocation);
        tournamentRepository.save(tournament);

        List<Tournament> tournamentList = newLocation.getTournamentList();
        tournamentList.add(tournament);
        newLocation.setTournamentList(tournamentList);
        locationService.update(newLocation);

        tournamentDTO = new TournamentDTO(tournament);

        return tournamentDTO;
    }

    public GameDTO getTournamentGame(int tournamentId) {
        GameDTO gameDTO;
        Tournament tournament = getTournamentById(tournamentId);

        if(tournament.getGame() == null)
            throw new EntityNotFoundException("Tournament with id " + tournamentId + " has no game assinged");
        gameDTO = new GameDTO(tournament.getGame());

        return gameDTO;
    }

    public TournamentDTO changeTournamentGame(int tournamentId, int gameId) {
        TournamentDTO tournamentDTO;
        Tournament tournament = getTournamentById(tournamentId);
        Game newGame = gameService.getGameById(gameId);
        Game oldGame = tournament.getGame();


        tournament.setGame(newGame);
        tournamentRepository.save(tournament);
        tournamentDTO = new TournamentDTO(tournament);

        List<Tournament> tournamentList = newGame.getTournamentList();
        tournamentList.add(tournament);
        newGame.setTournamentList(tournamentList);
        gameService.update(newGame);

        if(oldGame != null) {
            oldGame.setTournamentList(newGame.getTournamentList().stream().filter(tournament1 -> tournament1.getTournamentId() != tournamentId).collect(Collectors.toList()));
            gameService.update(newGame);
        }


        return tournamentDTO;
    }

    public List<SponsorDTO> getTournamentSponsors(int tournamentId) {
        List<SponsorDTO> sponsorDTOList;
        Tournament tournament = getTournamentById(tournamentId);

        sponsorDTOList = tournament.getSponsorList().stream().map(SponsorDTO::new).collect(Collectors.toList());

        return sponsorDTOList;
    }

    public TournamentDTO addTournamentSponsor(int tournamentId, int sponsorId) {
        TournamentDTO tournamentDTO;
        Tournament tournament = getTournamentById(tournamentId);
        Sponsor sponsor = sponsorService.getSponsorById(sponsorId);


        if (tournament.getSponsorList().stream().anyMatch(o -> o.getSponsorId() == sponsorId)){
            throw new CannotDeleteEntityException("Sponsor with id " + sponsorId + " is already funding this tournament");
        }

        List<Sponsor> sponsorList = tournament.getSponsorList();
        List<Tournament> tournamentList = sponsor.getTournamentList();

        sponsorList.add(sponsor);
        tournament.setSponsorList(sponsorList);
        tournamentRepository.save(tournament);

        tournamentList.add(tournament);
        sponsor.setTournamentList(tournamentList);
        sponsorService.update(sponsor);

        tournamentDTO = new TournamentDTO(tournament);


        return tournamentDTO;
    }

    public List<TournamentDTO> getTournamentsForToday() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Tournament> tournamentList = tournamentRepository.findAllByDate(dtf.format(LocalDateTime.now()));
        List<TournamentDTO> tournamentDTOList;

        if (tournamentList.isEmpty()) {
            throw new EntityNotFoundException("There are no tournaments for today");
        } else {
            tournamentDTOList = tournamentList.stream().map(TournamentDTO::new).collect(Collectors.toList());
        }

        return tournamentDTOList;
    }

    public List<TournamentDTO> getTournamentsForDate(String date) {

        List<TournamentDTO> tournamentDTOList = new ArrayList<>();
        if (!date.matches("[0-9]{4}-(0[1-9]|1[0-2])-([0-2][0-9]|3[0-1])")) {
            throw new DateNotValidException("Incorrect date format (yyyy-mm-dd ex:2000-01-20)");
        } else {
            List<Tournament> tournamentList = tournamentRepository.findAllByDate(date);
            if (tournamentList.isEmpty()) {
                throw new EntityNotFoundException("There are no tournaments for " + date);
            } else {
                tournamentDTOList = tournamentList.stream().map(TournamentDTO::new).collect(Collectors.toList());
            }
        }

        return tournamentDTOList;
    }
}
