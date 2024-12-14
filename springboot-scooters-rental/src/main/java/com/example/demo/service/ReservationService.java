package com.example.demo.service;

import java.time.LocalDate;

import com.example.demo.model.entity.Reservation;

public interface ReservationService {
	public void addToCart(int scooterId, LocalDate startDate, LocalDate endDate,int userId);
	public void reserveScooter();
	public double calculateRentalFee(int scooterId, LocalDate startDate, LocalDate endDate);
	public boolean checkAvailability(int scooterId, LocalDate startDate, LocalDate endDate);
}
