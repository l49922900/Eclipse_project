package com.example.demo.controller;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
}
