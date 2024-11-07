/*
登入系統執行流程:


 (輸入帳密)    (判斷行為)          (嘗試呼叫UserDao取得hash)    (嘗試從資料庫取得hash)
登入介面--->login servlet ---> CertService(執行驗證業務邏輯)--->UserDao(存取資料方法)--->users(資料庫)
				 |       <---        (驗證密碼)           <---   (傳回hash)        <---
				 |
				 |
				 |
				 |
 登入成功 ---------


 */


package javaweb.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 將 session 失效
		HttpSession session = req.getSession();
		session.invalidate(); // 所有 session 失效, sessionId 會重發
		//session.setAttribute("userCert", null); // 只讓憑證的 session 變數失效, 但 sessionId 不會重發
		/*
		若是完整登出：建議使用 session.invalidate()，確保所有敏感資訊都清除，並重新生成 sessionId 提升安全性。
		若只需部分登出或移除特定變數：可使用 session.setAttribute("userCert", null) 來達成，避免影響其他 session 狀態。
		 */
		
		String message = "登出成功";
		req.setAttribute("message", message);
		req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
	}
}