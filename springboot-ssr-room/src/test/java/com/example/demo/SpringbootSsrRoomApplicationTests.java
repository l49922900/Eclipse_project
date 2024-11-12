package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.mapper.RoomMapper;
import com.example.demo.model.dto.RoomDto;
import com.example.demo.model.entity.Room;

@SpringBootTest
class SpringbootSsrRoomApplicationTests {
	//測試類別，用來驗證 RoomMapper 的功能。
	
	@Autowired
	private RoomMapper roomMapper;
	
	@Test
	/*
	加上 @Test 的方法會被視為一個測試案例，可以被自動識別和執行。
	 */
	void testRoomMapper() {
	//執行測試:程式碼空白處按下滑鼠右鍵 > Run As > JUnit Test
	
		
		// Entity
		Room room = new Room(101, "101(S)", 4);
		System.out.println("原始 room: " + room);
		
		// toDto 將 Entity 轉 DTO
		RoomDto roomDto = roomMapper.toDto(room);
		System.out.println("測試 toDto: " + roomDto);
		
		// toEntity 將 DTO 轉 Entity
		room = roomMapper.toEntity(roomDto);
		System.out.println("測試 toEntity: " + room);
		
		
	}

}