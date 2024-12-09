package com.example.demo.security.utils;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.demo.model.entity.User.Role;

import javax.crypto.SecretKey;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

@Component
public class JwtUtils {
	
	@Autowired
	private MessageSource messageSource; 
	
	

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // 生成 JWT
    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
    	
    	String role = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElseThrow(() -> {
                    String errorMessage = messageSource.getMessage("error.no.role.found", null, Locale.getDefault());
                    return new IllegalArgumentException(errorMessage);
                });
    
    	
    	
        return Jwts.builder()
                .setSubject(username)  // 設定主題
                .claim("role", role)  // 加入角色資訊
                .setIssuedAt(new Date())  // 設定簽發時間
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // 設定過期時間
                .signWith(getSecretKey(), SignatureAlgorithm.HS512) // 使用秘鑰和演算法簽名
                .compact();  // 生成並返回 JWT 字串
    }

    // 解析 JWT
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            // 打印或處理解析錯誤
            throw new IllegalStateException("Invalid JWT token");
        }
    }


    // 檢查是否過期
    public boolean isTokenExpired(String token) {
        return getClaimsFromToken(token).getExpiration().before(new Date()); // 比較過期時間和目前時間
    }

    // 從 JWT 中獲取使用者名稱
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject(); // 從 Claims 中取得主題
    }
    
    // 取得角色權限資訊
    public String getRoleFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("role", String.class); 
    }

}
