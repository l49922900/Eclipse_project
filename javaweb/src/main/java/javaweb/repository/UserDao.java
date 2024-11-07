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

import javaweb.model.entity.User;

// User (DAO: Data Access Object)
public interface UserDao {
	// 多筆: 查詢所有使用者
	List<User> findAllUsers();
	
	// 單筆: 根據 username 查詢該筆使用者
	User getUser(String username);
	
	// 新增
	void addUser(User user);
	
	// 修改 active 狀態
	void updateUserActive(Integer userId, Boolean active);
	
	// 修改 role 狀態
	void updateUserRole(Integer userId, String role);
		
	// 刪除: 根據 userId 來刪除
	void deleteUser(Integer userId);
	
	// 修改密碼
	void updatePasswordHash(Integer userId, String newPasswordHash);
}
