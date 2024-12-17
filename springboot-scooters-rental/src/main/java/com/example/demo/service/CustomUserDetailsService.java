package com.example.demo.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        
        

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(), 
            user.getPasswordHash(), 
            user.getAccountStatus() == User.AccountStatus.active,
            true, // account not expired
            true, // credentials not expired
            true, // account not locked
            getAuthorities(user)
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        // 根據使用者角色創建對應的權限
        return Collections.singletonList(
//            new SimpleGrantedAuthority(user.getRole().name())
            new SimpleGrantedAuthority("ROLE_"+user.getRole().name().toUpperCase())
        );
    }
}
