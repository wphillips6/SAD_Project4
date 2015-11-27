<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="proj4.common.Student" %>
<%@ page import="proj4.common.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="proj4.serverapp.ServerApplication" %>

<%
	if (session == null || session.getAttribute("username") == null) {
		response.sendRedirect("index.jsp");
	} else {
		//s = (Student)session.getAttribute("student");
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="default.css">
<title>Student Page</title>
</head>
<body>
	<div class="content">
		<div class="content">
			<h1>
				Hello, ${student.getName()}
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

		<section> <span><p>Current Reccommendation</p></span>
		<table>
		<tr>
		<th>Course ID</th><th>Course Name</th>
		</tr>
		<%
		Student s = (Student)session.getAttribute("student");
		List l = s.getDesiredCourses();
		List lCurrRecs = s.getCurrentRecs();
		//System.out.println("Studnt List size:  " + l.size());
		for(int i = 0; i < lCurrRecs.size(); i++){
			Course c = (Course)lCurrRecs.get(i);
			out.println("<tr><td>" + c.getID() + "</td><td>" + c.getDescription() + "</td></tr>");
		}
		%>
		</table>
		</section>

		<section><span><p>Set Preferences</p></span>
			<form method="POST" action="Student">
			<%
			ServerApplication sa = (ServerApplication)session.getAttribute("srvapp");
			List<Course> crsList = sa.getAllCourses();
			for(int i = 0; i < 10; i++) {
				String crsOptionsHTML = "";
				out.println("<span style=\"float: left;\">"+(i+1)+":  </span><select name=\"pref"+i+"\">");
				boolean found = false;
				for(int j = 0; j < crsList.size(); j++){
					if(i < l.size() && l.get(i).equals(crsList.get(j))) {
						crsOptionsHTML += "<option value=\""+crsList.get(j).getNumber()+"\" selected>"+crsList.get(j).getDescription()+"</option>";
						found = true;
					} else {
						crsOptionsHTML += "<option value=\""+crsList.get(j).getNumber()+"\">"+crsList.get(j).getDescription()+"</option>";
					}
				}
				if(!found){
					out.println("<option value=\"0\" selected>----</option>");
				} else {
					out.println("<option value=\"0\">----</option>");
				}
				out.println(crsOptionsHTML);
				out.println("</select>");
			}
			out.println("<input type=\"hidden\" name=\"function\" value=\"setPrefs\" /> ");
			%>
			<br><span style="display: block;">Desired Number of Courses for next Term:</span><input type="text" name="numCourses" value="<% out.print(s.getNumDesiredCourses()); %>"/>
			<br><br><input type="submit" value="Submit New Preferences">
			</form>
		</section>
		
	</div>
</body>
</html>