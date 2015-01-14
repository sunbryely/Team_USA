/**
 * Filename: UserData.java
 * Created by Team USA
 * Date: 11/19/14
 * Description:
 *      This file contains the TransctionData class which is used for access to the
 *      database table(s) containing all the Transaction records.
 */
package database;

import model.Account;
import model.Transaction;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The database access class for CRUD operations on Transactions that's
 * stored in the database.
 *
 * Also contains methods for accessing info pertaining to any User operations.
 *
 * @author Team USA
 */
public class TransactionData extends Database {

    private static final String TABLE_NAME = "transaction";

    private static final String TABLE_SCHEMA =
            "CREATE TABLE IF NOT EXISTS " + DB_NAME + "." + TABLE_NAME + " " +
                    "(" +
                        "id int NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                        "transactionType VARCHAR(20) DEFAULT 'Deposit', " +
                        "amount DECIMAL(5,2) DEFAULT 0.0, " +
                        "transactionDate TIMESTAMP, " +
                        "userAccountNum BIGINT, " +
                        "otherAccountNum BIGINT " +
                    ");";

    private static final String GET_RECORD =
            "SELECT * FROM " + TABLE_NAME + " " +
            "WHERE id = ?;";

    private static final String GET_RECORDS_BY_NUMBER =
            "SELECT * FROM " + TABLE_NAME + " " +
            "WHERE userAccountNum = ?;";

    private static final String GET_RECORDS =
            "SELECT * FROM " + TABLE_NAME;

    private static final String ADD_RECORD =
            "INSERT INTO " + TABLE_NAME + " " +
            "( " +
                "amount, " +
                "transactionType, " +
                "userAccountNum " + // NOTE: no comma after last one
            ") " +
            "VALUES " +
            "( " +
                "?, " +
                "?, " +
                "? " +  //    // NOTE: no comma after last one
            ")";
    private static final String ADD_RECORD_W_OTHER =
            "INSERT INTO " + TABLE_NAME + " " +
                    "( " +
                    "amount, " +
                    "transactionType, " +
                    "userAccountNum, " +
                    "otherAccountNum " + // NOTE: no comma after last one
                    ") " +
                    "VALUES " +
                    "( " +
                    "?, " +
                    "?, " +
                    "?, " +
                    "? " +  //    // NOTE: no comma after last one
                    ")";

    /**
     * Public no-arg ctor.
     *
     * Creates a table for this class if it doesn't exist
     */
    public TransactionData() {
        super();
        createTable();
    }

    /**
     * Fetches a transaction record from the table based on the transaction passed in.
     * @param transaction The transaction for which we wish to fetch it's persisted data.
     * @return The transaction from the database if it exists, null otherwise
     */
    public Transaction getRecord(Transaction transaction)
    {
        return getRecord(transaction.getId());
    }

    /**
     * Fetches a transaction record from the table based on the id passed in.
     * @param id The id of transaction for which we wish to fetch it's persisted data.
     * @return The transaction from the database if it exists, null otherwise
     */
    public Transaction getRecord(int id)
    {
        // Add the query parameters
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(id);

        return (Transaction) queryOne(GET_RECORD, params);
    }

    /**
     * Gets a list of all the transactions in the database.
     *
     * @return a list of all the transaction records in the database
     */
    public List<Transaction> getAllRecords()
    {
        ArrayList<Object> retList = (ArrayList<Object>) queryAll(GET_RECORDS, null);
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        for(Object obj : retList) {
            transactions.add((Transaction) obj);
        }

        return transactions;
    }

    /**
     * Fetches a list of all the transactions related to a specific account.
     * @param accountNumber The account number of the account to fetch it's transactions
     * @return a list of all the transaction records in the database relating to the account,
     *         or an empty list if no records exist.
     */
    public List<Transaction> getAllRecordsByAccountNum(Long accountNumber)
    {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(accountNumber);

        ArrayList<Object> retList = queryAll(GET_RECORDS_BY_NUMBER, params);
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();

        for(Object obj : retList) {
            transactions.add((Transaction) obj);
        }

        return transactions;
    }

    /**
     * Fetches a list of all the transactions related to a specific user.
     * @param username The username relating to the user to fetch it's transactions
     * @return a list of all the transaction records in the database relating to the user,
     *         or an empty list if no records exist.
     */
    public List<Transaction> getAllRecordsByUsername(String username)
    {
        UserData userData = new UserData();
        User user = userData.getUserByUsername(username);

        return getAllRecordsByUser(user);
    }

    /**
     * Fetches a list of all the transactions related to a specific user.
     * @param user The user to fetch it's transactions
     * @return a list of all the transaction records in the database relating to the user,
     *         or an empty list if no records exist.
     */
    public List<Transaction> getAllRecordsByUser(User user)
    {
        List<Account> accounts = (new AccountData()).getAccountList(user);
        List<Transaction> transactions = new ArrayList<Transaction>();

        // Populate the list with all the transactions across all accounts for a user
        for(Account account : accounts) {
            List<Transaction> accTrans = getAllRecordsByAccountNum(account.getNumber());

            transactions.addAll(accTrans);
        }

        return transactions;
    }

    /*************************************************************************
     *            			 		Create call						         *
     *************************************************************************/
    /**
     * Adds a new transaction to the database
     *
     * @param transaction The Transaction entity to insert into the db
     * @return true if the insert was successful, false otherwise
     */
    public boolean createTransaction(Transaction transaction)
    {
        String query = ADD_RECORD;
        ArrayList<Object> params = new ArrayList<Object>();

        params.add(transaction.getAmount());
        params.add(transaction.getTransaction());
        params.add(transaction.getAccount().getNumber());

        if(transaction.getOtherAccount() != null) {
            query = ADD_RECORD_W_OTHER;
            params.add(transaction.getOtherAccount().getNumber());
        }

        return createRecord(query, params) > 0;
    }

    /**
     * Creates a new table for transactions if it doesn't exist already.
     */
    @Override
    public void createTable() {
        makeTable(TABLE_SCHEMA);
    }

    /**
     * Builds a transaction record based on the results from a query.
     *
     * This method is needed so the abstract database class knows how to build a type of this record.
     *
     * @param results The results from a database query to use to build an entity
     * @return The Object of the type that we fetched from the database
     * @throws SQLException
     */
    @Override
    protected Object buildRecord(ResultSet results) throws SQLException
    {
        Transaction transaction = new Transaction();
        AccountData accountData = new AccountData();

        transaction.setId(results.getInt("id"));
        transaction.setAmount(results.getDouble("amount"));
        transaction.setDate(results.getDate("transactionDate"));
        Long accountNum = results.getLong("userAccountNum");
        transaction.setAccount(accountData.getAccountByNumber(accountNum));
        transaction.setTransactionType(results.getString("transactionType"));

        // Make sure to get another account only if there is one
        Long otherAccountNum = results.getLong("otherAccountNum");
        if(otherAccountNum != null && otherAccountNum > 0) {
            transaction.setOtherAccount(accountData.getAccountByNumber(otherAccountNum));
        }

        return transaction;
    }
}
