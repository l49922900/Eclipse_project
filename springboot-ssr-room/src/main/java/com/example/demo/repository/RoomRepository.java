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



package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.example.demo.model.entity.Room;

// Spring JPA
@Repository
/*
@Repository 是 Spring Data JPA 的一部分，它標註 RoomRepository 為資料存取層的 Bean，
使 Spring 可以自動管理這個物件的生命週期。
 */
public interface RoomRepository extends JpaRepository<Room, Integer> { // Room: Entity, Integer: @Id 的型別
	/*
	JpaRepository 介面:
	
		這是 Spring Data JPA 提供的接口。JpaRepository 是 JPA 的高階介面之一，
		它包含了常見的 CRUD 操作方法，因此只要繼承 JpaRepository，
		就可以輕鬆實現 CRUD 操作，而無需自己撰寫 SQL 語句。
	 */
	
	
	
	// 預設自動實現 CRUD
	// 自定義查詢
	// 1. 查詢 roomSize 大於指定值的房間(自動生成 SQL)
	List<Room> findByRoomSizeGreaterThan(Integer size);
	
	// 2. 查詢 roomSize 大於指定值的房間(JPQL 以 entity 來操作)
	@Query("select r from Room r where r.roomSize > :size")
	//@Query 提供了使用 JPQL（Java Persistence Query Language）或原生 SQL 來進行自定義查詢的能力
	//優先使用 JPQL（findRoomsBySizeGreaterThen1）：在大多數情況下，使用 JPQL 可以滿足基本的查詢需求，並能保持良好的可移植性和可讀性
	List<Room> findRoomsBySizeGreaterThan1(@Param("size") Integer size);
	/*
	@Param("size") Integer size: 用來將方法參數 size 綁定到 JPQL（Java Persistence Query Language）或原生 SQL 查詢中的命名參數 :size 上。
	
	@Query 指定了一段 JPQL 查詢語句 "select r from Room r where r.roomSize > :size"。
	:size 是 JPQL 查詢語句中的命名參數。
	@Param("size") Integer size 告訴 JPA 將方法參數 size 的值綁定到查詢中的 :size 參數上。
	 */
	
	
	
	// 3. 查詢 roomSize 大於指定值的房間(SQL 以 table 來操作)
	@Query(value = "select room_id, room_name, room_size from room where room_size > :size", nativeQuery = true)
	//@Query 提供了使用 JPQL（Java Persistence Query Language）或原生 SQL 來進行自定義查詢的能力
	List<Room> findRoomsBySizeGreaterThen2(@Param("size") Integer  size);
}