<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import = "webd4201.zhouy.*" %>

<footer>
	<hr/>
		<a href="./">Home</a> | 
		<a href="./profile.jsp">Profile</a> | 
	<%
		// Check if the user is logged in
		Student loggedInStudent = (Student)session.getAttribute("student");
		if (loggedInStudent != null) {
	%>
		<a href="./Logout">Logout</a> 
	<%
		} else {
	%>
		<a href="./login.jsp">Login</a> | 
		<a href="./register.jsp">Register</a>
	<%
		}
	%>
		<br/>
			&copy;Yuqi Zhou
		<br/>
		${param.footerTitle}
</footer>