package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/admin") // 將所有管理員相關的 API 統一放在 /api/admin 路徑下
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper; // 用於物件之間的轉換


    
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        System.out.println("獲取所有user");
        List<User> users = userService.getAllUsers();
        
        // 使用 stream 和 modelMapper 將 List<User> 轉換為 List<UserDto>
        List<UserDto> userDtos = users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(userDtos);
    }
    
    @PutMapping("/users/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        System.out.println("LOG: 收到用戶更新請求，user ID: " + id);
        // 這裡我們假設 UserService 中有一個 updateUser 方法
        userService.updateUser(id, userDto);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        System.out.println("LOG: 收到用戶刪除請求， user ID: " + id);
        // 這裡我們假設 UserService 中有一個 deleteUser 方法
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // 回傳 204 No Content 表示成功
    }
}
