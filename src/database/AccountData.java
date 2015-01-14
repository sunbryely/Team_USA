/**
 * Filename: AccountData.java
 * Created by Team USA
 * Date: 11/3/14
 * Description:
 *      This file contains the AccountData class which is used for access to the
 *      database table(s) containing all the account records.
 */
package database;

import model.Account;
import model.Checkings;
import model.Savings;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates one table: account
 *
 * The account table is a generic table that holds all the information that's the same
 * for both type of accounts.
 *
 * The checkingAccount and savingsAccount tables (in other files) will hold any extra
 * information for that type of account. They each contain an accountNumber field
 * that links them to a row in this parent table for all their other data.
 *
 * @author Team USA
 */
public class AccountData extends Database
{
    private static final String TABLE_NAME = "account";

    // The table schema here defines the parent Account class.
    private static final String TABLE_SCHEMA =
            "CREATE TABLE IF NOT EXISTS " + DB_NAME + "." + TABLE_NAME + " " +
                    "( " +
                        "id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                        "accountNumber BIGINT NOT NULL, " + // Should be auto generated
                        "balance DECIMAL(20,2) DEFAULT 0.0, " +
                        "dateCreated TIMESTAMP, " +
                        "owner varchar(255) NOT NULL, " +
                        "accountType varchar(45) NOT NULL DEFAULT 'checking', " +
                        "dateBalanceUnderMin TIMESTAMP, " +
                        "interestRate DECIMAL(10, 10), " +
                        "interestPeriod TIMESTAMP, " +
                        "UNIQUE KEY (accountNumber) " + // NOTE: no comma after last one
                    ");";


    private static final String GET_RECORD =
            "SELECT * FROM " + TABLE_NAME + " " +
            "WHERE accountNumber = ?;";

    private static final String GET_RECORDS =
            "SELECT * FROM " + TABLE_NAME;

    private static final String GET_RECORDS_BY_USER =
            "SELECT * FROM " + TABLE_NAME + " " +
                    "WHERE owner = ?;";

    private static final String ADD_RECORD =
            "INSERT INTO " + TABLE_NAME + " " +
            "( " +
                "balance, " +
                "dateCreated, " +
                "owner, " +
                "interestRate, " +
                "dateBalanceUnderMin, " +
                "interestPeriod, " +
                "accountType " + // NOTE: no comma after last one
            ") " +
            "VALUES " +
            "( " +
                "?, " + //balance             (param 1)
                "?, " + //dateCreated         (param 2)
                "?, " + //owner               (param 3)
                "?, " + //interestRate        (param 4)
                "?, " + //dateBalanceUnderMin (param 5)
                "?, " + //interestPeriod      (param 6)
                "? " +  //accountType         (param 7) // NOTE: no comma after last one
            ")";

    private static final String EDIT_RECORD =
            "UPDATE " + TABLE_NAME + " " +
            "SET " +
                "balance = ?, " + 	          // param 1
                "owner = ?, " +               // param 2
                "interestRate = ?, " +        // param 3
                "dateBalanceUnderMin = ?, " + // param 4
                "interestPeriod = ? " +       // param 5 // NOTE: no comma after last one
            "WHERE accountNumber = ?;";	      // param 5

    private static final String DELETE_RECORD =
            "DELETE FROM " + TABLE_NAME + " " +
            "WHERE accountNumber = ?;";


    /**
     * Public ctor, handles creation of a table and initialization of
     * prepared statements.
     */
    public AccountData()
    {
        super();
        createTable();
    }

