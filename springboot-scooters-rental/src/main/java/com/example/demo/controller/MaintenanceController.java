package com.example.demo.controller;


import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.dto.MaintenanceActionForm;
import com.example.demo.model.dto.ScooterDto;
import com.example.demo.model.entity.PartMaintenance;
import com.example.demo.model.entity.PartMaintenance.ActionType;
import com.example.demo.model.entity.ScooterPart;
import com.example.demo.service.MaintenanceService;
import com.example.demo.service.ScooterService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admin/maintenance")
public class MaintenanceController {
    
    @Autowired
    private ScooterService scooterService;
    
    @Autowired
    private MaintenanceService maintenanceService;
    
    @GetMapping("/scooter/{scooterId}")
    public String getMaintenancePage(@PathVariable Integer scooterId, Model model) {
        try {
            ScooterDto scooter = scooterService.getScooterById(scooterId);
            List<ScooterPart> allParts = maintenanceService.getAllScooterParts(scooterId);
            
            model.addAttribute("scooter", scooter);
            model.addAttribute("parts", allParts);
            model.addAttribute("maintenanceForm", new MaintenanceActionForm());
            
            return "admin/maintenance";
        } catch (Exception e) {
            log.error("獲取維修頁面失敗", e);
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    
    @PostMapping("/scooter/parts/{partId}/report")
    public String reportDamage(
            @PathVariable Integer partId,
            @ModelAttribute MaintenanceActionForm form,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            ScooterPart part = maintenanceService.getPartById(partId);
            maintenanceService.reportPartDamage(partId, form.getNote(),authentication.getName() );
            redirectAttributes.addFlashAttribute("success", "零件損壞報修成功");
            return "redirect:/admin/maintenance/scooter/" + part.getScooter().getScooterId();
        } catch (Exception e) {
            log.error("報修失敗", e);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/error";
        }
    }
    
    @PostMapping("/scooter/parts/{partId}/repair")
    public String repairPart(
            @PathVariable Integer partId,
            @ModelAttribute MaintenanceActionForm form,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            ScooterPart part = maintenanceService.getPartById(partId);
            maintenanceService.repairPart(partId, form.getNote(), authentication.getName());
            redirectAttributes.addFlashAttribute("success", "零件維修成功");
            return "redirect:/admin/maintenance/scooter/" + part.getScooter().getScooterId();
        } catch (Exception e) {
            log.error("維修失敗", e);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/error";
        }
    }
    
    @PostMapping("/scooter/parts/{partId}/replace")
    public String replacePart(
            @PathVariable Integer partId,
            @ModelAttribute MaintenanceActionForm form,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            ScooterPart part = maintenanceService.getPartById(partId);
            maintenanceService.replacePart(partId, form.getNote(), authentication.getName());
            redirectAttributes.addFlashAttribute("success", "零件更換成功");
            return "redirect:/admin/maintenance/scooter/" + part.getScooter().getScooterId();
        } catch (Exception e) {
            log.error("更換零件失敗", e);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/error";
        }
    }
    
    @GetMapping("/search")
    public String searchMaintenanceList(
            @RequestParam(required = false) String partId,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String actionDate,
            @RequestParam(required = false) String actionType,
            Model model) {
        try {
            
            model.addAttribute("actionTypes", ActionType.values());
            
            
            boolean isReset = "reset".equals(partId);
            
            if (isReset) {
                
                return "redirect:/admin/maintenance/search";
            }
            
            
            boolean hasSearchParams = Stream.of(partId, userId, actionDate, actionType)
                    .anyMatch(param -> param != null && !param.isEmpty());
            
            List<PartMaintenance> maintenances;
            if (hasSearchParams) {
                
                maintenances = maintenanceService.searchMaintenance(partId, userId, actionDate, actionType);
            } else {
                
                maintenances = maintenanceService.getAllPartMaintenance();
            }
            
            model.addAttribute("maintenances", maintenances);
            return "admin/maintenance-list";
            
        } catch (Exception e) {
            log.error("查詢維修紀錄失敗", e);
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
