package com.example.letscompete.repository;

import com.example.letscompete.model.Game;
import com.example.letscompete.model.Team;
import com.example.letscompete.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TournamentRepository extends JpaRepository <Tournament, Integer>{

    @Query("select t from Tournament t where t.date = :date")
    List<Tournament> findAllByDate(String date);
}
