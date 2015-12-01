<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="proj4.common.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="proj4.serverapp.ServerApplication" %>
<%@ page import="proj4.common.Administrator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="default.css">

<title>Edit Course Offerings</title>
</head>
<body>
	<div class="content">
		<div class="content">
			<h1>
 				Modify Course Offerings..
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

		<section> <span>Enter Course and Action</span><br>
		<form method="POST" action="Admin">
			<input type="hidden" name="function" value="setCourse" /> 
			Course ID:<input name="crsid" /> <br><br>
			Course Description:<input name="crsdesc" /> <br><br>  
			Course Number:<input name="crsnum" /> <br><br> 
			
            Action:<br>
            <input type="radio" name="courseSetType" value="add" checked> Add Course
            <input type="radio" name="courseSetType" value="edit"> Edit Course Semester
			<br><br>
			
			Semesters offered:<br>
			<input type="radio" name="semester" value="EVERY" checked> All
			<input type="radio" name="semester" value="FALL"> Fall
			<input type="radio" name="semester" value="SPRING"> Spring
			<input type="radio" name="semester" value="SUMMER"> Summer
			<br><br>
			Model Mode:<br>
            <input type="radio" name="modelModeType" value="standard" checked> Standard
			<input type="radio" name="modelModeType" value="shadow"> Shadow Mode<br><br>
			<input type="submit" value="Make Change" />
		</form>
		</section>
				<section> <span>Enter Course and Action</span><br>
		<form method="POST" action="Admin">
			<input type="hidden" name="function" value="setCourse" /> 
			Course ID:<input name="crsname" /> <br><br> 
	
			<%
			ServerApplication sa = (ServerApplication)session.getAttribute("srvapp");
			List<Course> crsList = sa.getAllCourses();
			String crsTable = "<table><tr><th>Course Name</th><th>Offered</th></tr>";
			for(int i = 0; i < crsList.size(); i++){
				crsTable += "<tr><td>";
				crsTable += ((Course)crsList.get(i)).getID();
				crsTable += " ";
				crsTable += ((Course)crsList.get(i)).getDescription();
				crsTable += "</td><td><input type=\"checkbox\"/></td></tr>";
			}
			crsTable += "</table>";
			out.println(crsTable);
			%>			
			<br><br>
			
			Model Mode:<br>
            <input type="radio" name="modelModeType" value="standard" checked> Standard
			<input type="radio" name="modelModeType" value="shadow"> Shadow Mode<br><br>
			<input type="submit" value="Make Change" />
		</form>
		</section>


	</div>
</body>
</html>
