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


/*
為什麼RoomRepositoryJdbc 和 RoomRepository會分開設計:

RoomRepositoryJdbc 使用傳統 JDBC（Java Database Connectivity）來操作資料庫，
適合需要完全控制 SQL 查詢時的場景。例如，當 SQL 查詢相對複雜，或者需要進行批次更新、非同步查詢等
 

而 RoomRepository 則是使用 Spring Data JPA，透過 JpaRepository 提供標準的 CRUD 操作，
並能自動生成查詢語句。在需要簡單 CRUD 操作或是常見查詢語句時，使用 JPA 能夠減少 SQL 編寫的工作量
 */

package com.example.demo.repository.backend;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.entity.Scooter;

//Spring JPA
@Repository
/*
@Repository 是 Spring Data JPA 的一部分，它標註 RoomRepository 為資料存取層的 Bean，
使 Spring 可以自動管理這個物件的生命週期。
*/
public interface ScooterRepository extends JpaRepository<Scooter,Integer>{
	/*
	JpaRepository 介面:
	
		這是 Spring Data JPA 提供的接口。JpaRepository 是 JPA 的高階介面之一，
		它包含了常見的 CRUD 操作方法，因此只要繼承 JpaRepository，
		就可以輕鬆實現 CRUD 操作，而無需自己撰寫 SQL 語句。
		
	以下常用的方法都不需要額外實作即可使用：

		save()：用於新增或更新記錄。
		findById()：用於根據主鍵查詢特定記錄。
		findAll()：用於查詢所有記錄。
		deleteById()：用於根據主鍵刪除特定記錄。
		
	Room：這是要操作的實體類別（Entity class），代表對應的資料庫表。這裡的 Room 類別即為要存取的表格。
	
	Integer：這是實體類別中 @Id 註解標註的主鍵的型別。
	在這裡，Room 實體的主鍵（Primary Key）被設計為 Integer 型別，因此這裡的泛型型別指定為 Integer。
	 */
	
	
	/*
	當繼承了 JpaRepository（JPA的高階介面）後，Spring Data JPA 會根據方法名稱自動生成對應的 SQL 查詢語句。
	
	方法名稱解析：

		findByRoomSizeGreaterThan 是根據命名規則來構建的，Spring Data JPA 能夠識別並自動生成對應的 SQL 查詢。
		RoomSize 是要查詢的屬性名稱（對應到 Room 實體中的 roomSize 屬性）。
		GreaterThan 是查詢條件，表示查找 roomSize 大於指定值的所有記錄。
		
	基於方法名稱，Spring Data JPA 自動生成 SQL 查詢語句，相當於:
	
		SELECT * FROM room WHERE room_size > ?;
	 */
	
	
	List<Scooter> findBycc(Integer cc);
}
