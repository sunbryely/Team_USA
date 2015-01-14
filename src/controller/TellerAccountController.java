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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Team USA on 12/1/14.
 * 
 * Servlet implementation class TellerAccountController
 * This class controlls the Teller account interactions. 
 * 
 */

@WebServlet(name="tellerAccount", urlPatterns={"/tellerAccount"})
public class TellerAccountController extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String genericError = "No account found with that id.";
        String accessError = "You don't have permissions to view this account.";

        // See if we don't have a user logged in yet
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if(user == null)
        {
            response.sendRedirect("login");
            return;
        }

        if(!user.getisAdmin()) {
            request.setAttribute("error", accessError);
            request.getRequestDispatcher("/tellerAccount.jsp").forward(request, response);
            return;
        }

        AccountData accountData = new AccountData();
        List<Account> userAccounts = new ArrayList<>();
        String accountIdString = request.getParameter("id");
        Long accountNumber = StringParseService.parseLong(accountIdString);
        Account account = null;

        // Check for invalid account number input
        if(accountIdString == null || accountIdString.isEmpty() || accountNumber == null)
        {
            request.setAttribute("error", genericError);
            request.getRequestDispatcher("/tellerAccount.jsp").forward(request, response);
            return;
        }

        // Check if the account exists.
        account = accountData.getAccountByNumber(accountNumber);

        if(account == null){
            request.setAttribute("error", genericError);
            request.getRequestDispatcher("/tellerAccount.jsp").forward(request, response);
            return;
        }

        // Check if the account needs interest applied
        if( account.needsInterest() ) {


            request.setAttribute("interestAmount", account.getEstimatedInterest());
            request.setAttribute("needsInterest", "true");
        }

        // Check if this account has a penalty needing applying and set the appropriate attribute
        if( account.needsPenalty() ) {
            request.setAttribute("needsPenalty", "true");
        }

        // Get the transactions for this account
        TransactionData transactionData = new TransactionData();
        List<Transaction> transactions = transactionData.getAllRecordsByAccountNum(accountNumber);

        request.setAttribute("account", account);
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("/tellerAccount.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // See if we don't have a user logged in yet
        String genericError = "No account found with that id.";
        String accessError = "You don't have permissions to view this account.";

        // See if we don't have a user logged in yet
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if(user == null)
        {
            response.sendRedirect("login");
            return;
        }

        if(!user.getisAdmin()) {
            request.setAttribute("error", accessError);
            request.getRequestDispatcher("/tellerAccount.jsp").forward(request, response);
            return;
        }

        AccountData accountData = new AccountData();
        String accountIdString = request.getParameter("id");
        Long accountNumber = StringParseService.parseLong(accountIdString);
        Account account = null;

        // Check for invalid account number input
        if(accountIdString == null || accountIdString.isEmpty() || accountNumber == null)
        {
            request.setAttribute("error", genericError);
            request.getRequestDispatcher("/account.jsp").forward(request, response);
            return;
        }

        // Check if the account exists.
        account = accountData.getAccountByNumber(accountNumber);

        if(account == null)
        {
            request.setAttribute("error", genericError);
            request.getRequestDispatcher("/tellerAccount.jsp").forward(request, response);
            return;
        }

        // Check for applying penalty parameter
        String applyPenalty = request.getParameter("applyPenalty");
        if(applyPenalty != null && applyPenalty.equals("true"))
        {
            // Apply the penalty to the account
            account.applyPenalty();
            accountData.editAccount(account);

            TransactionData transactionData = new TransactionData();

            // Save the transaction
            Transaction transaction = new Transaction(account, "penalty", Account.LOW_BALANCE_PENALTY_AMOUNT);
            transactionData.createTransaction(transaction);

            List<Transaction> transactions = transactionData.getAllRecordsByAccountNum(accountNumber);
            // Check if the account needs interest applied
            if( account.needsInterest() ) {
                request.setAttribute("needsInterest", "true");
            }

            // Check if this account has a penalty needing applying and set the appropriate attribute
            if( account.needsPenalty() ) {
                request.setAttribute("needsPenalty", "true");
            }

            request.setAttribute("account", account);
            request.setAttribute("transactions", transactions);
        }

        // Check for applying interest parameter
        String applyInterest = request.getParameter("applyInterest");
        if(applyInterest != null && applyInterest.equals("true"))
        {
            // Apply the interest to the account.
            double interest = account.getEstimatedInterest();

            account.applyInterest();

            accountData.editAccount(account);

            TransactionData transactionData = new TransactionData();

            // Save the transaction
            Transaction transaction = new Transaction(account, "interest", interest);
            transactionData.createTransaction(transaction);

            List<Transaction> transactions = transactionData.getAllRecordsByAccountNum(accountNumber);
            // Check if the account needs interest applied
            if( account.needsInterest() ) {
                request.setAttribute("needsInterest", "true");
            }

            // Check if this account has a penalty needing applying and set the appropriate attribute
            if( account.needsPenalty() ) {
                request.setAttribute("needsPenalty", "true");
            }

            request.setAttribute("account", account);
            request.setAttribute("transactions", transactions);
        }

        String closeAccount = request.getParameter("closeAccount");
        if(closeAccount != null && closeAccount.equals("true"))
        {
            //check that account is empty
            if (account.getBalance() != 0) {
                request.setAttribute("closureError", "Account not empty. Transfer funds first");
                TransactionData transactionData = new TransactionData();
                List<Transaction> transactions = transactionData.getAllRecordsByAccountNum(accountNumber);

                // Check if the account needs interest applied
                if( account.needsInterest() ) {
                    request.setAttribute("needsInterest", "true");
                }

                // Check if this account has a penalty needing applying and set the appropriate attribute
                if( account.needsPenalty() ) {
                    request.setAttribute("needsPenalty", "true");
                }

                request.setAttribute("account", account);
                request.setAttribute("transactions", transactions);
                request.getRequestDispatcher("/tellerAccount.jsp").forward(request, response);
                return;
            }

            //passes all checks, remove account
            accountData.deleteAccount(accountNumber);
            request.setAttribute("accountClosed", "Account successfully closed.");
            response.sendRedirect("teller");
            return;
        }

        request.getRequestDispatcher("/tellerAccount.jsp").forward(request, response);
        return;
    }
}
