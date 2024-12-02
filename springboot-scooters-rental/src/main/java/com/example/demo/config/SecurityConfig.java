package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
    
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests() // 定義授權規則
//                .requestMatchers("/public/**").permitAll() // `/public/**` 路徑允許所有人訪問
//                .requestMatchers("/admin/**").hasRole("ADMIN") // `/admin/**` 僅允許擁有 ADMIN 角色
//                .anyRequest().authenticated() // 其他所有請求都需要身份驗證
//            .and()
//            .formLogin() // 啟用表單登入
//                .loginPage("/login") // 自訂登入頁面
//                .permitAll() // 允許所有人訪問登入頁面
//            .and()
//            .logout() // 啟用登出功能
//                .permitAll();
//
//        return http.build();
//    }

}
