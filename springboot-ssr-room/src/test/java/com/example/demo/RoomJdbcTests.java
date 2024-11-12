/*
測試全部:在檔案瀏覽器上點擊Run As -> JUnit Test
測試個別方法:在方法名上點擊Run As -> JUnit Test
 */
package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.model.entity.Room;
import com.example.demo.repository.RoomRepositoryJdbc;

@SpringBootTest
public class RoomJdbcTests {
	
	@Autowired
	private RoomRepositoryJdbc roomRepositoryJdbc;
	/*
	在 RoomJdbcTests 類別中注入的是 RoomRepositoryJdbc 介面，而不是具體的 RoomRepositoryJdbcImpl 實作類別:
	
	降低耦合度：透過注入介面而非具體的實作類別，可以讓測試類別與實作解耦，增加程式碼的彈性。
	當需要替換不同的實作類別（例如 Mock 類別或其他自訂的實作）時，只需調整注入的實作類別，而不需改動測試代碼。
	
	Spring Bean 的自動配置：當 RoomRepositoryJdbcImpl 類別被 @Repository 註解標記時，Spring 容器會自動掃描並註冊該類別為 Bean。
	由於 RoomRepositoryJdbcImpl 實作了 RoomRepositoryJdbc 介面，當需要注入 RoomRepositoryJdbc 型別的 Bean 時，
	Spring 會自動將 RoomRepositoryJdbcImpl 作為相依注入到 RoomJdbcTests 中。
	
	
	@Qualifier 註解的使用：如果系統中有多個實作了 RoomRepositoryJdbc 介面的類別，而需要注入特定的某一個，
	可以使用 @Qualifier 來指定。當 @Qualifier 未設定時，Spring 會預設注入唯一的實作類別（此處為 RoomRepositoryJdbcImpl）。
	 */
	
	
	@Test void testRoomAdd() {
		Room room = new Room(101, "101(S)", 3);
		int rowcount = roomRepositoryJdbc.save(room);
		System.out.println("測試新增: " + room + " 結果回傳: " + rowcount + " (1 表示正確新增一筆)");
	}
	
	@Test void testFindAllRooms() {
		System.out.println("測試查詢全部: " + roomRepositoryJdbc.findAll());
	}
	
	@Test void testGetOneRoom() {
		System.out.println("測試查詢單筆: " + roomRepositoryJdbc.findById(101));
		System.out.println("測試查詢單筆: " + roomRepositoryJdbc.findById(109));
	}
	
	@Test void updateRoom() {
		Room uptRoom = new Room(101, "101(L)", 100);
		int rowcount = roomRepositoryJdbc.update(uptRoom);
		System.out.println("測試修改: " + uptRoom + " 結果回傳: " + rowcount + " (1 表示正確修改一筆)");
	}
	
	@Test void deleteRoom() {
		int roomId = 101;
		int rowcount = roomRepositoryJdbc.deleteById(roomId);
		System.out.println("測試刪除: " + roomId + " 結果回傳: " + rowcount + " (1 表示刪除修改一筆)");
	}
	
}