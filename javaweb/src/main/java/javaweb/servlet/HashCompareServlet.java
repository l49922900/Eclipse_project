package javaweb.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javaweb.utils.Hash;

@WebServlet("/hash/compare")
/*
將 HashCompareServlet 類別綁定到 /hash/compare 的 URL 上
讓伺服器知道當有請求進入 /hash/compare 時，要由這個 Servlet 來處理。
 */



public class HashCompareServlet extends HttpServlet {
	
	private static Map<String, String> users = new ConcurrentHashMap<>();
	//這個靜態變數用來存放使用者帳號與雜湊過後的密碼
	
	static {
		users.put("john", "A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ="); // 密碼:1234(不含鹽)
		users.put("mary", "+GOLl5svT3k92229GX4O4lp6bqMrCuIvXjxdEZ2DnnU="); // 密碼:5678(不含鹽)
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/view/hash_compare.jsp").forward(req, resp);
		/*
		這行程式碼是將請求轉發到特定的 JSP 頁面 (hash_compare.jsp)，以便進一步處理和顯示結果
		
		getRequestDispatcher(String path):這個物件可以被用來將請求轉發 (forward) 到伺服器上的另一個資源
		
		forward() 方法:會將請求和回應物件傳遞給指定的目標資源，而不是讓使用者重新發送請求。
		
		
		此外，轉發操作是在伺服器端完成的，這樣可以使使用者無法直接訪問 /WEB-INF/view/hash_compare.jsp 路徑（因為直接訪問會遭到拒絕），
		但透過伺服器的轉發請求，hash_compare.jsp 頁面仍然可以被訪問。
		
		/WEB-INF/ 是在 Java Web 應用程式中具有特殊作用的目錄。
		 * 此資料夾通常用來存放應用程式的設定檔案、JSP 檔案或其他不應直接被外界訪問的資源。
		 */
		
		
		/*
		HttpServletResponse.sendRedirect
			
		功能:重導到某個網頁
		
		ex：res.sendRedirct http:// www.yahoo.com
		
		sendRedirect不是由 Server 發動的而是由 browser 所發動。
		
		
		注意：
		
			假設response 已經 committed 完成
			例如已經利用PrintWriter 物件將資料送給 client 端 之後就不能呼叫 sendRedirect () 
			否則 Servlet Container 會丟出一個 IllegalStateException 例外。
		
		
		錯誤使用的例子:
		
		PrintWriter out;
		String s = req.getParameter("s");
		out = res.getWriter();
		out.println(s);
		out.flush();
		res.sendRedirect("http://www.yahoo.com");
		
		//丟出例外IllegalStateException
		
		 */
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		//取得請求中的使用者名稱與密碼
		
		
		String hashPassword = users.get(username);
		//根據輸入的使用者名稱從 users Map 中取得對應的雜湊密碼。
		
		
		PrintWriter out;
		out = resp.getWriter();
		/*
		PrintWriter :
		
		我們常常使用 PrintWriter 來向 HTTP 回應中寫入資料，
		這些資料會被發送到客戶端，通常是瀏覽器。
		PrintWriter out; 宣告了一個 PrintWriter 類型的變數 out
		之後會用 getWriter() 方法初始化這個變數。
		
		getWriter():
		
		這個方法會返回一個 PrintWriter 物件，
		這個物件是專門用來將文字內容寫入 HTTP 回應的輸出流（Output Stream）。
		
		 */
		
		
		if(hashPassword == null) {			
			
			out.println("user not found !");
			return;
		}
		
		if(Hash.getHash(password).equals(hashPassword)) {
//這行使用 Hash 類別中的 getHash 方法，將輸入的密碼進行雜湊並與 hashPassword 進行比對。
			
			
			out.println("Login OK !");
			return;
		}
		out.println("Invalid password");
		
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
			}
	 */
	
}
