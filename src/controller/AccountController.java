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
 * Servlet implementation class AccountController
 * This class controls any interactions dealing with accounts. 
 * 
 */
@WebServlet(name="account", urlPatterns={"/account"})
public class AccountController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // See if we don't have a user logged in yet
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        if(user == null)
        {
            response.sendRedirect("login");
            return;
        }

        AccountData accountData = new AccountData();
        List<Account> userAccounts = new ArrayList<>();
        String genericError = "No account found with that id.";
        String accessError = "You don't have permissions to view this account.";
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

        if(account == null){
            request.setAttribute("error", genericError);
            request.getRequestDispatcher("/account.jsp").forward(request, response);
            return;
        }

        // Check if the user has access to the account
        userAccounts = accountData.getAccountList(user);

        if(!userAccounts.contains(account)) {
            request.setAttribute("error", accessError);
            request.getRequestDispatcher("/account.jsp").forward(request, response);
            return;
        }

        // Get the transactions for this account
        TransactionData transactionData = new TransactionData();
        List<Transaction> transactions = transactionData.getAllRecordsByAccountNum(accountNumber);

        request.setAttribute("account", account);
        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("/account.jsp").forward(request, response);
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

        AccountData accountData = new AccountData();
        List<Account> userAccounts = new ArrayList<>();
        String accountIdString = request.getParameter("id");
        Long accountNumber = StringParseService.parseLong(accountIdString);
        String genericError = "No account found with that id.";
        String accessError = "You don't have permissions to view this account.";
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

        if(account == null){
            request.setAttribute("error", genericError);
            request.getRequestDispatcher("/account.jsp").forward(request, response);
            return;
        }

        // Check if the user has access to the account
        userAccounts = accountData.getAccountList(user);

        if(!userAccounts.contains(account)) {
            request.setAttribute("error", accessError);
            request.getRequestDispatcher("/account.jsp").forward(request, response);
            return;
        }


        String closeAccount = request.getParameter("closeAccount");
        if(closeAccount.equals("true")) {

            //check that account is empty
            if (account.getBalance() != 0) {
                request.setAttribute("closureError", "Account not empty. Transfer funds first");
                TransactionData transactionData = new TransactionData();
                List<Transaction> transactions = transactionData.getAllRecordsByAccountNum(accountNumber);

                request.setAttribute("account", account);
                request.setAttribute("transactions", transactions);
                request.getRequestDispatcher("/account.jsp").forward(request, response);
                return;
            }

            //passes all checks, remove account
            accountData.deleteAccount(accountNumber);
            request.setAttribute("accountClosed", "Account successfully closed.");
            response.sendRedirect("dashboard");
            return;
        }
    }
}
