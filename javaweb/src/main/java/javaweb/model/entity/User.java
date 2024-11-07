package javaweb.model.entity;

import lombok.Data;

/*
-- 建立 users 資料表
create table if not exists users (
	user_id int primary key auto_increment comment '使用者ID',
    username varchar(50) not null unique comment '使用者名稱',
    password_hash varchar(255) not null comment '使用者Hash密碼',
    salt varchar(255) not null comment '隨機鹽',
    email varchar(255) comment '電子郵件',
    active boolean default false comment '帳號啟動',
    role varchar(50) not null default 'ROLE_USER' comment '角色權限'
);
 */




/*

UserDto 對應於 User entity
Dto 的屬性名稱建議可以與所對應的 Entity 物件不同


DTO (Data Transfer Object)"

	DTO 是一種用來在不同層之間傳遞資料的物件。
	其主要目的是簡化傳遞資料的過程，避免將與資料庫有關的實體物件（Entity）直接暴露在控制層或前端。
	有時，Entity 物件會包含大量與資料庫相關的屬性
	在前端或其他系統之間傳遞時，不需要這麼多資料。
	使用 DTO 可以減少不必要的資料傳輸，減少流量，並提高效能。\
	通常會將需要傳遞的資料打包進 DTO 物件中，再傳遞給其他層，這樣可以有效地控制資料的結構和範圍。
	
協作：

	UserDto通常在UserServlet和前端頁面（如user.jsp）之間進行數據傳遞，
	以便將必要的用戶資料傳遞給前端顯示。	

Entity
	
	Entity 是與資料庫直接關聯的物件，通常代表資料庫中的資料表（table）
	Entity 是指與資料庫中的表格相對應的 Java 類別
	Entity 會包含與資料庫對應的屬性，以及一些基本的資料驗證或約束。
	通常使用 ORM（例如 JPA，Java Persistence API）框架來管理 Entity，使其能夠對應到資料庫中的表格，
	並進行 CRUD（Create, Read, Update, Delete）操作。
	
	
協作：

	User.java的物件會被UserDAO、UserService等類使用，
	以便進行數據庫操作或業務邏輯處理。UserService在進行邏輯處理時，會將資料以UserDto的格式返回給前端。
	
過程:
	1.當用戶端發出一個請求，例如查詢用戶資訊，後端服務層會從資料庫中查詢 User 表並獲得對應的 Entity 物件（例如 User）。
	2.然後，後端會將這個 Entity 物件的資料轉換為 DTO 物件（例如 UserDto），以避免將與資料庫相關的細節暴露給前端。
	3.最後，後端返回 DTO 給前端，這樣可以控制哪些資料會被傳遞出去。
	
*/



@Data
public class User {
	private Integer userId; // 使用者ID
	private String username; // 使用者名稱
	private String passwordHash; // 使用者Hash密碼
	private String salt; // 隨機鹽
	private String email; // 電子郵件
	private Boolean active; // 帳號啟動
	private String role; // 角色權限
}