/**
 * Filename: CheckingData.java
 * Created by Team USA
 * Date: 11/4/14
 * Description:
 *      This file contains the CheckingData class which is used for access to the
 *      database table(s) containing all the CheckingAccount records.
 */
package database;

import model.Account;
import model.Checkings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates one table: checkingAccount
 *
 * The parent table is the account table that holds all the base information of the account.
 * This table holds extra information that relates to checking accounts. To get the base
 * account information you will need to fetch the appropriate row from the account table
 * using the AccountData class.
 *
 * @author Team USA
 */
public class CheckingData extends Database {

    private static final String CHECKING_TABLE_NAME = "checkingAccount";

    private static final String CHECKING_TABLE_SCHEMA =
            "CREATE TABLE IF NOT EXISTS " + DB_NAME + "." + CHECKING_TABLE_NAME + " " +
                    "( " +
                    "id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "accountNumber BIGINT NOT NULL, " + // This corresponds to the Account table
                    "overdraftProtection BOOLEAN DEFAULT FALSE, " +
                    "UNIQUE KEY (accountNumber) " +
                    ");";

    private static final String GET_RECORD =
            "SELECT * FROM " + CHECKING_TABLE_NAME + " " +
                    "WHERE accountNumber = ?;";

    private static final String GET_RECORDS =
            "SELECT * FROM " + CHECKING_TABLE_NAME;

    private static final String ADD_CHECKING_RECORD =
            "INSERT INTO " + CHECKING_TABLE_NAME + " " +
                    "( " +
                        "accountNumber, " +
                        "overdraftProtection " + // NOTE: no comma after last one
                    ") " +
                    "VALUES " +
                    "( " +
                        "?, " + //accountNumber       (param 1)
                        "? " +  //overdraftProtection   (param 2)  // NOTE: no comma after last one
                    ");";

    private static final String DELETE_CHECKING_RECORD =
            "DELETE FROM " + CHECKING_TABLE_NAME + " " +
                    "WHERE accountNumber = ?;";

    /**
     * Public ctor, handles creation of a table and initialization of
     * prepared statements.
     */
    public CheckingData()
    {
        super();
        createTable();
    }

    /**
     * Gets a a checkings account based on the number passed in.
     *
     * Note: that this does not get all the account information but the CHECKING ONLY
     * information related to this account. To get all the base account information
     * make sure to fetch an account row by using the AccountData access class.
     * @param number the number of the account to fetch from the database
     * @return The Checkings account containing only the information stored
     *         in the checkings table.
     */
    public Checkings getAccountByNumber(Long number)
    {
        // Add the query parameters
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(number);

        return (Checkings) queryOne(GET_RECORD, params);
    }

    /**
     Gets a a checkings account based on the account passed in.
     *
     * Note: that this does not get all the account information but the CHECKING ONLY
     * information related to this account. To get all the base account information
     * make sure to fetch an account row by using the AccountData access class.
     *
     * @param account The account to fetch the checkings information from
     * @return The Checkings account containing only the information stored
     *         in the checkings table.
     */
    public Checkings getAccount(Account account)
    {
        return getAccountByNumber(account.getNumber());
    }

    /**
     * @return list of all checkings accounts in the database
     */
    public List<Checkings> getAccountList()
    {
        ArrayList<Object> retList = (ArrayList<Object>) queryAll(GET_RECORDS, null);
        ArrayList<Checkings> accountList = new ArrayList<Checkings>();

        for(Object obj : retList) {
            accountList.add((Checkings) obj);
        }

        return accountList;
    }

    /**
     * Adds a new checkings entity to the database.
     *
     * @param account The checkings account entity to insert into the db.
     * @return The account number related to this checkings account.
     * @throws SQLException
     */
    public Long createCheckings(Checkings account) throws SQLException
    {
        AccountData accountData = new AccountData();

        Long accountNum = accountData.createAccount(account);
        // Make sure to add the generic row first
        if(accountNum != null) {
            ArrayList<Object> params = new ArrayList<Object>();
            params.add(accountNum);
            params.add(account.getoverdraftProtection());

            createRecord(ADD_CHECKING_RECORD, params);
            return accountNum;
        }

        return null;
    }

    /**
     * Updates a checkings account record in the database.
     *
     * @param account The record to update
     * @return true if the update was successful, false otherwise
     */
    public boolean editCheckings(Checkings account)
    {
        return (new AccountData()).editAccount(account);
    }

    /**
     * Removes a checkings account row from the database
     *
     * @param accountNumber The account number of the checkings account to
     *                      remove from the database
     * @return true if deleted, false otherwise.
     */
    public boolean deleteAccount(Long accountNumber)
    {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(accountNumber);

        return deleteRecord(DELETE_CHECKING_RECORD, params);
    }

    /**
     * Removes a checkings account row from the database
     *
     * @param account The account containing the number of the checkings
     *                account to remove from the database
     * @return true if deleted, false otherwise.
     */
    public boolean deleteAccount(Account account)
    {
        return deleteAccount(account.getNumber());
    }

    /**
     * Removes a checkings account row from the database
     *
     * @param account The checkings account containing the number of the
     *                checkings account to remove from the database
     * @return true if deleted, false otherwise.
     */
    public boolean deleteAccount(Checkings account) {
        return deleteAccount(account.getNumber());
    }

    /**
     * Creates a new checking table in the database if it doesn't exist yet.
     */
    @Override
    public void createTable() {
        makeTable(CHECKING_TABLE_SCHEMA);
    }

    /**
     * This method builds a Checkings account entity using data from the database.
     *
     * @param results The results received from the database query call.
     * @return A new User entity built from the results set.
     * @throws java.sql.SQLException
     */
    protected Checkings buildRecord(ResultSet results) throws SQLException
    {
        Checkings checkingsAccount = new Checkings();
        checkingsAccount.setoverdraftProtection( results.getBoolean("overdraftProtection"));
        return checkingsAccount;
    }

}
