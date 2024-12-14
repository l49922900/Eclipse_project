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

import com.example.demo.exception.ScooterException;
import com.example.demo.model.dto.ScooterDto;
import com.example.demo.service.ScooterService;
import com.example.demo.service.validation.AdvancedValidation;
import com.example.demo.service.validation.BasicValidation;

@Controller
//@RequestMapping(value = {"/scooter", "/scooters"})
@RequestMapping(value = {"/admin/scooter", "/admin/scooters"})
/*
@RequestMapping(value = {"/scooter", "/scooters"}):

	定義一個基礎路徑 (base path)。它的作用是將這個控制器中的所有路由 (routes) 都統一加上一個前綴。
	
	統一管理路由前綴:這種方式可以幫助集中管理某一類相關資源的路徑。例如，
	所有跟 Scooter 相關的 API 都以 /scooter 或 /scooters 開頭，這樣可以讓路由結構清晰且語義化。
	
	
	避免重複寫路徑前綴:在控制器內，如果每個方法的路徑都需要手動加上 /scooter，會比較繁瑣且容易出錯。
	透過 @RequestMapping，可以將前綴提取出來，減少重複工作。
 */




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
    
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        // 為 Integer 類型註冊自訂的 CustomNumberEditor
//        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, null, true));
///*
//@InitBinder: 這個註解用來對 WebDataBinder 進行初始化操作。
//WebDataBinder 是一個資料綁定器，它負責將表單中的輸入值轉換為對應的對象屬性。
//
//
//CustomNumberEditor(Integer.class, null, true): 這個轉換器允許將空字串轉換為 null，
//並且處理 Integer 型別的欄位。
// */
//    }
    
    
    @GetMapping
    public String getScooters(Model model, @ModelAttribute ScooterDto scooterDto) {
        // 查詢所有機車，顯示到 scooter/scooter 頁面
    	
/*
Model:
Spring MVC 中的一個介面，主要負責在控制器 (Controller) 與視圖 (View) 之間傳遞數據。
在控制器中，我們可以將數據加入到 Model 中，然後在視圖（通常是 JSP 或 Thymeleaf）中進行顯示。

@ModelAttribute:
方法參數標註，自動將 HTTP 請求中的參數綁定到一個物件，並將該物件作為控制器方法的參數。例如表單提交時，表單欄位自動對應到物件的屬性。
 */
        List<ScooterDto> scooterDtos = scooterService.getAllScooters();
        model.addAttribute("scooterDtos", scooterDtos);
        return "admin/scooter";
    }
    
    
    
    
    
    
    @PostMapping
    public String addScooter( @Validated({BasicValidation.class, AdvancedValidation.class})  @ModelAttribute ScooterDto scooterDto, BindingResult bindingResult, Model model) {
/*
@Validated 與 @Valid 區別：@Validated 支援分組驗證，而 @Valid 則不支援。因此，在需要使用分組驗證的地方，必須使用 @Validated。

分組驗證的作用：當你在 DTO (資料傳輸物件) 上定義不同的驗證規則時，可以通過分組來靈活控制不同的驗證場景。
 */
    	// 新增機車資料
        if (bindingResult.hasErrors()) {
            model.addAttribute("scooterDtos", scooterService.getAllScooters());
            return "admin/scooter";
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
/*
@PathVariable:
用於從 URL 路徑中擷取變數，並將其綁定到方法參數上。常用於 RESTful API 路徑設計，例如 /scooters/{scooterId} 中的 {scooterId}。    	
 */
    	
        scooterService.deleteScooter(scooterId);
        return "redirect:/scooters";
    }
    
    @GetMapping("/{scooterId}")
    public String getScooter(@PathVariable Integer scooterId, Model model) {
        // 查詢單筆機車資料，顯示在 scooter/scooter_update 頁面
        ScooterDto scooterDto = scooterService.getScooterById(scooterId);
        model.addAttribute("scooterDto", scooterDto);
        return "admin/scooter/scooter-update";
    }
    
    @PostMapping("/update/{scooterId}")
    public String updateScooter(@PathVariable Integer scooterId, @Validated({BasicValidation.class, AdvancedValidation.class}) @ModelAttribute ScooterDto scooterDto, BindingResult bindingResult, Model model) {
        // 更新機車資料
        if (bindingResult.hasErrors()) {
            model.addAttribute("scooterDto", scooterDto);
            return "admin/scooter/scooter-update";
        }
        
        
        scooterService.updateScooter(scooterId, scooterDto);
        return "redirect:/scooters";
    }
    
    
    @GetMapping("/admin-filter-menu")
    public String getAdminFilterMenu() {
        // 返回對應的 Thymeleaf 頁面名稱
        return "admin/admin-filter-menu";
    }
    
    @GetMapping("/filter")
    public String filterScooters(String type,String cc,String status,String dailyRate,Model modelAttr) {

            List<ScooterDto> filteredScooters = scooterService.filterScooters(type, cc, status, dailyRate);
            modelAttr.addAttribute("scooters", filteredScooters);
            return "admin/admin-filter-menu";

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
    
    @ExceptionHandler({ScooterException.class})
    public String handleScooterException(ScooterException e, Model model) {
        // 處理 ScooterException 類型異常，顯示錯誤訊息，並傳到error.html的<p th:text="${message}"></p>顯示
        model.addAttribute("message", e.getMessage());
        return "admin/error";
    }
    
    /*
    維修頁面:
    
		功能:選擇維修日期
		功能:維修資訊(如修了什麼地方等)填寫

     */
    
}
