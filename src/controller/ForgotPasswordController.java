package controller;

import database.UserData;
import model.User;
import services.MD5Hash;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Team USA on 12/8/14.
 */
@WebServlet(name = "forgotPassword", urlPatterns = {"/forgotPassword"})
public class ForgotPasswordController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int MIN_PASS_LENGTH = 6;
        String pageLoad = request.getParameter("pageLoad");
        String userId = request.getParameter("userId");
        UserData userData = new UserData();

        switch (pageLoad) {
            case "initialPageLoad": {

                request.setAttribute("initialPageLoad", "true");

                if (userId == null || userId.isEmpty()) {
                    request.setAttribute("errorMessage", "You must enter a valid userId");
                    request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
                    return;
                }

                User user = userData.getUserByUsername(userId);

                if (user == null) {
                    request.setAttribute("errorMessage", "Invalid userId, You must enter a valid userId");
                    request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("userId", userId);

                request.setAttribute("securityQuestion", user.getSecurityQuestion());
                request.setAttribute("initialPageLoad", "false");
                request.setAttribute("stepTwoLoad", "true");
                break;
            }
            case "stepTwoLoad": {
                request.setAttribute("stepTwoLoad", "true");

                if (userId == null || userId.isEmpty()) {
                    request.setAttribute("errorMessage", "You must enter a valid userId");
                    request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
                    return;
                }

                User user = userData.getUserByUsername(userId);

                if (user == null) {
                    request.setAttribute("errorMessage", "Invalid userId, You must enter a valid userId");
                    request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("userId", userId);
                request.setAttribute("securityQuestion", user.getSecurityQuestion());

                String securityAnswer = request.getParameter("securityAnswer");

                if (securityAnswer == null || securityAnswer.isEmpty()) {
                    request.setAttribute("errorMessage", "You must enter a security answer.");
                    request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
                    return;
                }

                if(!user.getSecurityAnswer().equals(securityAnswer)) {
                    request.setAttribute("errorMessage", "The security answer doesn't match, please try again.");
                    request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
                    return;
                }
                request.setAttribute("initialPageLoad", "false");
                request.setAttribute("stepTwoLoad", "false");
                request.setAttribute("stepThreeLoad", "true");

                break;
            }
            case "stepThreeLoad": {
                request.setAttribute("stepThreeLoad", "true");

                if (userId == null || userId.isEmpty()) {
                    request.setAttribute("errorMessage", "You must enter a valid userId");
                    request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
                    return;
                }

                User user = userData.getUserByUsername(userId);

                if (user == null) {
                    request.setAttribute("errorMessage", "Invalid userId, You must enter a valid userId");
                    request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("userId", userId);

                String password = request.getParameter("password");
                String encryptedPassword = "";

                if(password == null || password.isEmpty()) {
                    request.setAttribute("errorMessage", "You must enter a password.");
                    request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
                    return;
                }
                else if(password.length() <= MIN_PASS_LENGTH)
                {
                    request.setAttribute("errorMessage", "You password needs to be stronger, it must be " +
                            "longer than " + MIN_PASS_LENGTH + " characters and must contain a number.");
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
                        request.setAttribute("errorMessage", "You password needs to be stronger, it must be " +
                                "longer than " + MIN_PASS_LENGTH + " characters and must contain a number.");
                    }
                    else
                    {
                        encryptedPassword = MD5Hash.MD5(password);
                        user.setPassword(encryptedPassword);

                        userData.editUser(user);

                        request.setAttribute("initialPageLoad", "false");
                        request.setAttribute("stepTwoLoad", "false");
                        request.setAttribute("stepThreeLoad", "false");
                        request.setAttribute("successLoad", "true");
                    }
                }
                break;
            }
            default:
                response.sendError(400);
                return;
        }

        request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setAttribute("initialPageLoad", "true");
        request.getRequestDispatcher("/forgotPassword.jsp").forward(request, response);
    }
}
