<%@page import="javaweb.model.dto.UserDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	UserDto userDto = (UserDto)request.getAttribute("userDto");
%> 
<%-- 
從UserServlet的req.setAttribute("userDto", userDto ); 
傳送過來的userDto
--%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>User 修改</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
		<link rel="stylesheet" href="/javaweb/css/buttons.css">
	</head>
	<body style="padding: 15px">
		<table>
			<td valign="top">
				<form class="pure-form" method="post" action="/javaweb/user/update">
				<%-- http方法使用post，submit時執行/javaweb/user/update --%>
					<fieldset>
						<legend>User 修改</legend>
						序號: <input type="text" name="userId" value="<%=userDto.getUserId() %>" readonly /><p /> 
						帳號: <input type="text" name="username" value="<%=userDto.getUsername() %>" readonly /><p /> 
						電郵: <input type="email" name="email" value="<%=userDto.getEmail() %>" readonly /><p />
						狀態: <input type="radio" name="active" value="true"  <%=userDto.getActive()?"checked":"" %> />True
							 <input type="radio" name="active" value="false" <%=userDto.getActive()?"":"checked" %>  />False <p />
						權限: <select name="role">
								<option value="ROLE_ADMIN" <%=userDto.getRole().equals("ROLE_ADMIN")?"selected":"" %>>ADMIN</option>
								<option value="ROLE_USER"  <%=userDto.getRole().equals("ROLE_USER")?"selected":"" %>>USER</option>
							  </select><p />
						<button type="submit" class="button-success pure-button">Update</button>
						<%-- 按下更新後，輸入欄位的值會回傳到UserServlet的/update方法，執行更新 --%>  
					</fieldset>
				</form>
			</td>
			<td style="padding-left: 30px" valign="top">
				<form class="pure-form" method="post" action="/javaweb/user/update/password" >
					<fieldset>
						<legend>User 修改密碼(請自行實現)</legend>
						<input type="hidden" name="userId" value="<%=userDto.getUserId() %>" readonly /><p /> 
						<input type="hidden" name="username" value="<%=userDto.getUsername() %>" readonly /><p />
						舊密碼: <input type="password" name="password"><p /> 
						新密碼: <input type="password" name="newPassword"><p />
						<button type="submit" class="button-success pure-button">Update Password</button>	  
					</fieldset>
				</form>
			</td>
		</table>
		
	</body>
</html>