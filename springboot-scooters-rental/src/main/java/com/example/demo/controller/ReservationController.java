package com.example.demo.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.exception.ReservationNotFoundException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.dto.ReservationDto;
import com.example.demo.model.dto.ScooterDto;
import com.example.demo.model.entity.Reservation;
import com.example.demo.model.entity.Reservation.PaymentStatus;
import com.example.demo.model.entity.Reservation.Status;
import com.example.demo.model.entity.User;
import com.example.demo.model.entity.User.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ReservationService;
import com.example.demo.service.ScooterService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

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

@Slf4j
@Controller
public class ReservationController {
	
	private final ScooterService scooterService;
	private final ReservationService reservationService;
	private final UserRepository userRepository;
	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
	//使用 SLF4J 和 LoggerFactory 取得日誌實例。用於 ReservationController 中進行日誌紀錄。
	
	public ReservationController(ScooterService scooterService,ReservationService reservationService,UserRepository userRepository) {
		this.scooterService = scooterService;
		this.reservationService = reservationService;
		this.userRepository = userRepository;
	}
	
	
	@PostMapping("/reservation/calculate")
    public String calculateRental(@ModelAttribute("reservation") ReservationDto reservation, @RequestParam("source") String source,Model model) {
        // 從表單中取得 startDate 和 endDate
        LocalDate startDate = reservation.getStartDate();
        LocalDate endDate = reservation.getEndDate();

        // 計算租借天數
        long rentalDays = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        
        
        // 計算租金
        double totalAmount = reservationService.calculateRentalFee(reservation.getScooterId(), startDate, endDate);

        // 將總租金與預約資訊、預約天數傳回給頁面
        model.addAttribute("rentalDays", rentalDays);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("reservation", reservation);
        ScooterDto scooter = scooterService.getScooterById(reservation.getScooterId());
        model.addAttribute("scooter", scooter);

     
        // 根據來源頁面進行重導
        if ("userUpdatePage".equals(source)) {
            return "user/user-reservation-update"; // 修改頁面
        } else if ("reservationPage".equals(source)) {
            return "user/reservation"; // 預約頁面
        }else if(source.equals(null)) {
        	return "admin/admin-reservation-update";
		}
        
     // 預設重導
        return "redirect:/home";
    }
	
	
	@PostMapping("/reservation/admin-calculate")
	public String calculateRental(@RequestParam Integer reservationId,
	                            @RequestParam Integer scooterId,
	                            @RequestParam LocalDate startDate,
	                            @RequestParam LocalDate endDate,
	                            Model model) {
	    
	    // 計算租借天數
	    long rentalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
	    
	    // 計算租金
	    double totalAmount = reservationService.calculateRentalFee(scooterId, startDate, endDate);
	    
	    // 獲取原始預約資訊
	    Reservation reservation = reservationService.findReservationById(reservationId)
	            .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
	            
	    // 創建 DTO 並設置新的日期和金額
	    ReservationDto reservationDto = new ReservationDto();
	    reservationDto.setReservationId(reservationId);
	    reservationDto.setScooterId(scooterId);
	    reservationDto.setStartDate(startDate);
	    reservationDto.setEndDate(endDate);
	    reservationDto.setStatus(reservation.getStatus());
	    reservationDto.setPaymentStatus(reservation.getPaymentStatus());
	    reservationDto.setTotalAmount(totalAmount);
	    
	    // 獲取機車資訊
	    ScooterDto scooter = scooterService.getScooterById(scooterId);
	    
	    // 添加所需資訊到 Model
	    model.addAttribute("reservation", reservationDto);
	    model.addAttribute("scooter", scooter);
	    model.addAttribute("rentalDays", rentalDays);
	    model.addAttribute("totalAmount", totalAmount);
	    
	    return "admin/admin-reservation-update";
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
                reservation.setReservationDate(LocalDate.now());
                reservation.setStatus(Status.reserved);
                reservationService.saveReservation(reservation);
            }
            
            // 清空購物車
            session.removeAttribute("cart");
            redirectAttributes.addFlashAttribute("successMessage", "結帳成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "結帳失敗：" + e.getMessage());
        }
        
