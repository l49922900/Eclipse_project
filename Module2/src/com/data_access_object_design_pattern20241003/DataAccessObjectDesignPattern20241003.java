package com.data_access_object_design_pattern20241003;

import java.util.List;
import java.util.ArrayList;

class Order {
    int id;
    String customer;
    double amount;

    Order(int id, String customer, double amount) {
        this.id = id;
        this.customer = customer;
        this.amount = amount;
        

        }
    }

public class DataAccessObjectDesignPattern20241003 {

	/*
	 * DAO (Data Access Object) 設計模式 
	 * 是一種常用的設計模式，主要用於將資料存取邏輯與業務(執行)邏輯分離。
			(1)資料存取邏輯:
				資料存取邏輯是專門負責如何從資料庫讀取或寫入資料的部分。在DAO模式中，這些操作是核心內容。
				DAO會包含針對資料庫的CRUD操作（Create, Read, Update, Delete），
				這些操作封裝了資料庫相關的SQL查詢，使應用程式的其他部分不直接接觸資料庫，減少耦合度。
			(2)商業邏輯:
				商業邏輯則是指系統中處理應用程式特定業務需求的邏輯。
				(業務:應用程式或系統所支持的具體工作或任務，通常與公司、組織或使用者的需求有關)
				它負責應用業務規則、驗證、工作流程等業務相關的操作。
				商業邏輯通常不應該處理資料庫操作，而是專注於處理業務行為。
				商業邏輯應該通過DAO來與資料庫交互，也就是說，當系統需要存取資料時，它會呼叫DAO來進行資料存取，但具體的業務邏輯是由商業邏輯來處理的。
				
				例如，一個應用程式的商業邏輯可能包括以下操作：

					(1)當用戶註冊時，應該驗證他們的電子郵件格式。
					(2)當一筆訂單完成時，系統會發送確認郵件給用戶。


	 * 優點
	 * 		1.將商業邏輯與資料存取邏輯分離，商業邏輯元件改變,不影響資料存取元件
	 * 			(1)將系統中所有與數據庫互動的操作（如查詢、插入、更新、刪除等）都集中在一個特定的層級（DAO 層）中
	 * 			(2)而將這些操作的具體實現細節隱藏起來，只對外暴露一個簡單的介面
	 		2.簡化存取多種資料來源的方式
	 			(1)當資料來源改變,只需修改DAO物件,不影響商業邏輯元件
	 			(2)不同資料來源撰寫獨立的DAO物件,提供一致的資料存取方法
	 		3.資料存取邏輯可重複使用
	 		4.集中管理資料存取邏輯
	 		
	   DAO 模式的組成(可能有誤，實作時最好重查)

			1.DAO 介面（DAO Interface）: 定義了對數據庫進行操作的方法，例如查詢、插入、更新、刪除等。
			2.DAO 實現類（DAO Implementation）: 實現 DAO 介面，包含具體的數據庫操作邏輯。
				(1)使用具體的數據訪問技術，如JDBC、Hibernate、JPA等。
				(2)負責管理數據庫連接、執行SQL語句或操作ORM框架。
			3.數據傳輸對象 (DTO): 用於傳輸數據，將數據庫中的數據轉換為應用程式可用的格式。
	
	
		
	
		ex:
			假設我們有一個電子商務系統，需要查詢用戶的訂單資訊。

			沒有使用 DAO 的情況下:
				業務邏輯層的代碼可能會直接寫 SQL 語句去查詢數據庫，將查詢結果解析成 Java 對象。

			使用了 DAO 的情況下:
				業務邏輯層只會調用 DAO 層提供的 getUserOrders() 方法，DAO 層會負責執行 SQL 語句並返回訂單列表。
	
	 */
	///////////////////////////////////////////////////////////
	
	
	
	//範例
	
	class Order{}
	
	interface OrderDao {
	    List<Order> getUserOrders(int userId);
	    //提供了一個抽象的數據訪問層，明確了與數據庫交互的契約。
	}

	
	
	// DAO 實現類別
	class OrderDaoImpl implements OrderDao {
		
            List<Order> orders = new ArrayList<>();
            
		
		
	//這個類實現了 OrderDao 介面，提供了 getUserOrders 方法的具體實現。
	//負責所有的數據庫交互細節，將業務邏輯與數據訪問邏輯分開。	
		
	    // ... 撰寫了JDBC 連接相關代碼 ...

	    @Override
	    public List<Order> getUserOrders(int userId) {
	    //getUserOrders 方法會根據傳入的用戶 ID，構造相應的 SQL 語句，然後執行查詢，並將查詢結果映射為 Order 對象的列表。	
	    	
	    	
	        // ... 使用 JDBC 執行 SQL，查詢用戶訂單 ...
	        return orders;
	    }
	}

	
	
	// 業務邏輯層
	class OrderService {
		//OrderService 類依賴於 OrderDao 介面，通過注入的方式獲取 OrderDao 的實現。
	    //rderService 層只關注業務邏輯，不涉及數據庫操作的細節。
		
	    private OrderDao orderDao;
	    //OrderService 類會依賴注入一個 OrderDao 的實例。
	    //這意味著，OrderService 不需要自己創建 OrderDao 的實例，而是由容器（例如 Spring 框架）負責注入。

	    
	    
	    public List<Order> getUserOrders(int userId) {
	    	//List<Order> 代表一個 泛型集合。
	    	//List： 是一個介面，繼承自 Collection 介面，代表一個有序的集合。它允許重複元素，並且元素的順序是可預測的。
	    	//Order： 這是一個自定義的類，代表一個訂單。它可能包含訂單號、訂單日期、商品列表、客戶信息等屬性。
	    	//<Order>： 尖括號中的 Order 表示這個 List 集合中存放的元素都是 Order 類型的對象。
	    	
	    	
	    	
	        return orderDao.getUserOrders(userId);
	        ///getUserOrders 方法直接調用 OrderDao 的 getUserOrders 方法，將查詢結果返回。
	        //這運用到了多型，具體的實現則交給了OrderDao的實現類來完成。
	    }
	}
	

}
