package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.dto.UserCert;
import com.example.demo.service.CertService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login") 
//定義此控制器的基礎路徑為 /login，控制器中的方法將處理來自 /login 的請求。

public class LoginController {
	
	@Autowired
	private CertService certService;
	
	@GetMapping
	//@GetMapping：此方法對應 GET 請求。當用戶訪問 /login 時，此方法將被調用。
	public String loginPage() {
		return "login";
	//返回 login 這個視圖名稱，會映射到 login.jsp 
	}
	
	@PostMapping
	//對應 POST 請求，表示用戶提交登入表單後調用這個方法。
	public String checkLogin(@RequestParam String username, @RequestParam String password, HttpSession session, HttpServletRequest req, Model model) {
/*
@RequestParam String username, @RequestParam String password：
從請求中提取用戶名和密碼，這些數據是用戶在登入表單中輸入的。

HttpSession session：提供存取和管理用戶的 session（會話）資料。
(HttpSession session 是從 Spring MVC 自動注入的，不需要手動建立。
當使用者發送請求到控制器並進入 checkLogin() 方法時，
Spring 會自動將當前的 session 對象傳遞給這個方法的 session 參數。)


HttpServletRequest req：提供 request 對象，可從中取得其他客戶端資訊，
例如用戶端 IP、Header（標頭）資料、請求的 URL 路徑、Cookie 資訊、表單參數


Model model：用於向視圖傳遞數據。當控制器處理完請求並準備回應時，可以通過 Model 添加數據，
以便在視圖（例如 JSP 或 Thymeleaf）中顯示這些數據。
 */
		// 透過 userService 來確認登入
		// 驗證帳密並取得憑證
		UserCert userCert = null;
		
		try {
			userCert = certService.getCert(username, password);
		} catch (Exception e) {
			// 將錯誤丟給(重導) error.jsp
			model.addAttribute("message", e.getMessage());
			return "error";
		}
		
		// 將憑證放入 session 變數中以利其他程式進行取用與驗證
		session.setAttribute("userCert", userCert); // 放憑證
		session.setAttribute("locale", req.getLocale()); // 取得客戶端所在地 例如: zh_TW
		return "redirect:/rooms"; // 登入成功後到首頁
	}
	
}