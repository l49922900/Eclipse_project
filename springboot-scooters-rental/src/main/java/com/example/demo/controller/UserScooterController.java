package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.dto.ScooterDto;
import com.example.demo.service.ScooterService;
	

@Controller
@RequestMapping(value = {"/scooter", "/scooters"})
public class UserScooterController {
	
	
	private final ScooterService scooterService;
	
	public UserScooterController(ScooterService scooterService) {
		this.scooterService = scooterService;
	}
	
    @GetMapping("/home")
    public String getHome() {
        // 返回對應的 Thymeleaf 頁面名稱
        return "user/home";
    }
    
    
    @GetMapping("/user-filter")
    public String filterScooters(String type,String cc,String dailyRate,Model modelAttr) {

            List<ScooterDto> filteredScooters = scooterService.filterScooters(type, cc, dailyRate);
            modelAttr.addAttribute("scooters", filteredScooters);
            return "user/home";
    }

}
