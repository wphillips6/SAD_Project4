<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="default.css">

<title>Modify Course Enrollment Limits</title>
</head>
<body>
	<div class="content">
		<div class="content">
			<h1>
 				Modify Course Enrollment limits..
			</h1>
		</div>

		<nav>
		<ul>
			<li><a href="http://www.google.com">Google</a></li>
			<li><a href="http://www.yahoo.com">Yahoo</a></li>
			<%
				if (session.getAttribute("username") != null) {
					out.print("<li><a href=\"logout.jsp\">Logout</a></li>");
				}
			%>
		</ul>
		</nav>

		<section> <span>Enter Course and Limit</span>
		<form method="POST" action="Admin">
			<input type="hidden" name="function" value="setEnrollLims" /> Course
			Name or Number:<input name="crsname" /> 
			New Enrollment Limit:<input name="crslim" />
			<input type="submit" value="Edit Enrollment Limits" />
		</form>
		</section>


	</div>
</body>
</html>