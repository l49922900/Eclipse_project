package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ScooterNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.entity.Reservation;
import com.example.demo.model.entity.Scooter;
import com.example.demo.model.entity.User;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.ScooterRepository;
import com.example.demo.repository.ScooterRepositoryJdbc;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ReservationService;

import jakarta.servlet.http.HttpSession;


@Service
public class ReservationServiceImpl implements ReservationService {

	@Autowired
    private ScooterRepository scooterRepository;

    @Autowired
    private ScooterRepositoryJdbc scooterRepositoryJdbc;

    @Autowired
    private HttpSession session;
    
    @Autowired
    private ReservationRepository reservationRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    //確認車輛預約日期是否有衝突   
    @Override
    public boolean checkAvailability(int scooterId, LocalDate startDate, LocalDate endDate) {
    	List<Reservation> conflictingRentals = scooterRepositoryJdbc.findConflictingRentalsDays(
                scooterId, startDate, endDate);
        return conflictingRentals.isEmpty();
    }
    
    
    // 計算租金
    @Override
    public double calculateRentalFee(int scooterId, LocalDate startDate, LocalDate endDate) {
        Scooter scooter = scooterRepository.findById(scooterId)
                .orElseThrow(() -> new ScooterNotFoundException("Scooter not found with ID: " + scooterId));
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate)+1;
        //使用ChronoUnit.DAYS.between方法計算startDate和endDate之間的天數。
        
        return scooter.getDailyRate() * days;
    }



    
    // 新增到購物車
    @Override
    public void addToCart(int scooterId, LocalDate startDate, LocalDate endDate,int userId) {
        List<Reservation> cart = (List<Reservation>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        // 確認車輛是否存在
        Scooter scooter = scooterRepository.findById(scooterId)
                .orElseThrow(() -> new ScooterNotFoundException("Scooter not found with ID: " + scooterId));

        User user = userRepository.findById(userId)
        		.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        
        
        // 檢查可用性
        if (!checkAvailability(scooterId, startDate, endDate)) {
            throw new IllegalArgumentException("Scooter is not available for the selected dates.");
        }

        // 建立預約
        Reservation reservation = new Reservation();
        reservation.setScooter(scooter);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setUser(user);
        reservation.setTotalAmount(calculateRentalFee(scooterId, startDate, endDate));

        cart.add(reservation);
        session.setAttribute("cart", cart);
    }
    
    // 預約車輛
    @Override
    public void reserveScooter() {
    	// 取得購物車內容
        List<Reservation> cart = (List<Reservation>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            throw new IllegalStateException("The cart is empty. Cannot proceed with reservation.");
        }

        // 儲存預約與更新車輛狀態
        for (Reservation reservation : cart) {
            // 儲存預約
            reservationRepository.save(reservation);

            // 更新車輛狀態為 rented
            Scooter scooter = reservation.getScooter();
            scooter.setStatus(Scooter.Status.rented);
            scooterRepository.save(scooter); // 更新車輛狀態到資料庫
        }

        // 清空購物車
        session.removeAttribute("cart");
    }
    
    



}