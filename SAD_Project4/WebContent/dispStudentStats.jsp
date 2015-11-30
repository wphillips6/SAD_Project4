<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="proj4.serverapp.ServerApplication" %>
<%@ page import="proj4.serverapp.AdminEntry" %>
<%@ page import="proj4.common.Course" %>

<%@ page import="proj4.common.Administrator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="default.css">
		<title>Student Statistics</title>
	</head>
	<body>
		<div class="content">
			<div class="content">
			<h1>
			Display Recommendations for, or Preferences of, a Student
			</h1>
			<h3>${administrator.getName()}</h3>
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
			<%
			ServerApplication sa = (ServerApplication)session.getAttribute("srvapp"); 
			AdminEntry ae = sa.getAdminEntry();
			if(ae.displayStudentData()){
				List<Course> courses = ae.infoForCurrentStudent();
				out.println("<section> <span><p>Requested Course Data </p></span>");
				out.println("<table> <tr> <th>Course </th></tr>");
				for(int i = 0; i < courses.size(); ++i){
					out.println("<tr><td>" + courses.get(i).getID() + " " 
				                + courses.get(i).getDescription() + "</td></tr>");
				}
				out.println("</table>	</section>");
			}
			
			%>
			
			
			<section> <span>Enter Student ID and Request Type</span><br>
			<form method="POST" action="Admin">
				<input type="hidden" name="function" value="dispRecsPrefs" />
				Student ID:<input name="stuname" />
				<br><br>
				Display Type:<br>
				<input type="radio" name="dispType" value="recs" checked> Recommendations
				<input type="radio" name="dispType" value="prefs"> Preferences<br><br>
				<input type="submit" value="Get Data" />
			</form>
			</section>
		</div>
	</body>
</html>