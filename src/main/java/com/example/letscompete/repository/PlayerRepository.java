package com.example.letscompete.repository;

import com.example.letscompete.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository <Player, Integer>{

    Boolean existsByFirstName(String firstname);
}
