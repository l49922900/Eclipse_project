package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;

public interface UserService {
	public void registerUser(String username,String password,String email);
	public List<User> getAllUsers();
	
    void updateUser(Long id, UserDto userDto);


    void deleteUser(Long id);
}
