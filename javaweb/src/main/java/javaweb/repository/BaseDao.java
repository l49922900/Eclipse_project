/*
BaseDao (基礎資料訪問類):

	用途：BaseDao建立和管理與資料庫的連線，使用靜態區塊（static）初始化連線，提供連線給繼承它的類別使用。
	協作：UserDaoImpl繼承BaseDao，使用其中的conn進行所有與資料庫相關的操作（如查詢和新增資料）。
 */



package javaweb.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDao {
	protected static Connection conn;
	
	static {
		String username = "root";
		String password = "abc123";
		String dbUrl = "jdbc:mysql://localhost:3306/web?serverTimezone=Asia/Taipei&characterEncoding=utf-8&useUnicode=true";
		// 建立連線
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl, username, password);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}