<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="webd4201.zhouy.*" %>
<%@ page import="java.util.Vector" %>
<%@ page import="java.sql.*" %>

<%
	Connection c = DatabaseConnect.initialize();
	User.initialize(c);

    User loggedInUser = (User)session.getAttribute("user");
    if (loggedInUser == null || loggedInUser.getId() != 100000000) 
    {
        response.sendRedirect("./login.jsp");
        return;
    }

    Vector<User> UserList = UserDA.retrieveAll();
    User.terminate();
%>

<jsp:include page="./header.jsp">
    <jsp:param name="title" value="Admin Dashboard" />
</jsp:include>

<div id="contentContainer">
    <h2>Admin Dashboard</h2>
    <p>Current date and time is: <%= new java.util.Date() %></p>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Email</th>
                <th>Last Access</th>
                <th>Enrol date</th>
                <th>Enabled</th>
                <th>User Type</th>
                
                <th>Edit</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
            <% for (User user : UserList) { %>
            <tr>
                <td><%= user.getId() %></td>
                <td><%= user.getFirstName() %></td>
                <td><%= user.getLastName() %></td>
                <td><%= user.getEmailAddress() %></td>
                <td><%= user.getLastAccess() %></td>
                <td><%= user.getEnrolDate() %></td>
                <td><%= user.getEnabled() %></td>
                <td><%= user.getType() %></td>
                <td><a href="./EditUser?id=<%= user.getId() %>">Edit</a></td>
                <td><a href="./DeleteUser?id=<%= user.getId() %>" 
                	onclick="return confirm('Are you sure to delete this user?')">Delete</a></td>
            </tr>
            <% } %>
        </tbody>
    </table>
</div>

<jsp:include page="./footer.jsp">
    <jsp:param name="footerTitle" value="Admin Dashboard" />
</jsp:include>
