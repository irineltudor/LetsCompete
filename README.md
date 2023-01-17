# LetsCompete
 Spring Boot Application

# Abstract #

This document contains details related to the "Let’s Compete" application, a spring boot
application whose role is to keep track of game tournaments.


# Functionalities #

As said before ( in the abstract section ), this app keeps track of game tournaments. This app has
6 entities : Player, Team , Game , Location , Sponsor and Tournaments. Using each entity the
app will have certain functionalities. The entities are : Player , Team, Game, Location, Sponsor,
Tournament (for details check 2).

# Entities and Services #

This app has 6 entities :
1. Player - player id, first name, last name, dob, team
2. Team - team id, name, list of players, list of tournaments ( where the team is playing )
3. Game - game id, title, release date, list of tournaments ( where the game is played)
4. Location - location id , name, address, list of tournaments ( where the location is used)
5. Sponsor - sponsor id , name , address , list of tournaments ( where the sponsors is contribut-
ing)
6. Tournaments - tournament id, name, date, prize, game, location, list of sponsors, list of
teams


The relationships between entities are:
1. Team to Player ( a team has multiple players, but a player has only one team ) - One To
Many
2. Tournament to Team ( a tournament can have multiple teams competing, and a team can
compete to multiple tournaments ) - Many To Many
3. Tournament to Location ( a tournament can have a location but in a location we can have
multiple tournament ) - Many To One
4. Tournament To Game ( a tournament can have one game , but a game can be played in
multiple tournament ) - Many To One
5. Tournament To Sponsor ( a tournament can have multiple sponsors and a sponsor can fund
multiple tournaments ) - Many to Many


For each entity we will have a service:
1. PlayerService - contains functionality for get(all player,player by id, player’s team), add(player,bulkload),
update(player’s team) and delete(player’s team, player by id).
2. TeamService - contains functionality for get(all teams, team by id, team players), add(team)
and delete(team by id).
3. GameService - contains functionality for get(all games,game by title), add(game) and delete(game
by id).
4. LocationService - contains functionality for get(all locations,location by id), add(location),
update(location address) and delete(location by id).
5. SponsorService - contains functionality for get(all sponsors,sponsor by id), add(sponsor) and
delete(sponsor by id).
6. TournamentService - contains functionality for get(all tournaments, tournament by id, tour-
nament’s team, tournament’s location, tournament’s game, tournament’s sponsors, tourna-
ments happening today, tournaments happening on date), add(tournament, team for tour-
nament, sponsor for tournament), update(game for tournament, location for tournament)
and delete(location for tournament, game for tournament, team for tournament, sponsor for
tournament, tournament).

# The business requirements #

1. The app will retrieve details about every element that takes part in the tournament(Player,
Team, Game, Location, Sponsor, Tournament).
2. The app will offer support for adding, changing and deleting elements.
3. The app will show tournaments that are happening ”today” or in any other day that the
user specifies.

# Other details #

Each entity has a REST endpoint:
1. Player - ”localhost:8080/players”
2. Team - ”localhost:8080/teams”
3. Game - ”localhost:8080/games”
4. Location - ”localhost:8080/locations”
5. Sponsor - ”localhost:8080/sponsors”
6. Tournament - ”localhost:8080/tournaments”

The functionality of the application can be used with Postman.

How to use this app?
1. Player
   1. GET ”localhost:8080/players”
   2. POST ”localhost:8080/players” + player as body parameter
   3. DELETE ”localhost:8080/players” + player id as parameter
   4. POST ”localhost:8080/players/bulkload” + number as parameter
   5. GET "localhost:8080/players/{playerId}”
   6. GET ”localhost:8080/players/{playerId}/team”
   7. PUT ”localhost:8080/players/{playerId}/team” + team id as parameter
   8. DELETE ”localhost:8080/players/{playerId}/team”
2. Team
   1. GET ”localhost:8080/teams”
   2. POST ”localhost:8080/teams” + team as body parameter
   3. DELETE ”localhost:8080/teams” + team id as parameter
   4. GET ”localhost:8080/teams/{teamId}”
   5. ”localhost:8080/teams/{teamId}/players”
3. Game
   1. GET ”localhost:8080/games”
   2. POST ”localhost:8080/games” game as body parameter
   3. DELETE ”localhost:8080/games” game id as parameter
   4. GET ”localhost:8080/games/{title}” 
   5. GET "localhost:8080/games/genre/{genre}" genre as parameter
4. Location
   1. GET ”localhost:8080/locations”
   2. POST ”localhost:8080/locations” location as body parameter
   3. DELETE ”localhost:8080/locations” location id as parameter
   4. GET ”localhost:8080/locations/{locationId}”
   5. PUT ”localhost:8080/locations/{locationId}” address as parameter
5. Sponsor
   1. GET ”localhost:8080/sponsors”
   2. POST ”localhost:8080/sponsors” sponsor as body parameter
   3. DELETE ”localhost:8080/sponsors” sponsor id as parameter
   4. GET ”localhost:8080/sponsors/{sponsorId}”
6. Tournament 
   1. GET ”localhost:8080/tournaments”
   2. POST ”localhost:8080/tournaments” + tournament as body parameter
   3. DELETE ”localhost:8080/tournaments” + tournament id as parameter 
   4. GET ”localhost:8080/tournaments/{tournamentId}”
   5. GET ”localhost:8080/tournaments/{tournamentId}/teams”
   6. PUT ”localhost:8080/tournaments/{tournamentId}/teams” + team id as parameter
   7. DELETE ”localhost:8080/tournaments/{tournamentId}/teams” + team id as parameter
   8. GET ”localhost:8080/tournaments/{tournamentId}/game”
   9. PUT ”localhost:8080/tournaments/{tournamentId}/game” + game id as parameter
   10. DELETE ”localhost:8080/tournaments/{tournamentId}/game”
   11. GET ”localhost:8080/tournaments/{tournamentId}/location”
   12. PUT ”localhost:8080/tournaments/{tournamentId}/location” + location id as parameter
   13. DELETE ”localhost:8080/tournaments/{tournamentId}/location”
   14. GET ”localhost:8080/tournaments/{tournamentId}/sponsors”
   15. PUT ”localhost:8080/tournaments/{tournamentId}/sponsors” + sponsor id as parameter
   16. DELETE ”localhost:8080/tournaments/{tournamentId}/sponsors” + sponsor id as parameter
   17. GET ”localhost:8080/tournaments/on/today”
   18. GET ”localhost:8080/tournaments/on/{date}” + date as parameter

Or it can be used by accessing "http://localhost:8080/swagger-ui.html#/".

