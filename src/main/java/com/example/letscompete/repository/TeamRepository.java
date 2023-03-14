package com.example.letscompete.repository;

import com.example.letscompete.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer>{

    Team findTeamByName(String name);

    @Query("select a from Team a where a.name = :name")
    Team findTeamByNameWithJpql(String name);

    @Query(value = "select * from team where name = :name",nativeQuery = true)
    Team findTeamByNameWithNativeQuery(String name);
}
