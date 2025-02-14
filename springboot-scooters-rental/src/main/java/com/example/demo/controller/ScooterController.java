package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.ScooterException;
import com.example.demo.model.dto.ScooterDto;
import com.example.demo.model.entity.Scooter;
import com.example.demo.service.FileService;
import com.example.demo.service.ScooterService;
import com.example.demo.service.validation.AdvancedValidation;
import com.example.demo.service.validation.BasicValidation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = { "/admin/scooter", "/admin/scooters" })
/*
 * @RequestMapping(value = {"/admin/scooter", "/admin/scooters"}):
 * 
 * 定義一個基礎路徑 (base path)。它的作用是將這個控制器中的所有路由 (routes) 都統一加上一個前綴。
 * 
 * 統一管理路由前綴:這種方式可以幫助集中管理某一類相關資源的路徑。例如， 所有跟 Scooter 相關的 API 都以 /scooter 或
 * /scooters 開頭，這樣可以讓路由結構清晰且語義化。
 * 
 * 
 * 避免重複寫路徑前綴:在控制器內，如果每個方法的路徑都需要手動加上 /scooter，會比較繁瑣且容易出錯。
 * 透過 @RequestMapping，可以將前綴提取出來，減少重複工作。
 */

/*
 * 為什麼要分成"/scooter", "/scooters":
 * 
 * RESTful 設計慣例：在 RESTful API 設計中，複數（/scooters）通常用來表示資源的集合（如查詢所有機車），
 * 而單數（/scooter）用來表示對單一資源的操作（如新增或修改一個機車）。這種設計能清楚區分資源的不同操作。
 * 
 * 使用者體驗：提供單數與複數兩種路徑可以滿足不同使用者的直覺使用習慣，增強使用便利性。
 * 
 * 可擴展性：儘管目前 /scooter 和 /scooters 共用同一個控制器方法，未來可以更容易分別處理單一資源與資源集合的不同需求，
 * 提供更大的靈活性。
 */
public class ScooterController {

	
	@Autowired
	private FileService fileService;

	@Autowired
	private ScooterService scooterService;

	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

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
		 * Model: Spring MVC 中的一個介面，主要負責在控制器 (Controller) 與視圖 (View) 之間傳遞數據。
		 * 在控制器中，我們可以將數據加入到 Model 中，然後在視圖（通常是 JSP 或 Thymeleaf）中進行顯示。
		 * 
		 * @ModelAttribute: 方法參數標註，自動將 HTTP
		 * 請求中的參數綁定到一個物件，並將該物件作為控制器方法的參數。例如表單提交時，表單欄位自動對應到物件的屬性。
		 */
		List<ScooterDto> scooterDtos = scooterService.getAllScooters();
		model.addAttribute("scooterDto", new ScooterDto()); // 用於新增/編輯車輛
		model.addAttribute("scooterDtos", scooterDtos);
		return "admin/scooter";
	}

	@PostMapping
	public String addScooter(
			@Validated({ BasicValidation.class, AdvancedValidation.class }) @ModelAttribute ScooterDto scooterDto,
			@RequestParam("imageFile") MultipartFile imageFile, BindingResult bindingResult, Model model) {
		/*
		 * @Validated 與 @Valid 區別：@Validated 支援分組驗證，而 @Valid
		 * 則不支援。因此，在需要使用分組驗證的地方，必須使用 @Validated。
		 * 
		 * 分組驗證的作用：當你在 DTO (資料傳輸物件) 上定義不同的驗證規則時，可以通過分組來靈活控制不同的驗證場景。
		 */
		// 新增機車資料
		if (bindingResult.hasErrors()) {
			model.addAttribute("scooterDtos", scooterService.getAllScooters());
			return "admin/scooter";
		}
		try {
			String imagePath = fileService.saveImage(imageFile);
			scooterDto.setImagePath(imagePath);
		} catch (IOException e) {
			model.addAttribute("error", "圖片上傳失敗");
			return "admin/scooter";
		}
		Scooter savedScooter = scooterService.addScooter(scooterDto);
		scooterService.initializationAddPart(savedScooter);
		return "redirect:/admin/scooters";

		/*
		 * return "redirect:/scooters"; 表示當完成一個請求後，將客戶端重定向（redirect）到 /scooters 路徑。
		 * 這是為了避免在表單提交後重新整理頁面時重複提交資料，這是一種常見的 Post/Redirect/Get (PRG) 設計模式。
		 */
	}
	
	@GetMapping("/plus")
	public String Plus1(@RequestParam(required = false)Integer number,Model model){
		number = number + 1;
		model.addAttribute("newString",number);
		return "redirect:/admin/scooters/number";
	}

	@GetMapping("/delete/{scooterId}")
	public String deleteScooter(@PathVariable Integer scooterId) {
		// 刪除指定 scooterId 的機車
		/*
		 * @PathVariable: 用於從 URL 路徑中擷取變數，並將其綁定到方法參數上。常用於 RESTful API 路徑設計，例如
		 * /scooters/{scooterId} 中的 {scooterId}。
		 */

		scooterService.deleteScooter(scooterId);
		return "redirect:/admin/scooters";
	}

	@GetMapping("/{scooterId}")
	public String getScooter(@PathVariable Integer scooterId, Model model) {
		// 查詢單筆機車資料，顯示在 scooter/scooter_update 頁面
		ScooterDto scooterDto = scooterService.getScooterById(scooterId);
		model.addAttribute("scooterDto", scooterDto);
		return "admin/scooter/scooter-update";
	}

