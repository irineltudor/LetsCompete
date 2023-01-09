package com.example.letscompete.repository;

import com.example.letscompete.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationRepository extends JpaRepository <Location, Integer>{
}
