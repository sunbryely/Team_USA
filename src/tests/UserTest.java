/*
 * Filename: UserTest.java
 * Created by Team USA
 * Date: 10/18/14
 * Description: This test file check all of the settings for both
 * normal end user and teller accounts.
 */

package tests;

import model.*;

//import static org.junit.Assert.assertEquals;
//import User;
import junit.framework.TestCase;

public class UserTest extends TestCase {

	
	public void testUser() throws Exception
		
		{
	        String firstName = "jon";
	        String lastName = "natale";
	        String password = "123456";
	        String login = "jnatale";
	        String phone = "123456789";
	        String email = "jn@gmail.com";
	        String address = "123 st";


	        //test1
	        User user = new User( firstName,  lastName,  password,  login,
	                 phone,  email,  address);
	        assertEquals(user.getAddress(), address);
	        assertEquals(user.getEmail(),email);
	        assertEquals(user.getPhone(),phone);
	        assertEquals(user.getPassword(),password);
	        assertEquals(user.getLastname(),lastName);
	        assertEquals(user.getFirstname(),firstName);

	        //test2
	        boolean isAdmin = false;

	        user = new User( firstName,  lastName,  password,  login,
	                phone,  email,  address, isAdmin);
	        assertEquals(user.getAddress(), address);
	        assertEquals(user.getEmail(),email);
	        assertEquals(user.getPhone(),phone);
	        assertEquals(user.getPassword(),password);
	        assertEquals(user.getLastname(),lastName);
	        assertEquals(user.getFirstname(),firstName);
	        assertEquals(user.getisAdmin(), isAdmin);

	        //test3: testing copy constructor
	        User user2 = new User( user);

	        user2.setisAdmin(true);//also testing isAdmin setter in this test

	        assertEquals(user2.getAddress(), user.getAddress());
	        assertEquals(user2.getEmail(), user.getEmail());
	        assertEquals(user2.getPhone(), user.getPhone());
	        assertEquals(user2.getPassword(), user.getPassword());
	        assertEquals(user2.getLastname(),user.getLastname());
	        assertEquals(user2.getFirstname(),user.getFirstname());
	        assertEquals(user2.getisAdmin(), true);
	
}}
