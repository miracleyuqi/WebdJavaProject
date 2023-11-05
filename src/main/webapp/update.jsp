<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<jsp:include page="./header.jsp">
		<jsp:param name="title" value= "Update Page" />
	</jsp:include>
		
		<div id="contentContainer">
		<h2>Update Records</h2>
			<p>The current date and time is: <%= new java.util.Date() %></p>
			<p>
				This webpage is to update user records.
			</p>
		</div>
	
		<jsp:include page="./footer.jsp">
			<jsp:param name="footerTitle" value= "Update Page" />
		</jsp:include>
		
</body>
</html>
