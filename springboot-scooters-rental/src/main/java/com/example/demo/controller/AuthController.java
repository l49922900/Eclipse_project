package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.exception.ScooterException;
import com.example.demo.model.entity.User;
import com.example.demo.model.frontend.RegistrationRequest;
import com.example.demo.repository.backend.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 顯示註冊頁面
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
/*
初始化一個空的 RegistrationRequest 物件，並將其放入 Model 中，
讓頁面（register.html）可以使用。        
 */
        
        return "frontend/user/register";
    }

    // 處理註冊請求
    @PostMapping("/register")
    public String register(@Validated @ModelAttribute RegistrationRequest registrationRequest,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "frontend/user/register";
        }

        // 檢查使用者名稱是否已存在
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            model.addAttribute("error", "帳號已被使用！");
            return "frontend/user/register";
        }

        // 檢查電子郵件是否已被註冊
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            model.addAttribute("error", "電子郵件已被註冊！");
            return "frontend/user/register";
        }

        // 建立新使用者
        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPasswordHash(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setEmail(registrationRequest.getEmail());
        user.setAccountStatus(User.AccountStatus.inactive);

        userRepository.save(user);

        model.addAttribute("successMessage", "註冊成功！請檢查您的電子郵件以啟用帳號。");
        return "redirect:/frontend/user/login";
        
//        @ExceptionHandler({ScooterException.class})
//        public String handleScooterException(ScooterException e, Model model) {
//            // 處理 ScooterException 類型異常，顯示錯誤訊息，並傳到error.html的<p th:text="${message}"></p>顯示
//            model.addAttribute("message", e.getMessage());
//            return "backend/error";
//        }
    }
}
