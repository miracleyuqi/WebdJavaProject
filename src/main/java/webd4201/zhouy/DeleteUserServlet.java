package webd4201.zhouy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webd4201.zhouy.User;
import webd4201.zhouy.UserDA;
import webd4201.zhouy.DatabaseConnect;

@WebServlet("/DeleteUser")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("id");
		
		try {
			Connection c = DatabaseConnect.initialize();
			User.initialize(c);
			User user = UserDA.retrieve(Long.parseLong(userId));
			
			if(user != null) {
				// Delete the user
				UserDA.delete(user);
				User.terminate();
				
				// Redirect to dashboard with success message
				request.getSession().setAttribute("message", "User deleted successfully");
				response.sendRedirect("./AdminDashboard.jsp");
			} else {
				// Redirect to dashboard with error message
				request.getSession().setAttribute("message", "User doesn't exist");
				response.sendRedirect("./AdminDashboard.jsp");
			}
			
		} 
		catch (Exception e) {
			// Redirect to dashboard with error message
			request.getSession().setAttribute("message", e.getMessage());
			response.sendRedirect("./AdminDashboard.jsp");
		}
	}
}
