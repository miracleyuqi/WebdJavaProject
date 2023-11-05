<!-- 
 * Project Name:   WEBD4201 Assignment 4
 * File Name:      register.jsp
 * Student Name:   Yuqi Zhou
 * Student ID:     100854558
 * Date:           March 21, 2023
 * This is the register page file for WEBD-4201-01 Assignment 4
 -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
	String pageTitle="Student Registration Page";
%>


	<jsp:include page="./header.jsp">
		<jsp:param name="title" value="<%=pageTitle %>"  />
	</jsp:include>
		

<style>
	label {
		display: inline-block;
    	width: 100px;
    	text-align: left;
	}
	
	form {
		padding: 10px;
		width: 300px;
		border: 1px solid black;
	}
</style>
	
<body>
		<div id="contentContainer">
		<h2>Register here</h2>
			<p>Current date and time is: <%= new java.util.Date() %></p>
				<form method="post" action="./Register"> 
				
				<!-- Client side validation in HTML -->				
				<label>User Name:</label><input type="number" name="username" value="<%= request.getParameter("username") %>" min="100000000" max="999999999" /><br/>				
				<label>Password:</label><input type="password" name="password" value="<%= request.getParameter("password") %>" required pattern=".{8,}" /><br/> 
				<label>First Name:</label><input type="text" name="firstName" value="<%= request.getParameter("firstName") %>" required /><br/>
				<label>Last Name:</label><input type="text" name="lastName" value="<%= request.getParameter("lastName") %>" required /><br/>
				<label>Email:</label><input type="email" name="emailAddress" value="<%= request.getParameter("emailAddress") %>" required data-parsley-type="email"/><br/>
				<label>Program Code:</label><input type="text" name="programCode" value="<%= request.getParameter("programCode") %>" required /><br/>
				<label>Program Name:</label><input type="text" name="programDescription" value="<%= request.getParameter("programDescription") %>" required /><br/>
				<label>Year:</label><input type="number" name="year" value="<%= request.getParameter("year") %>" min="1" max="8" /><br/>
				<label>&nbsp;</label><button type="Submit" name="Submit" >Submit</button><br/>
				</form>
			</p>
		</div>

</body>

</html>
	<jsp:include page="./footer.jsp">
		<jsp:param name="footerTitle" value= "<%=pageTitle %>" />
	</jsp:include>