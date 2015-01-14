/*Class that creates a savings account for a user. Called by 
 *AccountCreationController. Inherits from Account class.
 *Saves the savings account in the database.
 *@author: Team-USA Date: 10/12/2014
 */
package model;



import java.util.Date;


public class Savings extends Account {
	private double interestRate; //interest rate for account.

	private static final String ACCOUNT_TYPE = "savings";
	public static final double DEFAULT_INTEREST_RATE = 0.00; // %0
	public static final double INTEREST_RATE_1000_TO_2000 = 0.02; // %2
	public static final double INTEREST_RATE_2000_TO_3000 = 0.03; // %3
	public static final double INTEREST_RATE_OVER_3000 = 0.04; // %4

	public Savings(){
		super(ACCOUNT_TYPE);
		this.interestRate = DEFAULT_INTEREST_RATE;
	}
	public Savings(Account account) {
		this(account.getNumber(), account.getBalance(), account.getdateCreated(), account.getUser(), account.getinterestRate());
	}

	public Savings(User user)
	{
		super(user, ACCOUNT_TYPE);
		this.interestRate = DEFAULT_INTEREST_RATE;
	}
	
	public Savings(double intRate, double balance)
	{
		super(ACCOUNT_TYPE);
		this.interestRate = intRate;
		this.balance = balance;
	}

	public Savings(User user, double intRate, double balance)
	{
		super(user, ACCOUNT_TYPE);
		this.interestRate = intRate;
		this.balance = balance;
	}

	public Savings(long accountNum, double accountBal, Date dateMade, User user, double intRate)
	{
		super(accountNum, accountBal, dateMade, user, ACCOUNT_TYPE);
		this.interestRate = intRate;
	}
	
	public Savings(Savings aSavingsAccount){
		this(aSavingsAccount.getNumber(),
			 aSavingsAccount.getBalance(),
			 aSavingsAccount.getdateCreated(),
			 aSavingsAccount.getUser(),
			 aSavingsAccount.getinterestRate());
	}

	@Override
	public void setInterestRate()
	{
		if(balance < 1000.0 && interestRate != DEFAULT_INTEREST_RATE)
		{
			interestRate = DEFAULT_INTEREST_RATE;
			interestPeriod = new Date();
		}
		else if(balance >= 1000.0 && balance < 2000.0 && interestRate != Savings.INTEREST_RATE_1000_TO_2000)
		{
			interestRate = Savings.INTEREST_RATE_1000_TO_2000;
			interestPeriod = new Date();
		}
		else if(balance >= 2000.0 && balance < 3000.0 && interestRate != Savings.INTEREST_RATE_2000_TO_3000)
		{
			interestRate = Savings.INTEREST_RATE_2000_TO_3000;
			interestPeriod = new Date();
		}
		else if(balance >= 3000.0 && interestRate != Savings.INTEREST_RATE_OVER_3000)
		{
			interestRate = Savings.INTEREST_RATE_OVER_3000;
			interestPeriod = new Date();
		}
	}
}
