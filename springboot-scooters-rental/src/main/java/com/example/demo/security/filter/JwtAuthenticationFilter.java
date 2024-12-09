package com.example.demo.security.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.utils.JwtUtils;
import com.example.demo.service.impl.UserServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	//自訂過濾器 創建一個過濾器，用於從請求頭中提取 JWT 並驗證。
	//繼承 OncePerRequestFilter：確保此過濾器在一次請求處理鏈中只執行一次。

	
	
	
	
	@Autowired
    private  JwtUtils jwtUtils;
	
	@Autowired
    private  UserServiceImpl userServiceImpl;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
/*
doFilterInternal：實作的核心方法。HttpServletRequest 和 HttpServletResponse 是請求和回應的對象，FilterChain 允許將請求繼續傳給其他過濾器或最終的控制器。
這裡覆寫 OncePerRequestFilter 的方法，實現自訂過濾邏輯。
 */
    	
    	
    	
        String header = request.getHeader("Authorization");
        String token = null;
        String username = null;

        if (header != null && header.startsWith("Bearer")) {
//header.startsWith("Bearer ") 檢查這個字串是否以 "Bearer " 開頭，如果是，則代表這是一個 Bearer Token。     	
        	
            token = header.substring(7);
//header.substring(7) 是從 header 字串的第 7 個字元開始提取，也就是跳過 "Bearer " 這段文字，剩下的部分就是實際的 JWT token            
            username = jwtUtils.getUsernameFromToken(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	UserDetails userDetails = userServiceImpl.loadUserByUsername(username);
/*
驗證用戶是否已認證:
	果用戶名存在且當前安全上下文中沒有認證信息：
	userDetailsService.loadUserByUsername(username)：根據用戶名加載用戶詳情。  	
 */  
        	
            if (!jwtUtils.isTokenExpired(token)) {
//jwtUtils.isTokenExpired(token)：檢查 Token 是否已過期。   
            	
            	 String role = jwtUtils.getRoleFromToken(token);
            	 //// 檢查角色是否符合要求
                 if (!"ROLE_USER".equals(role)) {
                     throw new AccessDeniedException("Unauthorized");
                 }
            	
            	
                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//UsernamePasswordAuthenticationToken：創建認證 Token，包含用戶詳情和權限                
                
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//authToken.setDetails()：設置當前請求的詳細信息。
                
                SecurityContextHolder.getContext().setAuthentication(authToken);
//SecurityContextHolder.getContext().setAuthentication()：將認證 Token 設置到當前的安全上下文中。 表示當前請求已經過身份驗證。    
            }
        }
        chain.doFilter(request, response);
//調用 FilterChain 的 doFilter 方法，將請求轉交給下一個過濾器或目標資源。        
    }
}
