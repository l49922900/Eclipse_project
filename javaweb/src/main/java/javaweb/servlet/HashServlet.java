

package javaweb.servlet;




import static javaweb.utils.Hash.getHash;
import static javaweb.utils.Hash.getSalt;
//這是我們自己寫的加密方法


import java.io.IOException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;;


/*
「雜湊」（Hash）:

	是一種將輸入資料透過特定函數（稱為「雜湊函數」，Hash Function）轉換成固定長度字串的技術。
	這些固定長度的字串被稱為「雜湊值」（Hash Value）或「雜湊碼」（Hash Code）。
	無論輸入資料的長度或內容為何，經過雜湊函數處理後都會得到一個特定長度的輸出，使其便於儲存、比對或作為資料查找的鍵值（Key）。




「雜湊函數」（Hash Function）保護密碼:

	透過雜湊與加鹽將轉換為一組唯一的固定長度雜湊值（Hash Value）
	然後將計算出的雜湊值與資料庫中的雜湊值比對。

*/



//雜湊加鹽與不加鹽


//指定路徑:
@WebServlet("/hash")



public class HashServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	//HttpServletRequest:包含了請求的詳細內容，如 HTTP 方法、URL 路徑、查詢參數、請求標頭、Cookie 等。
	//HttpServletResponse:用於構建和發送回應給客戶端。它包含了設置回應狀態碼、標頭資訊、以及將內容寫入回應主體（body）的功能。
		
		
		
		String password = req.getParameter("password");
		//用來從查詢字串（query string）或 URL 中取得名為 "password" 的參數
		//***HTTP 協議將所有參數作為 字串傳輸 ，因此您可能必須解析值並轉換數據類型。
		/*
		其他方法:
		
			String[] getParameterValues(String name):取得 name 參數的內容值集合
			EnumerationgetParameterNames:取的所有 client 所送來的參數名稱
		
		 */
		
		
		String hash = getHash(password);
		//呼叫 getHash(String password) 方法，產生密碼的雜湊值（未加鹽）。
		
		resp.getWriter().println("hash:" + hash);
		
		
		String salt = getSalt();
		//產生隨機鹽值
			
		resp.getWriter().println("salt:" + salt);
		//呼叫 getHash(String password, String salt) 方法進行加鹽雜湊。
		
		String hashSalt = getHash(password, salt);
		
		
		resp.getWriter().println("hashSalt:" + hashSalt);
		//用於將雜湊值寫入回應主體
	}
	
//	// 產生含鹽雜湊
//	public static String getHash(String password, String salt) {
//		try {
//			MessageDigest md = MessageDigest.getInstance("SHA-256");
//			//建立 SHA-256 雜湊演算法的 MessageDigest 實例。
//			
//			
//			md.update(salt.getBytes());
//			//將鹽值轉為位元組並添加到雜湊計算的初始狀態。
//			
//			
//			byte[] bytes = md.digest(password.getBytes());
//			//對密碼進行雜湊，返回雜湊的位元組數組。
//			
//			return Base64.getEncoder().encodeToString(bytes);
//			//將雜湊結果轉為 Base64 格式的字串，便於儲存與顯示。
//			//Base64 格式是一種用於將二進位資料編碼為文字格式的方式，常用於資料傳輸和儲存。
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	
	
	
//	// 產生鹽
//	public static String getSalt() {
//		SecureRandom secureRandom = new SecureRandom();
//		//用來生成安全的隨機數。
//		
//		byte[] salt = new byte[16];
//		secureRandom.nextBytes(salt);
//		//將生成的隨機數放入 salt 陣列中。
//		
//		
//		return Base64.getEncoder().encodeToString(salt);
//		//將隨機數組轉換為 Base64 字串。
//	}
//	
//	
//	
//	
//	// 產生雜湊
//	public static String getHash(String password) {
//		try {
//			
//			MessageDigest md = MessageDigest.getInstance("SHA-256");
//			//建立 SHA-256 雜湊演算法的 MessageDigest 實例。
//			
//			
//			byte[] bytes = md.digest(password.getBytes());
//			//對密碼直接進行雜湊。
//			
//			return Base64.getEncoder().encodeToString(bytes);
//			//將結果轉為 Base64 格式。
//	
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
}
