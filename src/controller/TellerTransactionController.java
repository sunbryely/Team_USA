package controller;

import database.AccountData;
import database.TransactionData;
import model.Account;
import model.Transaction;
import model.User;
import services.StringParseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Team USA on 11/19/14.
 * 
 * Servlet implementation class TellerTransactionController
 * This class controls any interactions dealing with teller transactions.
 * 
 */

@WebServlet(name="tellerTransaction", urlPatterns={"/tellerTransaction"})
public class TellerTransactionController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // Save the account in the database
    	HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");

        if(user != null)
        {
            if(user.getisAdmin())
            {
                //user exists and is admin
                AccountData accData = new AccountData();
                Account toTransact;

                String accNumber = request.getParameter("accountNumber");
                String transType = request.getParameter("transactionType");
                String amount = request.getParameter("amount");
                Long accountNumber = StringParseService.parseLong(accNumber);
                Double transAmount = StringParseService.parseDouble(amount);
                /*check if account is nonexistent*/
                if(accNumber == null || accNumber.isEmpty() || accountNumber == null)
                {
                    request.setAttribute("error", "Account Number is incorrect");
                	request.getRequestDispatcher("/tellerTransaction.jsp").forward(request, response);
                	return;
                }

                toTransact = accData.getAccountByNumber(accountNumber);

                if(toTransact == null)
                {
                    request.setAttribute("error", "No account exists with that account number.");
                    request.getRequestDispatcher("/tellerTransaction.jsp").forward(request, response);
                    return;
                }

                /*check if the transaction type is correct value*/
                if(!transType.equalsIgnoreCase("credit") && !transType.equalsIgnoreCase("debit"))
                {
                    request.setAttribute("error", "Invalid transaction type, valid types are: \"credit\" and \"debit\".");
                	request.getRequestDispatcher("/tellerTransaction.jsp").forward(request, response);
                	return;
                }

                if(amount == null || amount.isEmpty() || transAmount == null)
                {
                    request.setAttribute("error", "You must enter a valid amount for the transaction.");
                    request.getRequestDispatcher("/tellerTransaction.jsp").forward(request, response);
                    return;
                }

                /*checks if transaction is debit*/
                if(transType.equalsIgnoreCase("debit"))
                {
                	/*check for insufficient funds*/
                	if(toTransact.getBalance() - transAmount < 0)
                	{
                		request.setAttribute("debitError", "Insufficient funds to debit");
                		request.getRequestDispatcher("/tellerTransaction.jsp").forward(request, response);
                    	return;
                	}
                	
                	toTransact.withdraw(transAmount);
                }
                else if(transType.equalsIgnoreCase("credit"))
                {
                	toTransact.deposit(transAmount);
                }
                
                if(accData.editAccount(toTransact))
                {
                    // Save the transaction in the database
                    Transaction transaction = new Transaction(toTransact, transType, transAmount);
                    TransactionData transactionData = new TransactionData();
                    transactionData.createTransaction(transaction);

                	request.setAttribute("success", "Transaction was successful.");
                	request.getRequestDispatcher("/tellerTransaction.jsp").forward(request, response);
                	return;
                }

                request.setAttribute("error", "Transaction was not successful.");
                request.getRequestDispatcher("/tellerTransaction.jsp").forward(request, response);
                return;
                
            }
            else {
                // User exists but is not an admin
                response.sendRedirect("dashboard");
                return;
            }
        }
        else {
            // User is not logged in, redirect the to the login page
            response.sendRedirect("login");
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // See if we have a user already logged in
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");

        if(user != null)
        {
            if(user.getisAdmin() == true)
            {
                //user exists and is admin
                AccountData accData = new AccountData();

                List<Account> accounts = accData.getAccountList();

                request.setAttribute("accounts", accounts);

                request.getRequestDispatcher("/tellerTransaction.jsp").forward(request, response);
                return;
            }
            else {
                // User exists but is not an admin
                response.sendRedirect("dashboard");
                return;
            }
        }
        else {
            // User is not logged in, redirect the to the login page
            response.sendRedirect("login");
            return;
        }
    }
}
