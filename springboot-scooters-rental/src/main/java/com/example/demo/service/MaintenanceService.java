package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.example.demo.model.entity.PartMaintenance;
import com.example.demo.model.entity.ScooterPart;

public interface MaintenanceService {


	public List<ScooterPart> getAllScooterParts(Integer scooterId);

	public Map<String, List<PartMaintenance>> getMaintenanceHistory(Integer scooterId);

 
	public void reportPartDamage(Integer partId, String note, String adminUsername);


	public void repairPart(Integer partId, String note, String adminUsername);


	public void replacePart(Integer oldPartId, String note, String adminUsername);


	public ScooterPart getPartById(Integer partId);
    
    public List<PartMaintenance> searchMaintenance(String partId, String userId, String actionDate, String actionType);
    
    public List<PartMaintenance> getAllPartMaintenance();
}

