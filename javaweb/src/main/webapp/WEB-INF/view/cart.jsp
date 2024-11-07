<%@ page import="javaweb.model.dto.UserDto"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> <!-- 核心庫 -->
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %> <!-- 格式化庫 -->
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %> <!-- 功能庫 -->

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Cart</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/javaweb/css/buttons.css">
	</head>
	<body>
		<!-- menu bar include -->
		<%@ include file="/WEB-INF/view/menu.jspf" %>
		
		<!-- body content -->
		<div style="padding: 15px">
			<div class="pure-form">
				<fieldset>
					<legend>${ userCert.username } 的 Cart 購物車</legend>
					<table class="pure-table pure-table-bordered">
						<thead>
							<tr>
								<th>訂單 Id</th><th>使用者 Id</th><th>訂單日期</th>
								<th>商品 Id</th><th>商品名稱</th><th>商品單價</th>
								<th>訂購數量</th><th>小計</th><th>訂單狀態</th>
							</tr>
						</thead>
						<tbody>
							<!-- 初始化"總計"變數 -->
							<c:set var="total" value="0" />
							<!-- 設定 locale -->
							<fmt:setLocale value="${ locale }" />
							<c:forEach var="orderDto" items="${ orderDtos }">
								<tr>
									<td align="center">${ orderDto.orderId }</td>
									<td align="center">${ orderDto.userId }</td>
									<td>${ orderDto.orderDate }</td>
									<td align="center">${ orderDto.productId }</td>
									<td>${ orderDto.productName }</td>
									<td align="right">
										<fmt:formatNumber value="${ orderDto.unitPrice }" type="currency" maxFractionDigits="0" />
									</td>
									<td align="right">${ orderDto.quantity }</td>
									<td align="right">
										<fmt:formatNumber value="${ orderDto.subtotal }" type="currency" maxFractionDigits="0" />
									</td>
									<td>${ orderDto.orderStatus }</td>
								</tr>
								<!-- 累計，隨著迴圈執行total不斷加上subtotal -->
								<c:set var="total" value="${ total + orderDto.subtotal }" />
							
							
							</c:forEach>
							<!-- 顯示總計 -->
							<tr>
								<td colspan="6"></td>
				<%--
				表示此欄位要橫跨 6 個欄位（colspan），並且不包含任何內容。
				這種設置常用於表格中的佔位符，主要是讓總計、結帳等資訊可以在同一列的較右側位置顯示，
				增加頁面美觀度和資訊排版的整潔性。
				 --%>					
			
								<td align="center">總計</td>
								<td align="right">
									<strong>
										<fmt:formatNumber value="${ total }" type="currency" maxFractionDigits="0" />
				<%--
				這行使用 JSTL 格式化標籤（fmt）將 total 金額格式化為貨幣（currency）的顯示格式
				value="${ total }"：指定要格式化的變數值，在這裡是總計金額 total。
				type="currency"：表示將數值格式化為貨幣形式，會自動加入貨幣符號（依據所設定的 locale）。
				maxFractionDigits="0"：設定最多顯示的小數位數，0 表示不顯示小數部分。
				 --%>			
										
									</strong>
								</td>
								<td></td>
							</tr>
							<!-- 結帳/取消 -->
							<tr style="display: ${ fn:length(orderDtos) == 0 ? 'none' : '' };">
								<td colspan="6"></td>
								<td align="center">結帳</td>
								<td><a href="/javaweb/order/finish" class="button-success pure-button">結帳</a></td>
								<td><a href="/javaweb/order/cancel" class="button-warning pure-button">取消</a></td>
							</tr>
						</tbody>
					</table>
				</fieldset>
			</div>
		</div>
	</body>
</html>