package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.dto.ScooterDto;
import com.example.demo.model.entity.PartMaintenance;
import com.example.demo.model.entity.Scooter.Status;
import com.example.demo.model.entity.ScooterPart;
import com.example.demo.model.entity.User;
import com.example.demo.repository.PartMaintenanceRepository;
import com.example.demo.repository.ScooterPartRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MaintenanceService;
import com.example.demo.service.ScooterService;

@Service
@Transactional
public class MaintenanceServiceImpl implements MaintenanceService {
    
    @Autowired
    private ScooterPartRepository scooterPartRepository;
    
    @Autowired
    private PartMaintenanceRepository partMaintenanceRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ScooterService scooterService;
    
    @Override
    public List<ScooterPart> getAllScooterParts(Integer scooterId) {
        return scooterPartRepository.findAllByScooter_ScooterId(scooterId);
    }
    
    
    @Override
    public List<PartMaintenance> getAllPartMaintenance() {
        return partMaintenanceRepository.findAll();
    }
    
    @Override
    public Map<String, List<PartMaintenance>> getMaintenanceHistory(Integer scooterId) {
    //查詢特定機車 (Scooter) 所有零件的維修記錄，並根據零件名稱分組。
    //Map:鍵為零件名稱 (String)，值為該零件的維修記錄列表 (List<PartMaintenance>)。
    	
        List<ScooterPart> allParts = scooterPartRepository.findAllByScooter_ScooterId(scooterId);
        return allParts.stream()
            .flatMap(part -> part.getMaintenanceRecords().stream())
            
     //展平 (Flattening) :用於將嵌套結構的數據（如多個集合的集合）轉換為一個扁平結構
            
            .collect(Collectors.groupingBy(
                record -> record.getScooterPart().getPartName()));
    /*
    將排序後的維修記錄進行分組：
		鍵 (key)：每條記錄所屬零件的名稱 (record.getScooterPart().getPartName)。
		值 (value)：該零件的維修記錄列表。
     */
    }
    
    @Override
    public void reportPartDamage(Integer partId, String note, String adminUsername) {
        ScooterPart part = getPartById(partId);
        User admin = userRepository.findByUsername(adminUsername)
            .orElseThrow(() -> new RuntimeException("管理員不存在"));
        
        if (part.getStatus() != ScooterPart.PartStatus.normal) {
            throw new RuntimeException("零件已經處於損壞狀態");
        }
        
        part.setStatus(ScooterPart.PartStatus.damaged);
        part.setLastUpdate(LocalDate.now());
        part.setPartNote("零件損壞: "+note);
        
        PartMaintenance record = new PartMaintenance();
        record.setScooterPart(part);
        record.setAdmin(admin);
        record.setActionType(PartMaintenance.ActionType.report);
        record.setActionDate(LocalDate.now());
        record.setNotes("零件損壞: " + note);
        
        part.getMaintenanceRecords().add(record);
        scooterPartRepository.save(part);
        
        updateScooterStatus(part.getScooter().getScooterId());
    }
    
    @Override
    public void repairPart(Integer partId, String note, String adminUsername) {
        ScooterPart part = getPartById(partId);
        User admin = userRepository.findByUsername(adminUsername)
            .orElseThrow(() -> new RuntimeException("管理員不存在"));
        
        if (part.getStatus() != ScooterPart.PartStatus.damaged) {
            throw new RuntimeException("零件未處於損壞狀態");
        }
        
        part.setStatus(ScooterPart.PartStatus.normal);
        part.setLastUpdate(LocalDate.now());
        part.setPartNote("零件已維修: " + note);
        
        PartMaintenance record = new PartMaintenance();
        record.setScooterPart(part);
        record.setAdmin(admin);
        record.setActionType(PartMaintenance.ActionType.repair);
        record.setActionDate(LocalDate.now());
        record.setNotes(note);
        
        part.getMaintenanceRecords().add(record);
        scooterPartRepository.save(part);
        
        updateScooterStatus(part.getScooter().getScooterId());
    }
    
    @Override
    public void replacePart(Integer oldPartId, String note, String adminUsername) {
        ScooterPart part = getPartById(oldPartId);
        User admin = userRepository.findByUsername(adminUsername)
            .orElseThrow(() -> new RuntimeException("管理員不存在"));
        
        if (part.getStatus() != ScooterPart.PartStatus.damaged) {
            throw new RuntimeException("零件未處於損壞狀態");
        }
        
        
        part.setStatus(ScooterPart.PartStatus.normal);
        part.setLastUpdate(LocalDate.now());
        part.setPartNote("零件已更換: " + note);
        
        // 記錄更換紀錄
        PartMaintenance record = new PartMaintenance();
        record.setScooterPart(part);
        record.setAdmin(admin);
        record.setActionType(PartMaintenance.ActionType.replace);
        record.setActionDate(LocalDate.now());
        record.setNotes("零件更換: " + note);
        
        part.getMaintenanceRecords().add(record);
        
        scooterPartRepository.save(part);
        
        updateScooterStatus(part.getScooter().getScooterId());
    }
    
    private void updateScooterStatus(Integer scooterId) {
        List<ScooterPart> AllParts = getAllScooterParts(scooterId);
        boolean allPartsNormal = AllParts.stream()
            .allMatch(part -> part.getStatus() == ScooterPart.PartStatus.normal);
            
        ScooterDto scooterDto = scooterService.getScooterById(scooterId);
        scooterDto.setStatus(allPartsNormal ? Status.available : Status.maintenance);
        scooterService.updateScooter(scooterId, scooterDto);
    }
    
    @Override
    public ScooterPart getPartById(Integer partId) {
        return scooterPartRepository.findById(partId)
            .orElseThrow(() -> new RuntimeException("零件不存在"));
    }
    
    @Override
    public List<PartMaintenance> searchMaintenance(String partId, String userId, String actionDate, String actionType) {
        return partMaintenanceRepository.findAll().stream()
            .filter(maintenance -> partId == null || partId.isEmpty() || 
                   maintenance.getScooterPart().getPartId().equals(Integer.parseInt(partId)))
            .filter(maintenance -> userId == null || userId.isEmpty() || 
                   maintenance.getAdmin().getUserId().equals(Integer.parseInt(userId)))
            .filter(maintenance -> actionDate == null || actionDate.isEmpty() || 
                   maintenance.getActionDate().equals(LocalDate.parse(actionDate)))
            .filter(maintenance -> actionType == null || actionType.isEmpty() || 
                   maintenance.getActionType().name().equals(actionType))
            .collect(Collectors.toList());
    }
}
