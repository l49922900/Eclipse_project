<%-- 
 user.jsp (前端視圖)
 
	用途：user.jsp是前端頁面，用來顯示User相關數據，負責與用戶進行互動。
 --%>

<%@ page import="javaweb.model.dto.UserDto"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> 
<%--
這行用於引入 JSTL（Java Server Pages Standard Tag Library）的「核心庫」。
JSTL 是 JSP 用於處理資料的標準標籤庫，
prefix="c" 表示可以使用 <c:xxx> 格式來調用 JSTL 中的各種標籤，
如 <c:forEach>、<c:if> 等。此處專門用來在前端處理迴圈、條件判斷等邏輯。 
--%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>User</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<%-- 
		用來引入Pure.css框架。Pure.css是一個輕量級的CSS框架，提供簡潔的響應式CSS模組
		你可以用它來快速建立網頁的基本樣式，比如按鈕、表單、導航欄等元素。
		 --%>
		
		<link rel="stylesheet" href="/javaweb/css/buttons.css">
	</head>
	<body style="padding: 15px">
		<form class="pure-form" method="post" action="/javaweb/user/add">
			<fieldset>
				<legend>User 新增</legend>
				帳號: <input type="text" name="username" placeholder="請輸入 username" required /><p /> 
				密碼: <input type="text" name="password" placeholder="請輸入 password" required /><p /> 
				電郵: <input type="email" name="email" placeholder="請輸入 email" required /><p />
				權限: <select name="role">
						<option value="ROLE_ADMIN">ADMIN</option>
						<option value="ROLE_USER">USER</option>
					  </select><p />
				<button type="reset" class="button-warning pure-button">Reset</button>
				<button type="submit" class="button-success pure-button">Submit</button>	  
			</fieldset>
		</form>
		<p />
		<div class="pure-form">
			<fieldset>
				<legend>User 列表</legend>
				<table class="pure-table pure-table-bordered">
					<thead>
						<tr>
							<th>ID</th><th>帳號</th><th>郵件</th><th>action</th><th>角色(權限)</th>
							<th>修改</th><th>刪除</th>
						</tr>
					</thead>
					<c:forEach var="userDto" items="${ userDtos }">
			<%--
			<c:forEach> 是 JSTL 提供的標籤，用來迭代集合（如 List）。
			var="product" 代表每次迭代的項目都將儲存在 product 變數中，
			而 items="${ productList }" 指定了要迭代的數據來源為 productList。
			 
			每次迭代都會生成一行 <tr>，顯示商品的 ID、名稱、庫存、修改和刪除按鈕。
			 --%>
					
					
						<tr>
							<td>${ userDto.userId }</td>
							<td>${ userDto.username}</td>
							<td>${ userDto.email }</td>
							<td>${ userDto.active }</td>
							<td>${ userDto.role }</td>
							<td><a href="/javaweb/user/get?username=${ userDto.username }" class="button-secondary pure-button">修改</a></td>
							<%-- 
							為什麼不直接連接到user_update修改頁面，還要經過一層/get:
							
							/get這一層的路徑主要作用：
							
								1.資料查詢與預處理:用戶點擊「修改」按鈕時，這個連結先發送 GET 請求到 /javaweb/user/get?username=某用戶名。
								控制器（UserServlet）接收到此請求後，會依照username參數去查詢該用戶的詳細資料（UserDto物件）
								並將其存入 request 屬性中。這樣，user_update.jsp在接收到請求後，
								就可以直接從 request 中取得已經載入好的UserDto資料
								如果是直接訪問頁面無法透過控制器載入用戶的詳細資料
								
								
								2.流程控制的統一性：當操作包含某些驗證、轉發或日誌記錄需求時
								使用控制器來處理請求並將資料轉發給相應的頁面能保持一致性。如果直接在前端連到修改頁面
								這些額外的處理就無法統一管理。
							 --%>
							
							
							<td><a href="/javaweb/user/delete?userId=${ userDto.userId }" class="button-error pure-button">刪除</a></td>
							<%--
							為什麼這個刪除鍵沒有像上面那麼做:
							
								1.刪除操作不需要事先載入詳細資料：刪除操作只需要一個唯一標識（如userId）即可執行刪除，
								無需從資料庫取得其他數據						
							 --%>
							
							<%-- 在沒有特別註明 method 的情況下，表單或超連結預設會使用 GET 方法發送請求。這是 HTML 和 HTTP 協議的預設行為。 --%>
						</tr>
					</c:forEach>
				</table>
			</fieldset>
		</div>
	</body>
</html>

