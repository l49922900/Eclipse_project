package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;


@Service
public class UserServiceImpl implements UserService
//,UserDetailsManager 
{
	
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
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	@Override
    public void updateUser(Long id, UserDto userDto) {
        // 1. 根據 ID 從資料庫尋找使用者，如果找不到就拋出例外
        User user = userRepository.findById(Long.valueOf(id).intValue())
            .orElseThrow(() -> new UserNotFoundException("找不到要更新的使用者，ID: " + id));

        // 2. 更新允許修改的欄位 (這裡只更新角色和啟用狀態)
        
        // *** 修正 #1：將 String 轉換為 User.Role enum ***
        // 使用 User.Role.valueOf() 方法將來自 DTO 的字串（例如 "admin"）轉換成對應的 enum 值。
        try {
            user.setRole(User.Role.valueOf(userDto.getRole()));
        } catch (IllegalArgumentException e) {
            System.err.println("LOG: 無效的角色字串: " + userDto.getRole());
            // 可以在這裡拋出一個自訂的例外或採取預設行為
        }
        
        // *** 修正 #2：將 boolean 轉換為 User.AccountStatus enum ***
        // 使用三元運算子，如果 DTO 的 isEnabled() 是 true，就設定狀態為 active，否則為 inactive。
        user.setAccountStatus(userDto.isEnabled() ? User.AccountStatus.active : User.AccountStatus.inactive);
        
        System.out.println("LOG: 正在更新使用者 ID: " + id + " 的資料為: " + user.toString());

        // 3. 將更新後的 User 物件存回資料庫
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        // 1. 檢查使用者是否存在，如果不存在，直接拋出例外
        if (!userRepository.existsById(Long.valueOf(id).intValue())) {
            throw new UserNotFoundException("找不到要刪除的使用者，ID: " + id);
        }
        
        System.out.println("LOG: 正在刪除使用者 ID: " + id);

        // 2. 執行刪除
        userRepository.deleteById(Long.valueOf(id).intValue());
    }

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		 // 嘗試從資料庫中根據使用者名稱找到使用者
//        User user = userRepository.findByUsername(username)
//            .orElseThrow(() -> new UsernameNotFoundException("使用者不存在：" + username));
//
//     // 從資料庫中獲取角色
//        String role = user.getRole().name(); // 假設角色儲存在 `role` 欄位
//        
//        // 建立 Spring Security 的 UserDetails 實例
//        return org.springframework.security.core.userdetails.User
//            .withUsername(user.getUsername())
//            .password(user.getPasswordHash())
//            .roles(role) // 預設角色，可以根據需要客製化
//            .build();
//        
//	}
//
//	@Override
//	public void createUser(UserDetails user) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateUser(UserDetails user) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void deleteUser(String username) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void changePassword(String oldPassword, String newPassword) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean userExists(String username) {
//		// TODO Auto-generated method stub
//		return false;
//	};
}
