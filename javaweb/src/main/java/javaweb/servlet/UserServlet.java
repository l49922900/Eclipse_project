/*

MVC開發順序:
	1.entity
	2.Dto
	3.baseDao
	4.Dao
	5.DaoImpl
	6.Service
	7.Servlet
	8.jsp

 */




/*
UserServlet (控制器)

	用途：UserServlet是控制器（Controller），負責處理來自前端的請求，
	並將請求轉發給UserService進行業務邏輯處理，最後將結果返回前端。
	
	協作：
		當用戶發出GET請求（如查詢所有用戶），UserServlet會呼叫UserService的findAll()方法來獲取所有用戶的UserDto列表，
		並將結果存入request屬性中。然後，UserServlet將請求轉發至user.jsp，讓前端顯示用戶列表資料。
 */

/*
 
 MVC + 自訂框架
  
  request   +-------------+          +-------------+          +---------+
 ---------> | UserServlet | -------> | UserService | -------> | UserDao | ------->    MySQL
            | (Controller)| <------- |             | <------- |         | <------- (web.users)
  			+-------------+  UserDto +-------------+   User   +---------+
  			       |          (Dto)                  (Entity)
  			       |
  			       v
  			+-------------+
 <--------- |   user.jsp  |
  response	|    (View)   |
  			+-------------+                 
 
 查詢全部: GET  /user, /users
 新增單筆: POST /user/add
 查詢單筆: GET  /user/get?username=admin
 修改單筆: POST /user/update?userId=1
 刪除單筆: GET  /user/delete?userId=1 
 
 * */

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
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javaweb.exception.PasswordInvalidException;
import javaweb.exception.UserNotFoundException;
import javaweb.model.dto.UserCert;
import javaweb.model.dto.UserDto;
import javaweb.service.UserService;

@WebServlet(urlPatterns = {"/user/*", "/users"})
/*
@WebServlet:

 	註解用於定義一個 Servlet 可以處理的 URL 路徑
 	@WebServlet(urlPatterns = {"/user/*", "/users"}) 設定了兩個匹配模式：
 	
 	/users：當路徑為 /users 時，getPathInfo() 的結果是 null，
 	這表示查詢所有用戶的需求。此情況下，UserServlet 會呼叫 UserService 取得所有用戶的清單，並將結果顯示在 user.jsp。
 	
 	 /user/*：匹配 /user 開頭並帶有其他子路徑的請求。當路徑為 /user/add 或 /user/get 等時，
 	 getPathInfo() 會返回 /add 或 /get。程式可根據 pathInfo 的值判斷具體操作並執行相應的邏輯
 	 
 	 當使用者在瀏覽器中請求 /javaweb/product/add 時，這個請求 URL 會被 @WebServlet 註解攔截並指向 ProductServlet，
 	 同時 getPathInfo() 就會返回 /add。
 	  */



public class UserServlet extends HttpServlet {
	
	private UserService userService = new UserService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		/*
		pathInfo:
		
			用於取得請求路徑中的額外資訊。
			當瀏覽器發送的請求匹配了 @WebServlet 中的 URL 路徑模式（如 /product/* 或 /products）時，
			pathInfo 就包含了這些指定路徑後的額外路徑。
		 */
		
