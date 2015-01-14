/**
 * Filename: SavingsData.java
 * Created by Team USA
 * Date: 11/4/14
 * Description:
 *      This file contains the SavingsData class which is used for access to the
 *      database table(s) containing all the Savings Account records.
 */
package database;

import model.Account;
import model.Savings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates one table: savingsAccount
 *
 * The parent table is the account table that holds all the base information of the account.
 * This table holds extra information that relates to savings accounts. To get the base
 * account information you will need to fetch the appropriate row from the account table
 * using the AccountData class.
 *
 * @author Team USA
 */
public class SavingsData extends Database {

    private static final String SAVINGS_TABLE_NAME = "savingsAccount";

    private static final String SAVINGS_TABLE_SCHEMA =
            "CREATE TABLE IF NOT EXISTS " + DB_NAME + "." + SAVINGS_TABLE_NAME + " " +
                    "( " +
                    "id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "accountNumber BIGINT NOT NULL, " +  // This corresponds to the Account table
                    "UNIQUE KEY (accountNumber) " +
                    ");";

    private static final String GET_RECORD =
            "SELECT * FROM " + SAVINGS_TABLE_NAME + " " +
                    "WHERE accountNumber = ?;";

    private static final String GET_RECORDS =
            "SELECT * FROM " + SAVINGS_TABLE_NAME;


    private static final String ADD_SAVINGS_RECORD =
            "INSERT INTO " + SAVINGS_TABLE_NAME + " " +
                    "( " +
                        "accountNumber " +
                    ") " +
                    "VALUES " +
                    "( " +
                        "? " + //accountNumber (param 1)
                    ")";

    private static final String DELETE_SAVINGS_RECORD =
            "DELETE FROM " + SAVINGS_TABLE_NAME + " " +
                    "WHERE accountNumber = ?;";

    /**
     * Public ctor, handles creation of a table and initialization of
     * prepared statements.
     */
    public SavingsData()
    {
        super();
        createTable();
    }

    /**
     * Gets a a savings account based on the number passed in.
     *
     * Note: that this does not get all the account information but the SAVINGS ONLY
     * information related to this account. To get all the base account information
     * make sure to fetch an account row by using the AccountData access class.
     * @param number the number of the account to fetch from the database
     * @return The Savings account containing only the information stored
     *         in the savings table.
     */
    public Savings getAccountByNumber(Long number)
    {
        // Add the query parameters
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(number);

        return (Savings) queryOne(GET_RECORD, params);
    }

    /**
     Gets a a savings account based on the account passed in.
     *
     * Note: that this does not get all the account information but the SAVINGS ONLY
     * information related to this account. To get all the base account information
     * make sure to fetch an account row by using the AccountData access class.
     *
     * @param account The account to fetch the savings information from
     * @return The Savings account containing only the information stored
     *         in the savings table.
     */
    public Savings getAccount(Account account)
    {
        return getAccountByNumber(account.getNumber());
    }

    /**
     * @return list of all the savings accounts in the database
     */
    public List<Savings> getAccountList()
    {
        ArrayList<Object> retList = (ArrayList<Object>) queryAll(GET_RECORDS, null);

        // Make sure we return objects of type Savings
        ArrayList<Savings> accountList = new ArrayList<Savings>();
        for(Object obj : retList) {
            accountList.add((Savings) obj);
        }

        return accountList;
    }

    /**
     * Adds a new savings accounts to the database
     *
     * @param account the Savings account entity to insert into the db.
     * @return the account number relating to the newly inserted row.
     */
    public Long createSavings(Savings account)
    {
        // Make sure to add the generic row first
        Long accountNum = (new AccountData()).createAccount(account);

        if(accountNum != null) {
            ArrayList<Object> params = new ArrayList<Object>();
            params.add(accountNum);

            createRecord(ADD_SAVINGS_RECORD, params);
            // Return the account number
            return accountNum;
        }
        return null;
    }

    /**
     * Updates a savings account record in the database.
     *
     * @param account The record to update
     * @return true if the update was successful, false otherwise
     */
    public boolean editSavings(Savings account)
    {
        return (new AccountData()).editAccount(account);
    }

    /**
     * Deletes a savings account record from the database.
     *
     * WARNING this action can't be undone, only call when necessary.
     *
     * @param accountNumber the accountNumber of the account to delete
     * @return true if the delete was successful, false otherwise.
     */
    public boolean deleteAccount(Long accountNumber)
    {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(accountNumber);

        return deleteRecord(DELETE_SAVINGS_RECORD, params);
    }

    /**
     * Deletes a savings account record from the database.
     *
     * WARNING this action can't be undone, only call when necessary.
     *
     * @param account the account to delete
     * @return true if the delete was successful, false otherwise.
     */
    public boolean deleteAccount(Account account)
    {
        return deleteAccount(account.getNumber());
    }

    /**
     * Deletes a savings account record from the database.
     *
     * WARNING this action can't be undone, only call when necessary.
     *
     * @param account the savings account to delete
     * @return true if the delete was successful, false otherwise.
     */
    public boolean deleteAccount(Savings account)
    {
        return deleteAccount(account.getNumber());
    }

    /**
     * Creates a new savings table in the database if it doesn't exist yet.
     */
    @Override
    public void createTable() {
        makeTable(SAVINGS_TABLE_SCHEMA);
    }


    /**
     * This method builds a Savings account entity using data from the database.
     *
     * @param results The results received from the database query call.
     * @return A new User entity built from the results set.
     * @throws SQLException
     */
    protected Savings buildRecord(ResultSet results) throws SQLException
    {
        Savings savingsAccount = new Savings();
        // Add any data unique to savings account here.
        return savingsAccount;
    }
}
