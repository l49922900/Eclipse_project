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
    //getMaintenancePage:根據機車ID顯示相應的維修頁面
    //@PathVariable:將 URL 中的 scooterId 參數綁定到這個方法的 scooterId 參數上。
    //另一個與@PathVariable相似的方法:@RequestParam，但他是從 URL 查詢字串中提取變數	
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
            @PathVariable Integer partId, //將 URL 中的 scooterId 參數綁定到這個方法的 scooterId 參數上。
            @ModelAttribute MaintenanceActionForm form,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
    /*
    RedirectAttributes:用於 重定向後傳遞一次性訊息，例如成功或錯誤提示。
	Flash Attributes 在重定向後的第一個請求中可被取得，但之後就會被自動移除。
	可以確保 訊息不會出現在網址列 上，也不會因重新整理頁面而重複顯示。	
     */
        try {
            ScooterPart part = maintenanceService.getPartById(partId);
            maintenanceService.reportPartDamage(partId, form.getNote(),authentication.getName() );
            redirectAttributes.addFlashAttribute("success", "零件損壞報修成功");
     //將資料加到 Flash Attributes 中，這些資料只會在重定向後存在一次，然後就被移除。
            
            return "redirect:/admin/maintenance/scooter/" + part.getScooter().getScooterId();
     /*
    使用 redirect: 關鍵字進行重定向，會導致瀏覽器發出新的 GET 請求。
	此時，剛剛設置的 Flash Attributes ("success") 就會帶到重定向後的新請求中。       
      */
            
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
            //重導到該機車的維修頁面
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
        //將 ActionType 列舉類別（enum）的所有值傳遞給前端。在 HTML 頁面中可以使用這些值，例如做下拉選單。

            
            boolean isReset = "reset".equals(partId);
       //檢查 partId 是否為 "reset"，用於判斷是否重置搜尋條件。     
            
            if (isReset) {
       //如果 partId 是 "reset"，則重新導向（redirect）到 /admin/maintenance/search 頁面。用於清除搜尋條件，返回初始頁面。         
                return "redirect:/admin/maintenance/search";
            }
            
            
            boolean hasSearchParams = Stream.of(partId, userId, actionDate, actionType)
                    .anyMatch(param -> param != null && !param.isEmpty());
            /*
            用途:判斷使用者是否進行了搜尋操作。
            使用 Stream.of() 將所有查詢參數放入 Stream 中。
			用 anyMatch() 檢查是否至少有一個參數不為空或不為 null。
             */
            
            
            List<PartMaintenance> maintenances;
            
            if (hasSearchParams) {
            //如果有搜尋參數，則呼叫 maintenanceService.searchMaintenance(...) 方法進行條件查詢。    
                maintenances = maintenanceService.searchMaintenance(partId, userId, actionDate, actionType);
            } else {
                
                maintenances = maintenanceService.getAllPartMaintenance();
            //如果沒有搜尋參數，則呼叫 maintenanceService.getAllPartMaintenance() 取得所有維修紀錄。
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
