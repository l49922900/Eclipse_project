package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;


@Service
public class UserServiceImpl implements UserService,UserDetailsManager {
	
	@Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
	
	@Override
	public void registerUser(String username,String password,String email) {
		// 對密碼進行加密
        String encodedPassword = passwordEncoder.encode(password);

        // 創建用戶並保存到資料庫
        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(encodedPassword);
        user.setEmail(email);
        
        userRepository.save(user);
		
		
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 // 嘗試從資料庫中根據使用者名稱找到使用者
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("使用者不存在：" + username));

     // 從資料庫中獲取角色
        String role = user.getRole().name(); // 假設角色儲存在 `role` 欄位
        
        
        // 建立 Spring Security 的 UserDetails 實例
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPasswordHash())
            .roles(role) // 預設角色，可以根據需要客製化
            .build();
	}

	@Override
	public void createUser(UserDetails user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(UserDetails user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean userExists(String username) {
		// TODO Auto-generated method stub
		return false;
	};
}
