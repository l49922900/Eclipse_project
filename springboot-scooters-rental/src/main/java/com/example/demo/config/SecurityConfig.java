package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    public PasswordEncoder passwordEncoder() {
//加密工具		
		
        return new BCryptPasswordEncoder();
    }
    
    
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//在 SecurityConfig 中，必須顯式定義 AuthenticationManager Bean，這樣才能在其他地方使用它來進行登入帳密驗證。        
    	return authenticationConfiguration.getAuthenticationManager();
    }

    
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(authorize -> authorize
//        	.requestMatchers("/actuator/**", "/actuator/prometheus/**").permitAll() // 允許訪問 Actuator 端點
        	.requestMatchers("/register").permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().hasAnyRole("ADMIN", "USER")
        )
        .formLogin(form -> form
            .loginPage("/login") // 自訂登入頁面
            .loginProcessingUrl("/login") // 表單提交的處理 URL
            .defaultSuccessUrl("/home", true) // 登入成功後的頁面
            .failureUrl("/login?error=true") // 登入失敗後的頁面
            .permitAll()
        );
   return http.build();
    }
}
