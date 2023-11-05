<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Edit User</title>
</head>
<body>
	<h1>Edit User</h1>

	<form action="UpdateUser" method="GET">
		<label>ID:</label>
		<input type="text" name="id" value="${user.id}" readonly><br>
		<label>First Name:</label>
		<input type="text" name="firstName" value="${user.firstName}"><br>
		<label>Last Name:</label>
		<input type="text" name="lastName" value="${user.lastName}"><br>
		<label>Email:</label>
		<input type="email" name="email" value="${user.emailAddress}"><br>
		<label>Last Access:</label>
		<input type="text" name="lastAccess" value="${user.getLastAccess()}"><br>
		<label>Enrol Date:</label>
		<input type="text" name="enrolDate" value="${user.getEnrolDate()}"><br>
		<label>Enabled:</label>
		<input type="checkbox" name="enabled" value="true" ${user.enabled ? 'checked' : ''}><br>
		<label>User Type:</label>
		<input type="radio" name="type" value="s" ${user.type == UserType.STUDENT ? 'checked' : ''}>Student
		<input type="radio" name="type" value="f" ${user.type == UserType.FACULTY ? 'checked' : ''}>Faculty<br>
		
		<input type="submit" value="Update">
	</form>
</body>
</html>
