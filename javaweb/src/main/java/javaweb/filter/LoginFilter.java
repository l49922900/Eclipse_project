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


package javaweb.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/fruit/*", "/user/update/password", "/products","/order/*","/cart"}) 
// 設定要過濾/攔截的路徑，也就是在前往這些路徑前會先用LoginFilter攔截，要先登入
//***注意這裡是@WebFilter

public class LoginFilter extends HttpFilter {
	/*
	Filter（過濾器）:
	
		是 Java Web 應用程式中的一種元件，用來攔截並處理 Servlet 請求和回應。
		在處理請求（例如進入某些頁面或資源）之前，或是在回應發送給使用者之前，
		Filter 可以介入執行特定邏輯，例如驗證、記錄請求資訊或對內容進行修改。
		
	Filter 的工作流程:
	
		當請求進入應用程式時，過濾器會在請求抵達 Servlet 或其他目標資源之前先行執行。
		當 Filter 判斷符合條件並完成檢查後，它可以選擇：
		
			1.放行請求：呼叫 chain.doFilter(request, response);，讓請求繼續執行，交給其他過濾器或目標資源處理。
			2.攔截請求：直接回應或重新導向，不再傳遞給後續的資源或處理邏輯。
	 */
	
	
	
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 編碼
		//response.setCharacterEncoding("UTF-8");
		//response.setContentType("text/plain;charset=utf-8");
		
		System.out.println("攔截過濾 URL :" + request.getRequestURL());
		System.out.println("攔截過濾 URI :" + request.getRequestURI());
		
		// 判斷是否有憑證
		HttpSession session = request.getSession();
		if(session.getAttribute("userCert") == null) {
			
			
			session.setAttribute("redirectURL", request.getRequestURL());
			/*
			將當前請求的 URL 儲存到使用者的 Session 物件中，
			並將這個 URL 設為 redirectURL 的屬性。這樣做的目的是在用戶成功登入後，
			可以根據這個儲存的 redirectURL 重新導向回最初請求的頁面，讓使用者不必再次手動找到該頁面。
			
			 */
			
			
			
			
			response.sendRedirect("/javaweb/login"); // 自動重導到登入頁面
		} else {
			chain.doFilter(request, response); // 放行
			/*
			請求（request）和回應（response）傳遞給下一個過濾器或最終的目標資源。
			當使用者已經登入並擁有 userCert 憑證時，這行程式碼會允許請求繼續被處理。
			它類似於「放行」的概念，讓請求進一步執行。若沒有這行，請求將無法傳遞到其他資源（例如控制器或 JSP 頁面）。
			
			
			chain :
			
			是 FilterChain 類別的一個實例。FilterChain 是 Servlet API 中的一個介面（interface），
			用於將請求和回應物件傳遞給下一個過濾器（Filter）或最終的資源（例如，Servlet 或 JSP）。
			當呼叫 chain.doFilter(request, response) 時，系統會依序執行下一個過濾器或直接執行目標資源。
			 */
			
			
		}
		
	}
	
}