    /*************************************************************************
     *            			 	Get/Read calls						         *
     *************************************************************************/
    /**
     * Fetch an account by it's account number.
     *
     * @param number The number of the Account to fetch from the databse.
     * @return an Account that corresponds to the number passed in.
     *      This actually returns either a Checkings or a Savings type object but it's passed
     *      as the generic Account type.
     */
    public Account getAccountByNumber(Long number)
    {
        Account account = null;
        if(number != null)
        {
            // Add the query parameters
            ArrayList<Object> params = new ArrayList<Object>();
            params.add(number);

            account = (Account) queryOne(GET_RECORD, params);

            if(account != null)
            {
                // Get the appropriate account type based on whether it's a Checkings or Savings type
                if(account.getAccountType().equals("checking"))
                {
                    Checkings checkings = (new CheckingData()).getAccountByNumber(account.getNumber());
                    checkings.setAccountType(account.getAccountType());
                    checkings.setBalance(account.getBalance());
                    checkings.setdateCreated(account.getdateCreated());
                    checkings.setNumber(account.getNumber());
                    checkings.setinterestRate(account.getinterestRate());
                    checkings.setDateBalanceUnderMin(account.getDateBalanceUnderMin());
                    checkings.setInterestPeriod(account.getInterestPeriod());
                    checkings.setUser(account.getUser());
                    return checkings;
                }
                else if(account.getAccountType().equals("savings"))
                {
                    Savings savings = (new SavingsData()).getAccountByNumber(account.getNumber());
                    savings.setAccountType(account.getAccountType());
                    savings.setBalance(account.getBalance());
                    savings.setdateCreated(account.getdateCreated());
                    savings.setNumber(account.getNumber());
                    savings.setinterestRate(account.getinterestRate());
                    savings.setDateBalanceUnderMin(account.getDateBalanceUnderMin());
                    savings.setInterestPeriod(account.getInterestPeriod());
                    savings.setUser(account.getUser());
                    return savings;
                }
                // else we could add other account types here in the future.
            }
        }

        return account;
    }

    /**
     * Fetches an account record from the database based on the account number
     * of the Account object passed in.
     *
     * @param account The account to fetch it's data from the database
     * @return The account corresponding to the account passed in.
     */
    public Account getAccount(Account account)
    {
        // Delegate the call to another method
        return getAccountByNumber(account.getNumber());
    }

    /**
     * Gets the number of rows in the account table
     * @return The number of rows in the account table
     */
    public Long getCount()
    {
        return countQuery(TABLE_NAME);
    }

    /**
     * Fetches a list of all the accounts in the database.
     *
     * @return list of all accounts in the database
     */
    public List<Account> getAccountList()
    {
        ArrayList<Object> retList = (ArrayList<Object>) queryAll(GET_RECORDS, null);

        // We have to cast all the generic Object types to Accounts for proper return value
        ArrayList<Account> accountList = new ArrayList<Account>();
        for(Object obj : retList) {
            accountList.add((Account) obj);
        }
        return accountList;
    }

    /**
     * Fetches a list of all the accounts in the database that have the user
     * with the username passed in as their owner.
     *
     * @return list of all accounts in the database that have a user as their owner
     */
    public List<Account> getAccountList(String username)
    {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(username);

        // Delegate to the parent method to fetch the accounts
        ArrayList<Object> retList = (ArrayList<Object>) queryAll(GET_RECORDS_BY_USER, params);

        // We have to cast all the generic Object types to Accounts for proper return value
        ArrayList<Account> accountList = new ArrayList<Account>();
        for(Object obj : retList) {
            accountList.add((Account) obj);
        }

        return accountList;
    }

    /**
     * Fetches a list of all the accounts in the database that have the user
     * as their owner.
     *
     * @return list of all accounts in the database that have a user as their owner
     */
    public List<Account> getAccountList(User user)
    {
        return getAccountList(user.getUsername());
    }


    /*************************************************************************
     *            			 		Create call						         *
     *************************************************************************/
    /**
     * Adds a new generic account to the database.
     *
     * @param account The Account entity to insert into the db
     * @return The account number of the account that was inserted into the db
     */
    public Long createAccount(Account account) {

        ArrayList<Object> params = new ArrayList<Object>();

        params.add(account.getBalance());
        params.add(account.getdateCreated());
        params.add(account.getUser().getUsername());
        params.add(account.getinterestRate());
        params.add(account.getDateBalanceUnderMin());
        params.add(account.getInterestPeriod());
        params.add(account.getAccountType());

        // Return the new account number from the database.
        // The database has a constraint where all the auto-incremented ids start at 10000
        long number = createRecord(ADD_RECORD, params);

        if(number > 0) {
            return number;
        }
        return null;
    }