		if(pathInfo == null || pathInfo.equals("/*")) { // 查詢全部
			List<UserDto> userDtos = userService.findAll();
			req.setAttribute("userDtos", userDtos);
			//setAttribute 方法是用來將資料放入 HttpServletRequest 的屬性中。這樣一來，資料便能傳遞到 JSP 或其他頁面供顯示使用。
			/*
			userDtos 是從 userService.findAll() 中取得的用戶資料列表（List<UserDto>），
			並在這裡被設置成 request 屬性中的 userDtos。這樣，前端頁面 user.jsp 就能透過 request 物件來取得這份資料進行顯示。
			 */
			
			
			req.getRequestDispatcher("/WEB-INF/view/user.jsp").forward(req, resp);
			/*
			getRequestDispatcher 方法會返回一個 RequestDispatcher 物件，用來處理頁面跳轉或轉發請求的操作。
			這裡 "/WEB-INF/view/user.jsp" 指的是要轉發至的目標 JSP 頁面，也就是 user.jsp
			forward(req, resp) 方法將 request 和 response 物件一併轉發到 user.jsp，讓該頁面能夠讀取 request 中的 userDtos 屬性，
			從而顯示所有用戶的列表資料			
			 */
			
			
			return;
		} else if(pathInfo.equals("/delete")) { // 刪除
			/*
			其實判斷pathInfo是否等於/delete的程式碼放在dodelete比較好，只是老師放在dodelete
			 */
			
			String userId = req.getParameter("userId");
			userService.deleteUser(userId);
			// 刪除完畢之後, 重新執行指定頁面
			resp.sendRedirect("/javaweb/user");
			return;
		} else if(pathInfo.equals("/get")) { // 取得 user 資料並導入到修改頁面
			String username = req.getParameter("username");
			/*
			"username":
			
				是表單或 URL 中的參數名稱。在你的 user.jsp 頁面上，
				有一個輸入欄位名稱被設定為 username
				當表單透過 POST 或 GET 提交時，這些欄位名稱會成為請求的參數名稱，
				因此在 Servlet 中可用 req.getParameter("username") 去取得對應的參數值。
			 */
			
			
			
			
			UserDto userDto = userService.getUser(username);
			// 將必要資料加入到 request 屬性中以便交由 jsp 進行分析與呈現
			req.setAttribute("userDto", userDto);
			// 內重導到 user_update.jsp，修改頁面
			req.getRequestDispatcher("/WEB-INF/view/user_update.jsp").forward(req, resp);
			return;
		} else if(pathInfo.equals("/update/password")) { // 修改密碼頁面
			req.getRequestDispatcher("/WEB-INF/view/update_password.jsp").forward(req, resp);
			/*
			外重導(External Redirection):
			
				1.會返回 302 狀態碼給瀏覽器
				2.瀏覽器會收到新的 URL 並發起新的請求
				3.URL 會改變
				4.內重導只需一次請求,外重導需要兩次
				5.內重導可以保留 request 範圍的屬性,外重導不行
				6.外重導適合跳轉到其他網站或需要改變 URL 的情況
				
			
			內重導(Internal Redirection):
			
				1.會返回 302 狀態碼給瀏覽器
				2.瀏覽器會收到新的 URL 並發起新的請求
				3.URL 會改變
				4.內重導只需一次請求,外重導需要兩次
				5.內重導可以保留 request 範圍的屬性,外重導不行
				6.內重導適合在同一應用內部轉發請求
			 */
		
			return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		// 取得表單資料
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String role = req.getParameter("role");
		String active = req.getParameter("active"); // update 專用
		String userId = req.getParameter("userId"); // update 專用
		String oldPassword = req.getParameter("oldPassword"); // update/password 專用
		String newPassword = req.getParameter("newPassword"); // update/password 專用
		
		switch (pathInfo) {
			case "/add":
				userService.appendUser(username, password, email, role);
				break;
			case "/update":
				userService.updateUser(userId, active, role);
				break;
			case "/update/password":
				// 修改密碼需要在已登入的環境下
				HttpSession session = req.getSession();
				try {
					UserCert userCert = (UserCert)session.getAttribute("userCert"); // 取得 session 登入憑證
/*
(UserCert) 是一個「強制轉型」（Casting）的語法，它的作用是將 session.getAttribute("userCert") 的回傳值，強制轉型成 UserCert 類型。
session.getAttribute("userCert") 方法的回傳類型是 Object，為了讓編譯器確認這個物件的類型，
必須使用強制轉型將它轉成 UserCert，這樣才能正常賦值給變數 userCert。
 */					
					
					userService.updatePassword(userCert.getUserId(), userCert.getUsername(), oldPassword, newPassword);
					req.setAttribute("message", "密碼更新成功");
					req.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(req, resp);
				} catch (Exception e) {
					req.setAttribute("message", e.getMessage());
					req.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(req, resp);
				}
				return;
		}
		
		
		resp.sendRedirect("/javaweb/user");
		/*
		外重導(External Redirection):
		
			1.會返回 302 狀態碼給瀏覽器
			2.瀏覽器會收到新的 URL 並發起新的請求
			3.URL 會改變
			4.內重導只需一次請求,外重導需要兩次
			5.內重導可以保留 request 範圍的屬性,外重導不行
			6.外重導適合跳轉到其他網站或需要改變 URL 的情況
			
		
		內重導(Internal Redirection):
		
			1.會返回 302 狀態碼給瀏覽器
			2.瀏覽器會收到新的 URL 並發起新的請求
			3.URL 會改變
			4.內重導只需一次請求,外重導需要兩次
			5.內重導可以保留 request 範圍的屬性,外重導不行
			6.內重導適合在同一應用內部轉發請求
		 */
		
	}
	
		///////////////////////////////////////////////////////
			
		/*
		sendError:
		
		用來將錯誤訊息或 HTTP 狀態碼傳送回客戶端（例如瀏覽器）。
		
		語法:
		
		void sendError(int sc) throws IOException
		void sendError(int sc, String msg) throws IOException
		
		第一個方法接收一個整數參數 sc，表示 HTTP 狀態碼。
		第二個方法接收兩個參數：sc 為 HTTP 狀態碼，msg 為錯誤訊息。
		
		
		注意事項:
		
		1.sendError 方法在被呼叫後，伺服器將停止處理剩下的請求，並將錯誤碼和訊息返回給客戶端。
		2.若使用 sendError 後再進行寫入回應的操作，可能會引發例外，因為回應已經被提交。
		3.也可以使用自訂錯誤
		
		
		例子:
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "您無權限訪問此頁面");
		}					
		當使用者沒有權限訪問資源時，可以傳送 403 錯誤並附加錯誤訊息
		*/
		
		/////////////////////////////////////////////////////////
		
		/*
		getHeader:
		
		String getHeader(String headerName):
		取得headerName 變數名稱的 head 內容值。
		
		Enumeration getHeaders(String headerName):
		取得headerName 的集合資料
		
		Enumeration getHeaderNames():
		取得所有Header 名稱
		
		int getIntHeader (String headerName):
		1.取得headerName 的整數值，通常在處理 HTTP 請求時，我們可能需要取得標頭中的某些值，例如 Content-Length、Age 等數值標頭
		2.getIntHeader 會自動進行型別轉換，如果標頭的值無法轉換為整數，則會拋出異常，因此確保標頭值是數值格式非常重要。
		
		
		例子:
		
		Enumeration headers = req.getHeaderNames();
		
		
		while(headers.hasMoreElements()) {
		//判斷是否還有Header元素
		
		String header = (String)headers.nextElement();
		//取得當下的Header元素
		
		String value = req.getHeader(header);
		//取得Header 的內容值
		*/
	
	
	
}