/*
教學去看RoomController
 */

package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.exception.ScooterAlreadyExistsException;
import com.example.demo.exception.ScooterException;
import com.example.demo.model.dto.ScooterDto;
import com.example.demo.model.entity.Scooter;
import com.example.demo.model.entity.Scooter.Status;
import com.example.demo.service.ScooterService;
import com.example.demo.validation.AdvancedValidation;
import com.example.demo.validation.BasicValidation;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = {"/scooter", "/scooters"})
/*
為什麼要分成"/scooter", "/scooters":

RESTful 設計慣例：在 RESTful API 設計中，複數（/scooters）通常用來表示資源的集合（如查詢所有機車），
而單數（/scooter）用來表示對單一資源的操作（如新增或修改一個機車）。這種設計能清楚區分資源的不同操作。

使用者體驗：提供單數與複數兩種路徑可以滿足不同使用者的直覺使用習慣，增強使用便利性。

可擴展性：儘管目前 /scooter 和 /scooters 共用同一個控制器方法，未來可以更容易分別處理單一資源與資源集合的不同需求，
提供更大的靈活性。
 */
public class ScooterController {
    
    @Autowired
    private ScooterService scooterService;
    
    @GetMapping
    public String getScooters(Model model, @ModelAttribute ScooterDto scooterDto) {
        // 查詢所有機車，顯示到 scooter/scooter 頁面
        List<ScooterDto> scooterDtos = scooterService.getAllScooters();
        model.addAttribute("scooterDtos", scooterDtos);
        return "scooter";
    }
    
    @PostMapping
    public String addScooter( @Validated({BasicValidation.class, AdvancedValidation.class}) @ModelAttribute ScooterDto scooterDto, BindingResult bindingResult, Model model) {
        // 新增機車資料
        if (bindingResult.hasErrors()) {
            model.addAttribute("scooterDtos", scooterService.getAllScooters());
            return "scooter";
        }
        scooterService.addScooter(scooterDto);
        return "redirect:/scooters";
  /*
  return "redirect:/scooters"; 表示當完成一個請求後，將客戶端重定向（redirect）到 /scooters 路徑。
  這是為了避免在表單提交後重新整理頁面時重複提交資料，這是一種常見的 Post/Redirect/Get (PRG) 設計模式。  
   */
    }
    
    @GetMapping("/delete/{scooterId}")
    public String deleteScooter(@PathVariable Integer scooterId) {
        // 刪除指定 scooterId 的機車
        scooterService.deleteScooter(scooterId);
        return "redirect:/scooters";
    }
    
    @GetMapping("/{scooterId}")
    public String getScooter(@PathVariable Integer scooterId, Model model) {
        // 查詢單筆機車資料，顯示在 scooter/scooter_update 頁面
        ScooterDto scooterDto = scooterService.getScooterById(scooterId);
        model.addAttribute("scooterDto", scooterDto);
        return "scooter/scooter_update";
    }
    
    @PostMapping("/update/{scooterId}")
    public String updateScooter(@PathVariable Integer scooterId, @Validated({BasicValidation.class, AdvancedValidation.class}) @ModelAttribute ScooterDto scooterDto, BindingResult bindingResult, Model model) {
        // 更新機車資料
        if (bindingResult.hasErrors()) {
            model.addAttribute("scooterDto", scooterDto);
            return "scooter/scooter_update";
        }
        
        
        scooterService.updateScooter(scooterId, scooterDto);
        return "redirect:/scooters";
    }

    
    @ExceptionHandler({ScooterException.class})
    public String handleScooterException(ScooterException e, Model model) {
        // 處理 ScooterException 類型異常，顯示錯誤訊息，並傳到error.html的<p th:text="${message}"></p>顯示
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
