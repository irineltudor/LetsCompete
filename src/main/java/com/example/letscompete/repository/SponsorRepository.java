package com.example.letscompete.repository;

import com.example.letscompete.model.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SponsorRepository extends JpaRepository <Sponsor, Integer>{
}
