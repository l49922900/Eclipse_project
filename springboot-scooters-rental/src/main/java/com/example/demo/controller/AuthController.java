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
        return "user/login"; // 到你自訂的登入頁面
    }
    
    @GetMapping("/home")
    public String showHomePage() {
        return "user/home"; // 假設有 home.html 頁面
    }
    

}
