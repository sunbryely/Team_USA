/*
 * Filename: TransactionTest.java
 * Created by Team USA
 * Date: 11/28/14
 * Description: This file tests various transactions occuring between different  * accounts.
 */

package tests;

import model.*;


import java.util.Date;

import junit.framework.TestCase;

//import org.junit.*;

public class TransactionTest extends TestCase {

		public void testTransaction() throws Exception{
		        //Create the test accounts
			Account a = new Account();
			Account b = new Account();
			Date date = new Date();
			Transaction transactionDefault = new Transaction();
			Transaction transactionCustom = new Transaction(a, "Withdrawl", 100.0, date, b);
		
		
		    assertEquals(transactionDefault.getAmount(), 0.0);
		
			assertEquals(transactionCustom.getTransaction(), "Withdrawl");
		
			assertEquals(transactionCustom.getAccount(), a);
		
			assertEquals(transactionCustom.getAmount(), 100.0);
			
			assertEquals(transactionCustom.getDate(), date);
		
		}
		
	

}
