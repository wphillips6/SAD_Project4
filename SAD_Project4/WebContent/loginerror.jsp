<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login Error</title>
</head>
<body>
<center><p style="color:red">Invalid Username/Password entered. Try Again.</p></center>
<%
getServletContext().getRequestDispatcher("/index.jsp").include(request, response);
%>
</body>
</html>