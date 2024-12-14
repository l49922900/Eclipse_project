package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.model.dto.ScooterDto;
import com.example.demo.model.entity.Reservation;
import com.example.demo.model.entity.Scooter.Status;

public interface ScooterService {
	public List<ScooterDto> getAllScooters(); // 查詢所有機車
    public ScooterDto getScooterById(Integer scooterId); // 查詢單筆機車
    public void addScooter(ScooterDto scooterDto); // 新增機車
    public void addScooter(Integer scooterId, String licensePlate, String model, Integer cc, String type, Status status,
            Double dailyRate, String conditionNote, LocalDate lastMaintenanceDate);
    public void updateScooter(Integer scooterId, ScooterDto scooterDto); // 修改機車
    public void updateScooter(Integer scooterId, String licensePlate, String model, Integer cc, String type, Status status,
            Double dailyRate, String conditionNote, LocalDate lastMaintenanceDate); // 修改機車
    public void deleteScooter(Integer scooterId); // 刪除機車
    public List<ScooterDto> findScootersByCc(Integer cc);
    public List<ScooterDto> filterScooters(String type, String cc, String status, String dailyRate);
    public List<ScooterDto> filterScooters(String type, String cc, String dailyRate);
    
    
}
