package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.jwt.JwtUtil;
import com.example.demo.model.RegistrationRequest;
import com.example.demo.model.dto.LoginRequest;
import com.example.demo.service.UserService;

@Controller
public class AuthController {
	//AuthController:用於處理與認證 (Authentication) 和授權 (Authorization) 有關的請求。

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            // 驗證使用者帳號密碼
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (Exception e) {
            // 這裡可以拋出更明確的錯誤，例如 "帳號或密碼錯誤"
            throw new Exception("Incorrect username or password", e);
        }

        // 產生 JWT
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        // 回傳 JWT 給前端
        return ResponseEntity.ok(Map.of("token", jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        try {
            userService.registerUser(registrationRequest.getUsername(),registrationRequest.getPassword(),registrationRequest.getEmail());
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
	
	
	
	
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private UserServiceImpl UserService;
//    
//    
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//
//    // 顯示註冊頁面
//    @GetMapping("/register")
//    public String showRegisterPage(Model model) {
//        model.addAttribute("registrationRequest", new RegistrationRequest());
///*
//初始化一個空的 RegistrationRequest 物件，並將其放入 Model 中，讓頁面（register.html）可以使用。
//這樣在 Thymeleaf 模板 register.html 中可以透過 ${registrationRequest} 取得這個物件，方便表單綁定。        
// */
//        
//        return "user/register";
//    }
//
//    // 處理註冊請求
//    @PostMapping("/register")
//    public String register(@Validated @ModelAttribute RegistrationRequest registrationRequest,
//    		BindingResult bindingResult,Model model) {
//    /*
//    @Validated：啟用表單驗證，根據 RegistrationRequest 中的驗證註解進行檢查。
//    @ModelAttribute：將表單提交的資料綁定到 RegistrationRequest 物件中。	
//    Model：主要用於 將數據傳遞到視圖，如Thymeleaf
//    BindingResult bindingResult：儲存驗證結果，如果有錯誤 (例如欄位不符合格式)，會在這裡記錄。
//    */
//    	
//    	
//    	
//        if (bindingResult.hasErrors()) {
//            return "user/register";
//        }
//
//        // 檢查使用者名稱是否已存在
//        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
//            model.addAttribute("error", "帳號已被使用！");
//            return "user/register";
//        }
//
//        // 檢查電子郵件是否已被註冊
//        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
//            model.addAttribute("error", "電子郵件已被註冊！");
//            return "user/register";
//        }
//
//        
//        UserService.registerUser(registrationRequest.getUsername(),registrationRequest.getPassword() , registrationRequest.getEmail());
//        
//        return "redirect:/login";
//        
////        @ExceptionHandler({ScooterException.class})
////        public String handleScooterException(ScooterException e, Model model) {
////            // 處理 ScooterException 類型異常，顯示錯誤訊息，並傳到error.html的<p th:text="${message}"></p>顯示
////            model.addAttribute("message", e.getMessage());
////            return "backend/error";
////        }
//    }
//    
//    
//    
//    @GetMapping("/login")
//    public String showLoginPage() {
//        return "user/login"; // 到你自訂的登入頁面
//    }
//    
//    
//    @GetMapping("/user-home")
//    public String showUserHomePage() {
//
//        return "user/home";
//    }
//    
//    
//    @GetMapping("/home")
//    public String showHomePage(Authentication authentication) {
//    //showHomePage:依角色轉導相應首頁	
//    //Authentication:是 Spring Security 用來存放目前登入使用者資訊的物件。 	
//    	String username = authentication.getName();
//        User user = userRepository.findByUsername(username)
//                                  .orElseThrow(() -> new UserNotFoundException("User not found"));
//    	
//        return user.getRole() == Role.admin ? "redirect:/admin/scooter" : "user/home";
//    }
}
