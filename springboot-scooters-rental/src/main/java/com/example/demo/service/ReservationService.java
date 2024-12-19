package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.entity.Reservation;
import com.example.demo.model.entity.User;


public interface ReservationService {
	public void addToCart(int scooterId, LocalDate startDate, LocalDate endDate,int userId);
	public void reserveScooter();
	public double calculateRentalFee(int scooterId, LocalDate startDate, LocalDate endDate);
	public boolean checkAvailability(int scooterId, LocalDate startDate, LocalDate endDate);
	public void saveReservation(Reservation reservation);
	public List<Reservation> findAllReservations();
	public List<Reservation> findReservationsByUser(User user);
	public void deleteReservation(Integer reservationId);
	public Optional<Reservation> findReservationById(Integer reservationId);
}
