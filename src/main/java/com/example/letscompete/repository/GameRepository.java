package com.example.letscompete.repository;

import com.example.letscompete.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findGameByTitle(String title);

    @Query("select a from Game a where a.title = :title")
    Game findGameByTitleWithJpql(String title);

    @Query(value = "select * from game where title = :title",nativeQuery = true)
    Game findGameByTitleWithNativeQuery(String title);


    Optional<List<Game>> findAllByGenre(String genre);

}
