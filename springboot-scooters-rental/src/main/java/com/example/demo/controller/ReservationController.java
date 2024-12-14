package com.example.demo.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.dto.ReservationDto;
import com.example.demo.model.dto.ScooterDto;
import com.example.demo.service.ReservationService;
import com.example.demo.service.ScooterService;

/*
車輛預約頁面:
功能:選擇的車輛詳細資訊
功能與按鈕:日期選擇器（開始/結束日期）
功能與按鈕:即時計算租金與預約按鈕
按鈕:立即預約
按鈕:加入購物車
購物車頁面:
功能:顯示預約詳細資訊
按鈕:立即預約
租賃紀錄頁面：
功能:取消預約
按鈕:導向更改預約(只限將來的預約)
更改預約:
功能:更改日期、天數、車型
功能:即時計算租金
功能:選擇的車輛詳細資訊

 */


@Controller
public class ReservationController {
	
	private final ScooterService scooterService;
	private final ReservationService reservationService;
	
	public ReservationController(ScooterService scooterService,ReservationService reservationService) {
		this.scooterService = scooterService;
		this.reservationService = reservationService;
	}
	


	@GetMapping("/reservation/{scooterId}")
	public String showReservationPage(@PathVariable Integer scooterId,Model model) {
		ScooterDto scooter = scooterService.getScooterById(scooterId);
		 // 建立空的 ReservationForm 對象
	    ReservationDto reservation = new ReservationDto();
	    reservation.setScooterId(scooterId);
		
		model.addAttribute("scooter",scooter);
		model.addAttribute("reservation", reservation);
		return  "user/reservation";
	}
	
	
	@PostMapping("/reservation/calculate")
    public String calculateRental(@ModelAttribute("reservation") ReservationDto reservation, Model model) {
        // 從表單中取得 startDate 和 endDate
        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();

     // 計算租借天數
        long rentalDays = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        
        
        // 計算租金
        double totalRent = reservationService.calculateRentalFee(reservation.getScooterId(), startDate, endDate);

        // 將總租金與預約資訊、預約天數傳回給頁面
        model.addAttribute("rentalDays", rentalDays);
        model.addAttribute("totalRent", totalRent);
        model.addAttribute("reservation", reservation);
        ScooterDto scooter = scooterService.getScooterById(reservation.getScooterId());
        model.addAttribute("scooter", scooter);

        return "user/reservation";
    }

}
