<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

	<jsp:include page="./header.jsp">
		<jsp:param name="title" value= "Yuqi's Main Homepage" />
	</jsp:include>
		
		<div id="contentContainer">
		<h2>All Starts Here...</h2>
			<p>The current date and time is: <%= new java.util.Date() %></p>
			<p>
				This website is to track student mark for Durham College students.
			</p>
		</div>
	
		<jsp:include page="./footer.jsp">
			<jsp:param name="footerTitle" value= "Main Homepage" />
		</jsp:include>
	
</body>
</html>