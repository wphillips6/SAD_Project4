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
<title>Insert title here</title>
</head>
<body>
	<div class="content">
		<div class="content">
			<h1>
				Why, hello
				<%
				out.print(session.getAttribute("username"));
			%>!
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

		<section> <span>Title 1</span>
		<form method="POST" action="Admin">
			<input type="hidden" name="function" value="addCourse" /> Course
			Name:<input name="crsname" /> <input type="submit" value="Submit" />
		</form>
		</section>

		<section> <span>Title 1</span>
		<form method="POST" action="Admin">
			<input type="hidden" name="function" value="addProfessor" />
			Professor Name:<input name="profname" /> <input type="submit"
				value="Submit" />
		</form>
		</section>
		<section> <span>Title 1</span>
		<form method="POST" action="Admin">
			<input type="hidden" name="function" value="addStudent" /> Student
			Name:<input name="studentname" /> <input type="submit" value="Submit" />
		</form>
		</section>
		<section> <span>Title 1</span>
		<form method="POST" action="Admin">
			<input type="hidden" name="function" value="addTA" /> Teaching
			Assistant Name:<input name="taname" /> <input type="submit"
				value="Submit" />
		</form>
		</section>

		<section> <span>Title 2</span>
		<p>This is paragraph 2</p>
		</section>




	</div>
</body>
</html>