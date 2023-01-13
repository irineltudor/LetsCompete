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

        Team team1 = new Team("Team Liquid");
        Team team2 = new Team("OG");
        Team team3 = new Team("Evil Geniuses");
        Team team4 = new Team("Team Spirit");

        teamRepository.save(team1);
        teamRepository.save(team2);
        teamRepository.save(team3);
        teamRepository.save(team4);

        Game game1 = new Game("Dota2","MOBA","2013-07-09");
        Game game2 = new Game("Fortnite","BattleRoyale","2017-07-21");
        Game game3 = new Game("Counter-Strike: Global Offensive","TacticalShooter","2012-08-21");
        Game game4 = new Game("PUBG","BattleRoyale","2017-03-23");


        gameRepository.save(game1);
        gameRepository.save(game2);
        gameRepository.save(game3);
        gameRepository.save(game4);

        Location location1 = new Location("United States","Washington, D.C.");
        Location location2 = new Location("France","Paris");
        Location location3 = new Location("South Korea","Seul");

        locationRepository.save(location1);
        locationRepository.save(location2);
        locationRepository.save(location3);

        Sponsor sponsor1 = new Sponsor("INTEL");
        Sponsor sponsor2 = new Sponsor("COCA-COLA");
        Sponsor sponsor3 = new Sponsor("RED BULL");

        sponsorRepository.save(sponsor1);
        sponsorRepository.save(sponsor2);
        sponsorRepository.save(sponsor3);

        Player player1 = new Player("Ivan","Ivanov","2000-09-11");
        Player player2 = new Player("Johan","Sundstein","2000-09-11");
        Player player3 = new Player("Sumail","Hassan","2000-09-11");
        Player player4 = new Player("Magomed","Khalilov","2000-09-11");

        player1.setTeam(team1);
        player2.setTeam(team2);
        player1.setTeam(team3);
        player2.setTeam(team4);

        playerRepository.save(player1);
        playerRepository.save(player2);
        playerRepository.save(player3);
        playerRepository.save(player4);

        Tournament tournament1 = new Tournament("Dota 2 – The International 2023","5v5","2023-10-02","2.000.000$");
        Tournament tournament2 = new Tournament("Fortnite - World Cup 2023","5v5","2023-11-08","1.500.000$");
        Tournament tournament3 = new Tournament("CS:GO – BLAST Paris Major 2023","1v1", DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()),"1.000.000$ and redbull for life");

        tournament1.setGame(game1);
        tournament1.setLocation(location1);
        tournament1.setSponsorList(List.of(sponsor1,sponsor2));
        tournament1.setTeamList(List.of(team1,team2));

        tournament2.setGame(game2);
        tournament2.setLocation(location2);
        tournament2.setSponsorList(List.of(sponsor2));
        tournament2.setTeamList(List.of(team1,team3));

        tournament3.setGame(game3);
        tournament3.setLocation(location3);
        tournament3.setSponsorList(List.of(sponsor3));
        tournament3.setTeamList(List.of(team1,team4));


        tournamentRepository.save(tournament1);
        tournamentRepository.save(tournament2);
        tournamentRepository.save(tournament3);



    }
}
