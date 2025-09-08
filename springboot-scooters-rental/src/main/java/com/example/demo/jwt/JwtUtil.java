package com.example.demo.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private Key key; // 1. 新增 Key 型別的成員變數

    // 2. 使用 @PostConstruct 確保在元件注入 SECRET_KEY 後，立刻初始化 key
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        System.out.println("JWT Key initialized successfully."); // 加入 log 確認
    }

    // 從 token 中提取使用者名稱
    public String extractUsername(String token) {
            return extractClaim(token, Claims::getSubject);
    }

    // 從 token 中提取過期時間
    public Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        // 3. 更新解析 token 的方式，使用新的 parserBuilder() API 和 Key 物件
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // 檢查 token 是否過期
    private Boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
    }

    // 產生 token
    public String generateToken(UserDetails userDetails) {
            Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 設置 10 小時後過期
                // 4. 更新簽名的方式，使用 Key 物件
                .signWith(key, SignatureAlgorithm.HS256).compact();
    }

    // 驗證 token
    public Boolean validateToken(String token, UserDetails userDetails) {
            final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}