package com.example.demo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.dto.RoomDto;
import com.example.demo.model.entity.Room;

@Component 
/*
此元件由 Springboot 自動掃描後管理
讓應用程式可以通過依賴注入（Dependency Injection）來使用這些元件。
 */
public class RoomMapper {
	/*
	用於將 Room 實體（Entity）物件與 RoomDto 資料傳輸物件（DTO）之間互相轉換。
	 */
	
	
	
	@Autowired
	private ModelMapper modelMapper;
	/*
	使用 @Autowired 自動注入一個 ModelMapper 實例，
	ModelMapper 是一個第三方的物件映射庫，用於簡化 DTO 和實體之間的轉換。
	 */
	
	
	public RoomDto toDto(Room room) {
		// Entity 轉 DTO
		return modelMapper.map(room, RoomDto.class);
	}
	
	public Room toEntity(RoomDto roomDto) {
		// DTO 轉 Entity 
		return modelMapper.map(roomDto, Room.class);
	}
	
	
}