    /*************************************************************************
     *            			 		Edit calls						         *
     *************************************************************************/
    /**
     * Updates an account record in the database.
     *
     * @param account The record to update
     * @return true if the update was successful, false otherwise
     */
    public boolean editAccount(Account account)
    {
        ArrayList<Object> params = new ArrayList<Object>();

        params.add(account.getBalance());
        params.add(account.getUser().getUsername());
        params.add(account.getinterestRate());
        params.add(account.getDateBalanceUnderMin());
        params.add(account.getInterestPeriod());

        params.add(account.getNumber()); // The identifying param

        return editRecord(EDIT_RECORD, params);
    }


    /*************************************************************************
     *            			 		Delete calls						         *
     *************************************************************************/
    /**
     * Deletes an account record from the database.
     *
     * WARNING this action can't be undone,
     * only call when necessary.
     *
     * @param accountNumber the accountNumber of the user to delete
     */
    public void deleteAccount(Long accountNumber)
    {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(accountNumber);

        // make sure to delete the linked checkingAccount or savingsAccount row in the other table(s).
        if((new CheckingData()).deleteAccount(accountNumber) || (new SavingsData()).deleteAccount(accountNumber)) {
            deleteRecord(DELETE_RECORD, params);
        }
    }

    /**
     * Deletes an account record from the database.
     *
     * WARNING this action can't be undone,
     * only call when necessary.
     *
     * @param account the account to remove from the database
     */
    public void deleteAccount(Account account)
    {
        deleteAccount(account.getNumber());
    }

    /**
     * Deletes all the accounts associated with a user from the database
     *
     * @param username The username of the user whose accounts we want to delete.
     */
    public void deleteUsersAccounts(String username)
    {
        List<Account> usersAccounts = getAccountList(username);

        if(!usersAccounts.isEmpty())
        {
            CheckingData checkingData = new CheckingData();
            SavingsData savingsData = new SavingsData();

            // Remove all of a user's related accounts here
            for(Account account : usersAccounts)
            {
                if( account.getAccountType().equalsIgnoreCase("checking") ||
                    account.getAccountType().equalsIgnoreCase("checkings"))
                {
                    checkingData.deleteAccount(account);
                }
                else if(account.getAccountType().equalsIgnoreCase("savings"))
                {
                    savingsData.deleteAccount(account);
                }

                deleteAccount(account);
            }
        }
    }

    /*************************************************************************
     *            			 	Table creation call					     	 *
     *************************************************************************/
    /**
     * Creates the account table if it doesn't exist
     */
    @Override
    public void createTable() {
        makeTable(TABLE_SCHEMA);
    }

    /*************************************************************************
     * 								Helper methods							 *
     *************************************************************************/
    /**
     * This method builds an Account entity using data from the database.
     *
     * @param results The results received from the database query call.
     * @return A new Account entity built from the results set.
     * @throws SQLException
     */
    protected Account buildRecord(ResultSet results) throws SQLException
    {
        Account account = new Account();

        account.setNumber(             results.getLong("accountNumber"));
        account.setBalance(            results.getDouble("balance"));
        account.setdateCreated(        results.getDate("dateCreated"));
        account.setAccountType(        results.getString("accountType"));
        account.setinterestRate(       results.getDouble("interestRate") );
        account.setDateBalanceUnderMin(results.getDate("dateBalanceUnderMin") );
        account.setInterestPeriod(     results.getDate("interestPeriod"));

        // Make sure to Fetch and set the user to the Account here
        UserData userDb = new UserData();
        User user = userDb.getUserByUsername(results.getString("owner"));

        account.setUser(user);

        return account;
    }
}
