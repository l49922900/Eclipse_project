package com.example.demo.model.dto;

import lombok.Data;

@Data
public class UserDto {

	private Integer id;
    private String username;
    private String email;
    private String role;
    private boolean enabled;
}
