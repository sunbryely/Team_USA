/**
 * Filename: Database.java
 * Created by Team USA
 * Date: 11/3/14
 * Description:
 *      This file contains an abstract Database class that is meant to
 *      contain operations that are common for accessing the database.
 */

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Array;

/**
 * Parent class for all the database operations.
 *
 * It holds all the database info needed for accessing the database,
 * and handles the creation of a database if it doesn't exist.
 * It also contains the connection and prepared statements for
 * accessing database info.
 * 
 * @author Team USA
 */
public abstract class Database {
	protected Connection connection;  // The connection to the database

	// Database access, the child classes don't need this info
	private static final String DB_URL_PREFIX = "jdbc:mysql://";
	/* LIVE */
	private static final String DB_HOST = "104.131.153.77";
	private static final String DB_PORT = "3306";
	/* LOCAL */
	// private static final String DB_HOST = "localhost";
	// private static final String DB_PORT = "3306";
	
	// The database's name
	protected static final String DB_NAME = "teamusa";
	
	// The database account user name and password (for accessing the database)
	/* LIVE */
	protected static final String DB_USER = "dbConUser";
	protected static final String DB_PASS = "w31rdUn1c@rn5";
	/* LOCAL */
    // protected static final String DB_USER = "root";
    // protected static final String DB_PASS = "";

	// The JDBC Driver for our MySQL database
	protected static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	
	// The DB url is the url access for the database
	protected static final String DB_URL = DB_URL_PREFIX + DB_HOST + ":" +
								   		   DB_PORT + "/" + DB_NAME;
	
	/**
	 * Public no-arg ctor.
     *
	 * Creates a database if it doesn't exist.
	 */
	public Database() {
		createDb();
	}
	
