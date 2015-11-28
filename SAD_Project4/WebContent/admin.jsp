<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="proj4.common.Administrator" %>
<%
	if (session == null || session.getAttribute("username") == null ||
			session.getAttribute("isadmin") == null ||
			!session.getAttribute("isadmin").equals("1")) {
		response.sendRedirect("index.jsp");
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="default.css">
<title>Admin Landing Page</title>
</head>
<body>
	<div class="content">
		<div class="content">
			<h1>
				Welcome, ${administrator.getName()}. Would you like to...
			</h1>
		</div>

		<nav>
		<ul>
			<li><a href="http://www.gatech.edu/">GATech</a></li>
			<li><a href="http://www.omscs.gatech.edu/courses/">OMSCS Courses</a></li>
			<%
				if (session.getAttribute("username") != null) {
					out.print("<li><a href=\"logout.jsp\">Logout</a></li>");
				}
			%>
		</ul>
		</nav>

		<section> <span>Edit Class Sizes?</span>
		<li><a href="setEnroll.jsp">Set Enrollment Limits</a></li>
		</section>

		<section> <span>Edit Current Semester Offerings?</span>
		<li><a href="setCourses.jsp">Set Courses for Semester</a></li>	
		</section>
		
		<section> <span>Edit Staffing Assignments?</span>
		<li><a href="setStaff.jsp">Set Staffing</a></li>
		</section>
		
		<section> <span>Display Current Preferences or Recommendations for a Student?</span>
		<li><a href="dispStudentStats.jsp">Student Stats</a></li>

		</section>

	</div>
</body>
</html>