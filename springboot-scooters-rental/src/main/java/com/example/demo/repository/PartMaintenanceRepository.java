package com.example.demo.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


import com.example.demo.model.entity.PartMaintenance;

@Repository
public interface PartMaintenanceRepository extends JpaRepository<PartMaintenance,Integer> {
	


}
