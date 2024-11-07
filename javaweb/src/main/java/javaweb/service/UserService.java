/*
UserService (服務層):

	用途：UserService負責User相關的業務邏輯處理，並調用UserDaoImpl來執行具體的資料庫操作。
	協作：
		findAll()方法：調用UserDaoImpl的findAllUsers()方法來查詢所有用戶，
		並將每個User物件轉換為UserDto物件，最後返回UserDto列表給控制器。
		
		appendUser()方法：此方法接收用戶資料，利用Hash工具類生成密碼的雜湊值（passwordHash）和隨機鹽值（salt）
		然後創建User物件並設置相關屬性，最後透過UserDaoImpl的addUser方法將新用戶資料存入資料庫。
		
 */

package javaweb.service;

import java.util.ArrayList;
import java.util.List;

import javaweb.exception.PasswordInvalidException;
import javaweb.exception.UserNotFoundException;
import javaweb.model.dto.UserDto;
import javaweb.model.entity.User;
import javaweb.repository.UserDao;
import javaweb.repository.UserDaoImpl;
import javaweb.utils.Hash;

// UserService 是給 UserServlet(Controller) 使用
public class UserService {
	
	private UserDao userDao = new UserDaoImpl();
	
	// 所有使用者
	public List<UserDto> findAll() {
		List<UserDto> userDtos = new ArrayList<>();
		// 將 User 轉 UserDto
		// 向 userDao 索取 List<User> 集合
		List<User> users = userDao.findAllUsers();
		for(User user : users) {
			// 一個一個將 User 轉成 UserDto 並放在 userDtos 集合中
			UserDto userDto = new UserDto();
			userDto.setUserId(user.getUserId());
			userDto.setUsername(user.getUsername());
			userDto.setEmail(user.getEmail());
			userDto.setActive(user.getActive());
			userDto.setRole(user.getRole());
			// 存入(新增使用者)資料庫: 調用 userDao.addUser(user)
			userDtos.add(userDto);
		}
		
		return userDtos;
	}
	
	// 新增使用者
	public void appendUser(String username, String password, String email, String role) {
		String salt = Hash.getSalt(); // 得到隨機鹽
		String passwordHash = Hash.getHash(password, salt); // 得到 hash
		Boolean action = false; // email 尚未驗證成功
		// 根據上列參數封裝到 User 物件中
		User user = new User();
		user.setUsername(username);
		user.setPasswordHash(passwordHash);
		user.setSalt(salt);
		user.setEmail(email);
		user.setActive(action);
		user.setRole(role);
		// 存入(新增使用者): 調用 userDao.addUser(user)
		userDao.addUser(user);
	}
	
	// 刪除使用者
	public void deleteUser(String userId) {
		/*
		為什麼userId要設成String而非int:
			從前端或其他服務層傳入的資料常是 String 格式，例如 URL 路徑參數或表單輸入值。
			這樣設定可以避免在接收時多次轉換類型的麻煩，也能直接接收 String 格式的 userId。		
		 */	
		
		userDao.deleteUser(Integer.parseInt(userId));
	}
	
	// 取得指定使用者
	public UserDto getUser(String username) {
		User user = userDao.getUser(username);
		if(user == null) {
			return null;
		}
		// 一個一個將 User 轉成 UserDto
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getUserId());
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		userDto.setActive(user.getActive());
		userDto.setRole(user.getRole());
		return userDto;
	}
	
	// 修改使用者
	public void updateUser(String userId, String active, String role) {
		if(!active.isEmpty()) {
			userDao.updateUserActive(Integer.parseInt(userId), Boolean.parseBoolean(active));
		}
		if(!role.isEmpty()) {
			userDao.updateUserRole(Integer.parseInt(userId), role);
		}
	}
	
	// 變更密碼
	public void updatePassword(Integer userId, String username, String oldPassword, String newPassword) throws UserNotFoundException, PasswordInvalidException {
		User user = userDao.getUser(username);
		if(user == null) {
			throw new UserNotFoundException();
		}
		
		// 比對修改之前的 password 是否正確 
		String oldPasswordHash = Hash.getHash(oldPassword, user.getSalt());
		if(!oldPasswordHash.equals(user.getPasswordHash())) {
			throw new PasswordInvalidException("原密碼輸入錯誤");
		}
		
		// 產生新密碼的 Hash
		String newPasswordHash = Hash.getHash(newPassword, user.getSalt());
		// 密碼 Hash 修改
		userDao.updatePasswordHash(userId, newPasswordHash);
	}
	
}

