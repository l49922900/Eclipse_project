package com.example.demo.advice;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
//@ControllerAdvice 是 Spring MVC 中的一個注解，作用是定義全域性的攔截邏輯或輔助邏輯，例如全域例外處理、資料預處理等。
public class GlobalControllerAdvice {
	

    @ModelAttribute
    public void addUserInfo(Model model) {
    //addUserInfo:自動將當前登入的使用者資訊添加到所有視圖的 Model 中，這樣就不需要在每個 @Controller 手動處理使用者資訊，提升程式碼的可維護性與可重用性。	
    //Model：這是一個 Spring MVC 提供的類別，負責將數據傳遞到前端的 View（視圖）中，例如 Thymeleaf、JSP 等。

    	
    	
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    /*
   	SecurityContextHolder.getContext() 取得當前的安全上下文（Security Context）。
	.getAuthentication() 取得當前登入的 身份驗證對象（Authentication），它包含了登入者的詳細資訊，如使用者名稱、角色、憑證等。
     */
        
        
        if (authentication != null && authentication.getPrincipal() instanceof User) {
     //作用:確保 authentication 不是 null，並且 getPrincipal() 是 User   	
        	
            User user = (User) authentication.getPrincipal();
     //將登入使用者物件轉換為 User 類型，以便存取用戶資訊（如使用者名稱、權限等）。                  
            
            model.addAttribute("user", user);
     //將當前登入的使用者資訊存入 Model，這樣前端視圖可以透過 ${user} 直接存取登入使用者資訊。       
        }
    }
}

