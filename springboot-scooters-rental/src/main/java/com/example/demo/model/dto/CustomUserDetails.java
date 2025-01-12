package com.example.demo.model.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.entity.User;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails {

    private final User user; // 這是你自定義的 User 類別

    public CustomUserDetails(User user) {
        this.user = user;
    }

    
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 這裡返回的是角色權限（例如 "ROLE_USER", "ROLE_ADMIN"）
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name().toUpperCase()));
    /*
    使用 Collections.singletonList 返回一個只有單一元素的集合。
	SimpleGrantedAuthority 是 GrantedAuthority 的一個簡單實現類，用於表示角色。
     */
    }

    
    public String getPassword() {
        return user.getPasswordHash(); // 返回密碼哈希
    }

    
    public String getUsername() {
        return user.getUsername(); // 返回用戶名稱
    }

    
    public boolean isAccountNonExpired() {
        return user.getAccountStatus() == User.AccountStatus.active; // 根據帳號狀態判斷
    }

    
    public boolean isAccountNonLocked() {
        return user.getAccountStatus() == User.AccountStatus.active; // 根據帳號狀態判斷
    }

    
    public boolean isCredentialsNonExpired() {
        return true; // 如果密碼過期的情況，這邊可以根據需求調整
    }

   
    public boolean isEnabled() {
        return user.getAccountStatus() == User.AccountStatus.active; // 根據帳號狀態判斷
    }

    // 可以加上其它自定義方法來獲取 User 類別的其他資訊
    public User getUser() {
        return user;
    }
}

