<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import = "webd4201.zhouy.*" %>

<%
	Student loggedInStudent = (Student)session.getAttribute("student");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Yuqi Zhou - WEBD4201 - Assignment 3</title>
<link rel="stylesheet" href="./css/theme.css"/>
</head>
<body>
	<div id="container">
		<header>
			<p class="header">
				<% if (loggedInStudent != null) { %>
					Welcome <%= loggedInStudent.getFirstName() %> | 
					<a href="./update.jsp">Update</a> | 
					<a href="./Logout">Logout</a>
				<% } else { %> 
					<a href="./login.jsp">Login</a> | 
					<a href="./register.jsp">Register</a>
				<% } %>
			</p>
			<img width="60%" src="./images/ComputerWork.jpg" alt="Computer Work" />
			<h1><%=request.getParameter("title") %></h1>
		</header>
	</div>

</body>
</html>
