/**
 * Filename: UserData.java
 * Created by Team USA
 * Date: 11/1/14
 * Description:
 *      This file contains the UserData class which is used for access to the
 *      database table(s) containing all the User records.
 */
package database;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The database access class for CRUD operations on User that's
 * stored in the database.
 * 
 * Also contains methods for accessing info pertaining to any User operations.
 * 
 * @author Team USA
 */
public class UserData extends Database {

	private static final String TABLE_NAME = "user";

    private static final String TABLE_SCHEMA =
            "CREATE TABLE IF NOT EXISTS " + DB_NAME + "." + TABLE_NAME + " " +
            "(" +
                "id int NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                "username VARCHAR(45), " +
                "firstName VARCHAR(45), " +
                "lastName VARCHAR(45), " +
                "password VARCHAR(255), " +
                "email VARCHAR(255), " +
                "phoneNumber VARCHAR(20), " +
                "address VARCHAR(255), " +
                "securityQuestion VARCHAR(255), " +
                "securityAnswer VARCHAR(255), " +
                "isAdmin BOOLEAN DEFAULT FALSE NOT NULL, " +
                "UNIQUE KEY (username) " + // NOTE: no comma after last one
            ");";

    private static final String GET_RECORD =
            "SELECT * FROM " + TABLE_NAME + " " +
             "WHERE username = ?;";

    private static final String GET_RECORD_UNAME_PASS =
            "SELECT * FROM " + TABLE_NAME + " " +
                    "WHERE " +
                        "username = ? " +
                    "AND " +
                        "password = ?;";

    private static final String GET_RECORDS =
            "SELECT * FROM " + TABLE_NAME;

    private static final String ADD_RECORD =
            "INSERT INTO " + TABLE_NAME + " " +
            "( " +
                "username, " +
                "firstName, " +
                "lastName, " +
                "password, " +
                "email, " +
                "phoneNumber, " +
                "address, " +
                "isAdmin, " +
                "securityQuestion, " +
                "securityAnswer " + // NOTE: no comma after last one
            ") " +
            "VALUES " +
            "( " +
                "?, " + //username          (param 1)
                "?, " + //firstName         (param 2)
                "?, " + //lastName          (param 3)
                "?, " + //password 	        (param 4)
                "?, " + //email             (param 5)
                "?, " + //phoneNumber       (param 6)
                "?, " + //address           (param 7)
                "?, " + //isAdmin           (param 8)
                "?, " + //securityQuestion  (param 9)
                "? " +  //securityAnswer    (param 10) // NOTE: no comma after last one
            ")";

    private static final String EDIT_RECORD =
            "UPDATE " + TABLE_NAME + " " +
            "SET " +
                "username = ?, " +  		// param 1
                "firstName = ?, " +     	// param 2
                "lastName = ?, " + 		    // param 3
                "password = ?, " + 		    // param 4
                "email = ?, " +             // param 5
                "phoneNumber = ?, " +       // param 6
                "address = ?, " +           // param 7
                "isAdmin = ?, " +           // param 8
                "securityQuestion = ? ," +  // param 9
                "securityAnswer = ? " +     // param 10  // NOTE: no comma after last one
            "WHERE username = ?;";		    // param 11

    private static final String DELETE_RECORD =
            "DELETE FROM " + TABLE_NAME + " " +
            "WHERE username = ?;";

    /**
     * Public ctor, handles creation of a table and initialization of
     * prepared statements.
     */
    public UserData()
    {
        super();
        createTable();
    }

    /**
     * Fetches a user from the database based on the username passed in and the
     * encryptedPassword (md5 hash of the plaintext password) passed in.
     *
     * This method is good for validating a user for login.
     *
     * @param username The username of the user to fetch
     * @param encryptedPassword The md5 hash of the password related to the user to
     *                          fetch. Note that passwords are stored as their encrypted
     *                          versions in the database so this is used for that comparison.
     * @return The User if it was found, null otherwise.
     */
    public User getUserByUsernameAndPassword(String username, String encryptedPassword)
    {
        // Add the query parameters
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(username);
        params.add(encryptedPassword);

        return (User) queryOne(GET_RECORD_UNAME_PASS, params);
    }

