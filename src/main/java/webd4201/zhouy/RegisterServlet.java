/**
 * Project Name:   WEBD4201 Assignment 4
 * File Name:      RegisterServlet.java
 * Student Name:   Yuqi Zhou
 * Student ID:     100854558
 * Date:           March 21, 2023
 * Description:    This project was created for Assignment 4 of WEBD4201
 */

package webd4201.zhouy;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        HttpSession session = request.getSession(true);

	        Date currentDate = new Date();
	        // Obtain values from the form
	        String username = request.getParameter("username");
	        String password = request.getParameter("password");
	        String firstName = request.getParameter("firstName");
	        String lastName = request.getParameter("lastName");
	        String emailAddress = request.getParameter("emailAddress");
	        String programCode = request.getParameter("programCode");
	        String programDescription = request.getParameter("programDescription");
	        String yearString = request.getParameter("year");

	        // Convert strings to appropriate data types
	        long id = Long.parseLong(username);
	        int year = Integer.parseInt(yearString);

	        // Validate input fields
	        List<String> validationMessages = new ArrayList<String>();
	        
	        // Validate username
	        if (username.trim().isEmpty() || !username.matches("\\d{9}")) {
	            validationMessages.add("Username must be a 9-digit number.");
	        }
	        // Validate password
	        if (password.trim().isEmpty() || password.length() < 8) {
	            validationMessages.add("Password must be at least 8 characters.");
	        }
	        // Validate firstName
	        if (firstName.trim().isEmpty()) {
	            validationMessages.add("First name is required.");
	        }
	        // Validate lastName
	        if (lastName.trim().isEmpty()) {
	            validationMessages.add("Last name is required.");
	        }
	        // Validate email
	        if (emailAddress.trim().isEmpty() || !emailAddress.contains("@")) {
	            validationMessages.add("Email address must include @.");
	        }
	        // Validate programCode
	        if (programCode.trim().isEmpty()) {
	            validationMessages.add("Program code is required.");
	        }
	        // Validate programDescription
	        if (programDescription.trim().isEmpty()) {
	            validationMessages.add("Program description is required.");
	        }
	        // Validate year
	        if (yearString.trim().isEmpty() || !yearString.matches("[1-8]")) {
	            validationMessages.add("Year must be a single digit between 1 and 8.");
	        }

	        if (validationMessages.size() > 0) {
	            // Validation failed, redirect to registration page with error messages
	            session.setAttribute("validationMessages", validationMessages);
	            response.sendRedirect("./register.jsp");
	        } else {
	            // Validation passed, create new student and insert into database
	            Date lastAccess = currentDate;
	            Date enrolDate = currentDate;
	            char type = 's';

		        User newUser = new User(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate, true, type);
	            Student newStudent = new Student(id, password, firstName, lastName, emailAddress, lastAccess, enrolDate, true, type, programCode, programDescription, year);

	            System.out.println("ID: " + newStudent.getId());
	            System.out.println("Password: " + newStudent.getPassword());
	            System.out.println("First Name: " + newStudent.getFirstName());
	            System.out.println("Last Name: " + newStudent.getLastName());
	            System.out.println("Email Address: " + newStudent.getEmailAddress());
	            System.out.println("Program Code: " + newStudent.getProgramCode());
	            System.out.println("Program Description: " + newStudent.getProgramDescription());
	            System.out.println("Year: " + newStudent.getYear());

	            System.out.println("Starting Transaction!");

	            StudentDA.initialize(DatabaseConnect.initialize());
	            UserDA.initialize(DatabaseConnect.initialize());

		        //if (StudentDA.create(newStudent)) 
		        if (UserDA.create(newUser) && StudentDA.create(newStudent))
		        {
		            System.out.println("Transaction returned TRUE!");
		            session.setAttribute("id", newStudent.getId());
		            session.setAttribute("firstName", newStudent.getFirstName());
		            session.setAttribute("lastName", newStudent.getLastName());
		            session.setAttribute("emailAddress", newStudent.getEmailAddress());
		            session.setAttribute("programCode", newStudent.getProgramCode());
		            session.setAttribute("programDescription", newStudent.getProgramDescription());
		            session.setAttribute("year", newStudent.getYear());
		            response.sendRedirect("./dashboard.jsp");
		        } else {
		            System.out.println("Transaction returned FALSE!");
		            session.setAttribute("msg", "Error: Registration Failed!");
		            System.out.println("FAILURE");
		            response.sendRedirect("./register.jsp");
		        }

		        StudentDA.terminate();

		        System.out.println("SUCCESS");
		        session.setAttribute("msg", "Registration is going well so far!");
	        }
		} 
	    catch (Exception e) 
	    {
		        System.out.println("An error occurred: " + e);
	    }
		
	}
}
