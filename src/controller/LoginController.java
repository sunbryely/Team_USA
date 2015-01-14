package controller;

import database.UserData;
import model.User;
import services.MD5Hash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Team USA
 * 
 * Servlet implementation class LoginContoller
 * This class controls any interactions involving logging into the website
 * 
 */
@WebServlet(name="login", urlPatterns={"/login"})
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // See if we have a user already logged in
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if(user != null)
        {
            if(user.getisAdmin())
            {
                response.sendRedirect("teller");
                return;
            }

            // Redirect the to the dashboard page
            response.sendRedirect("dashboard");
        }
        else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userID = request.getParameter("userID");
		String password = request.getParameter("password");
        String encryptedPassword = MD5Hash.MD5(password);

		if(userID.isEmpty() || password.isEmpty())
		{
			request.setAttribute("errorMessage", "The userID and password do not match.");

			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		else
		{
            // check if the user exists in the database
            UserData userDatabase = new UserData();
            User user = userDatabase.getUserByUsernameAndPassword(userID, encryptedPassword);

            if(user != null)
            {
                // Start the user's server session (AKA log the user in)
                HttpSession session = request.getSession(true);
                session.setAttribute("user", user);

            	if(user.getisAdmin())
                {
                	response.sendRedirect("teller");
                	return;
                }

                // Redirect the to the dashboard page
                response.sendRedirect("dashboard");
            }
			else
            {
                request.setAttribute("errorMessage", "The userID and password do not match.");

                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
		}
	}
}
