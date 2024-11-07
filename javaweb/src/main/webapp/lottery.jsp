<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>




<% 
 		Random random = new Random();
		int n1 = random.nextInt(10);
		int n2 = random.nextInt(10);
		int n3 = random.nextInt(10);
		int n4 = random.nextInt(10);
%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>樂透選號</title>
</head>
<body>

	<%=n1%>
	<%=n2%>
	<%=n3%>
	<%=n4%>
</body>
</html>