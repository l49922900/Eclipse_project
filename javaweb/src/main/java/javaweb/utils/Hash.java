package javaweb.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;


public class Hash  {
	
	// 產生含鹽雜湊
	public static String getHash(String password, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			//建立 SHA-256 雜湊演算法的 MessageDigest 實例。
			
			
			md.update(salt.getBytes());
			//將鹽值轉為位元組並添加到雜湊計算的初始狀態。
			
			
			byte[] bytes = md.digest(password.getBytes());
			//對密碼進行雜湊，返回雜湊的位元組數組。
			
			return Base64.getEncoder().encodeToString(bytes);
			//將雜湊結果轉為 Base64 格式的字串，便於儲存與顯示。
			//Base64 格式是一種用於將二進位資料編碼為文字格式的方式，常用於資料傳輸和儲存。
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	// 產生鹽
	public static String getSalt() {
		SecureRandom secureRandom = new SecureRandom();
		//用來生成安全的隨機數。
		
		byte[] salt = new byte[16];
		secureRandom.nextBytes(salt);
		//將生成的隨機數放入 salt 陣列中。
		
		
		return Base64.getEncoder().encodeToString(salt);
		//將隨機數組轉換為 Base64 字串。
	}
	
	
	
	
	// 產生雜湊
	public static String getHash(String password) {
		try {
			
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			//建立 SHA-256 雜湊演算法的 MessageDigest 實例。
			
			
			byte[] bytes = md.digest(password.getBytes());
			//對密碼直接進行雜湊。
			
			return Base64.getEncoder().encodeToString(bytes);
			//將結果轉為 Base64 格式。
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
