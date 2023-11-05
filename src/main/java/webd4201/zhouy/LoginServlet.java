package webd4201.zhouy;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webd4201.zhouy.Student;
import webd4201.zhouy.DatabaseConnect;
import webd4201.zhouy.NotFoundException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		HttpSession ses = request.getSession(true);
		System.out.println("You have successfully logged out.");
		ses.removeAttribute("Student");
		ses.removeAttribute("loggedInStudent");
		response.sendRedirect("./login.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses = request.getSession(true);
		String login = request.getParameter("login");
		String pw = request.getParameter("password");
		//
		System.out.println("Input login: " + login);
	    System.out.println("Input password: " + pw);
		//
		Student defStudent = null;
		try {
			defStudent = Student.authenticate(Long.parseLong(login), pw);
		} 
		catch (NumberFormatException | NotFoundException | NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (defStudent == null)
		{
			System.out.println("<strong>Your sign in information is not valid.<br/>Please try again</strong>");
			ses.setAttribute("errors", "Authentication failed, try again");
			response.sendRedirect("./login.jsp");
		}		
		else if (defStudent.getId() == 100000000) 
		{
			System.out.println("Admin authentication successful");
		    // Redirect to AdminDashboard
		    response.sendRedirect("./AdminDashboard.jsp");
		    return;
		}
		else  
		{
			System.out.println("Authentication successful");
			ses.setAttribute("student", defStudent);
			ses.setAttribute("errors", "");
			response.sendRedirect("./dashboard.jsp");
		}
	}
}
