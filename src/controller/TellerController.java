package controller;

import database.AccountData;
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
 * Servlet implementation class Teller Controller
 * This class controls any interactions dealing with the teller page. 
 * 
 */
@WebServlet(name="teller", urlPatterns={"/teller"})
public class TellerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TellerController() {
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
                //user exists and is admin
        		AccountData accData = new AccountData();
        		
        		List<Account> accounts = accData.getAccountList();
        		
        		request.setAttribute("accounts", accounts);
        		
        		request.getRequestDispatcher("/teller.jsp").forward(request, response);
        		return;
        	}
        	else {
				// User exists but is not an admin
				response.sendRedirect("dashboard");
				return;
        	}
        }
        else {
            // Redirect the to the login page
            response.sendRedirect("login");
            return;
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}
