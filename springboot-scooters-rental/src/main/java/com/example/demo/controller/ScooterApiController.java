package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.ScooterDto;
import com.example.demo.model.entity.Scooter;
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
	
	
	
	@PostMapping("/scooters")
	public ResponseEntity<Scooter> createScooter(@RequestBody ScooterDto scooterDto){
		Scooter savedScooter = scooterService.addScooter(scooterDto);
		return new ResponseEntity<>(savedScooter,HttpStatus.CREATED);
	}
	
	
	@PutMapping("/scooters/{id}")
	public ResponseEntity<Void> updateScooter(@PathVariable("id") Integer id,@RequestBody ScooterDto scooterDto){
		System.out.println("Updating scooter with id: " + id);
		scooterService.updateScooter(id, scooterDto);
		return ResponseEntity.ok().build();
		
	}
	
	@DeleteMapping("/scooters/{id}")
	public ResponseEntity<Void>deleteScooter(@PathVariable("id") Integer id){
		System.out.println("Deleting scooter with id: " + id);
        scooterService.deleteScooter(id);
        return ResponseEntity.noContent().build();
	}
	
	
	
	
	@GetMapping("/scooters")
	public List<ScooterDto> listScooters(
			@RequestParam(required = false) String type,
            @RequestParam(required = false) String cc,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String dailyRate){
		 if (type != null || cc != null || status != null || dailyRate != null) {
             System.out.println(String.format("Filtering with: type=%s, cc=%s, status=%s, dailyRate=%s", type, cc, status, dailyRate));
             return scooterService.filterScooters(type, cc, status, dailyRate);
         } else {
             // 如果沒有篩選條件，回傳所有
             System.out.println("Fetching all scooters.");
             return scooterService.getAllScooters();
         }
	}
}
