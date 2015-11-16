<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

		<section> <span>Enter Course and Victim</span>
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
		</section>


	</div>
</body>
</html>