/*
 * Filename: SavingsTest.java
 * Created by Team USA
 * Date: 10/19/14
 * Description: This file tests the Savings Account creation and functionality.
 */

package tests;

import junit.framework.TestCase;
import model.Savings;

//import static org.junit.Assert.*;

public class SavingsTest extends TestCase {

    
    public void testSavings() throws Exception
    {
        //test1: testing default constructor
        Savings savings = new Savings();
        double intRate = savings.getinterestRate();

        assertTrue(intRate == 0.0);

        //test2: testing parameterized constructor
        intRate = 6.0;
        double balance = 100.0;
        savings = new Savings(intRate, balance);
        //checking interest and starting balance
        assertEquals(savings.getinterestRate(), intRate);
        assertEquals(savings.getBalance(), balance);

        //test3: testing copy constructor
        Savings otherSavings = new Savings(savings);

        assertEquals(savings.getinterestRate(), otherSavings.getinterestRate());
        assertEquals(savings.getBalance(), otherSavings.getBalance());
    }
}