//    @PostMapping("/update/{scooterId}")
//    public String updateScooter(@PathVariable Integer scooterId, @Validated({BasicValidation.class, AdvancedValidation.class}) @ModelAttribute ScooterDto scooterDto,@RequestParam(value = "imageFile", required = false) MultipartFile imageFile, BindingResult bindingResult, Model model) {
//        // 更新機車資料
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("scooterDto", scooterDto);
//            return "admin/scooter";
//        }
//        
//        try {
//            if (imageFile != null && !imageFile.isEmpty()) {
//                // 先刪除舊圖片
//                if (scooterDto.getImagePath() != null) {
//                    fileService.deleteImage(scooterDto.getImagePath());
//                }
//                // 保存新圖片
//                String imagePath = fileService.saveImage(imageFile);
//                scooterDto.setImagePath(imagePath);
//            }
//        } catch (IOException e) {
//            model.addAttribute("error", "圖片上傳失敗");
//            return "admin/scooter";
//        }
//        
//        
//        scooterService.updateScooter(scooterId, scooterDto);
//        return "redirect:/admin/scooters";
//    }

	@PostMapping("/update/{scooterId}")
	public String updateScooter(@PathVariable Integer scooterId,
			@Validated({ BasicValidation.class, AdvancedValidation.class }) @ModelAttribute ScooterDto scooterDto,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile, BindingResult bindingResult,
			Model model) {
		// 更新機車資料
		if (bindingResult.hasErrors()) {
			model.addAttribute("scooterDto", scooterDto);
			return "admin/scooter";
		}

		try {
			if (imageFile != null && !imageFile.isEmpty()) {
				// 先刪除舊圖片
				if (scooterDto.getImagePath() != null) {
					fileService.deleteImage(scooterDto.getImagePath());
				}
				// 保存新圖片
				String imagePath = fileService.saveImage(imageFile);
				scooterDto.setImagePath(imagePath);

				log.info("New image path: " + imagePath);
			}

			scooterService.updateScooter(scooterId, scooterDto);
			return "redirect:/admin/scooters";
		} catch (IOException e) {
			model.addAttribute("error", "圖片上傳失敗");
			return "admin/scooter";
		}

	}

	// 篩選車輛
	@GetMapping("/filter")
	public String filterScooters(@RequestParam(required = false) String type, @RequestParam(required = false) String cc,
			@RequestParam(required = false) String status, @RequestParam(required = false) String dailyRate,
			Model model) {

		List<ScooterDto> filteredScooters = scooterService.filterScooters(type, cc, status, dailyRate);
		model.addAttribute("scooterDtos", filteredScooters);
		model.addAttribute("scooterDto", new ScooterDto()); // 用於表單綁定

		return "admin/scooter";
	}

	@ExceptionHandler({ ScooterException.class })
	public String handleScooterException(ScooterException e, Model model) {
		// 處理 ScooterException 類型異常，顯示錯誤訊息，並傳到error.html的<p th:text="${message}"></p>顯示
		model.addAttribute("message", e.getMessage());
		return "admin/error";
	}

}