        return "redirect:/home";
    }
	


	//進入預約畫面
    @GetMapping("/reservation/page/{scooterId}")
	public String showReservationPage(@PathVariable Integer scooterId,Model model) {
		ScooterDto scooter = scooterService.getScooterById(scooterId);
		 // 建立空的 ReservationForm 對象
	    ReservationDto reservation = new ReservationDto();
	    reservation.setScooterId(scooterId);
		
		model.addAttribute("scooter",scooter);
		model.addAttribute("reservation", reservation);
		return  "user/reservation";
	}
	
	
	
	 // 加入購物車方法
    @PostMapping("/cart/add")
    public String addToCart(@ModelAttribute ReservationDto reservation, RedirectAttributes redirectAttributes, Authentication authentication) {
    	//RedirectAttributes 用來在進行重定向（redirect）時傳遞暫時性的訊息或屬性給下個頁面。
        	
    	try {
    		
    		boolean isAvailable = reservationService.checkAvailability(reservation.getScooterId(), reservation.getStartDate(), reservation.getEndDate(),null);
            if (!isAvailable) {
                redirectAttributes.addFlashAttribute("errorMessage", "該日期區間已被預訂，請選擇其他日期！");
                return "redirect:/reservation/page/" + reservation.getScooterId();
            }
    		
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
    
 // 顯示一般使用者或管理員的訂單
    @GetMapping("/reservation/list")
    public String listReservations(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UserNotFoundException("User not found"));
        
        List<Reservation> reservation;

        // 若為管理員，則顯示所有訂單，否則只顯示當前使用者的訂單
        if (user.getRole() == Role.admin) {
            reservation = reservationService.findAllReservations();
        } else {
            reservation = reservationService.findReservationsByUser(user);
        }

        model.addAttribute("reservation", reservation);
        return user.getRole() == Role.admin ? "admin/admin-reservations-list" : "user/user-reservations-list";
    }
    
    
 // 刪除預定
    @GetMapping("/reservation/delete/{reservationId}")
    public String deleteReservation(@PathVariable Integer reservationId, Authentication authentication, RedirectAttributes redirectAttributes) {
        try {
            // 取得當前登入的使用者
            String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                                      .orElseThrow(() -> new UserNotFoundException("User not found"));

            // 根據預定 ID 查找該預定
            Reservation reservation = reservationService.findReservationById(reservationId)
                                      .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

            // 確認使用者是否為管理員或為該預定的擁有者            
            if (user.getRole() == Role.admin || reservation.getUser().getUserId().equals(user.getUserId())) {
                // 刪除預定
                reservationService.deleteReservation(reservationId);
                redirectAttributes.addFlashAttribute("successMessage", "預定成功刪除！");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "您無權刪除此預定！");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "刪除預定時發生錯誤：" + e.getMessage());
        }

        return "redirect:/reservation/list"; // 返回到預定列表頁面
    }
    
    
    //顯示修改訂單頁面
    @GetMapping("/reservation/update-page/{reservationId}")
    public String showUpdateReservationPage(@PathVariable Integer reservationId, Model model, Authentication authentication) {
        // 模擬從資料庫獲取訂單資料
    	
    	
    	Reservation reservationEntity = reservationService.findReservationById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));

     // 獲取車輛資訊
        ScooterDto scooter = scooterService.getScooterById(reservationEntity.getScooter().getScooterId());
        
     // 計算試算金額
        double totalAmount = reservationEntity.getTotalAmount();
        long betweenDays = ChronoUnit.DAYS.between(reservationEntity.getStartDate(), reservationEntity.getEndDate()) + 1;
       
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UserNotFoundException("User not found"));

        
        
        ReservationDto reservation = new ReservationDto();
        reservation.setReservationId(reservationEntity.getReservationId());
        reservation.setUserId(reservationEntity.getUser().getUserId());  // 手動設定 UserId
        reservation.setScooterId(reservationEntity.getScooter().getScooterId());  // 手動設定 ScooterId
        reservation.setReservationDate(reservationEntity.getReservationDate());
        reservation.setStartDate(reservationEntity.getStartDate());
        reservation.setEndDate(reservationEntity.getEndDate());
        reservation.setStatus(reservationEntity.getStatus());
        reservation.setPaymentStatus(reservationEntity.getPaymentStatus());
        reservation.setTotalAmount(reservationEntity.getTotalAmount());
        
        
        model.addAttribute("reservation", reservation);
        model.addAttribute("scooter", scooter);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("rentalDays",betweenDays );
        
        return user.getRole() == Role.admin ? "admin/admin-reservation-update" : "user/user-reservation-update";
        
        
    }
    
    
    @PostMapping("/reservation/update")
    public String updateReservation(@ModelAttribute ReservationDto reservationDto, RedirectAttributes redirectAttributes, Authentication authentication) {
    	
    	try {
    		String username = authentication.getName();
            User user = userRepository.findByUsername(username)
                                      .orElseThrow(() -> new UserNotFoundException("User not found"));
    		
    		boolean isAvailable = reservationService.checkAvailability(reservationDto.getScooterId(), reservationDto.getStartDate(), reservationDto.getEndDate(),reservationDto.getReservationId());
            if (!isAvailable) {
                redirectAttributes.addFlashAttribute("errorMessage", "該日期區間已被預訂，請選擇其他日期！");
//                return user.getRole() == Role.admin ? "redirect:/admin/admin-reservation-update" : "redirect:/user/user-reservation-update";
                return "redirect:/reservation/update-page/"+reservationDto.getReservationId();
            }

	
            reservationService.updateReservation(reservationDto);
            redirectAttributes.addFlashAttribute("successMessage", "預約更新成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "更新失敗：" + e.getMessage());
            log.error("Update failed", e);  // 添加這行來記錄錯誤日誌
        }
        return "redirect:/reservation/list";
    }



}
