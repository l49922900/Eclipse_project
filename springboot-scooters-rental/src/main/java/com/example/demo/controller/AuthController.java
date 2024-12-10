package com.example.demo.controller;

import java.io.Console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.RegistrationRequest;
import com.example.demo.model.dto.LoginRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.utils.JwtUtils;
import com.example.demo.service.impl.UserServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl UserService;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    

    // 顯示註冊頁面
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
/*
初始化一個空的 RegistrationRequest 物件，並將其放入 Model 中，
讓頁面（register.html）可以使用。        
 */
        
        return "user/register";
    }

    // 處理註冊請求
    @PostMapping("/register")
    public String register(@Validated @ModelAttribute RegistrationRequest registrationRequest,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        // 檢查使用者名稱是否已存在
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            model.addAttribute("error", "帳號已被使用！");
            return "user/register";
        }

        // 檢查電子郵件是否已被註冊
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            model.addAttribute("error", "電子郵件已被註冊！");
            return "user/register";
        }

        
        UserService.registerUser(registrationRequest.getUsername(),registrationRequest.getPassword() , registrationRequest.getEmail());
        
        return "redirect:/login";
        
//        @ExceptionHandler({ScooterException.class})
//        public String handleScooterException(ScooterException e, Model model) {
//            // 處理 ScooterException 類型異常，顯示錯誤訊息，並傳到error.html的<p th:text="${message}"></p>顯示
//            model.addAttribute("message", e.getMessage());
//            return "backend/error";
//        }
    }
    
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "user/login"; // 返回你自訂的登入頁面
    }
    
    @GetMapping("/home")
    public String showHomePage() {
        return "user/home"; // 假設有 home.html 頁面
    }
    
    @PostMapping("/login")
    public String login(
        @ModelAttribute LoginRequest loginRequest, 
        Model model, 
        HttpServletResponse response
    ) {
        try {
            // 創建認證對象
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

            // 認證
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 生成 JWT Token
            String token = jwtUtils.generateToken(authentication.getName(), authentication.getAuthorities());
            
            
            // 將 JWT 設置為 Cookie
            Cookie jwtCookie = new Cookie("Authorization", token);
            jwtCookie.setHttpOnly(true);
          //設置這個 Cookie 為 HTTP-only，防止客戶端 JavaScript 訪問（增強安全性）
            
            jwtCookie.setSecure(true);
            //// 僅允許 HTTPS
            
            jwtCookie.setPath("/");
            ////設置 Cookie 的作用範圍為整個應用的所有路徑
            
            response.addCookie(jwtCookie);
            // //response.addCookie(jwtCookie)：將這個 Cookie 添加到 HTTP 響應中，返回給客戶端
            

            return "redirect:/home";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "登入失敗");
            return "user/login";
        }
    }

}
