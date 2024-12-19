package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Reservation;
import com.example.demo.model.entity.User;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
	List<Reservation> findByUser(User user);
}
