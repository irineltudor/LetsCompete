package com.example.letscompete;

import com.example.letscompete.model.*;
import com.example.letscompete.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootApplication
public class LetscompeteApplication implements CommandLineRunner {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private SponsorRepository sponsorRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TournamentRepository tournamentRepository;

    public static void main(String[] args) {
        SpringApplication.run(LetscompeteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Team team1 = new Team("Team1");
        Team team2 = new Team("Team2");
        Team team3 = new Team("Team3");

        teamRepository.save(team1);
        teamRepository.save(team2);
        teamRepository.save(team3);

        Game game1 = new Game("Game1","Genre1","2000-09-11");
        Game game2 = new Game("Game2","Genre2","2000-09-11");

        gameRepository.save(game1);
        gameRepository.save(game2);

        Location location1 = new Location("Location1","address1");
        Location location2 = new Location("Location2","address2");

        locationRepository.save(location1);
        locationRepository.save(location2);

        Sponsor sponsor1 = new Sponsor("Sponsor1");
        Sponsor sponsor2 = new Sponsor("Sponsor2");

        sponsorRepository.save(sponsor1);
        sponsorRepository.save(sponsor2);

        Player player1 = new Player("firstname1","lastname1","2000-09-11");
        Player player2 = new Player("firstname2","lastname2","2000-09-11");
        Player player3 = new Player("firstname3","lastname3","2000-09-11");

        player1.setTeam(team1);
        player2.setTeam(team2);

        playerRepository.save(player1);
        playerRepository.save(player2);
        playerRepository.save(player3);

        Tournament tournament1 = new Tournament("tournament1","1v1","2000-09-11","10000$");
        Tournament tournament2 = new Tournament("tournament2","5v5","2000-09-11","10001$");
        Tournament tournament3 = new Tournament("tournament3","5v5", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()),"10$");

        tournament1.setGame(game1);
        tournament1.setLocation(location1);
        tournament1.setSponsorList(List.of(sponsor1,sponsor2));
        tournament1.setTeamList(List.of(team1,team2));

        tournament2.setGame(game2);
        tournament2.setLocation(location2);
        tournament2.setSponsorList(List.of(sponsor2));
        tournament2.setTeamList(List.of(team1));


        tournamentRepository.save(tournament1);
        tournamentRepository.save(tournament2);
        tournamentRepository.save(tournament3);



    }
}
