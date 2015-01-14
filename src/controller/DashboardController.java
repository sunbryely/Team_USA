package controller;

import database.AccountData;
import database.UserData;
import model.Account;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Team USA
 * 
 * Servlet implementation class DashboardController
 * This class controls any interactions involving the Dashboard web page. 
 * 
 */

@WebServlet(name="dashboard", urlPatterns={"/dashboard"})
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // See if we have a user already logged in
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");

        //the dashboard jsp page
        if (user != null) {
            // if the user is an admin then redirect to the transaction controller
            if(user.getisAdmin()) {
                // User exists and is an admin
                response.sendRedirect("teller");
                return;
            }

            // else get the user's account information and redirect to the default dashboard.
            else {
            	
                AccountData accountDb = new AccountData();
                List<Account> accountList = accountDb.getAccountList(user);
				request.setAttribute("accounts", accountList);
            }
            request.getRequestDispatcher("/dashBoard.jsp").forward(request, response);
        } else {
            // Redirect the to the login page
            response.sendRedirect("login");
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) {
            // Redirect the to the login page
            response.sendRedirect("login");
            return;
		}

        String closeAccount = request.getParameter("closeAccount");
        if(closeAccount.equals("true"))
        {
            UserData userData = new UserData();

            // Delete all the accounts associated with this user
            AccountData accountData = new AccountData();
            accountData.deleteUsersAccounts(user.getUsername());

            //passes all checks, remove user
            userData.deleteUser(user.getUsername());
            session.invalidate(); // Log the user out
            response.sendRedirect("closedAccount.jsp");
            return;
        }

		request.getRequestDispatcher("/dashBoard.jsp").forward(request, response);
	}
}
