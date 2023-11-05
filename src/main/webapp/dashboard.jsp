<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import = "webd4201.zhouy.*" %>
<%
	Student aStudent = (Student)session.getAttribute("student");
	if (aStudent == null) 
	{
		response.sendRedirect("./login.jsp");
		return;
	}	
%>

	<jsp:include page="./header.jsp">
		<jsp:param name="title" value= "Dashboard Page" />
	</jsp:include>
		
		<div id="contentContainer">
		<h2>Welcome <%= aStudent.getFirstName() + ' ' + aStudent.getLastName() %></h2>
			<p>The current date and time is: <%= new java.util.Date() %></p>
			<p>
				You should only see this page if logged in. 
			</p>
		</div>
	
		<jsp:include page="./footer.jsp">
			<jsp:param name="footerTitle" value= "Company Contact Page" />
		</jsp:include>
	
</body>
</html>