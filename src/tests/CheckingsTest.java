/*
 * Filename: CheckingsTest.java
 * Created by Team USA
 * Date: 10/21/14
 * Description: This file works in a similar manner to SavingsTest.java, but it
 * checks the settings for Checking Accounts instead.
 */

package tests;

import model.*;

import junit.framework.TestCase;

public class CheckingsTest extends TestCase {

 
    public void testCheckings() throws Exception
    {
        //test1: testing default constructor
        Checkings checkings = new Checkings();
        boolean ovf = checkings.getoverdraftProtection();

        assertTrue(ovf == false);

        //test2: testing parameterized constructor
        ovf = true;
        double firstBalance = 100.0;
        checkings = new Checkings(firstBalance, ovf);

//        assertEquals(checkings.getisCheckings(), isCheckings);
//        assertEquals(checkings.getoverdraftProtection(), ovf);
        assertEquals(checkings.getBalance(), firstBalance);

        //test3: testing copy constructor
        Checkings otherCheckings = new Checkings(checkings);

        assertEquals(checkings.getoverdraftProtection(), otherCheckings.getoverdraftProtection());
        assertEquals(checkings.getBalance(), otherCheckings.getBalance());

    }

}
