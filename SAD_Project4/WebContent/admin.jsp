<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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
				Welcome, 
				<%
				out.print(session.getAttribute("username"));
			%>. Would you like to...
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

		<section> <span>Edit Class Sizes?</span>
		<form method="POST" action="Admin">
			<input type="hidden" name="function" value="editEnrollLims" />
			<input type="submit" value="Edit Enrollment Limits" />
		</form>
		</section>

		<section> <span>Edit Current Semester Offerings?</span>
		<form method="POST" action="Admin">
			<input type="hidden" name="function" value="editCourses" />
			<input type="submit" value="Edit Courses" />
		</form>
		</section>
		<section> <span>Edit Staffing Assignments?</span>
		<form method="POST" action="Admin">
			<input type="hidden" name="function" value="editStaff" /> 
			<input type="submit" value="Assign Staff" />
		</form>
		</section>
		<section> <span>Display Recommendations for Student?</span>
		<form method="POST" action="Admin">
			<input type="hidden" name="function" value="dispRecs" /> 
			<input type="submit" value="View Student Results" />
		</form>
		</section>

	</div>
</body>
</html>