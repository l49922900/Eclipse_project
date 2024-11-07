<%-- 
 product.jsp (前端視圖)
 
	用途：product.jsp是前端頁面，用來顯示Producy相關數據，負責與用戶進行互動。
 --%>


<%@ page import="javaweb.model.dto.UserDto"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
<%--
這行用於引入 JSTL（Java Server Pages Standard Tag Library）的「核心庫」。
JSTL 是 JSP 用於處理資料的標準標籤庫，
prefix="c" 表示可以使用 <c:xxx> 格式來調用 JSTL 中的各種標籤，
如 <c:forEach>、<c:if> 等。此處專門用來在前端處理迴圈、條件判斷等邏輯。 
--%>



<%@ taglib uri="jakarta.tags.fmt" prefix="f" %> <!-- 格式化庫 -->
    
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Product</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
				<%-- 
		用來引入Pure.css框架。Pure.css是一個輕量級的CSS框架，提供簡潔的響應式CSS模組
		你可以用它來快速建立網頁的基本樣式，比如按鈕、表單、導航欄等元素。
		 --%>
		
		
		<link rel="stylesheet" href="/javaweb/css/buttons.css">
	</head>
	<body>
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu.jspf" %>
		
		<!-- body content -->
		<div style="padding: 15px">
			<form class="pure-form" method="post" action="/javaweb/user/add">
				<fieldset>
					<legend>商品新增</legend>
					Homework	  
				</fieldset>
			</form>
			<p />
			<div class="pure-form">
				<fieldset>
					<legend>商品列表</legend>
					<table class="pure-table pure-table-bordered">
						<thead>
				<%--
				<thead> 是 HTML 表格的一部分，包含表頭資訊。
				 --%>
					
						
							<tr>
								<th>商品</th><th>商品名稱</th><th>商品單價</th><th>商品庫存</th>
							</tr>
						</thead>
						<c:forEach var="productDto" items="${ productDtos }">
			<%--
			<c:forEach> 是 JSTL 提供的標籤，用來迭代集合（如 List）。
			var="product" 代表每次迭代的項目都將儲存在 product 變數中，
			而 items="${ productList }" 指定了要迭代的數據來源為 productList。
			 
			 每次迭代都會生成一行 <tr>，顯示商品的 ID、名稱、庫存、修改和刪除按鈕。
			 --%>		
					
							
							<tr>
								<td align="center">${ productDto.productId }</td>
								<td>${ productDto.productName}</td>
								<td align="right">
									<f:formatNumber value="${ productDto.price }" type="currency" maxFractionDigits="0" />
								</td>
								<td align="right">
									<f:formatNumber value="${ productDto.stockQuantity }" />
								</td>
							</tr>
						</c:forEach>
					</table>
				</fieldset>
			</div>
		</div>
	</body>
</html>