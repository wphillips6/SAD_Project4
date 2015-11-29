<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="proj4.common.Student" %>
<%@ page import="proj4.common.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="proj4.serverapp.ServerApplication" %>
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

<title>Edit Enrollment Limits</title>
</head>
<body>
	<div class="content">
		<div class="content">
			<h1>
 				Modify Course Enrollment limits..
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

		<!-- <section> <span>Enter Course and Limit</span><br>
		<form method="POST" action="Admin">
			<input type="hidden" name="function" value="setEnrollLims" /> 
			Course ID:<input name="crsname" /> 
			<br>
		    Enroll Limit:<input name="crslim" />
			<br><br>
			Model Mode:<br>
            <input type="radio" name="modelModeType" value="standard" checked> Standard
			<input type="radio" name="modelModeType" value="shadow"> Shadow Mode<br><br>
			<input type="submit" value="Make Change" />
		</form>
		</section>  -->
		<section>
		<form method="POST" action="Admin">
			<input type="hidden" name="function" value="setEnrollLims" /> 
		<%
			ServerApplication sa = (ServerApplication)session.getAttribute("srvapp");
			List<Course> crsList = sa.getAllCourses();
			String crsOptionsHTML = "<span style=\"float: left;\"></span><select name=\"crsNum\">";
			String courseTable = "<table>";
			crsOptionsHTML += "<option value=\"0\" selected>----</option>";
			for(int j = 0; j < crsList.size(); j++){
					crsOptionsHTML += "<option value=\""+crsList.get(j).getNumber()+"\">"+crsList.get(j).getDescription()+"</option>";
					courseTable += "<tr><td>"+crsList.get(j).getDescription()+"</td><td>"+crsList.get(j).getEnrollLim()+"</td></tr>";
			}
			
			crsOptionsHTML += "</select>";
			out.println(crsOptionsHTML);

			courseTable += "</table>";

			out.println("<input type=\"hidden\" name=\"function\" value=\"setPrefs\" /> ");
		%>
		<br>
		    Enroll Limit:<input name="crslim" />
			<br><br>
			Model Mode:<br>
            <input type="radio" name="modelModeType" value="standard" checked> Standard
			<input type="radio" name="modelModeType" value="shadow"> Shadow Mode<br><br>
			<input type="submit" value="Make Change" />
		</form>
		<br><br>
		<% out.println(courseTable); %>
		</section>


	</div>
</body>
</html>