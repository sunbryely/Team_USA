/*
 * Filename: AccountTest.java
 * Created by Team USA
 * Date: 10/19/14
 * Description: This file is meant to test the account creation
 * process using a test account class.
 */

package tests;

import model.*;

import junit.framework.TestCase;

import java.util.Date;

//import static org.junit.Assert.*;

public class AccountTest extends TestCase {

    
    public void testAccount () throws Exception {

        //Setup Vars
        Account account = new Account();
        long number = 1234567;
        double balance = 100.0;
        Date date_created = new Date(2014, 1, 1);
        User owner = new User();
        Transaction trans = new Transaction();

        //Test 1: Test account creation
        //TODO: update the account test to match the new constructors
        account  =  new Account(number, balance, date_created, owner, "checking");
        //Test the parameters of the account
        assertEquals(account.getNumber(), 1234567);
        assertEquals(account.getBalance(), balance);
        assertEquals(account.getdateCreated(),date_created);
        assertEquals(account.getUser(), owner);

        //Test 2: Copy Constructor
        Account account2 = new Account(account);

        assertEquals(account2.getNumber(),number);
        assertEquals(account2.getBalance(), balance);
        assertEquals(account2.getdateCreated(),date_created);
        assertEquals(account2.getUser(), owner);


    }
}
