package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Scooter;
import com.example.demo.model.entity.ScooterPart;

@Repository
public interface ScooterPartRepository extends JpaRepository<ScooterPart,Integer> {
	List<ScooterPart> findAllByScooter_ScooterId(Integer scooterId);
}
