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
 * Servlet implementation class Registration Controller
 * This class controls the interactions on the website Registration page. 
 * 
 */

@WebServlet(name="register", urlPatterns={"/register"})
public class RegistrationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistrationController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // See if we have a user already logged in
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if(user != null)
        {
            // Redirect the to the dashboard page
            response.sendRedirect("dashboard");
        }
        else {
            request.getRequestDispatcher("/registrationPage.jsp").forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int MIN_PASS_LENGTH = 6;

		String emptyInputError1 = "Please Enter a UserID"; 
		String emptyInputError2 = "Please Enter a Password";
		String emptyInputError3 = "Please Enter a First Name";
		String emptyInputError4 = "Please Enter a Last Name";
		String emptyInputError5 = "Please Enter a Address";
		String emptyInputError6 = "Please Enter a Phone Number";
        String emptyInputError7 = "Please Enter a security answer";
        String emptyInputError8 = "Please choose a security question";
        String invalidStrengthPass = "You password needs to be stronger, it must be longer than " + MIN_PASS_LENGTH +
                " characters and contain a number.";
		
		String userID = request.getParameter("userID");
		String password = request.getParameter("password");
        String encryptedPassword = "";
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String address = request.getParameter("address");
		String phoneNumber = request.getParameter("phoneNumber");
        String securityQuestion = request.getParameter("securityQuestion");
        String securityAnswer = request.getParameter("securityAnswer");
			
        if(userID.isEmpty())
        {
            request.setAttribute("userError", emptyInputError1);
        }
        else
            request.setAttribute("userID", userID);

        if(password.isEmpty())
        {
            request.setAttribute("passwordError", emptyInputError2);
        }
        else if(password.length() <= MIN_PASS_LENGTH)
        {
            request.setAttribute("passwordError", invalidStrengthPass);
        }
        else
        {
            boolean foundNumber = false;

            for(int i = 0; i < password.length(); i++) {
                if(Character.isDigit(password.charAt(i)))
                {
                    foundNumber = true;
                    break;
                }
            }

            if(!foundNumber)
            {
                request.setAttribute("passwordError", invalidStrengthPass);
            }
            else
            {
                encryptedPassword = MD5Hash.MD5(password);
                request.setAttribute("password", password);
            }
        }

        if(firstName.isEmpty())
        {
            request.setAttribute("firstNameError", emptyInputError3);
        }
        else {
            request.setAttribute("firstName", firstName);
        }

        if(lastName.isEmpty())
        {
            request.setAttribute("lastNameError", emptyInputError4);
        }
        else {
            request.setAttribute("lastName", lastName);
        }

        if(address.isEmpty())
        {
            request.setAttribute("addressError", emptyInputError5);
        }
        else {
            request.setAttribute("address", address);
        }

        if(phoneNumber.isEmpty())
        {
            request.setAttribute("phoneNumberError", emptyInputError6);
        }
        else {
            request.setAttribute("phoneNumber", phoneNumber);
        }

        if(securityAnswer.isEmpty())
        {
            request.setAttribute("securityAnswerError", emptyInputError7);
        }
        else {
            request.setAttribute("securityAnswer", securityAnswer);
        }

        if(securityQuestion.isEmpty())
        {
            request.setAttribute("securityQuestionError", emptyInputError8);
        }
        else {
            request.setAttribute("securityQuestion", securityAnswer);
        }

        // Check if there was any empty input and return back to the registration page
        if( userID.isEmpty() ||
            encryptedPassword.isEmpty() ||
            firstName.isEmpty() ||
            lastName.isEmpty() ||
            address.isEmpty() ||
            phoneNumber.isEmpty() ||
            securityAnswer.isEmpty() ||
            securityQuestion.isEmpty() )
        {
			request.getRequestDispatcher("/registrationPage.jsp").forward(request, response);
            return;
		}
        // No empty input, continue
		else
		{
            // Create our user
            User user = new User();
            user.setUsername(userID);
            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setPassword(encryptedPassword);
            user.setAddress(address);
            user.setPhone(phoneNumber);
            user.setSecurityQuestion(securityQuestion);
            user.setSecurityAnswer(securityAnswer);

            UserData userDatabase = new UserData();
            // First check if a user already exists for the username
            User existingUser = userDatabase.getUserByUsername(userID);
            if(existingUser != null)
            {
                request.setAttribute("userError", "A user already exists with that username, please enter a " +
                        "different username.");
                request.getRequestDispatcher("/registrationPage.jsp").forward(request, response);
            }
            else {
                // Save the user in the database
                boolean isCreated = userDatabase.createUser(user);
                if (isCreated)
                {
                    // Start the user's server session (AKA log the user in)
                    HttpSession session = request.getSession(true);
                    session.setAttribute("user", user);
                    request.setAttribute("user", user);

                    // Redirect the to the dashboard page
                    response.sendRedirect("dashboard");

                } else {
                    request.setAttribute("userError", "Error saving the user in the database.");
                    request.getRequestDispatcher("/registrationPage.jsp").forward(request, response);
                }
            }
		}
	}

}
