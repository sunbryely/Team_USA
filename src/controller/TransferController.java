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
import java.util.Date;

/**
 * Created by Team USA
 * 
 * Servlet implementation class TransferController
 * This class controls any interactions involving transferring money 
 * between different accounts. 
 * 
 */
@WebServlet(name = "transfer", urlPatterns = {"/transfer"})
public class TransferController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransferController() {
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
            // We'll fetch the accounts here so we can display them in the .jsp page
            AccountData accountData = new AccountData();

            String accountIdString = request.getParameter("id");
            Long accountNumber = StringParseService.parseLong(accountIdString);
            Account account = null;

            // Check for invalid account number input
            if(accountIdString == null || accountIdString.isEmpty() || accountNumber == null)
            {
                request.setAttribute("error", "Invalid account number");
                request.getRequestDispatcher("/userTransaction.jsp").forward(request, response);
                return;
            }

            // Check if the account exists.
            account = accountData.getAccountByNumber(accountNumber);

            if(account == null){
                request.setAttribute("error", "No account exists ");
                request.getRequestDispatcher("/userTransaction.jsp").forward(request, response);
                return;
            }

            if (!account.getUser().equals(user)){
                request.setAttribute("error", "Account not accessible");
                request.getRequestDispatcher("/userTransaction.jsp").forward(request, response);
                return;
            }

            request.setAttribute("account", account);
            request.getRequestDispatcher("/userTransaction.jsp").forward(request, response);
        }
        else
        {
            // User is not logged in, send them to login
            response.sendRedirect("login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        AccountData userAccountData = new AccountData();
        Double transactionAmount = null;
        Account receiverAccount = null;

        if(user != null) {
        	//Create Error Strings
            String emptyInputError2_1 = "Please Enter a Receiver";
            String emptyInputError2_2 = "This Receiver's Account does not exist";
            String emptyInputError2_3 = "Please Enter an Account Number";
            
            String emptyInputError3_1 = "Please Enter an Amount";
            String emptyInputError3_3 = "Please Enter an Amount Number";
            String insufficientFundsError = "Insufficient funds for withdrawal.";

            String accountNumber = request.getParameter("sender");
            String receiver = request.getParameter("receiver");
            String amount = request.getParameter("amount");
            String error = null;

            Long num = Long.parseLong(accountNumber);
            AccountData accountData = new AccountData();
            Account account = accountData.getAccountByNumber(num);
            if (account == null){
                //redirect to error
                request.setAttribute("error", "Account not found, cannot transfer.");
                request.getRequestDispatcher("/userTransaction.jsp").forward(request, response);
                return;
            }

            request.setAttribute("account", account);
            
            // Can't transfer from an account to the same account
            if (accountNumber.equals(receiver)) {
                request.setAttribute("error", "Cannot transfer, same account");
                request.getRequestDispatcher("/userTransaction.jsp").forward(request, response);
                return;
            }

            //check that account is associated with user logged in
            if (!account.getUser().equals(user)){
                request.setAttribute("error", "Account not accessible");
                request.getRequestDispatcher("/userTransaction.jsp").forward(request, response);
                return;
            }

            /*
             * The checks for correct receiver input
             *    -check if correct account number
             *    -Check if the string entered is a Long
             *    -Call on the AccountDatabase and see if the account exists
             */
            if (receiver.isEmpty()) {
                error = emptyInputError2_1;
            }
            else if(!StringParseService.isLong(receiver)) {
            	//Checks if receiver account is a long 
                error = emptyInputError2_3;
            }
            else
            {
                request.setAttribute("receiver", receiver);

            	//string to long conversion
            	Long receiverNum = Long.parseLong(receiver);
            	receiverAccount = accountData.getAccountByNumber(receiverNum);
            	
            	if(receiverAccount == null) {
                    error = emptyInputError2_2;
            	}          	
            }

            /*
             * Checks for correct amount input
             */
            if(amount.isEmpty()) {
                error = emptyInputError3_1;
            }
            //isLong is just used to make sure the amount variable is a numerical number
            else if(!StringParseService.isDouble(amount)) {
                error = emptyInputError3_3;
            }
            else {
                request.setAttribute("amount", amount);

                // check if the sender (current user) has enough money to send this amount of money
                transactionAmount = Double.parseDouble(amount);

                // Check for insufficient funds
                if(account.getBalance() - transactionAmount < 0)
                {
                    error = insufficientFundsError;
                }
            }

            // Errors? return now.
            if( error != null && !error.isEmpty() )
            {
                request.setAttribute("error", error);
                request.getRequestDispatcher("/userTransaction.jsp").forward(request, response);
                return;
            }


            if(receiverAccount != null)
            {
                // Perform the transaction
                account.withdraw(transactionAmount);
                receiverAccount.deposit(transactionAmount);

                // Save the accounts in the database
                if(accountData.editAccount(account) && accountData.editAccount(receiverAccount))
                {
                    // Save the transaction in the database
                    Transaction transaction = new Transaction(account, "transfer", transactionAmount, new Date(), receiverAccount);
                    Transaction transaction2 = new Transaction(receiverAccount, "transfer incoming", transactionAmount, new Date(), account);

                    TransactionData transactionData = new TransactionData();
                    transactionData.createTransaction(transaction);
                    transactionData.createTransaction(transaction2);

                    session.setAttribute("user", user);

                    request.setAttribute("amount", "");
                    request.setAttribute("receiver", "");

                    request.setAttribute("success", "Successfully transferred $" + transactionAmount + " to the " +
                            "account " + receiver + ".");
                }
                else
                {
                    request.setAttribute("error", "Error processing the transaction, couldn't save the transaction " +
                            "in the database.");
                }
            }

            request.getRequestDispatcher("/userTransaction.jsp").forward(request, response);
            return;
        }
        else {
            // User is not logged in, send them to login
            response.sendRedirect("login");
            return;
        }
    }
}

	
	
