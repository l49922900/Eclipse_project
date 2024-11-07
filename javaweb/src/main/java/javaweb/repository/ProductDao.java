/*
UserDAO

	是資料訪問物件（DAO, Data Access Object）的介面
	定義了操作User表的基本方法，例如新增、查詢、更新和刪除（CRUD）操作。
	
協作：

	UserDAO的具體實現通常由UserDaoImpl來完成，並在UserService中被呼叫。
	這樣可以實現與資料庫的分離，使得數據庫操作邏輯不直接出現在業務層或控制層中。	
 */



package javaweb.repository;

import java.util.List;
import java.util.Map;

import javaweb.model.entity.Product;

public interface ProductDao {
	// 多筆: 
	List<Product> findAllProducts();
	
	// Map<商品名稱(product_name), 銷售金額 (total_sales)
	Map<String, Double> querySalesRanking(); // 銷售排行
}
