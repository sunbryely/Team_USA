package controller;

import database.CheckingData;
import database.SavingsData;
import database.TransactionData;
import model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Team USA on 12/1/14.
 * 
 * Servlet implementation class AccountCreation
 * This class controls any interactions dealing with the creation of accounts 
 * 
 */
@WebServlet(name="addAccount", urlPatterns={"/addAccount"})
public class AccountCreationController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // See if we don't have a user logged in yet
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if(user == null)
        {
            response.sendRedirect("login");
            return;
        }

        // Redirect the to the account creation page
        request.getRequestDispatcher("/accountCreation.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // See if we don't have a user logged in yet
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if(user == null)
        {
            response.sendRedirect("login");
            return;
        }

        String emptyAccountTypeError = "You must choose an account type.";
        String invalidAccountTypeError = "The account type must be either \"checking\" or \"savings\".";
        String invalidDepositError = "Invalid value entered for the deposit.";

        String accountType = request.getParameter("accountType");
        String deposit = request.getParameter("deposit");
        Double depositAmount = 0.0;
        boolean error = false;

        // Validate the input from the user
        if( accountType == null || accountType.isEmpty() )
        {
            error = true;
            request.setAttribute("accountTypeError", emptyAccountTypeError);
        }
        else if(!accountType.equalsIgnoreCase("checking") && !accountType.equalsIgnoreCase("savings"))
        {
            error = true;
            request.setAttribute("accountTypeError", invalidAccountTypeError);
        }
        else
        {
            request.setAttribute("accountType", accountType);
        }

        if(deposit != null && !deposit.isEmpty())
        {
            // Make sure the deposit is a valid double
            depositAmount = Double.parseDouble(deposit);

            if(depositAmount == null)
            {
                error = true;
                request.setAttribute("depositError", invalidDepositError);
            }
            else if(depositAmount < Account.MIN_BALANCE) {
                error = true;
                request.setAttribute("depositError", "Your initial deposit must be at least $" + Account.MIN_BALANCE + "0");
            }
            else {
                request.setAttribute("deposit", deposit);
            }
        }
        else
        {
            request.setAttribute("deposit", "0");
        }

        // If there's an error send the user back with an error message
        if(error)
        {
            // Redirect the to the account creation page
            request.getRequestDispatcher("/accountCreation.jsp").forward(request, response);
            return;
        }

        TransactionData transactionData = new TransactionData();

        // Create and save the accounts
        if(accountType.equalsIgnoreCase("checking"))
        {
            CheckingData checkingData = new CheckingData();

            Checkings checkings = new Checkings(user);
            checkings.deposit(depositAmount);
            checkings.setInterestRate();

            try {
                Long accountNumber = checkingData.createCheckings(checkings);
                checkings.setNumber(accountNumber);

                Transaction transaction = new Transaction(checkings, "deposit", depositAmount);
                transactionData.createTransaction(transaction);
                response.sendRedirect("dashboard");
                return;
            }
            catch(Exception e) {
                e.printStackTrace();
                response.sendError(500);
                return;
            }
        }
        else if(accountType.equalsIgnoreCase("savings"))
        {
            SavingsData savingsData = new SavingsData();

            Savings savings = new Savings(user);
            savings.deposit(depositAmount);
            savings.setInterestRate();

            try {
                Long accountNumber = savingsData.createSavings(savings);
                savings.setNumber(accountNumber);

                Transaction transaction = new Transaction(savings, "deposit", depositAmount);
                transactionData.createTransaction(transaction);
                response.sendRedirect("dashboard");
                return;
            }
            catch(Exception e) {
                e.printStackTrace();
                response.sendError(500);
                return;
            }
        }

    }
}
