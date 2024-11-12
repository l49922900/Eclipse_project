/*
JPA（Java Persistence API，Java 持久化應用程式介面）

是一個 Java 規範，主要用於在 Java 應用程式中管理資料的持久化。
持久化（Persistence）是指將資料儲存到持久性的儲存設備（如資料庫）中，使資料在應用程式結束運行後仍然存在。
JPA 提供了一套標準化的 API，幫助開發者將 Java 物件（通常稱為「實體（Entity）」）映射到資料庫表格上，使得操作資料庫更簡便。


JPA 的主要功能:

物件-關聯映射（Object-Relational Mapping，ORM）： JPA 提供了 ORM 的功能，使 Java 類別可以直接映射到資料庫表格。
每個 Java 類別的實例（也稱為物件）可以對應到資料庫中的一個紀錄，這樣的映射減少了繁瑣的 SQL 操作，
並讓資料庫操作更具可讀性和擴展性。


簡化資料操作： JPA 透過 EntityManager 介面提供了基本的資料操作方法，
如增、刪、改、查等。開發者不需要直接撰寫 SQL 查詢，只需呼叫 JPA 提供的 API 即可。


查詢語言 JPQL： JPA 提供了 JPQL（Java Persistence Query Language），類似 SQL 的查詢語言，
但針對物件進行操作。例如，可以直接使用 Java 類別名稱和屬性進行查詢，而不是傳統的資料庫表名稱和欄位名稱。


常見的 JPA 實作有：

	Hibernate：最廣泛使用的 JPA 實作之一，擁有豐富的功能和良好的擴展性。
 */

package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Room;
import com.example.demo.repository.RoomRepository;
import com.example.demo.repository.RoomRepositoryJdbc;

@SpringBootTest
public class RoomJPATests {
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Test void testRoomAdd() {
		Room room = new Room(101, "101(S)", 3);
		room = roomRepository.save(room);
		System.out.println("測試新增: " + room);
	}
	
	@Test void testFindAllRooms() {
		System.out.println("測試查詢全部: " + roomRepository.findAll());
	}
	
	@Test void testGetOneRoom() {
		System.out.println("測試查詢單筆: " + roomRepository.findById(101));
		System.out.println("測試查詢單筆: " + roomRepository.findById(201));
	}
	
	@Test void updateRoom() {
		Room uptRoom = new Room(101, "101(L)", 100);
		System.out.println("修改前: " + uptRoom);
		Room room = roomRepository.save(uptRoom);
		System.out.println("修改後: " + room);
	}
	
	@Test void deleteRoom() {
		int roomId = 101;
		roomRepository.deleteById(roomId);
		System.out.println("測試刪除: " + roomId);
	}
	
}