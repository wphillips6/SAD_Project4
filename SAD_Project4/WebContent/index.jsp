<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="default.css">
<title>Project 4</title>
</head>
<body>
	<div class="content">
		<div class="content">
			<h1>Welcome to the Dynamic Student-Course Assignment for CS6310 Fall 2015</h1>
		</div>

		<nav>
		<ul>
			<li><a href="http://www.gatech.edu/">GATech</a></li>
			<li><a href="http://www.omscs.gatech.edu/courses/">OMSCS Courses</a></li>
		</ul>
		</nav>

		<section>
		<p>
			<span>Please Enter your Username and Password</span>
		</p>

		<form method="POST" action="Login">
			<div>
				Username:<input name="username" />
			</div>
			<div>
				Password:<input name="password" type="password" />
			</div>
			<div>
				<button type="submit" >Login</button>
			</div>

		</form>

		</section>

	</div>
</body>
</html>