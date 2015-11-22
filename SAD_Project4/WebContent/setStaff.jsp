<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="proj4.serverapp.ServerApplication" %>
<%@ page import="proj4.common.Professor" %>
<%@ page import="proj4.common.Course" %>
<%@ page import="proj4.common.TeacherAssistant" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="default.css">

<title>Set Staff Assignments</title>
</head>
<body>
	<div class="content">
		<div class="content">
			<h1>
 				Modify Professor or TA Course Assignments
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

		<!-- <section> <span>Enter Course and Victim</span>
		<form method="POST" action="Admin">
			<input type="hidden" name="function" value="setStaffAsts" /> 
			Staff ID:<input name="staffname" /> <br>
			Course(s): <input name="crsname" /> <br> <br>
			
            Staff Type:<br>
            <input type="radio" name="staffType" value="ta" checked> TA
			<input type="radio" name="staffType" value="professor"> Professor
			<br><br>
			Update Type1:<br>
			<input type="radio" name="staffUpdateType1" value="proficiency" checked> Proficiency
			<input type="radio" name="staffUpdateType1" value="assignment"> Assignment
			<br><br>
			Update Type2:<br>
			<input type="radio" name="staffUpdateType2" value="append" checked> Append
			<input type="radio" name="staffUpdateType2" value="remove"> Remove
			<br><br>
			Model Mode:<br>
            <input type="radio" name="modelModeType" value="standard" checked> Standard
			<input type="radio" name="modelModeType" value="shadow"> Shadow Mode
			<br><br>
			<input type="submit" value="Make Change" />
		</form>
		</section> -->
		<section>
				<form method="POST" action="Admin">
			<input type="hidden" name="function" value="setStaffAsts" /> 
				Staff ID:<br><input name="staffname" /> <br>
				Available:<select name="available"><option value="0">No</option><option value="1">Yes</option></select><br>
				Competencies:<br><input name="competencies" /> <br>
				Model Mode:<br>
            <input type="radio" name="modelModeType" value="standard" checked> Standard
			<input type="radio" name="modelModeType" value="shadow"> Shadow Mode
			<br><br>
			<input type="submit" value="Make Change" />
		</form>
			<br><br>
			<%
			ServerApplication sa = (ServerApplication)session.getAttribute("srvapp");
			List<Professor> profList = sa.getAllProfs();
			List<Course> crsList = sa.getAllCourses();
			String profTable = "<table><tr><th>Professor</th><th>Available</th><th>ID</th><th>Assigned</th><th>Capabilities</th></tr>";
			for(int i = 0; i < profList.size(); i++){
				//crsTable += "<tr><td>"+((Course)crsList.get(i)).getDescription()+"</td><td><input type=\"checkbox\"/></td></tr>";
				Professor p = (Professor)profList.get(i);
				profTable += "<tr><td>"+p.getName()+"</td><td><input name=available_"+p.getProfID()+" type=\"checkbox\"";
				if(p.getAvailNextTerm() == 1) {
					profTable += " checked ";
				}
				//profTable += "/></td><td></td><td><input type=\"text\" name=\"profCap_"+p.getProfID()+"\" value=\""+p.getStrComp()+"\"/></td></tr>";
				profTable += "/></td><td>"+p.getProfID()+"</td><td></td><td>"+p.getStrComp()+"</td></tr>";
			}
			profTable += "</table>";
			out.println(profTable);
			out.println("<br><br>");
			
			List<TeacherAssistant> taList = sa.getAllTAs();
			String taTable = "<table><tr><th>Teacher Assistant</th><th>Available</th><th>ID</th><th>Assigned</th><th>Capabilities</th></tr>";
			for(int i = 0; i < taList.size(); i++){
				TeacherAssistant ta = (TeacherAssistant)taList.get(i);
				taTable += "<tr><td>"+ta.getName()+"</td><td><input name=available_"+ta.getTaID()+" type=\"checkbox\"";
				if(ta.getAvailNextTerm() == 1) {
					taTable += " checked ";
				}
				taTable += "/></td><td>"+ta.getTaID()+"</td><td></td><td>"+ta.getStrComp()+"</td></tr>";
			}
			taTable += "</table>";
			out.println(taTable);
			%>	
			<br><br>

		</section>


	</div>
</body>
</html>