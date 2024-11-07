/*
Servlet (控制器)

	用途：Servlet是控制器（Controller），負責處理來自前端的請求，
	並將請求轉發給Service進行業務邏輯處理，最後將結果返回前端。
	
	協作：
		當用戶發出GET請求（如查詢所有用戶），Servlet會呼叫Service的findAll()方法來獲取所有用戶的Dto列表，
		並將結果存入request屬性中。然後，Servlet將請求轉發至jsp，讓前端顯示用戶列表資料。
 */



package javaweb.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javaweb.model.dto.ProductDto;
import javaweb.service.ProductService;


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
-- 商品 product
+------------+------------------+----------+----------------+
| product_id | product_name     | price    | stock_quantity |
+------------+------------------+----------+----------------+
| 1          | PC               | 30000.00 | 50             |
| 2          | Mobile           | 15000.00 | 100            |
| 3          | MusicBox         | 3000.00  | 200            |
| 4          | Pad              | 20000.00 | 75             |
| 5          | Watch            | 8000.00  | 150            |
+------------+------------------+----------+----------------+

 -- 創建商品表
create table if not exists product (
	product_id int primary key auto_increment comment '商品Id',
	product_name varchar(50) not null unique comment '商品名稱',
	price decimal(10, 2) not null comment '商品價格',
	stock_quantity int not null default 0 comment '商品庫存'
); 


 MVC + 自訂框架
  
  request   +----------------+             +----------------+          +------------+
 ---------> | ProductServlet | ----------> | ProductService | -------> | ProductDao | ------->    MySQL
            | (Controller)   | <---------- |                | <------- |            | <------- (web.product)
  			+----------------+  ProductDto +----------------+  Product +------------+
  			       |              (Dto)                       (Entity)
  			       |
  			       v
  			+-------------+
 <--------- | product.jsp |
  response	|    (View)   |
  			+-------------+                 
 
要有的幾個檔案:

	Dao
	DaoImpl
	Servlet
	Dto
	entity
	Service
	BaseDao
	JSP
	
 
*/ 



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
 	 
@WebServlet(value = {"/products", "/product/sales/ranking"})
public class ProductServlet extends HttpServlet{
	

private ProductService productService = new ProductService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String servletPath = req.getServletPath();
		switch (servletPath) {
			case "/product/sales/ranking":
				req.setAttribute("salesRankingMap", productService.querySalesRanking());
				req.getRequestDispatcher("/WEB-INF/view/sales_ranking.jsp").forward(req, resp);
				break;
			case "/products":
			default:
				req.setAttribute("productDtos", productService.findAllProducts());
				req.getRequestDispatcher("/WEB-INF/view/product.jsp").forward(req, resp);
		}
		
	}
	

	///////////////////以下是舊版自己寫的////////////////////////////
	
	
	
//	private ProductService productService =  new ProductService();
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String pathInfo = req.getPathInfo();
		/*
		pathInfo:
		
			用於取得請求路徑中的額外資訊。
			當瀏覽器發送的請求匹配了 @WebServlet 中的 URL 路徑模式（如 /product/* 或 /products）時，
			pathInfo 就包含了這些指定路徑後的額外路徑。
		 */
		
		
		
//		if(pathInfo == null || pathInfo.equals("/*")) {
//		
//			List<ProductDto> productList =  productService.findAll();
//			req.setAttribute("productList", productList );
			//setAttribute 方法是用來將資料放入 HttpServletRequest 的屬性中。這樣一來，資料便能傳遞到 JSP 或其他頁面供顯示使用。
			/*
			userDtos 是從 userService.findAll() 中取得的用戶資料列表（List<UserDto>），
			並在這裡被設置成 request 屬性中的 userDtos。這樣，前端頁面 user.jsp 就能透過 request 物件來取得這份資料進行顯示。
			 */
			
//			req.getRequestDispatcher("/WEB-INF/view/product.jsp").forward(req, resp);
			/*
			getRequestDispatcher 方法會返回一個 RequestDispatcher 物件，用來處理頁面跳轉或轉發請求的操作。
			這裡 "/WEB-INF/view/user.jsp" 指的是要轉發至的目標 JSP 頁面，也就是 user.jsp
			forward(req, resp) 方法將 request 和 response 物件一併轉發到 user.jsp，讓該頁面能夠讀取 request 中的 userDtos 屬性，
			從而顯示所有用戶的列表資料			
			 */
			
//			return;
//		}else if (pathInfo.equals("/get")) {
//			String productName = req.getParameter("productname");
//			ProductDto product = productService.getProduct(productName);
			/*
			"productName":
			
				是表單或 URL 中的參數名稱。在你的 product.jsp 頁面上，
				有一個輸入欄位名稱被設定為 productName
				當表單透過 POST 或 GET 提交時，這些欄位名稱會成為請求的參數名稱，
				因此在 Servlet 中可用 req.getParameter("productName") 去取得對應的參數值。
			 */
			
			

//			req.setAttribute("product", product );
//			// 內重導到 product_update.jsp，修改頁面
//			req.getRequestDispatcher("/WEB-INF/view/product_update.jsp").forward(req, resp);
//			return;
//		}else if (pathInfo.equals("/delete")) {
//			String productId = req.getParameter("productId");
//			productService.deleteProduct(productId);
//			resp.sendRedirect("/javaweb/product");
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
			
			/*
			其實判斷pathInfo是否等於/delete的程式碼放在dodelete比較好，只是老師放在dodelete
			 */
			
//			return;
//		}

		
		
		
		
		
		
//	}

//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		String pathInfo = req.getPathInfo();
//		String productId = req.getParameter("productId");
//		String productName = req.getParameter("productName");
//		String productPrice = req.getParameter("price");
//		String productStock = req.getParameter("stockQuantity");
//		
//		switch (pathInfo) {
//		case "/add":
//			productService.appendProduct(productName, productPrice, productStock);
//			break;
//		case "/update":
//			//修改的方法
//			productService.updateProduct(productId, productName, productPrice, productStock);
//			break;
//		}
//		resp.sendRedirect("/javaweb/product");
		
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
//	}	
	
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