	/**
	 * Close our prepared statements upon garbage collection.
	 * 
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() {
		this.close();
	}
	
	/**
	 * This method creates a new database using the database info declared at the top.
	 */
	protected void createDb()
    {
		Connection con = null;
		Statement stmt = null;
		
		// If the database doesn't exist we create a new database
		try {
			final String URL = DB_URL_PREFIX + DB_HOST + ":" + DB_PORT; // The connection URL
			
			Class.forName(DB_DRIVER);
			con = DriverManager.getConnection(
					URL, DB_USER, DB_PASS);
			
			stmt = con.createStatement();
			stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
		}
        catch (Exception e) {
			e.printStackTrace();
		}
		finally {
            // Make sure to close all the statements no matter what
			if( null != stmt ) {
				try {
					stmt.close();
				} catch( SQLException e) {
					e.printStackTrace();
				}
			}
			
			if( null != con ) {
				try {
					con.close();
				} catch( SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

    /**
     * Connects to a database if the database hasn't been connected to yet.
     */
    protected void connect()
    {
        // Only connect if the connection is closed
        if( isClosed() )
        {
            try {
                Class.forName(DB_DRIVER);
                connection = DriverManager.getConnection(
                        DB_URL, DB_USER, DB_PASS
                );
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks whether a connection is open for the given class
     *
     * @return whether the connection is closed or not
     */
    protected boolean isClosed()
    {
        if(null != connection) {
            try {
                return connection.isClosed();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * Closes this connection
     */
    public void close() {
        closeCon(connection);
    }

	/**
	 * Closes a result set preventing connection leaks.
	 * First checks if the result is null then closes accordingly.
	 * 
	 * @param results The results to close
	 */
	public void closeResult(ResultSet results)
	{
		if( null != results ) {
			try {
				results.close();
				results = null;
			}
            catch( SQLException e) {
				System.out.println("Error Closing results!");
				e.printStackTrace();
			}
		}
	}

    /**
     * Closes the statement, preventing connection leaks.
     *
     * WARNING you must first close all ResultSets that were created with
     * this prepared statement before closing the statement.
     *
     * @param stmt The statement to close
     */
    public void closeStmt(PreparedStatement stmt)
    {
        if( null != stmt ) {
            try {
                stmt.close();
                stmt = null;
            }
            catch( SQLException e) {
                System.out.println("Error Closing statement!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes a connection preventing connection leaks.
     *
     * WARNING you must close all PreparedStatements and ResultSets that
     * were created with this connection before closing the connection.
     *
     * @param con The connection to close
     */
	public void closeCon(Connection con) 
	{
		if( null != con ) {
			try {
				con.close();
				con = null;
			}
			catch( SQLException e ) {
				System.out.println("Error closing connection!");
				e.printStackTrace();
			}
		}
	}

    /**
     * Adds parameters of varying types to the prepared statement passed in
     *
     * Note this only handles parameters of types:
     *      - String
     *      - Integer
     *      - Float
     *      - Date
     *      - Long
     *      - Double
     *      - Boolean
     *      - Array
     *
     * @param prep The prepared statement to add the parameters to
     * @param parameters The parameters to add to the prepared statement
     * @return The same prepared statement that was passed in, but updated
     *         with the parameters added to it.
     * @throws SQLException
     */
    private PreparedStatement addParamsToQuery(PreparedStatement prep, ArrayList<Object> parameters) throws SQLException
    {
        if(parameters != null)
        {
            // Go through each parameter adding the appropriate type to the prepared statement
            for (int i = 0; i < parameters.size(); i++) {
                Object param = parameters.get(i);
                if (param instanceof String) {
                    prep.setString((i + 1), (String) param);
                }
                else if (param instanceof Integer) {
                    prep.setInt((i + 1), (Integer) param);
                }
                else if (param instanceof Float) {
                    prep.setFloat((i + 1), (Float) param);
                }
                else if (param instanceof Date) {
                    prep.setDate((i + 1), (Date) param);
                }
                else if (param instanceof Long) {
                    prep.setLong((i + 1), (Long) param);
                }
                else if (param instanceof Double) {
                    prep.setDouble((i + 1), (Double) param);
                }
                else if (param instanceof Boolean) {
                    prep.setBoolean((i + 1), (Boolean) param);
                }
                else if (param instanceof Array) {
                    prep.setArray((i + 1), (Array) param);
                }
                else {
                    prep.setObject((i + 1), param);
                }
            }
        }
        return prep;
    }

    /**
     * This query counts the number of rows in a given table.
     *
     * @param tableName The name of the table to count the rows in
     * @return The number of rows in a table
     */
    protected Long countQuery(String tableName)
    {
        connect();

        PreparedStatement prep = null;
        ResultSet results = null;
        Long retVal = null;

        try {
            prep = connection.prepareStatement("SELECT COUNT(*) FROM " + tableName);

            results = prep.executeQuery();

            if(results.next()) {
                retVal = results.getLong(1);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeResult(results);
            closeStmt(prep);
            close();
        }

        return retVal;
    }

    /**
     * Performs a SQL query on the database fetching either one object or a list of objects.
     *
     * @param queryString The SQL query to run on the database
     * @param parameters List of parameters to apply to the query string
     * @param fetchOne If this is true we only fetch one record, otherwise fetch all records
     *                 matching the query.
     * @return Either the one record or a list of all the records matching the SQL query.
     */
    private Object query(String queryString, ArrayList<Object> parameters, boolean fetchOne)
    {
        connect();

        Object returnObject = null;
        ArrayList<Object> returnList = null;
        PreparedStatement prep = null;
        ResultSet results = null;

        try
        {
            prep = connection.prepareStatement(queryString);

            // Set the parameters
            prep = addParamsToQuery(prep, parameters);

            // obtain user from db
            results = prep.executeQuery();

            if(fetchOne)
            {
                // We get the first row returned.
                // Don't loop over results cause we only want first record.
                if(results.next()) {
                    returnObject = buildRecord(results);
                }
            }
            else
            {
                // Get a list of all the objects matching the query
                // We loop over all the results building each record as we go
                returnList = new ArrayList<Object>();

                while(results.next()) {
                    Object record = buildRecord(results);
                    returnList.add(record);
                }
            }

        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            // Make sure to always close our connections to prevent connection leaks
            closeResult(results);
            closeStmt(prep);
            close();
        }

        // Return one object or a list of objects?
        if(fetchOne) {
            return returnObject;
        }
        else {
            return returnList;
        }
    }

    /**
     * Creates a new row in the database based on the SQL string passed in.
     *
     * @param SQL The creation SQL needed for creating the record
     * @param parameters Parameters associated with the SQL string.
     *
     * @return True if created successfully, false otherwise
     */
    public long createRecord(String SQL, ArrayList<Object> parameters)
    {
        connect();
        PreparedStatement prep = null;
        long auto_id = 0;

        try {
            prep = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

            // Set the parameters
            prep = addParamsToQuery(prep, parameters);

            // Make sure the record was added
            prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            rs.next();
            auto_id = rs.getLong(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            // Make sure to always close our connections to prevent connection leaks
            closeStmt(prep);
            close();
        }

        return auto_id;
    }

    /**
     * Updates a row in the database based on the SQL string passed in.
     *
     * @param SQL The editing SQL needed for creating the record
     * @param parameters Parameters associated with the SQL string.
     * @return True if edited successfully, false otherwise
     */
    public boolean editRecord(String SQL, ArrayList<Object> parameters)
    {
        connect();
        PreparedStatement prep = null;
        boolean isUpdated = false;

        try {
            prep = connection.prepareStatement(SQL);
            // Set the parameters
            prep = addParamsToQuery(prep, parameters);
            prep.executeUpdate();

            isUpdated = true;
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        finally {
            // Make sure to always close our connections to prevent connection leaks
            closeStmt(prep);
            close();
        }

        return isUpdated;
    }

    /**
     * Deletes a row in the database based on the SQL string passed in.
     *
     * @param SQL The deleting SQL needed for creating the record
     * @param parameters Parameters associated with the SQL string.
     * @return True if deleted successfully, false otherwise
     */
    public boolean deleteRecord(String SQL, ArrayList<Object> parameters)
    {
        connect();
        PreparedStatement prep = null;
        boolean isDeleted = false;

        try {
            prep = connection.prepareStatement(SQL);
            prep = addParamsToQuery(prep, parameters);
            prep.executeUpdate();

            isDeleted = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            // Make sure to always close our connections to prevent connection leaks
            closeStmt(prep);
            close();
        }

        return isDeleted;
    }

    /**
     * This allows us to run a SQL query on a database, and retrieve only one item from the database
     *
     * @param queryString The SQL query to run on the database
     * @param parameters A list of parameters to match with the SQL query
     *
     * @return The item returned from the database matching the SQL query.
     */
    public Object queryOne(String queryString, ArrayList<Object> parameters)
    {
        return query(queryString, parameters, true);
    }

    /**
     * This allows us to run a SQL query on a database, and retrieve all the items matching the
     * query from the database
     *
     * @param queryString The SQL query to run on the database
     * @param parameters A list of parameters to match with the SQL query
     * @return A list of items returned from the database matching the SQL query.
     */
    @SuppressWarnings("unchecked")
    public ArrayList<Object> queryAll(String queryString, ArrayList<Object> parameters)
    {
        return (ArrayList<Object>) query(queryString, parameters, false);
    }

    /**
     * This method creates a new table in the database according to the
     * schema passed in.
     *
     * @param tableSchema The schema to run to create a new table
     */
    public void makeTable(String tableSchema) {
        Connection con = null;
        Statement stmt = null;
        try {
            Class.forName(DB_DRIVER);

            // Connect to our main database
            con = DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASS
            );

            stmt = con.createStatement();
            stmt.executeUpdate(tableSchema);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Make sure to always close our connections to prevent connection leaks
            if (null != stmt) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (null != con) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

	//*** Method(s) that are meant to be overwritten in child classes. ***//
	
	/**
	 * Creates a table in the database for the given type of data.
	 */
	public abstract void createTable();

    /**
     * This method builds an object (by using the getters and setters) of the type retrieved from the database
     *
     * @return an object of the type we're trying to fetch
     */
    protected abstract Object buildRecord(ResultSet results) throws SQLException;

} // End class Database