    /**
     * Fetches a user from the database based on the username passed in
     * @param username The username of the user to fetch
     * @return The User if it was found, null otherwise.
     */
	public User getUserByUsername(String username)
	{
        // Add the query parameters
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(username);

        return (User) queryOne(GET_RECORD, params);
	}

    /**
     * Fetches a user from the database based on the username of the User object passed in
     * @param user The user containing the username of the user to fetch
     * @return The User if it was found, null otherwise.
     */
	public User getUser(User user)
	{
		return getUserByUsername(user.getUsername());
	}

	/**
	 * @return list of all users in the database
	 */
	public List<User> getUserList()
	{
		ArrayList<Object> retList = (ArrayList<Object>) queryAll(GET_RECORDS, null);
        ArrayList<User> userList = new ArrayList<User>();

        for(Object obj : retList) {
            userList.add((User) obj);
        }

		return userList;
	}

    /*************************************************************************
     *            			 		Create call						         *
     *************************************************************************/
    /**
     * Adds a new user to the database
     *
     * @param user The User entity to insert into the db
     * @return true if the insert was successful, false otherwise
     */
    public boolean createUser(User user)
    {
        ArrayList<Object> params = new ArrayList<Object>();

        params.add(user.getUsername());
        params.add(user.getFirstname());
        params.add(user.getLastname());
        params.add(user.getPassword());
        params.add(user.getEmail());
        params.add(user.getPhone());
        params.add(user.getAddress());
        params.add(user.getisAdmin());
        params.add(user.getSecurityQuestion());
        params.add(user.getSecurityAnswer());

        return createRecord(ADD_RECORD, params) > 0;
    }

    /*************************************************************************
     *            			 		Edit calls						         *
     *************************************************************************/
    /**
     * Updates a user record in the database.
     *
     * @param user The record to update (note that the username must have been
     * 			   set for this user).
     * @return true if the update was successful, false otherwise
     */
    public boolean editUser(User user)
    {
        ArrayList<Object> params = new ArrayList<Object>();

        params.add(user.getUsername());
        params.add(user.getFirstname());
        params.add(user.getLastname());
        params.add(user.getPassword());
        params.add(user.getEmail());
        params.add(user.getPhone());
        params.add(user.getAddress());
        params.add(user.getisAdmin());
        params.add(user.getSecurityQuestion());
        params.add(user.getSecurityAnswer());

        params.add(user.getUsername()); // The identifying param

        return editRecord(EDIT_RECORD, params);
    }

    /**
     * Deletes a user record from the database.
     * <br><br>
     * <strong>WARNING</strong> this action can't be undone,
     * only call when necessary.
     *
     * @param username the username of the user to delete
     */
    public void deleteUser(String username)
    {
        ArrayList<Object> params = new ArrayList<Object>();
        params.add(username);

        deleteRecord(DELETE_RECORD, params);
    }

    public void deleteUser(User user)
    {
        deleteUser(user.getUsername());
    }

    /*************************************************************************
     * 								Helper methods							 *
     *************************************************************************/
    /**
     * This method builds a User entity using data from the database.
     *
     * @param results The results received from the database query call.
     * @return A new User entity built from the results set.
     * @throws SQLException
     */
    protected User buildRecord(ResultSet results) throws SQLException
    {
        User user = new User();

        user.setUsername( 	    results.getString("username") );
        user.setFirstname( 	    results.getString("firstName") );
        user.setLastname( 	    results.getString("lastName") );
        user.setPassword( 	    results.getString("password") );
        user.setEmail(          results.getString("email") );
        user.setPhone(          results.getString("phoneNumber") );
        user.setAddress(        results.getString("address") );
        user.setisAdmin(        results.getBoolean("isAdmin") );
        user.setSecurityQuestion( results.getString("securityQuestion") );
        user.setSecurityAnswer(  results.getString("securityAnswer") );

        return user;
    }

    /*************************************************************************
     *            			 	Table creation call					     	 *
     *************************************************************************/
    /**
     * Creates the user table if it doesn't exist
     */
    @Override
    public void createTable() {
        makeTable(TABLE_SCHEMA);
    }

}
