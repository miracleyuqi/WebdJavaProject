package webd4201.zhouy;

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/Update")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("./dashboard.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection c = DatabaseConnect.initialize();
		User.initialize(c);

		HttpSession session = request.getSession(true);
		User loggedInUser = (User)session.getAttribute("user");

		// retrieve form data
		long id = Long.parseLong(request.getParameter("id"));
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String emailAddress = request.getParameter("emailAddress");
		String lastAccess = request.getParameter("lastAccess");
		String enrolDate = request.getParameter("enrolDate");
		String type = request.getParameter("type");
		boolean enabled = request.getParameter("enabled") != null;

		// create user object
		User user = null;
		try {
			user = new User();
		} catch (InvalidUserDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// update user information
		int success = 0;
		try {
			success = UserDA.update(user);
		} catch (NotFoundException | InvalidUserDataException | InvalidPasswordException | InvalidNameException
				| InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		User.terminate();
		DatabaseConnect.terminate();

		if (success > 0) {
		    session.setAttribute("message", "User information updated successfully.");
		} 
		else {
		    session.setAttribute("message", "Failed to update user information.");
		}

		response.sendRedirect("./EditUser?id=" + id);
    }
}
