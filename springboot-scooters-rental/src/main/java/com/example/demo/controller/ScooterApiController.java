package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.ScooterDto;
import com.example.demo.service.ScooterService;

@RestController
@RequestMapping("/api")
public class ScooterApiController {

	
	@Autowired
	private ScooterService scooterService;
	
	
	@GetMapping("/scooters")
    public ResponseEntity<List<ScooterDto>> getAllScooters() {
        // 從 Service 獲取所有 Scooter 實體
        List<ScooterDto> scooterDtos = scooterService.getAllScooters();
        
        // 為了方便 debug，在後端印出 log
        System.out.println("獲取所有機車: " + scooterDtos.size());
        
        // 使用 ResponseEntity.ok() 來包裝我們的資料，這會自動設定 HTTP 狀態碼為 200 OK
        return ResponseEntity.ok(scooterDtos);
    }
}
