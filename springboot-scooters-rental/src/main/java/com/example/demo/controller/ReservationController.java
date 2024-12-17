package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.dto.ReservationDto;
import com.example.demo.model.dto.ScooterDto;
import com.example.demo.model.entity.Reservation;
import com.example.demo.model.entity.Reservation.PaymentStatus;
import com.example.demo.model.entity.Reservation.Status;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ReservationService;
import com.example.demo.service.ScooterService;

import jakarta.servlet.http.HttpSession;

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
	private final UserRepository userRepository;	
	
	public ReservationController(ScooterService scooterService,ReservationService reservationService,UserRepository userRepository) {
		this.scooterService = scooterService;
		this.reservationService = reservationService;
		this.userRepository = userRepository;
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
	
	
	 // 加入購物車方法
    @PostMapping("/cart/add")
    public String addToCart(@ModelAttribute ReservationDto reservation, RedirectAttributes redirectAttributes, Authentication authentication) {
    	//RedirectAttributes 用來在進行重定向（redirect）時傳遞暫時性的訊息或屬性給下個頁面。
        
    	
    	
    	
    	try {
    		// 取得當前登入使用者的 username
            String username = authentication.getName();

            // 透過 username 查詢對應的 User 資料
            User user = userRepository.findByUsername(username)
                                      .orElseThrow(() -> new UserNotFoundException("User not found"));

            // 取得 userId
            Integer userId = user.getUserId();
        	
            reservationService.addToCart(reservation.getScooterId(), reservation.getStartDate(), reservation.getEndDate(), userId);
            redirectAttributes.addFlashAttribute("successMessage", "成功將機車加入購物車！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "加入購物車時發生錯誤：" + e.getMessage());
        }
        return "redirect:/cart";
    }
    
    
 // 顯示購物車
    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        List<Reservation> cart = (List<Reservation>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
     // 計算總計
        double total = cart.stream()
            .mapToDouble(Reservation::getTotalAmount)
            .sum();
        
        model.addAttribute("cart", cart);
        model.addAttribute("cartTotal", total);
        return "user/cart";
    }
    
    
    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
        List<Reservation> cart = (List<Reservation>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(reservation -> reservation.getReservationId().equals(id));
            session.setAttribute("cart", cart);
            redirectAttributes.addFlashAttribute("successMessage", "成功移除購物車品項！");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "移除失敗，購物車可能已清空！");
        }
        return "redirect:/cart";
    }

    
    //儲存預約
    @PostMapping("/reservation/checkout")
    public String checkout(HttpSession session, RedirectAttributes redirectAttributes, Authentication authentication) {
        List<Reservation> cart = (List<Reservation>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "購物車是空的，無法結帳！");
            return "redirect:/cart";
        }
        
        try {
            // 取得當前登入的使用者
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                                      .orElseThrow(() -> new UserNotFoundException("User not found"));
            
            // 結帳過程
            for (Reservation reservation : cart) {
                reservation.setUser(user);
                reservation.setPaymentStatus(PaymentStatus.pending);
                reservation.setPaymentDate(LocalDate.now());
                reservation.setStatus(Status.reserved);
                reservationService.saveReservation(reservation);
            }
            
            // 清空購物車
            session.removeAttribute("cart");
            redirectAttributes.addFlashAttribute("successMessage", "結帳成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "結帳失敗：" + e.getMessage());
        }
        
        return "redirect:/reservation/confirmation";
    }
    
    


}
