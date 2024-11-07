<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>訂購商品</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/javaweb/css/buttons.css">
	</head>
	<body>
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu.jspf" %>
		<%-- 
		這行代碼的目的是將 menu.jspf 文件中的內容，例如網站的導航選單或菜單欄，
		加入到當前頁面中。這樣可以讓多個頁面共用相同的導航模組，而不必在每個頁面中重複編寫相同的 HTML。 
		--%>
		
		
		<!-- body content -->
		<div style="padding: 15px">
			<form class="pure-form" method="post" action="/javaweb/order">
				<fieldset>
					<legend>訂購商品</legend>
					<table class="pure-table">
						<thead>
							<tr>
								<th>商品 id</th><th>商品名稱</th><th>商品單價</th><th>目前庫存</th><th>下單數量</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>
									1
									<input type="hidden" name="productId" value="1" >
			<%--
			每一行商品都設定了一個隱藏的 productId 欄位，這樣當表單提交時，
			伺服器端就能夠識別出使用者選擇了哪些商品。這樣的設計可以在處理訂單時獲取商品編號 (productId)，
			再結合使用者輸入的數量 (amount) 來進行數據處理或儲存。
			 --%>	
	
								</td>
								<td>PC</td>
								<td>
									30000
									<input type="hidden" name="unitPrice" value="30000" >
			<%--
			type="hidden"：定義了這個欄位為隱藏欄位，不會在頁面上顯示，但會包含在表單提交時的數據中。
			name="unitPrice"：指定這個欄位的名稱為 unitPrice，它將會成為表單提交時的 key 值。這樣伺服器端就可以通過 unitPrice 這個名稱來取得該欄位的值。
			value="15000"：設置此欄位的值為 15000，在提交時，這個數值會一同傳送給伺服器，用來表示商品的單價。	
			
			之所以在這裡設置unitPrice與單價，而不從伺服器抓去價格，是為了防止從伺服器抓取價格與呈現到前端時的時間落差(即使只有一秒也不行)			
			 --%>						
									
								</td>
								<td>50</td>
								<td><input type="number" name="amount" style="width: 100px" value="0" min="0" max="50"></td>
							</tr>
							<tr>
								<td>
									2
									<input type="hidden" name="productId" value="2" >
								</td>
								<td>Mobile</td>
								<td>
									15000
									<input type="hidden" name="unitPrice" value="15000" >
								</td>
								<td>100</td>
								<td><input type="number" name="amount" style="width: 100px" value="0" min="0" max="100"></td>
							</tr>
							<tr>
								<td>
									3
									<input type="hidden" name="productId" value="3" >
								</td>
								<td>MusicBox</td>
								<td>
									3000
									<input type="hidden" name="unitPrice" value="3000" >
								</td>
								<td>200</td>
								<td><input type="number" name="amount" style="width: 100px" value="0" min="0" max="200"></td>
							</tr>
							<tr>
								<td>
									4
									<input type="hidden" name="productId" value="4" >
								</td>
								<td>Pad</td>
								<td>
									20000
									<input type="hidden" name="unitPrice" value="20000" >
								</td>
								<td>75</td>
								<td><input type="number" name="amount" style="width: 100px" value="0" min="0" max="75"></td>
							</tr>
							<tr>
								<td>
									5
									<input type="hidden" name="productId" value="5" >
								</td>
								<td>Watch</td>
								<td>
									8000
									<input type="hidden" name="unitPrice" value="80000" >
								</td>
								<td>150</td>
								<td><input type="number" name="amount" style="width: 100px" value="0" min="0" max="150"></td>
							</tr>
						</tbody>
					</table>
					<p>
					<button type="reset" class="button-warning pure-button">Reset</button>
					<button type="submit" class="button-success pure-button">Submit</button>	  
				</fieldset>
			</form>
		</div>
		<p />
		
	</body>
</html>